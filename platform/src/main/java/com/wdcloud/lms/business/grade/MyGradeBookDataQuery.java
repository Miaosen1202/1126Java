package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.grade.enums.CompleteEnum;
import com.wdcloud.lms.business.grade.enums.ShowScoreTypeEnum;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.GradeSchemeItem;
import com.wdcloud.lms.core.base.vo.MyGradeBookListVo;
import com.wdcloud.lms.util.MathUtils;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 查询我的评分信息-学生端界面
 *
 * @author zhangxutao
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_My_Grade_Book
)
public class MyGradeBookDataQuery implements ISelfDefinedSearch<Map<String, Object>> {

    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private GradeSchemeDataQuery gradeSchemeDataQuery;
    @Autowired
    private CourseDao courseDao;
    /**
     * @api {get} /gradeDataQuery/myGradeBook/query 查询我的评分信息
     * @apiName myGradeBookSearch
     * @apiGroup Grade2.0
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 内容基本信息
     * @apiSuccess {Object[]} entity.modelList 内容基本信息
     * @apiSuccess {Number} entity.modelList.id 评分ID
     * @apiSuccess {Number} entity.modelList.originId 任务ID
     * @apiSuccess {Number} entity.modelList.originType 任务类型
     * @apiSuccess {String} entity.modelList.title 题目
     * @apiSuccess {String} entity.modelList.submissionStatus 提交状态
     * @apiSuccess {String} entity.modelList.scores 得分情况
     * @apiSuccess {String} entity.modelList.gradeScore 每项任务得分 【******新增********】
     * @apiSuccess {String} entity.modelList.gradeScoreFull 每项任务满分 【******新增********】
     * @apiSuccess {String} entity.modelList.showScoreTag 得分显示标签 【******新增********】
     * @apiSuccess {String} entity.modelList.weight 权重 默认100 【******新增********】
     * @apiSuccess {String} entity.modelList.byPercentLetter 比率
     * @apiSuccess {Number} entity.modelList.gradeCount 评论总数
     * @apiSuccess {String} entity.modelList.type 类型：作业、讨论、测验
     * @apiSuccess {Number} entity.modelList.dueTime 截止时间
     * @apiSuccess {String} entity.modelList.missionGroup 所属任务组-名称
     * @apiSuccess {string} entity.modelList.userId 用户ID
     * @apiSuccess {Number} entity.modelList.subTime 提交时间
     * @apiSuccess {Number} entity.modelList.closeTime 关闭时间
     * @apiSuccess {Number} entity.modelList.includeGrade 任务是否计入总分 0:不计入(显示!) 1:计入
     * @apiSuccess {String} entity.totalScores  得分/总分
     * @apiSuccess {Number} entity.totalPercent 比例
     */
    @Override
    public Map<String, Object> search(Map<String, String> condition) {
        Long courseId = Long.parseLong(condition.get("courseId"));
        List<MyGradeBookListVo> myGradeBookList = gradeCommentDao.getMyGradeBookList(WebContext.getUserId(), courseId);

        if (ListUtils.isNotEmpty(myGradeBookList)) {
            //权重处理
            buildMyGradeBookListWithWeight(myGradeBookList,courseId);
            //构建作业拓展属性
            buildAssignment(myGradeBookList,courseId);
            Map<String, Object> rlt = new HashMap<>();
            //设置total字段
            setTotal(myGradeBookList,rlt);
            rlt.put("modelList",myGradeBookList);
            return rlt;
        }
        return new HashMap();
    }

    /**
     * 权重处理
     * @param myGradeBookList
     * @param courseId
     */
    private void buildMyGradeBookListWithWeight(List<MyGradeBookListVo> myGradeBookList, Long courseId) {
        Course course=courseDao.get(courseId);
        if (course.getIsWeightGrade()!=null&&course.getIsWeightGrade()==1) {
            myGradeBookList.forEach(myGradeBookListVo -> {
                Double weight = myGradeBookListVo.getWeight()/100;
                myGradeBookListVo.setGradeScore(MathUtils.formatDouble2(myGradeBookListVo.getGradeScore() * weight));
                myGradeBookListVo.setGradeScoreFull(MathUtils.formatDouble2(myGradeBookListVo.getGradeScoreFull()*weight));
            });
        }
    }

