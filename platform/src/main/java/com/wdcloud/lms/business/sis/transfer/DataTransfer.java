package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.business.sis.SisImportService;
import com.wdcloud.lms.business.sis.vo.ImportedData;
import com.wdcloud.lms.business.sis.vo.SisImportProcessRecode;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTransfer {
    @Autowired
    private OrgTransfer orgTransfer;
    @Autowired
    private TermTransfer termTransfer;
    @Autowired
    private UserTransfer userTransfer;
    @Autowired
    private CourseTransfer courseTransfer;
    @Autowired
    private SectionTransfer sectionTransfer;
    @Autowired
    private SectionUserTransfer sectionUserTransfer;
    @Autowired
    private StudyGroupSetTransfer studyGroupSetTransfer;
    @Autowired
    private StudyGroupTransfer studyGroupTransfer;
    @Autowired
    private StudyGroupUserTransfer studyGroupUserTransfer;
    @Autowired
    private SisImportService sisImportService;

    private long getTransferNumber(ImportedData data) {
        long num = 0;
        if (ListUtils.isNotEmpty(data.getPreDeleteCourses())) {
            num += data.getPreDeleteCourses().size();
        }

        if (ListUtils.isNotEmpty(data.getImportOrgs())) {
            num += data.getImportOrgs().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportTerms())) {
            num += data.getSisImportTerms().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportUsers())) {
            num += data.getSisImportUsers().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportCourses())) {
            num += data.getSisImportCourses().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportSections())) {
            num += data.getSisImportSections().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportSectionUsers())) {
            num += data.getSisImportSectionUsers().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportStudyGroupSets())) {
            num += data.getSisImportStudyGroupSets().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportStudyGroups())) {
            num += data.getSisImportStudyGroups().size();
        }

        if (ListUtils.isNotEmpty(data.getSisImportStudyGroupUsers())) {
            num += data.getSisImportStudyGroupUsers().size();
        }

        return num;
    }

    public void dataTransfer(ImportedData importedData, String batchCode, Long opUserId, Org rootOrg) {
        long transferNumber = getTransferNumber(importedData);

        int numCount = 0;
        if (ListUtils.isNotEmpty(importedData.getPreDeleteCourses())) {
            courseTransfer.transfer(importedData.getPreDeleteCourses(), opUserId, rootOrg);

            numCount += importedData.getPreDeleteCourses().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getImportOrgs())) {
            orgTransfer.transfer(importedData.getImportOrgs(), opUserId, rootOrg);

            numCount += importedData.getImportOrgs().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportTerms())) {
            termTransfer.transfer(importedData.getSisImportTerms(), opUserId, rootOrg);

            numCount += importedData.getSisImportTerms().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportUsers())) {
            userTransfer.transfer(importedData.getSisImportUsers(), opUserId, rootOrg);

            numCount += importedData.getSisImportUsers().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportCourses())) {
            courseTransfer.transfer(importedData.getSisImportCourses(), opUserId, rootOrg);

            numCount += importedData.getSisImportCourses().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportSections())) {
            sectionTransfer.transfer(importedData.getSisImportSections(), opUserId, rootOrg);

            numCount += importedData.getSisImportSections().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportSectionUsers())) {
            sectionUserTransfer.transfer(importedData.getSisImportSectionUsers(), opUserId, rootOrg);

            numCount += importedData.getSisImportSectionUsers().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportStudyGroupSets())) {
            studyGroupSetTransfer.transfer(importedData.getSisImportStudyGroupSets(), opUserId, rootOrg);

            numCount += importedData.getSisImportStudyGroupSets().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportStudyGroups())) {
            studyGroupTransfer.transfer(importedData.getSisImportStudyGroups(), opUserId, rootOrg);

            numCount += importedData.getSisImportStudyGroups().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        if (ListUtils.isNotEmpty(importedData.getSisImportStudyGroupUsers())) {
            studyGroupUserTransfer.transfer(importedData.getSisImportStudyGroupUsers(), opUserId, rootOrg);

            numCount += importedData.getSisImportStudyGroupUsers().size();
            updateTransferProcess(batchCode, numCount, transferNumber);
        }

        updateTransferProcess(batchCode, transferNumber, transferNumber);
    }

    private void updateTransferProcess(String batchCode, long currentLine, long totalLine) {
        if (totalLine <= 0 || totalLine <= currentLine) {
            sisImportService.updateProcess(batchCode, SisImportProcessRecode.ProcessPhase.TRANSFER, 100);
        } else {
            sisImportService.updateProcess(batchCode, SisImportProcessRecode.ProcessPhase.TRANSFER, Math.round((float)currentLine / totalLine * 100));
        }
    }
}
