package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.discussion.dto.DiscussionConfigDTO;
import com.wdcloud.lms.core.base.dao.DiscussionConfigDao;
import com.wdcloud.lms.core.base.model.DiscussionConfig;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("JavaDoc")
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_DISCUSSION_CONFIG)
public class DiscussionConfigDataEdit implements IDataEditComponent {
    @Autowired
    private DiscussionConfigDao discussionConfigDao;


    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        return null;
    }


    /**
     * @api {post} /discussionConfig/modify 讨论设置-学生设置
     * @apiName discussionConfigModify讨论配置修改
     * @apiGroup Discussion
     * @apiParam {Number} id 讨论配置ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1} [allowStudentCreateDiscussion] 学生可创建讨论
     * @apiParam {Number=0,1} [allowStudentEditDiscussion] 学生可编辑/删除自己话题和评论
     * @apiParam {Number=0,1} [allowDiscussionAttachFile] 允许学生讨论上传附件
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 课程ID
     */
    @Override
    @ValidationParam(clazz = DiscussionConfigDTO.class,groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        DiscussionConfigDTO dto = JSON.parseObject(dataEditInfo.beanJson, DiscussionConfigDTO.class);
        dto.setUserId(WebContext.getUserId());
        //1.更新讨论配置基本表
        discussionConfigDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getCourseId()));
    }


    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        return null;
    }
}
