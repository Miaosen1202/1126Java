package com.wdcloud.lms.business.grade.vo;

import com.wdcloud.lms.core.base.model.Assign;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * @author 张旭涛
 * 批量、单个评分基础信息返回值
 */
@Data
public class GradeInfoVo {
    //评分类型 1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
    private Integer showScoreType;

    private Integer originType;
    private Long originId;
    private String gradeType;
    private String originName;
    private List<Assign> close;
    private String score;
    private Long courseId;
    private Long assignmentGroupItemId;
    private Integer releaseType;
    private StringBuilder submissionType;

}
