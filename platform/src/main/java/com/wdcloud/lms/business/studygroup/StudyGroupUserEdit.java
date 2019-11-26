package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.StudyGroupService;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.RandomUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupDelete;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_STUDY_GROUP_USER)
public class StudyGroupUserEdit implements IDataEditComponent {
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupService studyGroupService;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private CourseUserDao courseUserDao;

    /**
     * @api {post} /studyGroupUser/add 学习小组学生添加
     * @apiName studyGroupUserAdd
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} userId 学生ID
     * @apiParam {Number} studyGroupId 学习小组
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 用户ID
     */
    @ValidationParam(clazz = StudyGroupUserEditVo.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        StudyGroupUser groupUser = JSON.parseObject(dataEditInfo.beanJson, StudyGroupUser.class);

        Long userId = groupUser.getUserId();
        Long studyGroupId = groupUser.getStudyGroupId();

        StudyGroup studyGroup = studyGroupDao.get(studyGroupId);
        if (studyGroup == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_STUDY_GROUP_ID, studyGroupId);
        }

        if (!courseUserDao.hasUser(studyGroup.getCourseId(), userId)) {
            throw new PropValueUnRegistryException(Constants.PARAM_USER_ID, userId);
        }

        StudyGroupSet studyGroupSet = studyGroupSetDao.get(studyGroup.getStudyGroupSetId());
        boolean isStudentGroupSet = Objects.equals(studyGroupSet.getIsStudentGroup(), Status.YES.getStatus());
        if (isStudentGroupSet && studyGroupUserDao.hasUser(studyGroupId, userId)
                || !isStudentGroupSet && studyGroupUserDao.hasAddToGroup(studyGroup.getStudyGroupSetId(), userId)) {
            throw new BaseException("user.exists.in.group");
        }

        // todo 判断用户是否在同一班级（用户可以属于多个班级）

        groupUser.setStudyGroupSetId(studyGroup.getStudyGroupSetId());
        groupUser.setCourseId(studyGroup.getCourseId());
        //根据具体条件更新小组长和小组用户
        studyGroupService.UpdateLeaderAndGroupUser(studyGroupSet.getLeaderSetStrategy(), studyGroup, groupUser);

        return new LinkedInfo(String.valueOf(groupUser.getUserId()));
    }

    /**
     * @api {post} /studyGroupUser/deletes 学习小组学生移除
     * @apiName studyGroupUserDelete
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} studyGroupId 学习小组
     * @apiParam {Number} [userId] 学生ID，参数没有时表示从小组中移除自己
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 用户ID
     */
    @ValidationParam(clazz = StudyGroupUserEditVo.class, groups = GroupDelete.class)
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        StudyGroupUserEditVo groupUserEditVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupUserEditVo.class);

        StudyGroupUser groupUser = StudyGroupUser.builder().studyGroupId(groupUserEditVo.getStudyGroupId())
                .userId(groupUserEditVo.getUserId() == null ? WebContext.getUserId() : groupUserEditVo.getUserId())
                .build();

        groupUser = studyGroupUserDao.findOne(groupUser);

        //如果是小组长，则学习小组的leader置为null
        studyGroupService.updateLeaderINulldByIsLeader(groupUser, groupUserEditVo.getStudyGroupId());
        studyGroupUserDao.delete(groupUser.getId());

        return new LinkedInfo(String.valueOf(groupUserEditVo.getUserId()));
    }


    @Data
    public static class StudyGroupUserEditVo extends StudyGroupUser {

        @Null(groups = {GroupAdd.class})
        @Override
        public Long getStudyGroupSetId() {
            return super.getStudyGroupSetId();
        }

        @NotNull(groups = {GroupAdd.class, GroupDelete.class})
        @Override
        public Long getStudyGroupId() {
            return super.getStudyGroupId();
        }
    }

}
