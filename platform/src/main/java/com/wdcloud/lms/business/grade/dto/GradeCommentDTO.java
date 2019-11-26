package com.wdcloud.lms.business.grade.dto;

import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author zhangxutao
 * 删除评论的dto
 */
public class GradeCommentDTO extends GradeComment {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;
}
