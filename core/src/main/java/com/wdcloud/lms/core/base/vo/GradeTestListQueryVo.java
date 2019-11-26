package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GradeTestListQueryVo{
    public Long id;

    /**
     * 任务名字
     */
    public String title;

    /**
     * 来源ID
     */
    public Long originId;

    /**
     * 任务类型： 1: 作业 2: 讨论 3: 测验
     */
    public Integer originType;

    /**
     * 任务总分
     */
    public Long score;

    /**
     * 是小组任务还是个人任务
     */
    public Long assignmentGroupId;

    public String name;
    public BigDecimal weight;

    /**
     * 是否包含到总成绩统计里
     */
    public String isIncludeGrade;
    /**
     * 评分方式 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
     */
    public Integer showScoreType;

    //代码构造

    /**
     * 学生的提交情况列表
     */
    public List<UserSubmitRecord> submitGradeQuery;

    /**
     * 任务分配列表
     */
    public List<Assign> assignsList;

    /**
     * 是小组任务还是个人任务
     */
    public String isGradeAssignment;

    /**
     * 学习小组id
     */
    public Long  studyGroupSetId;

    /**
     * 测验
     */
    public Quiz quizOne;

}
