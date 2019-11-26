package com.wdcloud.lms.business.sis;

import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.business.sis.reader.*;
import com.wdcloud.lms.business.sis.transfer.DataTransfer;
import com.wdcloud.lms.business.sis.vo.ImportedData;
import com.wdcloud.lms.business.sis.vo.SisImportProcessRecode;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.Tuple;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.file.ZipUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import top.shareall.charset.detector.CharsetDetector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("all")
@Slf4j
@Component
public class SisImportTask {
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private SisImportDao sisImportDao;
    @Autowired
    private SisImportFileDao sisImportFileDao;
    @Autowired
    private SisImportErrorDao sisImportErrorDao;
    @Autowired
    private OssClient ossClient;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private SisImportService sisImportService;
    @Autowired
    private SisImportOrgDao sisImportOrgDao;
    @Autowired
    private SisImportTermDao sisImportTermDao;
    @Autowired
    private SisImportUserDao sisImportUserDao;
    @Autowired
    private SisImportCourseDao sisImportCourseDao;
    @Autowired
    private SisImportSectionDao sisImportSectionDao;
    @Autowired
    private SisImportSectionUserDao sisImportSectionUserDao;
    @Autowired
    private SisImportStudyGroupSetDao sisImportStudyGroupSetDao;
    @Autowired
    private SisImportStudyGroupDao sisImportStudyGroupDao;
    @Autowired
    private SisImportStudyGroupUserDao sisImportStudyGroupUserDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private DataTransfer dataTransfer;
    @Autowired
    private UserDao userDao;

    public static final Set<String> ZIP_FILE_SUFFIXS = Set.of("zip");

    public static final String FILE_ACCOUNT = "accounts.csv";
    public static final String FILE_USER = "user.csv";
    public static final String FILE_TERM = "terms.csv";
    public static final String FILE_COURSE = "courses.csv";
    public static final String FILE_SECTION = "sections.csv";
    public static final String FILE_ENROLLMENT = "enrollments.csv";
    public static final String FILE_GROUP_CATEGORY = "group_categories.csv";
    public static final String FILE_GROUP = "groups.csv";
    public static final String FILE_GROUP_MEMBERSHIP = "group_membership.csv";
    private static final Set<String> SIS_IMPORT_FILE_NAMES = Set.of(FILE_ACCOUNT,
            FILE_USER, FILE_TERM, FILE_COURSE, FILE_SECTION, FILE_ENROLLMENT,
            FILE_GROUP, FILE_GROUP_CATEGORY, FILE_GROUP_MEMBERSHIP);


