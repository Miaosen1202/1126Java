package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.SisImportCourseDao;
import com.wdcloud.lms.core.base.dao.SisImportStudyGroupSetDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportCourse;
import com.wdcloud.lms.core.base.model.SisImportStudyGroupSet;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class StudyGroupSetReader extends AbstractSisCsvReader<SisImportStudyGroupSet> {
    private static final String FIELD_CATEGORY_ID = "group_category_id";
    private static final String FIELD_NAME = "category_name";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_STATUS = "status";

    private static final Set<String> HEADERS = Set.of(FIELD_CATEGORY_ID, FIELD_NAME, FIELD_COURSE_ID, FIELD_STATUS);
    private static final Set<String> REQUIRED_FIELD = Set.of(FIELD_CATEGORY_ID, FIELD_NAME, FIELD_COURSE_ID, FIELD_STATUS);

    private Org opUserOrg;
    private SisImportCourseDao sisImportCourseDao;
    private SisImportStudyGroupSetDao sisImportStudyGroupSetDao;

    public StudyGroupSetReader(Path csvFile, Org opUserOrg, SisImportCourseDao sisImportCourseDao,
                               SisImportStudyGroupSetDao sisImportStudyGroupSetDao) {
        super(
                csvFile,
                HEADERS,
                REQUIRED_FIELD
        );

        this.opUserOrg = opUserOrg;
        this.sisImportCourseDao = sisImportCourseDao;
        this.sisImportStudyGroupSetDao = sisImportStudyGroupSetDao;
    }

    @Override
    protected List<SisImportStudyGroupSet> checkRecord(List<RecordVo<SisImportStudyGroupSet>> recordVos) {

        Set<String> courseIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportStudyGroupSet::getCourseId)
                .collect(Collectors.toSet());
        List<String> groupSetIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportStudyGroupSet::getGroupCategoryId)
                .collect(Collectors.toList());

        Map<String, SisImportCourse> sysCourseIdMap = sisImportCourseDao.findByCourseIds(courseIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportCourse::getCourseId, a -> a));

        Map<String, SisImportStudyGroupSet> sysStudyGroupIdMap =
                sisImportStudyGroupSetDao.findByGroupCategoryIds(groupSetIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportStudyGroupSet::getGroupCategoryId, a -> a));

        List<SisImportStudyGroupSet> validImports = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportStudyGroupSet> recordVo : recordVos) {
            SisImportStudyGroupSet record = recordVo.getRecord();
            long row = recordVo.getRow();

            SisImportCourse sysCourse = sysCourseIdMap.get(record.getCourseId());
            SisImportStudyGroupSet sysStudyGroupSet = sysStudyGroupIdMap.get(record.getGroupCategoryId());

            if (sysCourse == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_COURSE_ID, record.getGroupCategoryId()));
                continue;
            }
            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType())
                    && sysStudyGroupSet == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_CATEGORY_ID, record.getGroupCategoryId()));
                continue;
            }

            record.setId(sysStudyGroupSet == null ? null : sysStudyGroupSet.getId());
            validImports.add(record);
        }
        return validImports;
    }

    @Override
    protected SisImportStudyGroupSet buildRecord(Map<String, String> record, long row) throws ReaderException {
        return SisImportStudyGroupSet.builder()
                .groupCategoryId(record.get(FIELD_CATEGORY_ID))
                .categoryName(record.get(FIELD_NAME))
                .courseId(record.get(FIELD_COURSE_ID))
                .operation(record.get(FIELD_STATUS))
                .build();
    }

    @Override
    protected String getUniqueKey(SisImportStudyGroupSet record) {
        return FIELD_CATEGORY_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportStudyGroupSet record) {
        return record.getGroupCategoryId();
    }
}
