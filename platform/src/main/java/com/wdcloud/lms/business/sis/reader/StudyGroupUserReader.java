package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.business.sis.enums.SisRoleEnum;
import com.wdcloud.lms.core.base.dao.SisImportSectionUserDao;
import com.wdcloud.lms.core.base.dao.SisImportStudyGroupDao;
import com.wdcloud.lms.core.base.dao.SisImportStudyGroupSetDao;
import com.wdcloud.lms.core.base.model.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class StudyGroupUserReader extends AbstractSisCsvReader<SisImportStudyGroupUser> {
    private static final String FIELD_GROUP_ID = "group_id";
    private static final String FIELD_USER_ID = "user_id";
    private static final String FIELD_STATUS = "status";

    private static final Set<String> HEADERS = Set.of(FIELD_GROUP_ID, FIELD_USER_ID, FIELD_STATUS);
    private static final Set<String> REQUIRED_FIELD = Set.of(FIELD_GROUP_ID, FIELD_USER_ID, FIELD_STATUS);

    /**
     * csv 文件
     */
    private Path csvFile;
    /**
     * 导入数据执行用户所在机构
     */
    private Org rootOrg;
    private SisImportStudyGroupDao sisImportStudyGroupDao;
    private SisImportStudyGroupSetDao sisImportStudyGroupSetDao;
    private SisImportSectionUserDao sisImportSectionUserDao;

    public StudyGroupUserReader(Path csvFile, Org rootOrg, SisImportStudyGroupSetDao sisImportStudyGroupSetDao,
                                SisImportStudyGroupDao sisImportStudyGroupDao,
                                SisImportSectionUserDao sisImportSectionUserDao) {
        super(
                csvFile,
                HEADERS,
                REQUIRED_FIELD
        );

        this.rootOrg = rootOrg;
        this.sisImportStudyGroupSetDao = sisImportStudyGroupSetDao;
        this.sisImportStudyGroupDao = sisImportStudyGroupDao;
        this.sisImportSectionUserDao = sisImportSectionUserDao;
    }

    @Override
    protected List<SisImportStudyGroupUser> checkRecord(List<RecordVo<SisImportStudyGroupUser>> recordVos) {

        Set<String> groupIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportStudyGroupUser::getGroupId)
                .collect(Collectors.toSet());
        Set<String> userIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportStudyGroupUser::getUserId)
                .collect(Collectors.toSet());

        Map<String, SisImportStudyGroup> sysGroupIdMap = sisImportStudyGroupDao.findByStudyGroupIds(groupIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportStudyGroup::getGroupId, a -> a));

        Set<String> groupSetIds = sysGroupIdMap.values().stream()
                .map(SisImportStudyGroup::getGroupCategoryId)
                .collect(Collectors.toSet());

        Map<String, SisImportStudyGroupSet> studyGroupSetMap = sisImportStudyGroupSetDao.findByGroupCategoryIds(groupSetIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(SisImportStudyGroupSet::getGroupCategoryId, a -> a));

        List<SisImportSectionUser> sysSectionUsers = sisImportSectionUserDao.findByUserIds(userIds, rootOrg.getTreeId());

        List<SisImportStudyGroupUser> validImports = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportStudyGroupUser> recordVo : recordVos) {
            SisImportStudyGroupUser record = recordVo.getRecord();
            long row = recordVo.getRow();

            SisImportStudyGroup sysGroup = sysGroupIdMap.get(record.getGroupId());
            if (sysGroup == null || Objects.equals(sysGroup.getOperation(), OperationTypeEnum.DELETED.getType())) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_GROUP_ID, record.getGroupId()));
                continue;
            }

            SisImportStudyGroupSet importStudyGroupSet = studyGroupSetMap.get(sysGroup.getGroupCategoryId());

            boolean existsCourse = false;
            for (SisImportSectionUser sysSectionUser : sysSectionUsers) {
                String courseId = sysSectionUser.getCourseId();
                if (Objects.equals(sysSectionUser.getUserId(), record.getUserId())
                        && !Objects.equals(sysSectionUser.getOperation(), OperationTypeEnum.DELETED.getType())
                        && Objects.equals(importStudyGroupSet.getCourseId(), courseId)) {
                    existsCourse = true;
                    break;
                }
            }
            if (!existsCourse) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_USER_ID, record.getUserId()));
                continue;
            }

            validImports.add(record);
        }

        return validImports;
    }

    @Override
    protected SisImportStudyGroupUser buildRecord(Map<String, String> record, long row) throws ReaderException {
        return SisImportStudyGroupUser.builder()
                .groupId(record.get(FIELD_GROUP_ID))
                .userId(record.get(FIELD_USER_ID))
                .operation(record.get(FIELD_STATUS))
                .build();
    }

    @Override
    protected String getUniqueKey(SisImportStudyGroupUser record) {
        return FIELD_GROUP_ID + "-" + FIELD_USER_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportStudyGroupUser record) {
        return record.getGroupId() + "-" + record.getUserId();
    }
}
