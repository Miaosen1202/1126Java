package com.wdcloud.lms.business.sis.vo;

import com.wdcloud.lms.core.base.model.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportedData {
    List<SisImportOrg> importOrgs = new ArrayList<>();
    List<SisImportTerm> sisImportTerms = new ArrayList<>();
    List<SisImportUser> sisImportUsers = new ArrayList<>();
    List<SisImportCourse> sisImportCourses = new ArrayList<>();
    List<SisImportSection> sisImportSections = new ArrayList<>();
    List<SisImportSectionUser> sisImportSectionUsers = new ArrayList<>();

    List<SisImportStudyGroupSet> sisImportStudyGroupSets = new ArrayList<>();
    List<SisImportStudyGroup> sisImportStudyGroups = new ArrayList<>();
    List<SisImportStudyGroupUser> sisImportStudyGroupUsers = new ArrayList<>();

    List<SisImportCourse> preDeleteCourses = new ArrayList<>();
}
