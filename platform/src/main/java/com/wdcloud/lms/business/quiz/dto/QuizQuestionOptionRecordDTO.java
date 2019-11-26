package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**功能：测验问题选项记录dto
 *  * @author 黄建林
 */
@Data
public class QuizQuestionOptionRecordDTO extends QuizQuestionOptionRecord {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 问题记录ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizQuestionRecordId;

    /**
     * 问题选项ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long questionOptionId;

    /**
     * 题干中占位符
     */
    @Length(max = 256,groups = {GroupAdd.class, GroupModify.class})
    private String code;

    /**
     * 选择题：是否为正确选项
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer isCorrect;

    /**
     * 排序：如果是重排序选项，则是重排序后的选项顺序
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer seq;

    /**
     * 是否选中
     */
    private Integer isChoice;

    /**
     * 内容
     */
    private String content;

    /**
     * 选择该项的提示
     */
    private String explanation;

}