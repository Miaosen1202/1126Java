//package com.wdcloud.lms.business.studygroup.vo;
//
//import com.wdcloud.lms.core.base.model.StudyGroup;
//import com.wdcloud.validate.groups.GroupAdd;
//import com.wdcloud.validate.groups.GroupModify;
//import lombok.Data;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Null;
//
//@Data
//public class StudyGroupVo extends StudyGroup {
//
//    @NotNull(groups = GroupModify.class)
//    @Override
//    public Long getId() {
//        return super.getId();
//    }
//
//    @NotNull(groups = {GroupAdd.class})
//    @Null(groups = GroupModify.class)
//    @Override
//    public Long getStudyGroupSetId() {
//        return super.getStudyGroupSetId();
//    }
//
//    @Null(groups = {GroupAdd.class, GroupModify.class})
//    @Override
//    public Long getCourseId() {
//        return super.getCourseId();
//    }
//
//}