    public void handle(SisImportEdit.SisImportDto sisImportDto, String batchCode, List<FileInfo> importFiles, Long opUserId, Org rootOrg) {
        SisImport sisImport = SisImport.builder()
                .batchCode(batchCode)
                .orgId(rootOrg.getId())
                .startTime(DateUtil.now())
                .isFullBatchUpdate(sisImportDto.getIsFullBatchUpdate())
                .opUserId(opUserId)
                .opUserOrgTreeId(rootOrg.getTreeId())
                .build();
        try {
            sisImportDao.save(sisImport);

            List<SisImportError> sisImportErrors = new ArrayList<>();

            List<Tuple<FileInfo, Path>> validFiles
                    = fileCheck(importFiles, StringUtil.isEmpty(sisImportDto.getZipFileId()) ? Status.NO : Status.YES, batchCode, opUserId, sisImportErrors, rootOrg);
            for (SisImportError sisImportError : sisImportErrors) {
                sisImportError.setBatchCode(batchCode);
                sisImportError.setOpUserId(opUserId);
                sisImportError.setOpUserOrgTreeId(rootOrg.getTreeId());
            }
            sisImportErrorDao.batchSave(sisImportErrors);
            sisImportErrors = new ArrayList<>();

            if (ListUtils.isEmpty(validFiles)) {
                return;
            }

            // 导入csv文件中数据到中间表
            ImportedData importedData = importData(sisImportDto, validFiles, sisImportErrors, batchCode, opUserId, rootOrg);

            sisImport.setOrgNumber(importedData.getImportOrgs().size());
            sisImport.setTermNumber(importedData.getSisImportTerms().size());
            sisImport.setUserNumber(importedData.getSisImportUsers().size());
            sisImport.setCourseNumber(importedData.getSisImportCourses().size());
            sisImport.setSectionNumber(importedData.getSisImportSections().size());
            sisImport.setSectionUserNumber(importedData.getSisImportSectionUsers().size());
            sisImport.setStudyGroupSetNumber(importedData.getSisImportStudyGroupSets().size());
            sisImport.setStudyGroupNumber(importedData.getSisImportStudyGroups().size());
            sisImport.setStudyGroupUserNumber(importedData.getSisImportStudyGroupUsers().size());

            int total = importedData.getImportOrgs().size() + importedData.getSisImportTerms().size()
                    + importedData.getSisImportUsers().size() + importedData.getSisImportCourses().size()
                    + importedData.getSisImportSections().size() + importedData.getSisImportSectionUsers().size()
                    + importedData.getSisImportStudyGroupSets().size() + importedData.getSisImportStudyGroups().size()
                    + importedData.getSisImportStudyGroupUsers().size();
            sisImport.setTotalNumber(total);
            sisImportDao.update(sisImport);

            for (SisImportError sisImportError : sisImportErrors) {
                sisImportError.setBatchCode(batchCode);
                sisImportError.setOpUserId(opUserId);
                sisImportError.setOpUserOrgTreeId(rootOrg.getTreeId());
            }
            sisImportErrorDao.batchSave(sisImportErrors);

            // 批量更新，删除学期下数据
            if (Objects.equals(sisImportDto.getIsFullBatchUpdate(), Status.YES.getStatus())
                    && ListUtils.isNotEmpty(importedData.getSisImportCourses())) {
                ImportedData data = doBatchUpdate(sisImportDto, batchCode, rootOrg.getTreeId());
                importedData.setPreDeleteCourses(data.getPreDeleteCourses());
            }

            dataTransfer.dataTransfer(importedData, batchCode, opUserId, rootOrg);
        } finally {
            try {
                sisImport.setEndTime(new Date());
                sisImportDao.update(sisImport);

                sisImportService.updateTotalPercent(batchCode, 100);
            } catch (Exception e) {
                log.debug("Import tash exception={}", ExceptionUtils.getFullStackTrace(e), e);
            }
            sisImportService.unlock(rootOrg);
        }
    }

    private ImportedData doBatchUpdate(SisImportEdit.SisImportDto sisImportDto, String batchCode, String treeId) {
        ImportedData result = new ImportedData();

        Term term = termDao.get(sisImportDto.getTermId());

        Example courseExample = sisImportCourseDao.getExample();
        Example.Criteria criteria = courseExample.createCriteria()
                .andNotEqualTo(SisImportCourse.BATCH_CODE, batchCode)
                .andLike(SisImportCourse.ORG_TREE_ID, treeId + "%");
        if (Objects.equals(term.getIsDefault(), Status.YES.getStatus())) {
            criteria.andEqualTo(SisImportCourse.TERM_ID, "");
        } else {
            criteria.andEqualTo(SisImportCourse.TERM_ID, term.getSisId());
        }
        List<SisImportCourse> deleteCourses = sisImportCourseDao.find(courseExample);

        for (SisImportCourse deleteCourse : deleteCourses) {
            deleteCourse.setBatchCode(batchCode);
            deleteCourse.setOperation(OperationTypeEnum.DELETED.getType());
        }
        sisImportCourseDao.batchSaveOrUpdate(deleteCourses);

        result.setPreDeleteCourses(deleteCourses);

        // 课程数据只变更状态，其他相关数据删除
        List<String> deleteCourseIds = deleteCourses.stream()
                .map(SisImportCourse::getCourseId)
                .collect(Collectors.toList());
        Example sectionExample = sisImportSectionDao.getExample();
        sectionExample.createCriteria()
                .andIn(SisImportSection.COURSE_ID, deleteCourseIds)
                .andLike(SisImportSection.ORG_TREE_ID, treeId + "%");
        List<SisImportSection> deleteSections = sisImportSectionDao.find(sectionExample);
        for (SisImportSection deleteSection : deleteSections) {
            deleteSection.setBatchCode(batchCode);
            deleteSection.setOperation(OperationTypeEnum.DELETED.getType());
        }
        sisImportSectionDao.batchSaveOrUpdate(deleteSections);

        return result;
    }


