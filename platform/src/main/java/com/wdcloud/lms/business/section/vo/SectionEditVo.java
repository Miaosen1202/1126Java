package com.wdcloud.lms.business.section.vo;

import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SectionEditVo extends Section {
    @NotNull(groups = GroupModify.class)
    @Override
    public Long getId() {
        return super.getId();
    }

    @NotNull(groups = {GroupAdd.class})
    @Override
    public Long getCourseId() {
        return super.getCourseId();
    }

    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    @Size(max = 50)
    @Override
    public String getName() {
        return super.getName();
    }
}
