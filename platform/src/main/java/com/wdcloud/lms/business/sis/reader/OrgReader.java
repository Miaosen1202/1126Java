package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.SisImportOrgDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportOrg;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class OrgReader extends AbstractSisCsvReader<SisImportOrg> {
    private static final String FIELD_ACCOUNT_ID = "account_id";
    private static final String FIELD_PARENT_ACCOUNT_ID = "parent_account_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_STATUS = "status";

    private Org rootOrg;
    private SisImportOrgDao sisImportOrgDao;

    public OrgReader(Path csvFile, Org rootOrg, SisImportOrgDao sisImportOrgDao) {
        super(csvFile,
                Set.of(FIELD_ACCOUNT_ID, FIELD_PARENT_ACCOUNT_ID, FIELD_NAME, FIELD_STATUS),
                Set.of(FIELD_ACCOUNT_ID, FIELD_NAME, FIELD_STATUS));
        this.rootOrg = rootOrg;
        this.sisImportOrgDao = sisImportOrgDao;
    }

    /*
     * fixme
     *  如果存在机构树 a -> a_1 -> a_1_1，且机构都是 active
     *  导入文件文件中导入了 a_1_1_1 作为 a_1_1 下级机构，并在后续行中，指定修改 a 为 deleted
     *  这是机构 a 删除了，但是增加了一个下级机构，需要考虑这种情况的处理
     */
    /*
     * 数据检查： 删除数据 account_id 必须存在数据库中
     *          新增时，parent_account_id 指定的父机构必须存在数据所在行前，或在系统中，且是active状态
     */
    @Override
    protected List<SisImportOrg> checkRecord(List<RecordVo<SisImportOrg>> recordVos) {
        Set<String> accountIds = new HashSet<>();
        for (RecordVo<SisImportOrg> recordVo : recordVos) {
            SisImportOrg record = recordVo.getRecord();
            accountIds.add(record.getAccountId());
            if (StringUtil.isNotEmpty(record.getParentAccountId())) {
                accountIds.add(record.getParentAccountId());
            }
        }

        Map<String, SisImportOrg> sysImportOrgAccountIdMap = sisImportOrgDao.findByAccountIds(accountIds, rootOrg.getTreeId())
                .stream()
                .filter(org -> org.getOrgTreeId().startsWith(rootOrg.getTreeId()))
                .collect(Collectors.toMap(SisImportOrg::getAccountId, a -> a));

        Map<String, SisImportOrg> validImportOrgMap = new LinkedHashMap<>();
        for (RecordVo<SisImportOrg> recordVo : recordVos) {
            SisImportOrg record = recordVo.getRecord();
            long row = recordVo.getRow();

            SisImportOrg sysOrg = sysImportOrgAccountIdMap.get(record.getAccountId());
            record.setId(sysOrg == null ? null : sysOrg.getId());

            if (Objects.equals(record.getOperation(), OperationTypeEnum.ACTIVE.getType()) && StringUtil.isNotEmpty(record.getParentAccountId())) {
                String parentAccountId = record.getParentAccountId();

                SisImportOrg sysParentOrg = sysImportOrgAccountIdMap.get(parentAccountId);
                SisImportOrg importOrg = validImportOrgMap.get(parentAccountId);
                boolean isParentExists;
                if (importOrg != null) {
                    isParentExists = Objects.equals(importOrg.getOperation(), OperationTypeEnum.ACTIVE.getType());
                } else {
                    isParentExists = sysParentOrg == null ? false : Objects.equals(sysParentOrg.getOperation(), OperationTypeEnum.ACTIVE.getType());
                }
                if (!isParentExists) {
                    this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_PARENT_ACCOUNT_ID, parentAccountId));
                    continue;
                }
            }
            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType())) {
                if (sysOrg == null) {
                    this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_ACCOUNT_ID, record.getAccountId()));
                    continue;
                }
            }

            validImportOrgMap.put(record.getAccountId(), record);
        }
        return new ArrayList<>(validImportOrgMap.values());
    }

    @Override
    protected SisImportOrg buildRecord(Map<String, String> record, long row) {
        String accountId = record.get(FIELD_ACCOUNT_ID);
        String parentAccountId = record.get(FIELD_PARENT_ACCOUNT_ID);
        String name = record.get(FIELD_NAME);
        String status = record.get(FIELD_STATUS);

        SisImportOrg importOrg = SisImportOrg.builder()
                .accountId(accountId)
                .parentAccountId(StringUtil.isEmpty(parentAccountId) ? rootOrg.getSisId() : parentAccountId)
                .name(name)
                .operation(status)
                .build();

        return importOrg;
    }

    @Override
    protected String getUniqueKey(SisImportOrg record) {
        return FIELD_ACCOUNT_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportOrg record) {
        return record.getAccountId();
    }

}