    private ImportedData importData(SisImportEdit.SisImportDto sisImportDto, List<Tuple<FileInfo, Path>> validFiles,
                            List<SisImportError> importErrors, String batchCode, Long opUserId, Org rootOrg) {

        ImportedData importedData = new ImportedData();
        if (ListUtils.isEmpty(validFiles)) {
            return importedData;
        }

        Map<String, Path> fileNameMap = new HashMap<>();
        for (Tuple<FileInfo, Path> validFile : validFiles) {
            fileNameMap.put(validFile.getOne().getOriginName(), validFile.getTwo());
        }

        DataImportFileLineInfo fileLineInfo = calcFileLine(validFiles, batchCode, opUserId);

        long currentDataNum = 0;
        if (Objects.equals(sisImportDto.getIsFullBatchUpdate(), Status.NO.getStatus())) {
            if (fileNameMap.containsKey(FILE_ACCOUNT)) {
                List<SisImportOrg> importOrgs = importAccounts(fileNameMap.get(FILE_ACCOUNT), batchCode, opUserId, rootOrg);
                importedData.setImportOrgs(importOrgs);

                currentDataNum += fileLineInfo.getOrgFileLine();
                updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
            }
            if (fileNameMap.containsKey(FILE_TERM)) {
                List<SisImportTerm> importTerms = importTerms(fileNameMap.get(FILE_TERM), batchCode, opUserId, rootOrg);
                importedData.setSisImportTerms(importTerms);

                currentDataNum += fileLineInfo.getTermFileLine();
                updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
            }
            if (fileNameMap.containsKey(FILE_USER)) {
                List<SisImportUser> sisImportUsers = importUsers(fileNameMap.get(FILE_USER), batchCode, opUserId, rootOrg);
                importedData.setSisImportUsers(sisImportUsers);

                currentDataNum += fileLineInfo.getUserFileLine();
                updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
            }
        }

        if (fileNameMap.containsKey(FILE_COURSE)) {
            List<SisImportCourse> sisImportCourses = importCourses(fileNameMap.get(FILE_COURSE), batchCode, opUserId, rootOrg);
            importedData.setSisImportCourses(sisImportCourses);

            currentDataNum += fileLineInfo.getCourseFileLine();
            updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
        }
        if (fileNameMap.containsKey(FILE_SECTION)) {
            List<SisImportSection> sisImportSections = importSections(fileNameMap.get(FILE_SECTION), batchCode, opUserId, rootOrg);
            importedData.setSisImportSections(sisImportSections);

            currentDataNum += fileLineInfo.getSectionFileLine();
            updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
        }
        if (fileNameMap.containsKey(FILE_ENROLLMENT)) {
            List<SisImportSectionUser> sisImportSectionUsers = importEnrollments(fileNameMap.get(FILE_ENROLLMENT), batchCode, opUserId, rootOrg);
            importedData.setSisImportSectionUsers(sisImportSectionUsers);

            currentDataNum += fileLineInfo.getSectionUserFileLine();
            updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
        }
        if (fileNameMap.containsKey(FILE_GROUP_CATEGORY)) {
            List<SisImportStudyGroupSet> sisImportStudyGroupSets = importGroupCategories(fileNameMap.get(FILE_GROUP_CATEGORY), batchCode, opUserId, rootOrg);
            importedData.setSisImportStudyGroupSets(sisImportStudyGroupSets);

            currentDataNum += fileLineInfo.getGroupSetFileLine();
            updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
        }
        if (fileNameMap.containsKey(FILE_GROUP)) {
            List<SisImportStudyGroup> sisImportStudyGroups = importGroups(fileNameMap.get(FILE_GROUP), batchCode, opUserId, rootOrg);
            importedData.setSisImportStudyGroups(sisImportStudyGroups);

            currentDataNum += fileLineInfo.getGroupFileLine();
            updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
        }
        if (fileNameMap.containsKey(FILE_GROUP_MEMBERSHIP)) {
            List<SisImportStudyGroupUser> sisImportStudyGroupUsers = importGroupMemberships(fileNameMap.get(FILE_GROUP_MEMBERSHIP), batchCode, opUserId, rootOrg);
            importedData.setSisImportStudyGroupUsers(sisImportStudyGroupUsers);

            currentDataNum += fileLineInfo.getGroupUserFileLine();
            updateImportProcess(batchCode, currentDataNum, fileLineInfo.getTotalFileLine());
        }

        updateImportProcess(batchCode, fileLineInfo.getTotalFileLine(), fileLineInfo.getTotalFileLine());

        return importedData;
    }

