package com.wdcloud.lms.business.syllabus.dto;

import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *  @author 米照雅
 */
@Data
public class SyllabusDTO{
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 课程ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long courseId;

    /**
     * 评论内容
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
     private String comments;

}
