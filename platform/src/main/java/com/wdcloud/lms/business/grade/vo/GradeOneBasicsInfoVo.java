package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author zhangxutao
 */
@Data
public class GradeOneBasicsInfoVo {
    /**
     * // 任务状态 On time / Late / Missing
     */
    private String originState;
    /**
     * //提交时间
     */
    private Date submittedTime;
    /**
     * //答题用时 只有任务类型是测验的时候显示
     */
    private String attemptTook;
    /**
     * //作业跟讨论 可以提交多次 显示多次提交时间 ；测验不显示【屏蔽】
     */
    private List<Date> submissionToView;


}
