package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.enums.QuizTypeEnum;
import com.wdcloud.lms.core.base.dao.AssignUserDao;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.Grade;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.UserByIdVo;
import com.wdcloud.lms.core.base.vo.UserSubmitRecordVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_GREDE,
        functionName = Constants.FUNCTION_TYPE_USERBOOK
)
public class GradeUserQuery implements ISelfDefinedSearch<Map<String, Object>> {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private AssignUserDao assignUserDao;
    @Autowired
    private UserDao userDao;
    /**
     * @api {get} /grade/userBook/query 单个学生的成绩统计概要信息
     * @apiDescription 单个学生的成绩统计概要信息
     * @apiName gradeUserBookQuery
     * @apiGroup Grade
     * @apiParam {Integer} userId 学生id
     * @apiParam {Integer} sectionId 班级id
     * @apiParam {Integer} courseId 课程id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {Object} entity.userByIdVo 学生的基本信息
     * @apiSuccess {Long} entity.userByIdVo.id 用户id
     * @apiSuccess {String} entity.userByIdVo.fullname 用户全称
     * @apiSuccess {String} entity.userByIdVo.email 邮箱
     * @apiSuccess {String} entity.userByIdVo.fileUrl 学生头像路径
     * @apiSuccess {Integer} entity.userByIdVo.overdue 逾期提交
     * @apiSuccess {Integer} entity.userByIdVo.unOverdue 未逾期提交
     * @apiSuccess {Integer} entity.userByIdVo.gradeScore 用户下所有任务的得分
     * @apiSuccess {Integer} entity.userByIdVo.score 用户下所有任务的总分
     * @apiSuccess {Integer} entity.userByIdVo.proportionScore 得分/总分的比例
     * @apiSuccess {Integer} entity.ranking 学生当前班级排名
     * @apiSuccess {Object[]} entity.taskToTheEnd 任务得分比例从高到底
     * @apiSuccess {String} entity.taskToTheEnd.title 任务名
     * @apiSuccess {Integer} entity.askToTheEnd.gradeScore 得分
     * @apiSuccess {Integer} entity.taskToTheEnd.score 总分
     * @apiSuccess {Integer} entity.taskToTheEnd.proportionScore 比例
     * @apiSuccess {Integer} entity.taskToTheEnd.originId 来源ID
     * @apiSuccess {Integer} entity.taskToTheEnd.originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {Object[]} entity.taskLowToHigh 任务得分比例从低到高
     * @apiSuccess {String} entity.taskLowToHigh.title 任务名
     * @apiSuccess {Integer} entity.taskLowToHigh.gradeScore 得分
     * @apiSuccess {Integer} entity.taskLowToHigh.score 总分
     * @apiSuccess {Integer} entity.taskLowToHigh.proportionScore 比例
     * @apiSuccess {Integer} entity.taskToTheEnd.originId 来源ID
     * @apiSuccess {Integer} entity.taskToTheEnd.originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {Integer} entity.missQuery 缺交个数
     */
    @Override
    public Map<String, Object> search(Map<String, String> condition) {
        Long userId =Long.parseLong(condition.get("userId")) ;
        Long sectionId =Long.parseLong(condition.get("sectionId")) ;
        Long courseId =Long.parseLong(condition.get("courseId"));
        Map<String, Object> map = new HashMap<>();
        //查询学生的基本信息
        User user1=User.builder().id(userId).build();
        User userOne = userDao.findOne(user1);
        userOne.setPassword(null);
        UserByIdVo userByIdVo = BeanUtil.beanCopyProperties(userOne, UserByIdVo.class);
        //计算学生在课程下的成绩得分
        int gradeScore=0;
        int score=0;
        //查询学生在当前课程下分配的所有任务
        List<Map<String, Object>> userIdByGrade =assignUserDao.userIdByGrade(userId,courseId);
        for (Map<String, Object> stringObjectMap : userIdByGrade) {
            int originType = Integer.parseInt(stringObjectMap.get("originType").toString());
            Long originId = Long.valueOf(stringObjectMap.get("originId").toString());
            //作业isIncludeGrade=0不计入总分
            if(OriginTypeEnum.ASSIGNMENT.getType().equals(originType)){
               if(stringObjectMap.containsKey("isIncludeGrade")){
                  int isIncludeGrade = Integer.parseInt(stringObjectMap.get("isIncludeGrade").toString());
                 if(isIncludeGrade == Status.YES.getStatus()){
                     Grade grade=Grade.builder().originId(originId).originType(originType).userId(userId).courseId(courseId).build();
                     Grade gradeDaoOne = gradeDao.findOne(grade);
                     if(gradeDaoOne!=null){
                         gradeScore+=gradeDaoOne.getGradeScore();
                         score+=gradeDaoOne.getScore();
                     }
                 }
               }
            }else  if(OriginTypeEnum.QUIZ.getType().equals(originType)){
                //测验type=1不计入总分
                if(stringObjectMap.containsKey("type")){
                   int type = Integer.parseInt(stringObjectMap.get("type").toString());
                    if(type!= QuizTypeEnum.PRACTICE_QUIZ.getType()){
                        Grade grade=Grade.builder().originId(originId).originType(originType).userId(userId).courseId(courseId).build();
                        Grade gradeDaoOne = gradeDao.findOne(grade);
                        if(gradeDaoOne!=null){
                            gradeScore+=gradeDaoOne.getGradeScore();
                            if(gradeDaoOne.getScore()!=null){
                                score+=gradeDaoOne.getScore();
                            }
                        }
                    }
                }
            }else if(OriginTypeEnum.DISCUSSION.getType().equals(originType)){
                Grade grade=Grade.builder().originId(originId).originType(originType).userId(userId).courseId(courseId).build();
                Grade gradeDaoOne = gradeDao.findOne(grade);
                if(gradeDaoOne!=null){
                    gradeScore+=gradeDaoOne.getGradeScore();
                    score+=gradeDaoOne.getScore();
                }
            }
        }
         //学生在当前课程下任务的总得分
         userByIdVo.setGradeScore(gradeScore);
         //学生在当前课程下任务的总分
         userByIdVo.setScore(score);
         //得分比例
         userByIdVo.setProportionScore((double)gradeScore/score);
        //查询学生提交作业的情况统计,如果是小组任务，有一人提交就算小组全部提交
        List<UserSubmitRecordVo> submitRecordQuery=userSubmitRecordDao.submitRecordQuery(courseId,userId)
                .stream()
                .collect(Collectors. collectingAndThen( Collectors.toCollection(()
                  -> new TreeSet<>(Comparator.comparing(o -> o.getOriginType() + ";" + o.getOriginId()))), ArrayList::new));
        int overdue=0;
        int unOverdue=0;
        for (UserSubmitRecordVo userSubmitRecordVo : submitRecordQuery) {
            if(userSubmitRecordVo.getIsOverdue()==Status.YES.getStatus()){
                //逾期统计
                overdue++;
            } else if(userSubmitRecordVo.getIsOverdue()==Status.NO.getStatus()){
                //未逾期统计
                unOverdue++;
            }
        }
        userByIdVo.setOverdue(overdue);
        userByIdVo.setUnOverdue(unOverdue);
        //查询学生在班级的排名
        List<UserByIdVo> list=gradeDao.ext().userBySection(sectionId,courseId);
        int ranking = 0;
        for (int i = 0; i < list.size(); i++) {
            if (0 == Long.compare(userId, list.get(i).getId())) {
                ranking = ++i;
                break;
            }
        }
        //计算学生有多少次缺交
        Integer missQuery=0;
        AssignUser assignUser= AssignUser.builder().courseId(courseId).userId(userId).build();
        List<AssignUser> assignUserList = assignUserDao.find(assignUser);
        for (AssignUser user : assignUserList) {
            UserSubmitRecordVo userSubmitRecord = userSubmitRecordDao.findUserSubmitRecord(courseId, user.getOriginId(), user.getOriginType(), user.getUserId());
            if(userSubmitRecord==null){
                Date now = new Date();
                //如果结束时间不为空，用当前的时间跟结束时间比较，，过了结束时间还没提交就是缺交
                if(user.getEndTime()!=null&&!user.getEndTime().equals("")){
                    if(now.getTime()>user.getEndTime().getTime()){
                         missQuery++;
                    }
                }
            }
        }
        //获取用户得分比例最高的前10个的任务
        List<Map<String, Object>> taskToTheEnd = gradeDao.ext().taskToTheEnd(userId,courseId);
        //获取用户得分比例最低的前10个的任务
        List<Map<String, Object>> taskLowToHigh = gradeDao.ext().taskLowToHigh(userId,courseId);
        //学生的基本信息
        map.put("userByIdVo", userByIdVo);
        //学生在当前课程，在班级中的排名
        map.put("ranking", ranking);
        //获取用户得分比例最高的前10个的任务
        map.put("taskToTheEnd", taskToTheEnd);
        //获取用户得分比例最低的前10个的任务
        map.put("taskLowToHigh", taskLowToHigh);
        //学生在当前课程下的缺交次数
        map.put("missQuery", missQuery);
        return map;
    }
}

