package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP_USER,
        functionName = Constants.FUNCTION_TYPE_LEADER
)
public class StudyGroupUserLeaderEdit implements ISelfDefinedEdit {
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private StudyGroupDao studyGroupDao;

    /**
     * @api {post} /studyGroupUser/leader/edit 学习小组组长设置
     * @apiName studyGroupUserLeaderEdit
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} [leaderId] Leader用户ID，为空时移除小组Leader
     * @apiParam {Number} studyGroupId 学习小组
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 用户ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        StudyGroupLeaderVo studyGroupLeaderVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupLeaderVo.class);
        Long leaderId = studyGroupLeaderVo.getLeaderId();

        StudyGroup studyGroup = studyGroupDao.get(studyGroupLeaderVo.getStudyGroupId());
        if (studyGroup == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_STUDY_GROUP_ID, studyGroupLeaderVo.getStudyGroupId());
        }
        if (leaderId == null) {
            // 移除组长
            studyGroup.setLeaderId(null);
            studyGroupDao.updateIncludeNull(studyGroup);

            studyGroupUserDao.setLeader(studyGroup.getId(), studyGroupLeaderVo.getLeaderId(), Status.NO);
        } else {
            // 设置新组长
            if (!studyGroupUserDao.hasUser(studyGroup.getId(), studyGroupLeaderVo.getLeaderId())) {
                throw new PropValueUnRegistryException(Constants.PARAM_LEADER_ID, studyGroupLeaderVo.getLeaderId());
            }

            studyGroupUserDao.cancelLeader(studyGroup.getId());
            studyGroupUserDao.setLeader(studyGroup.getId(), studyGroupLeaderVo.getLeaderId(), Status.YES);

            studyGroup.setLeaderId(leaderId);
            studyGroupDao.update(studyGroup);

        }

        return new LinkedInfo(String.valueOf(leaderId));
    }

    @Data
    public static class StudyGroupLeaderVo {
        private Long leaderId;
        @NotNull
        private Long studyGroupId;
    }
}