    private void updateImportProcess(String batchCode, long currentLine, long totalLine) {
        if (totalLine <= 0 || totalLine <= currentLine) {
            sisImportService.updateProcess(batchCode, SisImportProcessRecode.ProcessPhase.IMPORT, 100);
        } else {
            sisImportService.updateProcess(batchCode, SisImportProcessRecode.ProcessPhase.IMPORT, Math.round((float)currentLine / totalLine * 100));
        }
    }

    private List<SisImportOrg> importAccounts(Path accountsCsv, String batchCode, Long opUserId, Org rootOrg) {
        OrgReader orgReader = new OrgReader(accountsCsv, rootOrg, sisImportOrgDao);
        List<SisImportOrg> sisImportOrgs = orgReader.read();

        saveErrors(orgReader.getErrors(), FILE_ACCOUNT, batchCode, opUserId);

        if (ListUtils.isNotEmpty(sisImportOrgs)) {
            for (SisImportOrg sisImportOrg : sisImportOrgs) {
                sisImportOrg.setBatchCode(batchCode);
                sisImportOrg.setOpUserId(opUserId);
                sisImportOrg.setOpUserOrgTreeId(rootOrg.getTreeId());
                sisImportOrg.setOrgTreeId(rootOrg.getTreeId());
            }
            sisImportOrgDao.batchSaveOrUpdate(sisImportOrgs);
        }

        return sisImportOrgs;
    }

    private List<SisImportTerm> importTerms(Path termsCsv, String batchCode, Long opUserId, Org rootOrg) {
        TermReader termReader = new TermReader(termsCsv, rootOrg, sisImportTermDao);
        List<SisImportTerm> importTerms = termReader.read();

        saveErrors(termReader.getErrors(), FILE_TERM, batchCode, opUserId);

        for (SisImportTerm importTerm : importTerms) {
            importTerm.setBatchCode(batchCode);
            importTerm.setOpUserId(opUserId);
            importTerm.setOpUserOrgTreeId(rootOrg.getTreeId());
            importTerm.setOrgTreeId(rootOrg.getTreeId());
        }
        sisImportTermDao.batchSaveOrUpdate(importTerms);

        return importTerms;
    }

    private List<SisImportUser> importUsers(Path usersCsv, String batchCode, Long opUserId, Org opUserOrg) {
        UserReader userReader = new UserReader(usersCsv, opUserOrg, sisImportUserDao, sisImportOrgDao, userDao);
        List<SisImportUser> importUsers = userReader.read();

        saveErrors(userReader.getErrors(), FILE_USER, batchCode, opUserId);

        for (SisImportUser importUser : importUsers) {
            importUser.setBatchCode(batchCode);
            importUser.setOpUserId(opUserId);
            importUser.setOpUserOrgTreeId(opUserOrg.getTreeId());
            importUser.setOrgTreeId(opUserOrg.getTreeId());
        }
        sisImportUserDao.batchSaveOrUpdate(importUsers);

        return importUsers;
    }

    private List<SisImportCourse> importCourses(Path coursesCsv, String batchCode, Long opUserId, Org opUserOrg) {
        CourseReader courseReader = new CourseReader(coursesCsv, opUserOrg, sisImportCourseDao, sisImportOrgDao, sisImportTermDao);
        List<SisImportCourse> importCourses = courseReader.read();

        saveErrors(courseReader.getErrors(), FILE_COURSE, batchCode, opUserId);

        for (SisImportCourse importCourse : importCourses) {
            importCourse.setBatchCode(batchCode);
            importCourse.setOpUserId(opUserId);
            importCourse.setOpUserOrgTreeId(opUserOrg.getTreeId());
            importCourse.setOrgTreeId(opUserOrg.getTreeId());
        }

        sisImportCourseDao.batchSaveOrUpdate(importCourses);
        return importCourses;
    }

