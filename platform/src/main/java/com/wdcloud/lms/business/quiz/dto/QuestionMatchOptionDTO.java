package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**功能：匹配类问题选项dto
 *  * @author 黄建林
 */
@Data
public class QuestionMatchOptionDTO extends QuestionMatchOption {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
     private Long id;

    /**
     * 问题ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long questionId;

    /**
     * 匹配选项，空表示是一个错误匹配项
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long questionOptionId;

    /**
     * 排序
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
     private Integer seq;

    /**
     * 内容
     */
    private String content;

}