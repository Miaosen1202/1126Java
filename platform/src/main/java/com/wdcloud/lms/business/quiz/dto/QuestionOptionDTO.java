package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**功能：问题选项dto
 *  * @author 黄建林
 */
@Data
public class QuestionOptionDTO extends QuestionOption {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 问题ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long questionId;

    /**
     * 题干中占位符
     */
    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    @Length(max = 256,groups = {GroupAdd.class,GroupModify.class})
    private String code;

    /**
     * 选择题：是否为正确选项
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer isCorrect;

    /**
     * 排序
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer seq;

    /**
     * 内容
     */
    private String content;

    /**
     * 选择该项的提示
     */
    private String explanation;
}
