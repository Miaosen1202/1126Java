package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**功能：题库dto
 * @author 黄建林
 */
@Data
public class QuestionBankDTO {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 课程ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
     private Long courseId;

    /**
     * 题库名称
     */
    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    @Length(max = 256,groups = {GroupAdd.class,GroupModify.class})
    private String name;
}
