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
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP,
        functionName = Constants.FUNCTION_TYPE_JOIN
)
public class StudyGroupJoinEdit implements ISelfDefinedEdit {

    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupService studyGroupService;

    /**
     * @api {post} /studyGroup/join/edit 用户加入小组
     * @apiDescription 用户加入小组
     * @apiName JoinStudyGroup
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} id 小组id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 加入小组人员的id
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        StudyGroup studyGroup = JSON.parseObject(dataEditInfo.beanJson, StudyGroup.class);

        studyGroup = studyGroupDao.get(studyGroup.getId());
        if (studyGroup == null) {
            throw new ParamErrorException();
        }

        Long userId = WebContext.getUserId();
        if (Objects.equals(studyGroup.getIsStudentGroup(), Status.YES.getStatus())) {
            StudyGroupUser studyGroupUser = studyGroupUserDao.findOne(StudyGroupUser.builder().studyGroupId(studyGroup.getId()).userId(userId).build());
            if (studyGroupUser == null) {
                studyGroupUser = StudyGroupUser.builder()
                        .courseId(studyGroup.getCourseId())
                        .studyGroupSetId(studyGroup.getStudyGroupSetId())
                        .studyGroupId(studyGroup.getId())
                        .userId(userId)
                        .isLeader(Status.NO.getStatus())
                        .build();
                studyGroupUserDao.save(studyGroupUser);
            }
        } else {
            StudyGroupUser studyGroupUser = studyGroupUserDao.findOne(StudyGroupUser.builder().studyGroupSetId(studyGroup.getStudyGroupSetId()).userId(userId).build());
            if (studyGroupUser != null) {
                //如果是小组长，则学习小组的leader置为null
                studyGroupService.updateLeaderINulldByIsLeader(studyGroupUser, studyGroupUser.getStudyGroupId());

                studyGroupUser.setStudyGroupId(studyGroup.getId());
                studyGroupUser.setIsLeader(Status.NO.getStatus());
            } else {
                studyGroupUser = StudyGroupUser.builder()
                        .courseId(studyGroup.getCourseId())
                        .studyGroupSetId(studyGroup.getStudyGroupSetId())
                        .studyGroupId(studyGroup.getId())
                        .userId(userId)
                        .isLeader(Status.NO.getStatus())
                        .build();
            }

            StudyGroupSet studyGroupSet = studyGroupSetDao.get(studyGroup.getStudyGroupSetId());
            //根据具体条件更新小组长和小组用户
            studyGroupService.UpdateLeaderAndGroupUser(studyGroupSet.getLeaderSetStrategy(), studyGroup, studyGroupUser);
        }

        return new LinkedInfo(userId + "");
    }
}