    private List<SisImportSection> importSections(Path sectionsCsv, String batchCode, Long opUserId, Org opUserOrg) {
        SectionReader reader = new SectionReader(sectionsCsv, opUserOrg, sisImportSectionDao, sisImportCourseDao);
        List<SisImportSection> sections = reader.read();
        saveErrors(reader.getErrors(), FILE_SECTION, batchCode, opUserId);

        for (SisImportSection section : sections) {
            section.setBatchCode(batchCode);
            section.setOpUserId(opUserId);
            section.setOpUserOrgTreeId(opUserOrg.getTreeId());
            section.setOrgTreeId(opUserOrg.getTreeId());
        }
        sisImportSectionDao.batchSaveOrUpdate(sections);
        return sections;
    }

    private List<SisImportSectionUser> importEnrollments(Path enrollmentsCsv, String batchCode, Long opUserId, Org opUserOrg) {
        SectionUserReader reader = new SectionUserReader(enrollmentsCsv, opUserOrg, sisImportCourseDao, sisImportSectionDao,
                sisImportUserDao, sisImportSectionUserDao);
        List<SisImportSectionUser> sectionUsers = reader.read();

        saveErrors(reader.getErrors(), FILE_ENROLLMENT, batchCode, opUserId);
        for (SisImportSectionUser sectionUser : sectionUsers) {
            sectionUser.setBatchCode(batchCode);
            sectionUser.setOpUserId(opUserId);
            sectionUser.setOpUserOrgTreeId(opUserOrg.getTreeId());
            sectionUser.setOrgTreeId(opUserOrg.getTreeId());
        }

        sisImportSectionUserDao.batchSaveOrUpdate(sectionUsers);
        return sectionUsers;
    }

    private List<SisImportStudyGroupSet> importGroupCategories(Path groupCategoriesCsv, String batchCode, Long opUserId, Org opUserOrg) {
        StudyGroupSetReader reader = new StudyGroupSetReader(groupCategoriesCsv, opUserOrg, sisImportCourseDao, sisImportStudyGroupSetDao);
        List<SisImportStudyGroupSet> studyGroupSets = reader.read();

        saveErrors(reader.getErrors(), FILE_GROUP_CATEGORY, batchCode, opUserId);

        for (SisImportStudyGroupSet studyGroupSet : studyGroupSets) {
            studyGroupSet.setBatchCode(batchCode);
            studyGroupSet.setOpUserId(opUserId);
            studyGroupSet.setOpUserOrgTreeId(opUserOrg.getTreeId());
            studyGroupSet.setOrgTreeId(opUserOrg.getTreeId());
        }

        sisImportStudyGroupSetDao.batchSaveOrUpdate(studyGroupSets);
        return studyGroupSets;
    }

    private List<SisImportStudyGroup> importGroups(Path groupsCsv, String batchCode, Long opUserId, Org opUserOrg) {
        StudyGroupReader reader = new StudyGroupReader(groupsCsv, opUserOrg, sisImportStudyGroupSetDao, sisImportStudyGroupDao);
        List<SisImportStudyGroup> imports = reader.read();

        saveErrors(reader.getErrors(), FILE_GROUP, batchCode, opUserId);
        for (SisImportStudyGroup studyGroup : imports) {
            studyGroup.setBatchCode(batchCode);
            studyGroup.setOpUserId(opUserId);
            studyGroup.setOpUserOrgTreeId(opUserOrg.getTreeId());
            studyGroup.setOrgTreeId(opUserOrg.getTreeId());
        }
        sisImportStudyGroupDao.batchSaveOrUpdate(imports);
        return imports;
    }

    private List<SisImportStudyGroupUser> importGroupMemberships(Path groupMembersCsv, String batchCode, Long opUserId, Org opUserOrg) {
        StudyGroupUserReader reader = new StudyGroupUserReader(groupMembersCsv, opUserOrg, sisImportStudyGroupSetDao, sisImportStudyGroupDao, sisImportSectionUserDao);
        List<SisImportStudyGroupUser> imports = reader.read();

        saveErrors(reader.getErrors(), FILE_GROUP_MEMBERSHIP, batchCode, opUserId);

        for (SisImportStudyGroupUser groupUser : imports) {
            groupUser.setBatchCode(batchCode);
            groupUser.setOpUserId(opUserId);
            groupUser.setOpUserOrgTreeId(opUserOrg.getTreeId());
        }

        sisImportStudyGroupUserDao.batchSave(imports);
        return imports;
    }

