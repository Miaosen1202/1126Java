package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.discussion.dto.DiscussionReplyTree;
import com.wdcloud.lms.business.grade.enums.StudyGroupSetEnum;
import com.wdcloud.lms.business.grade.vo.AssignmentReplyVo;
import com.wdcloud.lms.business.grade.vo.QuizQuestionVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignmentReplyTypeEnum;
import com.wdcloud.lms.core.base.enums.QuestionTypeEnums;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.Grade;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.CosGradeVo;
import com.wdcloud.lms.core.base.vo.DiscussionReplyVO;
import com.wdcloud.lms.core.base.vo.QuizObjectiveDataListVo;
import com.wdcloud.lms.core.base.vo.QuizSubjectivityDataListVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author zhangxutao
 * 提交答案信息——评分
 **/

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_AssignmentReply_Graded
)
public class GradeAssignmentReplyDataQuery implements ISelfDefinedSearch<AssignmentReplyVo> {

    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /gradeDataQuery/gradeAssignmentReply/query 评分查询内容信息
     * @apiName gradeAssignmentReply
     * @apiGroup GradeGroup
     * @apiParam {Number} originType 任务类型： 1: 作业 2: 讨论 3: 测验',
     * @apiParam {Number} originId 任务类型ID
     * @apiParam {Number} studyGroupId 小组ID
     * @apiParam {Number} userId 用户ID
     * @apiParam {String} submitTime 提交时间
     * @apiParam {String} courseId 课程id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {String} entity.content 内容{包括文本内容、URL内容}
     * @apiSuccess {Integer} entity.replyType 1: 文本　2: 文件 3: URL 4: 媒体',
     * @apiSuccess {Object[]} [entity.attachments] 附件List
     * @apiSuccess {String} entity.attachments.id 文件ID
     * @apiSuccess {String} entity.attachments.fileName 文件名称
     * @apiSuccess {String} entity.attachments.fileType 文件类型
     * @apiSuccess {String} entity.attachments.fileSize 文件大小
     * @apiSuccess {String} entity.attachments.fileUrl 文件路径
     * @apiSuccess {Object[]} [entity.disEntity] 讨论lits
     * @apiSuccess {String} entity.disEntity.fileName 文件ID
     * @apiSuccess {String} entity.disEntity.fileUrl 评论附件地址
     * @apiSuccess {String} entity.disEntity.userId 用户ID
     * @apiSuccess {String} entity.disEntity.userNickname 用户昵称
     * @apiSuccess {String} entity.disEntity.userAvatarFileId 用户头像文件ID
     * @apiSuccess {String} entity.disEntity.userAvatarName 用户头像名称
     * @apiSuccess {String} entity.disEntity.userAvatarUrl 用户头像URL
     * @apiSuccess {String} entity.disEntity.isRead 是否已读 0：未读：其他已读
     * @apiSuccess {String} entity.disEntity.discussionId 讨论ID
     * @apiSuccess {String} entity.disEntity.studyGroupId 学习小组ID
     * @apiSuccess {String} entity.disEntity.discussionReplyId 讨论回复ID
     * @apiSuccess {String} entity.disEntity.treeId 树ID
     * @apiSuccess {String} entity.disEntity.content 内容
     * @apiSuccess {Object[]} entity.quizEntity 内容基本信息
     * @apiSuccess {Object[]} entity.quizEntity.quizObjective  测验选项题
     * @apiSuccess Long entity.quizEntity.quizObjective.id 试题ID
     * @apiSuccess {Long} entity.quizEntity.quizObjective.questionId 问题ID
     * @apiSuccess {String} entity.quizEntity.quizObjective.questionsTitle 试题题目
     * @apiSuccess {Object[]}  entity.quizEntity.optionList 选项 排序决定ABCD
     * @apiSuccess {Long}  entity.quizEntity.optionList.optionId 选项ID
     * @apiSuccess {Long} entity.quizEntity.optionList.quizQuestionRecordId
     * @apiSuccess {Long} entity.quizEntity.optionList.questionOptionId
     * @apiSuccess {String} entity.quizEntity.optionList.code 题干中占位符
     * @apiSuccess {String} entity.quizEntity.optionList.content 内容
     * @apiSuccess {String} entity.quizEntity.optionList.explanation 选择该项的提示
     * @apiSuccess {Integer} entity.quizEntity.optionList.isCorrect 选择题：是否为正确选项
     * @apiSuccess {Integer} entity.quizEntity.optionList.seq 排序
     * @apiSuccess {Integer} entity.quizEntity.optionList.isChoice 是否选中
     * @apiSuccess {Integer} entity.quizEntity.quizObjective.seq 排序
     * @apiSuccess {Integer} entity.quizEntity.quizObjective.score 分值
     * @apiSuccess {Integer} entity.quizEntity.quizObjective.gradeScore 得分
     * @apiSuccess {Long} entity.quizEntity.quizObjective.quizRecordId 测验记录ID
     * @apiSuccess {String} entity.quizEntity.quizObjective.correctComment 正确提示
     * @apiSuccess {String} entity.quizEntity.quizObjective.wrongComment 错误提示
     * @apiSuccess {String} entity.quizEntity.quizObjective.generalComment 通用提示
     * @apiSuccess {Object[]} entity.quizEntity.quizSubjectivity
     * @apiSuccess {Integer} entity.quizEntity.quizSubjectivity.id ID
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.questionsId 试题ID
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.questionsTitle 试题题目
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.seq 排序
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.answer 我的答案
     * @apiSuccess {Integer} entity.quizEntity.quizSubjectivity.score 分值
     * @apiSuccess {Integer} entity.quizEntity.quizSubjectivity.gradeScore 得分
     * @apiSuccess {Integer} entity.quizEntity.quizSubjectivity.quizRecordId 测验记录ID
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.correctComment 正确提示
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.wrongComment 错误提示
     * @apiSuccess {String} entity.quizEntity.quizSubjectivity.generalComment 通用提示
     * @apiSuccess {Integer} entity.quizEntity.score   总分
     * @apiSuccess {Integer} entity.quizEntity.gradeScore 得分
     * @apiSuccess {Long} entity.quizEntity.userId 提交用户
     * @apiSuccess {Long} entity.quizEntity.graderId 评分用户
     */
    @Override
    public AssignmentReplyVo search(Map<String, String> condition) {
        AssignmentReplyVo result = new AssignmentReplyVo();
        Integer originType = Integer.parseInt(condition.get("originType"));
        Long originId = Long.parseLong(condition.get("originId"));
        Long studyGroupId = Long.parseLong(condition.get("studyGroupId"));
        Long userId = Long.parseLong(condition.get("userId"));
        Long courseId = Long.parseLong(condition.get("courseId"));
        Long submitTime = Long.parseLong(condition.get("submitTime"));
        java.util.Date subTime = null;
        if (submitTime != null && submitTime > 0) {
            subTime = new java.util.Date(submitTime);
        }
        studyGroupId = studyGroupId == StudyGroupSetEnum.Yes.getValue().longValue() ? null : studyGroupId;
        /**
         * 获取任务的总分值跟得分
         */
        getOriginGradeScore( result,  studyGroupId, originType,
                 originId, userId, courseId);
        /**
         * 作业信息内容
         */
        if (originType.equals(OriginTypeEnum.ASSIGNMENT.getType())) {
            getAssignmentReplyList(result, originId, userId, studyGroupId, subTime);
        }
        /**
         * 讨论信息内容
         */
        if (originType.equals(OriginTypeEnum.DISCUSSION.getType())) {
            getDiscussionList(result, originId, userId, studyGroupId);
        }
        /**
         * 测验信息内容
         */
        if (originType.equals(OriginTypeEnum.QUIZ.getType())) {
            getQuizQuestionList(result, originType, originId,
                    userId, studyGroupId, courseId, subTime);
        }

        return result;
    }

