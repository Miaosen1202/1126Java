package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuizItem;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**功能：测验问题项dto
 *  * @author 黄建林
 */
@Data
public class QuizItemDTO  extends QuizItem {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 测验ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizId;

    /**
     * 类型，1. 问题 2. 问题组
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer type;

    /**
     * 问题/问题组ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long targetId;

    /**
     * 排序
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer seq;


}