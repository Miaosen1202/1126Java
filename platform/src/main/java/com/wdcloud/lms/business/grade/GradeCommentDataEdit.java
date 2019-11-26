package com.wdcloud.lms.business.grade;


import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.enums.*;
import com.wdcloud.lms.business.grade.vo.GradeCommentEditVo;
import com.wdcloud.lms.business.grade.vo.GradeEditVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.alibaba.fastjson.JSON;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author zhangxutao
 * 提交评分信息
 **/
@ResourceInfo(name = Constants.RESOURCE_TYPE_Graded_Comment_Edit)
public class GradeCommentDataEdit implements IDataEditComponent {

    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private GradeCommentService gradeCommentService;

    /**
     * @api {post} /gradedCommentEdit/add 添加单一评分评论内容信息
     * @apiDescription 添加单一评分评论内容信息
     * @apiName gradedCommentEdit
     * @apiGroup GradeGroup
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} assignmentGroupItemId 作业组item
     * @apiParam {Number} originType　任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiParam {Number} originId　来源ID
     * @apiParam {Number} studyGroupId  小组ID
     * @apiParam {String} content　评论内容
     * @apiParam {String} [isSendGroupUser]　小组作业时，评论是否组成员可见
     * @apiParam {Number} releaseType 个人或小组类型
     * @apiParam {Number} gradeType 单一或批量 1：单一 2：批量
     * @apiParam {Number} userId　评论用户
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 评分内容
     */

    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        GradeCommentEditVo gradeCommentEditVo = JSON.parseObject(dataEditInfo.beanJson, GradeCommentEditVo.class);
        if (gradeCommentEditVo.getReleaseType().equals(ReleaseTypeEnum.GROUP.getValue())) {
            addGradeCommentInfo(gradeCommentEditVo);
        }
        if (gradeCommentEditVo.getReleaseType().equals(ReleaseTypeEnum.USER.getValue())) {
            Long userId = gradeCommentEditVo.getUserId();
            addGradeComments(gradeCommentEditVo, userId);
        }
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * 获取提交人员进行评分-小组人员包括自然人；还有个人【包括所有人】
     *
     * @param gradeCommentEditVo
     */
    private void addGradeCommentInfo(GradeCommentEditVo gradeCommentEditVo) {
        if (gradeCommentEditVo.getUserType().equals(UserTypeEnum.All.getValue())) {
            /**
             * 先查询提交表中带小组的 取重复-未评分
             */
            List<Map<String, Object>> notGradeStudyGroupUserSubmitList = userSubmitRecordDao.
                    getNotGradeStudyGroupUserSubmit(
                            gradeCommentEditVo.getOriginType(),
                            gradeCommentEditVo.getOriginId());
            if (ListUtils.isNotEmpty(notGradeStudyGroupUserSubmitList)) {
                for (Map<String, Object> item : notGradeStudyGroupUserSubmitList) {
                    Long studyGroupId = Long.parseLong(item.get("study_group_id").toString());
                    addStudyUserGradeComment(gradeCommentEditVo, studyGroupId);
                }
            }
            /**
             * 在去查提交表中组为空的-是否有自然人-未已评分
             */
            List<Map<String, Object>> userList = userSubmitRecordDao.getNotStudyGroupUserSubmit(
                    gradeCommentEditVo.getOriginType(),
                    gradeCommentEditVo.getOriginId(),
                    gradeCommentEditVo.getCourseId());
            if (ListUtils.isNotEmpty(userList)) {
                for (Map<String, Object> userItem : userList) {
                    addGradeComments(gradeCommentEditVo, Long.parseLong(userItem.get("user_id").toString()));
                }
            }
        }
        if (gradeCommentEditVo.getUserType().equals(UserTypeEnum.Yes.getValue())) {
            addStudyUserGradeComment(gradeCommentEditVo, gradeCommentEditVo.getStudyGroupId());
        }
        if (gradeCommentEditVo.getUserType().equals(UserTypeEnum.No.getValue())) {
            addGradeComments(gradeCommentEditVo, gradeCommentEditVo.getUserId());
        }

    }

    /**
     * 查询小组内人员-提交评论
     **/
    private void addStudyUserGradeComment(GradeCommentEditVo gradeCommentEditVo, Long studyGroupId) {
        List<StudyGroupUser> groupUserList = studyGroupUserDao.find(
                StudyGroupUser.builder()
                        .studyGroupId(studyGroupId)
                        .build());
        if (ListUtils.isNotEmpty(groupUserList)) {
            for (StudyGroupUser studyGroupUser : groupUserList) {
                Long userId = studyGroupUser.getUserId();
                addGradeComments(gradeCommentEditVo, userId);
            }
        }
    }

    /**
     * 组装数据添加评论
     * @param gradeCommentEditVo
     * @param userId
     */
    private void addGradeComments(GradeCommentEditVo gradeCommentEditVo, Long userId) {
        GradeEditVo gradeEditVo = new GradeEditVo();
        gradeEditVo.setAssignmentGroupItemId(gradeCommentEditVo.getAssignmentGroupItemId());
        gradeEditVo.setOriginId(gradeCommentEditVo.getOriginId());
        gradeEditVo.setOriginType(gradeCommentEditVo.getOriginType());
        gradeEditVo.setCourseId(gradeCommentEditVo.getCourseId());
        gradeEditVo.setContent(gradeCommentEditVo.getContent());
        gradeEditVo.setReleaseType(gradeCommentEditVo.getReleaseType());
        gradeEditVo.setIsSendGroupUser(gradeCommentEditVo.getIsSendGroupUser());
        gradeCommentService.addGradeCommentInfo(gradeEditVo, userId);
    }
}