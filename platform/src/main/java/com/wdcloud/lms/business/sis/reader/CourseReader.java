package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.business.sis.enums.SisCourseFormatEnum;
import com.wdcloud.lms.core.base.dao.SisImportCourseDao;
import com.wdcloud.lms.core.base.dao.SisImportOrgDao;
import com.wdcloud.lms.core.base.dao.SisImportTermDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportCourse;
import com.wdcloud.lms.core.base.model.SisImportOrg;
import com.wdcloud.lms.core.base.model.SisImportTerm;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class CourseReader extends AbstractSisCsvReader<SisImportCourse> {
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_SHORT_NAME = "short_name";
    private static final String FIELD_LONG_NAME = "long_name";
    private static final String FIELD_COURSE_FORMAT = "course_format";
    private static final String FIELD_ACCOUNT_ID = "account_id";
    private static final String FIELD_TERM_ID = "term_id";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_START_DATE = "start_date";
    private static final String FIELD_END_DATE = "end_date";

    private SisImportCourseDao sisImportCourseDao;
    private SisImportOrgDao sisImportOrgDao;
    private SisImportTermDao sisImportTermDao;
    private Org opUserOrg;

    public CourseReader(Path csvFile, Org opUserOrg, SisImportCourseDao sisImportCourseDao, SisImportOrgDao sisImportOrgDao,
                        SisImportTermDao sisImportTermDao) {
        super(
                csvFile,
                Set.of(FIELD_COURSE_ID, FIELD_SHORT_NAME, FIELD_LONG_NAME,
                        FIELD_COURSE_FORMAT, FIELD_ACCOUNT_ID, FIELD_TERM_ID, FIELD_START_DATE, FIELD_END_DATE, FIELD_STATUS),
                Set.of(FIELD_COURSE_ID, FIELD_SHORT_NAME, FIELD_LONG_NAME, FIELD_STATUS)
        );

        this.opUserOrg = opUserOrg;
        this.sisImportCourseDao = sisImportCourseDao;
        this.sisImportOrgDao = sisImportOrgDao;
        this.sisImportTermDao = sisImportTermDao;
    }

    @Override
    protected List<SisImportCourse> checkRecord(List<RecordVo<SisImportCourse>> recordVos) {

        List<String> importCourseIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportCourse::getCourseId)
                .collect(Collectors.toList());

        List<String> importAccountIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportCourse::getAccountId)
                .filter(StringUtil::isNotEmpty)
                .collect(Collectors.toList());

        List<String> importTermIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportCourse::getTermId)
                .filter(StringUtil::isNotEmpty)
                .collect(Collectors.toList());

        Map<String, SisImportCourse> courseIdMap = sisImportCourseDao.findByCourseIds(importCourseIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportCourse::getCourseId, a -> a));
        Map<String, SisImportOrg> accountIdMap = sisImportOrgDao.findByAccountIds(importAccountIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportOrg::getAccountId, a -> a));
        Map<String, SisImportTerm> termIdMap = sisImportTermDao.findByTermIds(importTermIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportTerm::getTermId, a -> a));

        List<SisImportCourse> validImports = new ArrayList<>();
        for (RecordVo<SisImportCourse> recordVo : recordVos) {
            long row = recordVo.getRow();
            SisImportCourse record = recordVo.getRecord();
            SisImportCourse sysCourse = courseIdMap.get(record.getCourseId());

            SisImportOrg sysOrg = accountIdMap.get(record.getAccountId());
            if (sysOrg == null || Objects.equals(sysOrg.getOperation(), OperationTypeEnum.DELETED.getType())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_ACCOUNT_ID, record.getAccountId()));
                continue;
            }

            String termId = record.getTermId();
            if (StringUtil.isNotEmpty(termId)) {
                SisImportTerm sysTerm = termIdMap.get(record.getTermId());
                if (sysTerm == null || Objects.equals(sysTerm.getOperation(), OperationTypeEnum.DELETED.getType())) {
                    this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_TERM_ID, termId));
                    continue;
                }
            }

            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType()) && sysCourse == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_COURSE_ID, record.getCourseId()));
                continue;
            }

            if (sysCourse != null) {
                record.setId(sysCourse.getId());
            }
            validImports.add(record);
        }
        return validImports;
    }

    @Override
    protected SisImportCourse buildRecord(Map<String, String> record, long row) throws ReaderException {
        String accountId = record.get(FIELD_ACCOUNT_ID);
        SisImportCourse importCourse = SisImportCourse.builder()
                .courseId(record.get(FIELD_COURSE_ID))
                .shortName(record.get(FIELD_SHORT_NAME))
                .longName(record.get(FIELD_LONG_NAME))
                .accountId(StringUtil.isEmpty(accountId) ? opUserOrg.getSisId() : accountId)
                .termId(record.get(FIELD_TERM_ID))
                .startDate(record.get(FIELD_START_DATE))
                .endDate(record.get(FIELD_END_DATE))
                .courseFormat(record.get(FIELD_COURSE_FORMAT))
                .operation(record.get(FIELD_STATUS))
                .build();

        if (StringUtil.isNotEmpty(importCourse.getCourseFormat())
                && SisCourseFormatEnum.formatOf(importCourse.getCourseFormat()) == null) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_COURSE_FORMAT,
                    importCourse.getCourseFormat()));
        }

        try {
            parseDate(importCourse.getStartDate());
        } catch (ParseException e) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, row, FIELD_START_DATE, importCourse.getStartDate()));
        }
        try {
            parseDate(importCourse.getEndDate());
        } catch (ParseException e) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, row, FIELD_END_DATE, importCourse.getEndDate()));
        }
        return importCourse;
    }

    @Override
    protected String getUniqueKey(SisImportCourse record) {
        return FIELD_COURSE_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportCourse record) {
        return record.getCourseId();
    }
}
