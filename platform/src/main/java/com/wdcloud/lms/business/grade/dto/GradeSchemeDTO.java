package com.wdcloud.lms.business.grade.dto;

import com.wdcloud.lms.core.base.model.GradeSchemeItem;
import com.wdcloud.validate.groups.GroupAdd;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wangff
 * @date 2019/9/5 15:51
 */
@Data
public class GradeSchemeDTO {
    @NotNull(groups = GroupAdd.class)
    private Long courseId;

    @NotEmpty(groups = GroupAdd.class)
    private List<GradeSchemeItem> schemeItems;
}
