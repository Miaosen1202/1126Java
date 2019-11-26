package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuestionGroup;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 功能：试题组dto
 * * @author 黄建林
 */

@Data
public class QuestionGroupDTO extends QuestionGroup {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long quizId;

    /**
     * 组名
     */
    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    @Length(max = 256,groups = {GroupAdd.class,GroupModify.class})
    private String name;

    /**
     * 组内每个问题得分
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer eachQuestionScore;

    /**
     * 挑选问题个数
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer pickQuestionNumber;

    /**
     * 排序
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer seq;
}