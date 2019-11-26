package com.wdcloud.lms.business.assignment.dto;

import com.google.common.collect.Lists;
import com.wdcloud.lms.core.base.model.Assign;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Builder
@Data
public class AssignmentDTO {
    private Long id;

    /**
     * 课程ID
     */
    @NotNull
    private Long courseId;
    /**
     * 修改传过来
     */
    private Long assignmentGroupItemId;

    /**
     * 标题
     */
    @NotNull
    private String title;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分
     */
    private Integer gradeType;

    /**
     * 评分方案
     */
    private Long gradeSchemeId;

    /**
     * 包含到总成绩统计里
     */
    private Integer isIncludeGrade;

    /**
     * 提交类型, 1. 在线、2. 书面、3. 外部工具、 4. 不提交
     */
    private Integer submissionType;

    /**
     * 外部工具URL，submission_type=3
     */
    private String toolUrl;

    /**
     * 内嵌工具（不在新窗口打开工具）
     */
    private Integer isEmbedTool;

    /**
     * 在线提交submission_type=1：文本输入
     */
    private Integer allowText;

    /**
     * 在线提交submission_type=1：网站地址
     */
    private Integer allowUrl;

    /**
     * 在线提交submission_type=1：媒体录音
     */
    private Integer allowMedia;

    /**
     * 在线提交submission_type=1：文件上传
     */
    private Integer allowFile;

    /**
     * 在线提交submission_type=1&allow_file=1: 上传文件类型限制
     */
    private String fileLimit;

    /**
     * 小组作业，小组集ID
     */
    private Long studyGroupSetId;

    /**
     * 分别为每位学生指定评分
     */
    private Integer isGradeEachOne;

    /**
     * 发布状态
     */
    private Integer status;
    private String description;
    @NotNull
    private Long assignmentGroupId;
    private List<Assign> assign = Lists.newArrayList();

    private Integer showScoreType;
}
