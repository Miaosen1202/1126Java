package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.SisImportStudyGroupDao;
import com.wdcloud.lms.core.base.dao.SisImportStudyGroupSetDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportStudyGroup;
import com.wdcloud.lms.core.base.model.SisImportStudyGroupSet;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class StudyGroupReader extends AbstractSisCsvReader<SisImportStudyGroup> {
    private static final String FIELD_GROUP_ID = "group_id";
    private static final String FIELD_GROUP_CATEGORY_ID = "group_category_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_STATUS = "status";

    private static final Set<String> HEADERS = Set.of(FIELD_GROUP_ID, FIELD_GROUP_CATEGORY_ID, FIELD_NAME, FIELD_STATUS);
    private static final Set<String> REQUIRED_FIELD = Set.of(FIELD_GROUP_ID, FIELD_GROUP_CATEGORY_ID, FIELD_NAME, FIELD_STATUS);

    private Org opUserOrg;
    private SisImportStudyGroupSetDao sisImportStudyGroupSetDao;
    private SisImportStudyGroupDao sisImportStudyGroupDao;

    public StudyGroupReader(Path csvFile, Org opUserOrg, SisImportStudyGroupSetDao sisImportStudyGroupSetDao,
                            SisImportStudyGroupDao sisImportStudyGroupDao) {
        super(
                csvFile,
                HEADERS,
                REQUIRED_FIELD
        );

        this.opUserOrg = opUserOrg;
        this.sisImportStudyGroupSetDao = sisImportStudyGroupSetDao;
        this.sisImportStudyGroupDao = sisImportStudyGroupDao;
    }

    @Override
    protected List<SisImportStudyGroup> checkRecord(List<RecordVo<SisImportStudyGroup>> recordVos) {

        List<String> groupIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportStudyGroup::getGroupId)
                .collect(Collectors.toList());
        Set<String> groupCategoryIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportStudyGroup::getGroupCategoryId)
                .collect(Collectors.toSet());

        Map<String, SisImportStudyGroupSet> sysGroupSetIdMap =
                sisImportStudyGroupSetDao.findByGroupCategoryIds(groupCategoryIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportStudyGroupSet::getGroupCategoryId, a -> a));
        Map<String, SisImportStudyGroup> sysGroupIdMap = sisImportStudyGroupDao.findByStudyGroupIds(groupIds, opUserOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportStudyGroup::getGroupId, a -> a));

        List<SisImportStudyGroup> validImports = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportStudyGroup> recordVo : recordVos) {
            SisImportStudyGroup record = recordVo.getRecord();
            long row = recordVo.getRow();

            SisImportStudyGroup sysGroup = sysGroupIdMap.get(record.getGroupId());

            if (!sysGroupSetIdMap.containsKey(record.getGroupCategoryId())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_GROUP_CATEGORY_ID,
                        record.getGroupCategoryId()));
                continue;
            }

            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType()) && sysGroup == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_GROUP_ID, record.getGroupId()));
                continue;
            }

            record.setId(sysGroup == null ? null : sysGroup.getId());
            validImports.add(record);
        }

        return validImports;
    }

    @Override
    protected SisImportStudyGroup buildRecord(Map<String, String> record, long row) throws ReaderException {
        return SisImportStudyGroup.builder()
                .groupId(record.get(FIELD_GROUP_ID))
                .groupCategoryId(record.get(FIELD_GROUP_CATEGORY_ID))
                .name(record.get(FIELD_NAME))
                .operation(record.get(FIELD_STATUS))
                .build();
    }

    @Override
    protected String getUniqueKey(SisImportStudyGroup record) {
        return FIELD_GROUP_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportStudyGroup record) {
        return record.getGroupId();
    }
}
