package com.wdcloud.lms.business.calender.dto;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.validate.groups.GroupDelete;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class AssignDTO extends Assign {
    @NotNull(groups = GroupDelete.class)
    @Range(min = 1,max = 3,groups = GroupDelete.class)
    private Integer originType;
    @NotNull(groups = GroupDelete.class)
    private Long assignTableId;
}