    private void saveErrors(List<ReaderError> errors, String fileName, String batchCode, Long opUserId) {
        if (ListUtils.isNotEmpty(errors)) {
            List<SisImportError> importErrors = new ArrayList<>(errors.size());
            for (ReaderError error : errors) {
                importErrors.add(SisImportError.builder()
                        .fileName(fileName)
                        .errorCode(error.getCode().getCode())
                        .batchCode(batchCode)
                        .fieldName(error.getFieldName())
                        .fieldValue(error.getFieldValue())
                        .rowNumber(error.getRowNumber())
                        .opUserId(opUserId)
                        .build());
            }
            sisImportErrorDao.batchSave(importErrors);
        }
    }

    /**
     * 检查并下载文件
     *
     * @param importFiles
     * @param isZipImport
     * @param batchCode
     * @param opUserId
     * @param importErrors 文件错误信息会添加到该列表中返回
     * @return 返回正确的csv文件，格式为 (fileInfo, file) 键值对列表
     */
    private List<Tuple<FileInfo, Path>> fileCheck(List<FileInfo> importFiles, Status isZipImport, String batchCode,
                                                  Long opUserId, List<SisImportError> importErrors, Org rootOrg) {
        List<FileInfo> validFileInfos = new ArrayList<>();


        if (Objects.equals(isZipImport, Status.YES)) {
            FileInfo fileInfo = importFiles.get(0);

            sisImportFileDao.save(SisImportFile.builder()
                    .batchCode(batchCode)
                    .fileId(fileInfo.getFileId())
                    .fileName(fileInfo.getOriginName())
                    .opUserId(opUserId)
                    .opUserOrgTreeId(rootOrg.getTreeId())
                    .build());

            if (!ZIP_FILE_SUFFIXS.contains(fileInfo.getFileType())) {
                importErrors.add(SisImportError.builder()
                        .batchCode(batchCode)
                        .errorCode(ErrorCodeEnum.UNKNOWN_FILE.getCode())
                        .fileName(fileInfo.getOriginName())
                        .opUserId(opUserId)
                        .opUserOrgTreeId(rootOrg.getTreeId())
                        .build());
            } else {
                validFileInfos.add(fileInfo);
            }
        } else {
            int index = 0;
            for (FileInfo fileInfo : importFiles) {
                index++;

                sisImportFileDao.save(SisImportFile.builder()
                        .batchCode(batchCode)
                        .fileId(fileInfo.getFileId())
                        .fileName(fileInfo.getOriginName())
                        .opUserId(opUserId)
                        .opUserOrgTreeId(rootOrg.getTreeId())
                        .build());

                if (!SIS_IMPORT_FILE_NAMES.contains(fileInfo.getOriginName())) {
                    importErrors.add(SisImportError.builder()
                            .batchCode(batchCode)
                            .errorCode(ErrorCodeEnum.UNKNOWN_FILE.getCode())
                            .fileName(fileInfo.getOriginName())
                            .opUserId(opUserId)
                            .opUserOrgTreeId(rootOrg.getTreeId())
                            .build());
                } else {
                    validFileInfos.add(fileInfo);
                }
            }
        }

        List<Tuple<FileInfo, Path>> files = new ArrayList<>(validFileInfos.size());
        for (int i = 0; i < validFileInfos.size(); i++) {
            FileInfo fileInfo = validFileInfos.get(i);
            Path file = null;
            try {
                file = ossClient.download(fileInfo.getFileId());
            } catch (Exception e) {
                // 文件服务器获取文件出错
                importErrors.add(SisImportError.builder()
                        .fileName(fileInfo.getOriginName())
                        .errorCode(ErrorCodeEnum.UNKNOWN_ERR.getCode())
                        .opUserId(opUserId)
                        .build());
                continue;
            }

            if (file == null) {
                importErrors.add(SisImportError.builder()
                        .fileName(fileInfo.getOriginName())
                        .errorCode(ErrorCodeEnum.FILE_NOT_EXISTS.getCode())
                        .opUserId(opUserId)
                        .build());
                continue;
            }

            if (file != null) {
//                files.add(new Tuple<>(fileInfo, file));
                if (ZIP_FILE_SUFFIXS.contains(fileInfo.getFileType())) {
                    String charset = null;
                    try {
                        charset = CharsetDetector.detect(file.toFile());
                    } catch (IOException e) {
                        charset = "UTF-8";
                        log.debug("[SisImportTask] detect file charset error, use utf-8, file={}", fileInfo.getOriginName());
                    }
                    try {
                        List<File> unZipFiles = ZipUtils.unzip(file.toFile(), charset);
                        for (File unZipFile : unZipFiles) {
                            if (!SIS_IMPORT_FILE_NAMES.contains(unZipFile.getName())) {
                                importErrors.add(SisImportError.builder()
                                        .batchCode(batchCode)
                                        .fileName(unZipFile.getName())
                                        .errorCode(ErrorCodeEnum.UNKNOWN_FILE.getCode())
                                        .opUserId(opUserId)
                                        .opUserOrgTreeId(rootOrg.getTreeId())
                                        .build());
                            } else {
                                files.add(new Tuple<>(FileInfo.builder().originName(unZipFile.getName()).build(), unZipFile.toPath()));
                            }
                        }
                    } catch (IOException e) {
                        importErrors.add(SisImportError.builder()
                                .fileName(fileInfo.getOriginName())
                                .errorCode(ErrorCodeEnum.FILE_FORMAT_ERR.getCode())
                                .opUserId(opUserId)
                                .build());
                    }
                } else {
                    files.add(new Tuple<>(fileInfo, file));
                }
            }

            sisImportService.updateProcess(batchCode, SisImportProcessRecode.ProcessPhase.FILE_CHECK,
                    Math.round(((float) i + 1) / validFileInfos.size() * 100));
        }

        sisImportService.updateProcess(batchCode, SisImportProcessRecode.ProcessPhase.FILE_CHECK, 100);
        return files;
    }