    /**
     * 获取任务的总分值跟得分
     * @param result
     * @param studyGroupId
     * @param originType
     * @param originId
     * @param userId
     * @param courseId
     */
    private void getOriginGradeScore(AssignmentReplyVo result, Long studyGroupId,Integer originType,
                                     Long originId,Long userId,Long courseId){
        //查询该任务在小组里是否已经评分 - 任务得分跟总分信息
        if (studyGroupId != null) {
            CosGradeVo gradeInfo = gradeDao.getCosGradeInfo(originType, originId, userId, studyGroupId, courseId);
            //获取已评分信息
            if (gradeInfo != null) {
                result.setScore(gradeInfo.getScore());
                result.setGradeScore(gradeInfo.getGradeScore());
            }
        } else {
            Grade gradeList = gradeDao.findOne(Grade.builder()
                    .originType(originType)
                    .originId(originId)
                    .userId(userId)
                    .courseId(courseId)
                    .build());
            if (gradeList != null) {
                result.setScore(gradeList.getScore());
                result.setGradeScore(gradeList.getGradeScore());
            }
        }
    }

    /**
     * 获取学生作业信息
     *
     * @param result
     * @param originId
     * @param userId
     * @param studyGroupId
     * @param subTime
     */
    private void getAssignmentReplyList(AssignmentReplyVo result, Long originId, Long userId, Long studyGroupId, Date subTime) {
        List<Map<String, Object>> assignmentReply = assignmentReplyDao.getAssignmentReply(originId, userId,
                studyGroupId, subTime);
        if (ListUtils.isNotEmpty(assignmentReply)) {
            for (Map<String, Object> item : assignmentReply) {
                if (AssignmentReplyTypeEnum.FILE.getType().equals(Integer.parseInt(item.get("reply_type").toString()))
                        || AssignmentReplyTypeEnum.MATE.getType().equals(Integer.parseInt(item.get("reply_type").toString()))) {
                    List<UserFile> files = assignmentReplyDao.assignmentReplyFile(originId, Long.parseLong(item.get("id").toString()));
                    result.setReplyType(Integer.parseInt(item.get("reply_type").toString()));
                    result.setAttachments(files);
                } else {
                    result.setContent(item.get("content").toString());
                    result.setReplyType(Integer.parseInt(item.get("reply_type").toString()));
                }
            }
        }
        result.setOriginType(OriginTypeEnum.ASSIGNMENT.getType());
    }

