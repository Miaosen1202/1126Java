package com.wdcloud.lms.business.org.dto;//

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class OrgDTO {
    @NotNull(groups = GroupModify.class)
    private Long id;
    @NotNull(groups = {GroupAdd.class})
    private Long parentId;
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    @Length(max = 100, groups = {GroupAdd.class, GroupModify.class})
    private String name;
}
