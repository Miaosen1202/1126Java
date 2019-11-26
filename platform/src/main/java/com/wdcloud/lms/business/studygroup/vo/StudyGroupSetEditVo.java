package com.wdcloud.lms.business.studygroup.vo;

import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class StudyGroupSetEditVo extends StudyGroupSet {

    /**
     * 初始化小组数量
     */
    private Integer createNGroup;

    @NotNull(groups = GroupModify.class)
    @Null(groups = GroupAdd.class)
    @Override
    public Long getId() {
        return super.getId();
    }

    @NotNull(groups = GroupAdd.class)
    @Override
    public Long getCourseId() {
        return super.getCourseId();
    }

    @Size(max = 100, groups = GroupAdd.class)
    @Override
    public String getName() {
        return super.getName();
    }
}