    private void buildAssignment(List<MyGradeBookListVo> myGradeBookList, Long courseId) {
        //获取作业IDs
        List<Long> assignmentIds = myGradeBookList.stream()
                .filter(vo -> vo.getOriginType().equals(OriginTypeEnum.ASSIGNMENT.getType()))
                .map(MyGradeBookListVo::getOriginId).collect(Collectors.toList());
        if (ListUtils.isNotEmpty(assignmentIds)) {
            List<Assignment> assignmentList = assignmentDao.gets(assignmentIds);
            Map<Long, Assignment> assignmentMap=assignmentList.stream().collect(Collectors.toMap(Assignment::getId,a->a));
            //通过courseId获取评分模板
            List<GradeSchemeItem> itemList=gradeSchemeDataQuery.find(courseId.toString());
            myGradeBookList.forEach(item->{
                if (item.getOriginType().equals(OriginTypeEnum.ASSIGNMENT.getType())) {
                    Assignment assignment = assignmentMap.get(item.getOriginId());
                    //构建 includeGrade showScoreType
                    item.setIncludeGrade(assignment.getIsIncludeGrade());
                    item.setShowScoreType(assignment.getShowScoreType());
                    //构建 showScoreTag 字母 百分比等
                    buildShowScoreTag(item,itemList);
                }
            });

        }
        myGradeBookList.forEach(item->{
            if (!item.getOriginType().equals(OriginTypeEnum.ASSIGNMENT.getType())) {
                item.setShowScoreTag(MathUtils.formatDouble2(item.getGradeScore())+"");
            }
        });
    }

    private void buildShowScoreTag(MyGradeBookListVo item, List<GradeSchemeItem> itemList) {
        if (ShowScoreTypeEnum.PERCENTAGE.getValue().equals(item.getShowScoreType())) {
            if (item.getGradeScoreFull()!=0){
                item.setShowScoreTag(MathUtils.formatDouble2(item.getGradeScore()*100/item.getGradeScoreFull())+"%");
            }else{
                item.setShowScoreTag("0%");
            }
        }else if(ShowScoreTypeEnum.DIGIT.getValue().equals(item.getShowScoreType())) {
            item.setShowScoreTag(MathUtils.formatDouble2(item.getGradeScore())+"");
        }else if(ShowScoreTypeEnum.COMPLETION.getValue().equals(item.getShowScoreType())) {
            item.setShowScoreTag(item.getGradeScore()>0? CompleteEnum.COMPLETE.getValue():CompleteEnum.INCOMPLETE.getValue());
        }else if(ShowScoreTypeEnum.LETTER.getValue().equals(item.getShowScoreType())) {
            letterMapping(item,itemList);
        }
    }

    private void letterMapping(MyGradeBookListVo item, List<GradeSchemeItem> itemList) {
        if (CollectionUtil.isNotNullAndEmpty(itemList)) {
            //计算得分率*100
            double rate = 0;
            if (item.getGradeScoreFull()!=0){
                rate = item.getGradeScore()*100 / item.getGradeScoreFull();
            }
            for (GradeSchemeItem schemeItem : itemList) {
                if(rate>=schemeItem.getRangeStart().doubleValue()&&rate<schemeItem.getRangeEnd().longValue()){
                    item.setShowScoreTag(schemeItem.getCode());
                    break;
                }
            }
        }
    }

    private void setTotal(List<MyGradeBookListVo> modelList, Map<String, Object> rlt) {
        //汇总total score 得分/总分 排除不计总分的任务
        Double molecule = MathUtils.formatDouble2(modelList.stream().filter(a -> a.getIncludeGrade().equals(Status.YES.getStatus()))
                .map(MyGradeBookListVo::getGradeScore).reduce(Double::sum).get());
        Double denominator = MathUtils.formatDouble2(modelList.stream().filter(a -> a.getIncludeGrade().equals(Status.YES.getStatus()))
                .map(MyGradeBookListVo::getGradeScoreFull).reduce(Double::sum).get());

        rlt.put("totalScores",molecule+"/"+denominator);
        rlt.put("totalPercent", MathUtils.formatDouble2(molecule * 100 / denominator));
    }

}
