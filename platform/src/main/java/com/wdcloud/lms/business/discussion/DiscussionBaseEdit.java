package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.dao.DiscussionReplyDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.DiscussionReply;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 讨论基本信息设置 评论开关|发布开关|置顶开关
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_DISCUSSION,
        functionName = Constants.FUNCTION_TYPE_COURSE_STATUS_TOGGLE
)
public class DiscussionBaseEdit implements ISelfDefinedEdit {
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    /**
     * @api {post} /discussion/statusToggle/edit 讨论的 评论开关|发布开关|置顶开关
     * @apiName discussion 讨论的 评论开关|发布开关|置顶开关| 设置
     * @apiGroup Discussion
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} id 讨论ID
     * @apiParam {Number=0,1} [allowComment]  评论开关
     * @apiParam {Number=0,1} [status] 发布状态
     * @apiParam {Number=0,1} [isPin]  置顶
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 讨论ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        Discussion param = JSON.parseObject(dataEditInfo.beanJson, Discussion.class);
        if (param.getStatus()!=null&&param.getStatus()==0) {
            int count=discussionReplyDao.count(DiscussionReply.builder().discussionId(param.getId()).roleId(RoleEnum.STUDENT.getType()).isDeleted(0).build());
            if (count>0){
                throw new BaseException("op.unpublish.has.commit.error");
            }
        }
        discussionDao.update(param);
        return new LinkedInfo(String.valueOf(param.getId()));
    }
}
