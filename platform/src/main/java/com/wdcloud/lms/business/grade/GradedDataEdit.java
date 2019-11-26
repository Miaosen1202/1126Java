package com.wdcloud.lms.business.grade;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.grade.enums.*;
import com.wdcloud.lms.business.grade.vo.GradeEditVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author zhangxutao
 * 提交评分信息
 **/
@ResourceInfo(name = Constants.RESOURCE_TYPE_Graded_Edit)
public class GradedDataEdit implements IDataEditComponent {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private GradeCommentService gradeCommentService;


    /**
     * @api {post} /gradedEdit/add 添加评分信息
     * @apiDescription 添加评分信息
     * @apiName gradedEdit
     * @apiGroup Grade2.0
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} assignmentGroupItemId 作业组item
     * @apiParam {Number} originType　任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiParam {Number} originId　来源ID
     * @apiParam {Number} studyGroupId  小组ID
     * @apiParam {String} content　评论内容
     * @apiParam {Number} userId　评论用户
     * @apiParam {String} isSendGroupUser　小组作业时，评论是否组成员可见
     * @apiParam {String} score 总分
     * @apiParam {String} gradeScore 得分
     * @apiParam {Number} graderId 评分用户
     * @apiParam {Number} gradeType 单一或批量 1：单一 2：批量
     * @apiParam {Number} releaseType 个人或小组类型 1:个人 2小组
     * @apiParam {Number} isOverWrite 是否覆盖之前的评分 0不覆盖  1覆盖
     * @apiParam {Number} isSetLowestScore 设置最低评分 0不设置  1设置
     * @apiParam {Number} isSetLowestScore 设置最低评分 0不设置  1设置
     * @apiParam {Number=1,2,3,4,5} showScoreType  评分方式 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分'【新增】
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 评分
     */

    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        GradeEditVo gradeEditVo = JSON.parseObject(dataEditInfo.beanJson, GradeEditVo.class);
        gradeEditVo.setGradeScore(gradeEditVo.getGradeScore().split("\\.")[0]);
        /**
         * 评论打分跟个人打分 gradeType 单一或批量 1：单一 2：批量
         */
        if (GradeTypeEnum.Batch.getValue().equals(gradeEditVo.getGradeType())) {
            /**
             * 批量评分-个人批量评分
             */
            if (ReleaseTypeEnum.USER.getValue().equals(gradeEditVo.getReleaseType())) {
                /**
                 * 覆盖评分
                 */
                if (gradeEditVo.getIsOverWrite().equals(Status.YES.getStatus())) {
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.No);
                    updateGradeInfo(gradeEditVo, StudyGroupSetEnum.No);
                }
                /**
                 * 正常评分
                 */
                else if (gradeEditVo.getIsOverWrite().equals(Status.NO.getStatus())) {
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.No);
                }
                /**
                 * 设置最低分
                 */
                if (gradeEditVo.getIsSetLowestScore().equals(SetLowestScoreEnum.Set.getValue())) {
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.No);
                    updateGradeLowestScore(gradeEditVo);
                }
            }
            /**
             * 批量评分-小组批量评分
             */
            else if (ReleaseTypeEnum.GROUP.getValue().equals(gradeEditVo.getReleaseType())) {
                /**
                 * 覆盖评分
                 */
                if (gradeEditVo.getIsOverWrite().equals(Status.YES.getStatus())) {
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.Yes);
                    updateGradeInfo(gradeEditVo, StudyGroupSetEnum.Yes);
                } else {
                    /**
                     * 正常评分
                     */
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.Yes);
                }
                /**
                 * 自然人And覆盖评分
                 */
                if (gradeEditVo.getStudyGroupId().equals(Status.NO.getStatus()) &&
                        gradeEditVo.getIsSetLowestScore().equals(SetLowestScoreEnum.Set.getValue())) {
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.Yes);
                    updateGradeLowestScore(gradeEditVo);
                }
                /**
                 * 设置最低分
                 */
                if (gradeEditVo.getIsSetLowestScore().equals(SetLowestScoreEnum.Set.getValue())) {
                    addGradeInfo(gradeEditVo, StudyGroupSetEnum.No);
                    updateGradeLowestScore(gradeEditVo);
                }
            }
        }
        /**
         * 单一评分
         */
        if (GradeTypeEnum.One.getValue().equals(gradeEditVo.getGradeType())) {
            if (ReleaseTypeEnum.USER.getValue().equals(gradeEditVo.getReleaseType())) {
                addGradeComment(gradeEditVo, gradeEditVo.getUserId());
            }
            else if (ReleaseTypeEnum.GROUP.getValue().equals(gradeEditVo.getReleaseType())) {
                addGradeInfo(gradeEditVo, StudyGroupSetEnum.Yes);
            }
        }
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * 获取提交人员进行评分-小组人员包括自然人；还有个人【包括所有人】
     *
     * @param gradeEditVo
     * @param studyGroupSetEnum
     */
    private void addGradeInfo(GradeEditVo gradeEditVo, StudyGroupSetEnum studyGroupSetEnum) {
        if (StudyGroupSetEnum.No.equals(studyGroupSetEnum)) {
            List<Map<String, Object>> notStudyGroupUserList = null;
            if (studyGroupSetEnum.equals(StudyGroupSetEnum.No)) {
                notStudyGroupUserList = userSubmitRecordDao.getUserNotGroupGradeList(
                        Status.NO.getStatus(), gradeEditVo.getOriginType(), gradeEditVo.getOriginId());
            } else {
                notStudyGroupUserList = userSubmitRecordDao.getUserGradeList(
                        Status.NO.getStatus(), gradeEditVo.getOriginType(), gradeEditVo.getOriginId(), gradeEditVo.getStudyGroupId());
            }
            if (notStudyGroupUserList != null) {
                for (Map<String, Object> userItem : notStudyGroupUserList) {
                    Long userId = Long.parseLong(userItem.get("user_id").toString());
                    addGradeComment(gradeEditVo, userId);
                }
            }
        }
        if (StudyGroupSetEnum.Yes.equals(studyGroupSetEnum)) {
            if (gradeEditVo.getUserType().equals(UserTypeEnum.All.getValue())) {
                /**
                 * 先查询提交表中带小组的 取重复-未评分
                 */
                List<Map<String, Object>> notGradeStudyGroupUserSubmitList = userSubmitRecordDao.
                        getNotGradeStudyGroupUserSubmit(gradeEditVo.getOriginType(), gradeEditVo.getOriginId());
                if (ListUtils.isNotEmpty(notGradeStudyGroupUserSubmitList)) {
                    for (Map<String, Object> item : notGradeStudyGroupUserSubmitList) {
                        Long studyGroupId = Long.parseLong(item.get("study_group_id").toString());
                        addStudyUserGrade(gradeEditVo, studyGroupId);
                    }
                }
                /**
                 * 在去查提交表中组为空的-是否有自然人-未已评分
                 */
                List<Map<String, Object>> userList = userSubmitRecordDao.getNotStudyGroupUserSubmit(
                        gradeEditVo.getOriginType(), gradeEditVo.getOriginId(), gradeEditVo.getCourseId());
                if (ListUtils.isNotEmpty(userList)) {
                    for (Map<String, Object> userItem : userList) {
                        if (Integer.parseInt(userItem.get("is_graded").toString()) != Status.YES.getStatus()) {
                            addGradeComment(gradeEditVo, Long.parseLong(userItem.get("user_id").toString()));
                        }
                    }
                }
            }
            if (gradeEditVo.getUserType().equals(UserTypeEnum.Yes.getValue())) {
                addStudyUserGrade(gradeEditVo, gradeEditVo.getStudyGroupId());
            }
            if (gradeEditVo.getUserType().equals(UserTypeEnum.No.getValue())) {
                addGradeComment(gradeEditVo, gradeEditVo.getUserId());
            }
        }
        if (StudyGroupSetEnum.GroupOverWrite.equals(studyGroupSetEnum)) {
            /**
             * 查询已提交未评分的人员--注意是否传组 --只是在批量评分界面下进行
             * 先查询提交人是否组
             */
            List<UserSubmitRecord> userSubmitRecordList = userSubmitRecordDao.find(
                    UserSubmitRecord.builder()
                            .originType(gradeEditVo.getOriginType())
                            .originId(gradeEditVo.getOriginId())
                            .build());
            if (ListUtils.isNotEmpty(userSubmitRecordList)) {
                for (UserSubmitRecord userItem : userSubmitRecordList) {
                    if (userItem.getStudyGroupId() != null) {
                        addStudyUserGrade(gradeEditVo, userItem.getStudyGroupId());
                    } else {
                        addGradeComment(gradeEditVo, userItem.getUserId());
                    }
                }
            }
        }
    }

    /**
     * 查询小组内人员-提交
     **/
    private void addStudyUserGrade(GradeEditVo gradeEditVo, Long studyGroupId) {
        List<StudyGroupUser> groupUserList = studyGroupUserDao.find(
                StudyGroupUser.builder()
                        .studyGroupId(studyGroupId)
                        .build());
        if (ListUtils.isNotEmpty(groupUserList)) {
            for (StudyGroupUser studyGroupUser : groupUserList) {
                Long userId = studyGroupUser.getUserId();
                addGradeComment(gradeEditVo, userId);
            }
        }
    }

    /**
     * 添加评分数据记录
     * 1、先判断是否有记录，有则修改，没有则插入新记录
     * 2、在插入评论信息，如果评论内容为null则不插入
     * 3、修改用户作业组项提交记录 is_graded 为1
     *
     * @param gradeEditVo
     * @param userId
     */
    private void addGradeComment(GradeEditVo gradeEditVo, Long userId) {
        Grade grade = gradeDao.findOne(Grade.builder()
                .originId(gradeEditVo.getOriginId())
                .originType(gradeEditVo.getOriginType())
                .userId(userId)
                .build());
        Grade model = Grade.builder()
                .id(grade==null?null:grade.getId())
                .courseId(gradeEditVo.getCourseId())
                .assignmentGroupItemId(Long.parseLong(gradeEditVo.getAssignmentGroupItemId()))
                .originType(gradeEditVo.getOriginType())
                .originId(gradeEditVo.getOriginId())
                .score(Integer.parseInt(gradeEditVo.getScore()))
                .gradeScore(Integer.parseInt(gradeEditVo.getGradeScore()))
                .userId(userId)
                .isGraded(Status.YES.getStatus())
                .graderId(WebContext.getUserId())
                .build();
        if (grade==null) {
            gradeDao.save(model);
        } else {
            gradeDao.update(model);
        }
        gradeCommentService.addGradeCommentInfo(gradeEditVo, userId);
        List<UserSubmitRecord> userSubmitRecordList = userSubmitRecordDao.find(
                UserSubmitRecord.builder().originType(gradeEditVo.getOriginType()).originId(gradeEditVo.getOriginId()).userId(userId).build());

        if (ListUtils.isNotEmpty(userSubmitRecordList)) {
            userSubmitRecordList.forEach(userSubmitRecord -> {
                userSubmitRecord.setIsGraded(Status.YES.getStatus());
                userSubmitRecordDao.update(userSubmitRecord);
            });
        }
    }

    /**
     * 修改评分信息 要区分小组、自然人、个人
     *
     * @param gradeEditVo
     * @param studyGroupSetEnum
     */
    private void updateGradeInfo(GradeEditVo gradeEditVo, StudyGroupSetEnum studyGroupSetEnum) {
        if (StudyGroupSetEnum.No.equals(studyGroupSetEnum)) {
            List<Grade> gradeList = gradeDao.find(
                    Grade.builder()
                            .originType(gradeEditVo.getOriginType())
                            .originId(gradeEditVo.getOriginId())
                            .assignmentGroupItemId(Long.parseLong(gradeEditVo.getAssignmentGroupItemId()))
                            .build());
            if (ListUtils.isNotEmpty(gradeList)) {
                for (Grade grade : gradeList) {
                    updateGrade(gradeEditVo, grade.getId());
                }
            }
        }
        if (StudyGroupSetEnum.Yes.equals(studyGroupSetEnum)) {
            Long studyGroupId = gradeEditVo.getStudyGroupId();
            if (gradeEditVo.getUserType().equals(UserTypeEnum.All.getValue())) {
                studyGroupId = null;
            }
            if (gradeEditVo.getUserType().equals(UserTypeEnum.No.getValue())) {
                List<Grade> gradeList = gradeDao.find(
                        Grade.builder()
                                .originType(gradeEditVo.getOriginType())
                                .originId(gradeEditVo.getOriginId())
                                .userId(gradeEditVo.getUserId())
                                .assignmentGroupItemId(Long.parseLong(gradeEditVo.getAssignmentGroupItemId()))
                                .build());
                if (ListUtils.isNotEmpty(gradeList)) {
                    for (Grade grade : gradeList) {
                        updateGrade(gradeEditVo, grade.getId());
                    }
                }
            }
            List<Map<String, Object>> gradeCommentList = gradeCommentDao.getGradeGroupCount(
                    gradeEditVo.getCourseId(), gradeEditVo.getOriginId(), gradeEditVo.getOriginType(), studyGroupId);
            if (ListUtils.isNotEmpty(gradeCommentList)) {
                for (Map<String, Object> item : gradeCommentList) {
                    updateGrade(gradeEditVo, Long.parseLong(item.get("id").toString()));
                }
            }
        }
    }

    /**
     * 修改评分表中的is_grade 为1
     *
     * @param gradeEditVo
     * @param id
     */
    private void updateGrade(GradeEditVo gradeEditVo, Long id) {
        Grade model = gradeDao.get(id);
        model.setGradeScore(Integer.parseInt(gradeEditVo.getGradeScore()));
        model.setIsGraded(Status.YES.getStatus());
        gradeDao.update(model);
        gradeCommentService.addGradeCommentInfo(gradeEditVo, model.getUserId());

    }

    /**
     * 设置最低分
     *
     * @param gradeEditVo
     */
    private void updateGradeLowestScore(GradeEditVo gradeEditVo) {
        List<Grade> gradeList = gradeDao.find(
                Grade.builder()
                        .originType(gradeEditVo.getOriginType())
                        .originId(gradeEditVo.getOriginId())
                        .assignmentGroupItemId(Long.parseLong(gradeEditVo.getAssignmentGroupItemId()))
                        .build());
        if (ListUtils.isNotEmpty(gradeList)) {
            for (Grade item : gradeList) {
                if (Integer.parseInt(gradeEditVo.getGradeScore()) > item.getGradeScore()) {
                    item.setGradeScore(Integer.parseInt(gradeEditVo.getGradeScore()));
                    item.setIsGraded(Status.YES.getStatus());
                    gradeDao.update(item);
                    gradeCommentService.addGradeCommentInfo(gradeEditVo, item.getUserId());
                }
            }
        }
    }
}
