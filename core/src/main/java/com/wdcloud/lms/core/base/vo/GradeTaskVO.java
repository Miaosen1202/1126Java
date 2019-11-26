package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import lombok.Data;

import java.util.List;

@Data
public class GradeTaskVO extends AssignmentGroupItem {
    /*
    * 任务的分配列表
    * */
    private List<Assign> fplb;

    /*
     * 任务的评论个数
     * */
    private int plgs;

    /**
     * 是小组任务还是个人任务
     */
    private String isGradeAssignment;

    /**
     * 任务已评分的个数
     */
    private Integer graderIdcount;

    /**
     * 任务未评分的个数
     */
    private Integer unGraderIdcount;

    /**
     * 任务的平均分
     */
    private Integer taskAvg;
    private Integer showScoreType;

}
