package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.enums.StudyGroupSetEnum;
import com.wdcloud.lms.business.grade.vo.GradeOneStuGroupVo;
import com.wdcloud.lms.business.grade.vo.GroupListVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.StudentDataListVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author zhangxutao
 * 批量评分基础信息
 **/

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_Grade_One_Stu_Group
)
public class GradeOneStuGroupDataQuery implements ISelfDefinedSearch<GradeOneStuGroupVo> {

    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private UserDao userDao;


    /**
     * @api {get} /gradeDataQuery/gradeOneStuGroupQuery/query 查询单个任务的组、学生list
     * @apiName gradeOneStuGroupQuery
     * @apiGroup GradeGroup
     * @apiParam {Integer} originType 任务类型： 1: 作业 2: 讨论 3: 测验',
     * @apiParam {Long} originId 任务类型ID
     * @apiParam {Integer} releaseType 个人或小组类型 1:个人 2小组
     * @apiParam {String} sequence 排序：1:按提交时间 2:按照名称音序排序
     * @apiParam {Integer} gradedStatus 评分状态 2 全部 0未评分 1已评分
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]}  [entity] 查询单个任务的组、学生list
     * @apiSuccess {Object[]} entity.groupList 组
     * @apiSuccess {Long} entity.groupList.id 组ID
     * @apiSuccess {String} entity.groupList.groupName 组名称
     * @apiSuccess {Long} entity.groupList.id 学生ID
     * @apiSuccess {String} entity.groupList.studentName 学生名称
     * @apiSuccess {String} entity.groupList.studentFile 学生头像
     * @apiSuccess {Object[]} entity.studentList 学生list
     * @apiSuccess {Long} entity.studentList.id 学生ID
     * @apiSuccess {String} entity.studentList.studentName 学生名称
     * @apiSuccess {String} entity.studentList.studentFile 学生头像
     */
    @Override
    public GradeOneStuGroupVo search(Map<String, String> condition) {
        Integer originType = Integer.parseInt(condition.get("originType"));
        Long originId = Long.parseLong(condition.get("originId"));
        Integer sequence = Integer.parseInt(condition.get("sequence"));
        Integer gradedStatus = Integer.parseInt(condition.get("gradedStatus"));
        Long studyGroupSetId = null;
        Long courseId = null;
        /**
         * 获取作业、讨论、测验任务中的小组集ID、课程ID
         * 说明：测验没有小组集的概念，所以为null
         */
        if (OriginTypeEnum.ASSIGNMENT.getType().equals(originType)) {
            Assignment assignment = assignmentDao.get(originId);
            studyGroupSetId = assignment.getStudyGroupSetId();
            courseId = assignment.getCourseId();
        } else if (OriginTypeEnum.DISCUSSION.getType().equals(originType)) {
            Discussion discussion = discussionDao.get(originId);
            courseId = discussion.getCourseId();
            studyGroupSetId = discussion.getStudyGroupSetId();
        } else if (OriginTypeEnum.QUIZ.getType().equals(originType)) {
            Quiz quiz = quizDao.get(originId);
            courseId = quiz.getCourseId();
            studyGroupSetId = null;
        }
        GradeOneStuGroupVo model = gradeOneStuGroupList(originId, originType, studyGroupSetId, courseId, sequence, gradedStatus);
        return model;
    }

    /**
     * 获取小组或个人列表
     * 说明：分三种情况
     * 1、查询小组集中的小组
     * 2、查询小组集中的自然人
     * 3、查询个人列表
     * 使用界面：批量评分、单一评分界面
     *
     * @param originId        任务ID
     * @param originType      任务类型
     * @param studyGroupSetId 小组集ID
     * @param courseId        课程ID
     * @param sequence        排序方式
     * @param gradedStatus    评分状态
     * @return GradeOneStuGroupVo
     */
    private GradeOneStuGroupVo gradeOneStuGroupList(Long originId, Integer originType,
                                                    Long studyGroupSetId, Long courseId,
                                                    Integer sequence, Integer gradedStatus) {
        GradeOneStuGroupVo model = new GradeOneStuGroupVo();
        List<GroupListVo> groupListVoList = new ArrayList<>();
        if (studyGroupSetId != null) {
            //查询小组
            List<Map<String, Object>> getGroupStudentList = userSubmitRecordDao.getGroupStudentList(
                    originId, studyGroupSetId, originType, gradedStatus);
            if (ListUtils.isNotEmpty(getGroupStudentList)) {
                for (Map<String, Object> item : getGroupStudentList) {
                    GroupListVo group = new GroupListVo();
                    List<Map<String, Object>> groupList = userSubmitRecordDao.getGroupList(
                            Long.parseLong(item.get("user_id").toString()), sequence, courseId);
                    if (groupList != null) {
                        for (Map<String, Object> gItem : groupList) {
                            group.setId(Long.parseLong(gItem.get("id").toString()));
                            group.setType(StudyGroupSetEnum.Yes.getValue());
                            group.setGroupName(gItem.get("name").toString());
                        }
                    } else {
                        group.setId(Long.parseLong(item.get("user_id").toString()));
                        group.setType(StudyGroupSetEnum.No.getValue());
                        if (item.containsKey("username")) {
                            group.setGroupName(item.get("username").toString());
                        }
                    }
                    group.setUserId(Long.parseLong(item.get("user_id").toString()));

                    if (item.containsKey("full_name")) {
                        group.setFullName(item.get("full_name").toString());
                    }
                    group.setStudentName(item.get("username").toString());
                    if (item.containsKey("nickname")) {
                        group.setNickName(item.get("nickname").toString());
                    }
                    if (item.containsKey("avatar_file_id")) {
                        group.setStudentFile(item.get("avatar_file_id").toString());
                    }
                    groupListVoList.add(group);
                }
            }
            /**
             * 查询课程下的所有提交作业的学生List-非小组但是在小组集下面-提交的
             */
            List<Map<String, Object>> userSubmitRecord = userSubmitRecordDao
                    .getNotStudyGroupUserSubmit(originType, originId, courseId);
            if (ListUtils.isNotEmpty(userSubmitRecord)) {
                for (Map<String, Object> item : userSubmitRecord) {
                    User user = userDao.get(Long.parseLong(item.get("user_id").toString()));
                    GroupListVo group = new GroupListVo();
                    if (user != null) {
                        group.setType(StudyGroupSetEnum.No.getValue());
                        group.setId(user.getId());
                        group.setGroupName(user.getUsername());
                        group.setUserId(user.getId());
                        group.setNickName(user.getNickname());
                        group.setFullName(user.getFullName());
                        group.setStudentName(user.getUsername());
                        group.setStudentFile(user.getAvatarFileId());
                        groupListVoList.add(group);
                    }
                }
            }
            if (!ListUtils.isNotEmpty(groupListVoList)) {
                GroupListVo group = new GroupListVo();
                groupListVoList.add(group);
            }
            model.setGroupListVoList(groupListVoList);
            List<StudentDataListVo> stuList = new ArrayList<>();
            StudentDataListVo listVo = new StudentDataListVo();
            stuList.add(listVo);
            model.setStudentListVos(stuList);
        } else {
            List<StudentDataListVo> studentList = userSubmitRecordDao.getGradeStudentList(
                    originType, originId, gradedStatus, sequence);
            if (!ListUtils.isNotEmpty(studentList)) {
                StudentDataListVo listVo = new StudentDataListVo();
                studentList.add(listVo);
            }
            model.setStudentListVos(studentList);
        }

        return model;
    }
}
