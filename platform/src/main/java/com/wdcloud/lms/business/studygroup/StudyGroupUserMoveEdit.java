package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.StudyGroupService;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.exceptions.OperateRejectedException;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP_USER,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class StudyGroupUserMoveEdit implements ISelfDefinedEdit {
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupService studyGroupService;

    /**
     * @api {post} /studyGroupUser/move/edit 移动小组用户
     * @apiName studyGroupUserUserMove
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} [userId] 移动用户，参数没有时表示操作自己
     * @apiParam {Number} targetGroupId 移动目标小组
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 用户ID
     */
    @ValidationParam(clazz = StudyGroupUserMoveVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        StudyGroupUserMoveVo moveVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupUserMoveVo.class);
        StudyGroup targetGroup = studyGroupDao.get(moveVo.getTargetGroupId());

        Long userId = moveVo.getUserId() == null ? WebContext.getUserId() : moveVo.getUserId();
        if (targetGroup == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_TARGET_GROUP_ID, moveVo.getTargetGroupId());
        }

        StudyGroupUser user = studyGroupUserDao.findOne(StudyGroupUser.builder()
                .userId(moveVo.getUserId())
                .studyGroupSetId(targetGroup.getStudyGroupSetId())
                .build());
        if (user == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_USER_ID, userId);
        }

        if (!Objects.equals(user.getStudyGroupId(), targetGroup.getId())) {

            //如果是小组长，则学习小组的leader置为null
            studyGroupService.updateLeaderINulldByIsLeader(user, user.getStudyGroupId());
            user.setIsLeader(Status.NO.getStatus());
            user.setStudyGroupId(targetGroup.getId());
            StudyGroupSet studyGroupSet = studyGroupSetDao.get(targetGroup.getStudyGroupSetId());

            //根据具体条件更新小组长和小组用户
            studyGroupService.UpdateLeaderAndGroupUser(studyGroupSet.getLeaderSetStrategy(), targetGroup, user);
        }


        return new LinkedInfo(String.valueOf(user.getUserId()));
    }

    @Data
    public static class StudyGroupUserMoveVo {

        private Long userId;

        @NotNull
        private Long targetGroupId;
    }
}
