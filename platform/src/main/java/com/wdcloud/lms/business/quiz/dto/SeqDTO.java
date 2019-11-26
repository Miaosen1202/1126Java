package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**功能：Seqdto
 * @author 黄建林
 */
@Data
public class SeqDTO {
    /**
     * 操作类型
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer operate;
    /**
     * 测验ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizId;
    /**
     * 问题类型
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer questionType;
    /**
     * 被移动的问题或问题组的ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long moveId;
    /**
     * 移动到那个ID前面
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long beforeId;
}
