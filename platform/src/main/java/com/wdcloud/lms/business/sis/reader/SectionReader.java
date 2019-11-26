package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.SisImportCourseDao;
import com.wdcloud.lms.core.base.dao.SisImportSectionDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportCourse;
import com.wdcloud.lms.core.base.model.SisImportSection;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SectionReader extends AbstractSisCsvReader<SisImportSection> {
    private static final String FIELD_SECTION_ID = "section_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_START_DATE = "start_date";
    private static final String FIELD_END_DATE = "end_date";
    private static final String FIELD_STATUS = "status";


    private Org opUserOrg;
    private SisImportSectionDao sisImportSectionDao;
    private SisImportCourseDao sisImportCourseDao;

    public SectionReader(Path csvFile, Org opUserOrg, SisImportSectionDao sectionDao, SisImportCourseDao courseDao) {
        super(
                csvFile,
                Set.of(FIELD_SECTION_ID, FIELD_NAME, FIELD_COURSE_ID, FIELD_START_DATE,
                        FIELD_END_DATE, FIELD_STATUS),
                Set.of(FIELD_SECTION_ID, FIELD_NAME, FIELD_COURSE_ID, FIELD_STATUS)
        );

        this.opUserOrg = opUserOrg;
        this.sisImportSectionDao = sectionDao;
        this.sisImportCourseDao = courseDao;
    }

    @Override
    protected List<SisImportSection> checkRecord(List<RecordVo<SisImportSection>> recordVos) {

        List<String> sectionIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportSection::getSectionId)
                .collect(Collectors.toList());
        Set<String> courseIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportSection::getCourseId)
                .collect(Collectors.toSet());

        Map<String, SisImportSection> sysSectionIdMap = sisImportSectionDao.findBySectionIds(sectionIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportSection::getSectionId, a -> a));
        Map<String, SisImportCourse> sysCourseIdMap = sisImportCourseDao.findByCourseIds(courseIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportCourse::getCourseId, a -> a));

        List<SisImportSection> validImports = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportSection> recordVo : recordVos) {
            SisImportSection record = recordVo.getRecord();
            long row = recordVo.getRow();

            SisImportSection sysSection = sysSectionIdMap.get(record.getSectionId());
            SisImportCourse sysCourse = sysCourseIdMap.get(record.getCourseId());

            if (sysCourse == null || Objects.equals(sysCourse.getOperation(), OperationTypeEnum.DELETED.getType())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_COURSE_ID, record.getCourseId()));
                continue;
            }
            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType()) && sysSection == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_SECTION_ID, record.getSectionId()));
                continue;
            }

            if (sysSection != null) {
                record.setId(sysSection.getId());
            }
            validImports.add(record);
        }

        return validImports;
    }

    @Override
    protected SisImportSection buildRecord(Map<String, String> record, long row) throws ReaderException {
        String startDate = record.get(FIELD_START_DATE);
        String endDate = record.get(FIELD_END_DATE);
        SisImportSection section = SisImportSection.builder()
                .sectionId(record.get(FIELD_SECTION_ID))
                .name(record.get(FIELD_NAME))
                .courseId(record.get(FIELD_COURSE_ID))
                .startDate(startDate)
                .endDate(endDate)
                .operation(record.get(FIELD_STATUS))
                .build();

        try {
            parseDate(startDate);
        } catch (ParseException e) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, row, FIELD_START_DATE, startDate));
        }
        try {
            parseDate(endDate);
        } catch (ParseException e) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, row, FIELD_END_DATE, endDate));
        }

        return section;
    }

    @Override
    protected String getUniqueKey(SisImportSection record) {
        return FIELD_SECTION_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportSection record) {
        return record.getSectionId();
    }
}