    /**
     * 获取讨论信息内容
     *
     * @param result
     * @param originId
     * @param userId
     * @param studyGroupId
     */
    private void getDiscussionList(AssignmentReplyVo result, Long originId, Long userId, Long studyGroupId) {
        Discussion disList = discussionDao.get(originId);
        User user = userDao.get(disList.getCreateUserId());
        Map<String, String> param = new HashMap<String, String>();
        param.put("userId", userId.toString());
        param.put("discussionId", originId.toString());
        if (studyGroupId != null) {
            param.put("studyGroupId", studyGroupId.toString());
        }
        List<DiscussionReplyVO> list = discussionReplyDao.getDiscussionReplyList(param);
        List<DiscussionReplyVO> tree = DiscussionReplyTree.getList(list);
        result.setDiscussionReplyVOList(tree);
        result.setDiscussion(disList);
        result.setUser(user);
        result.setOriginType(OriginTypeEnum.DISCUSSION.getType());
    }

    /**
     * 获取测验的回复信息列表
     *
     * @param result
     * @param originType
     * @param originId
     * @param userId
     * @param studyGroupId
     * @param courseId
     * @param subTime
     */
    private void getQuizQuestionList(AssignmentReplyVo result, Integer originType, Long originId,
                                     Long userId, Long studyGroupId, Long courseId, Date subTime) {
        QuizQuestionVo quizQuestionVo = new QuizQuestionVo();
        CosGradeVo cosGradeInfo = gradeDao.getCosGradeInfo(originType, originId, userId, studyGroupId, courseId);
        //获取已评分信息
        if (cosGradeInfo != null) {
            result.setScore(cosGradeInfo.getScore());
            result.setGradeScore(cosGradeInfo.getGradeScore());
            quizQuestionVo.setUserId(cosGradeInfo.getUserId());
            quizQuestionVo.setGraderId(cosGradeInfo.getGraderId());
        }

        List<QuizSubjectivityDataListVo> subjectivityList  = new ArrayList<>();
        List<QuizObjectiveDataListVo>  objList = new ArrayList<>();
        List<Map<String, Object>> subQuizList = quizDao.getQuizRecordInfo(originId, userId, subTime);
        if (ListUtils.isNotEmpty(subQuizList)) {
            for (Map<String, Object> stringObjectMap : subQuizList) {
                Long quizRecordId = Long.parseLong(stringObjectMap.get("id").toString());
                subjectivityList = quizDao.getQuizSubjectivityList(QuestionTypeEnums.FULL_BLANK.getType(), quizRecordId);
                objList =  quizDao.getObjectiveDataList(quizRecordId);
            }
        }
        quizQuestionVo.setQuizObjectiveListVo(objList);
        quizQuestionVo.setQuizSubjectivityListVo(subjectivityList);
        result.setQuizQuestionVo(quizQuestionVo);
        result.setOriginType(OriginTypeEnum.QUIZ.getType());
    }
}
