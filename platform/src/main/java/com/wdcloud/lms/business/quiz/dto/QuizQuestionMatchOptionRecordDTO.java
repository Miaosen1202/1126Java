package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**功能：匹配类问题选项选择记录dto
 *  * @author 黄建林
 */
@Data
public class QuizQuestionMatchOptionRecordDTO extends QuizQuestionMatchOptionRecord {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 匹配类问题选项
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long questionMatchOptionId;

    /**
     * 问题记录ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizQuestionRecordId;

    /**
     * 选项ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizQuestionOptionRecordId;

    /**
     * 内容
     */
    private String content;

}