    @Data
    class DataImportFileLineInfo {
        private Long orgFileLine;
        private Long termFileLine;
        private Long userFileLine;
        private Long courseFileLine;
        private Long sectionFileLine;
        private Long sectionUserFileLine;
        private Long groupSetFileLine;
        private Long groupFileLine;
        private Long groupUserFileLine;
        private Long totalFileLine;
    }

    private DataImportFileLineInfo calcFileLine(List<Tuple<FileInfo, Path>> validFiles, String batchCode, Long opUserId) {
        DataImportFileLineInfo fileLineInfo = new DataImportFileLineInfo();

        long totalLine = 0;
        for (Tuple<FileInfo, Path> validFile : validFiles) {

            long count = 0;
            try {
                Path path = validFile.getTwo();
                if (Files.exists(path) && Files.isRegularFile(path)) {
                    count = Files
                            .lines(path, Charset.forName("UTF-8"))
                            .count();
                }
//            } catch (MalformedInputException e) {
//                saveErrors(List.of(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, 0L)), validFile.getOne().getOriginName(), batchCode, opUserId);
            } catch (Exception e) {
                count = Integer.MAX_VALUE;
//                saveErrors(List.of(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, 0L)), validFile.getOne().getOriginName(), batchCode, opUserId);
            }

            switch (validFile.getOne().getOriginName()) {
                case FILE_ACCOUNT:
                    fileLineInfo.setOrgFileLine(count);
                    break;
                case FILE_TERM:
                    fileLineInfo.setTermFileLine(count);
                    break;
                case FILE_USER:
                    fileLineInfo.setUserFileLine(count);
                    break;
                case FILE_COURSE:
                    fileLineInfo.setCourseFileLine(count);
                    break;
                case FILE_SECTION:
                    fileLineInfo.setSectionFileLine(count);
                    break;
                case FILE_ENROLLMENT:
                    fileLineInfo.setSectionUserFileLine(count);
                    break;
                case FILE_GROUP_CATEGORY:
                    fileLineInfo.setGroupSetFileLine(count);
                    break;
                case FILE_GROUP:
                    fileLineInfo.setGroupFileLine(count);
                    break;
                case FILE_GROUP_MEMBERSHIP:
                    fileLineInfo.setGroupUserFileLine(count);
                    break;
                default:
                    count = 0;
                    break;
            }
            totalLine += count;
        }

        fileLineInfo.setTotalFileLine(totalLine);

        return fileLineInfo;
    }

}
