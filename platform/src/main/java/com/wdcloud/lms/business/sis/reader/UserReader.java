package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.SisImportOrgDao;
import com.wdcloud.lms.core.base.dao.SisImportUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportOrg;
import com.wdcloud.lms.core.base.model.SisImportUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class UserReader extends AbstractSisCsvReader<SisImportUser> {
    private static final String FIELD_USER_ID = "sis_id";
    private static final String FIELD_LOGIN_ID = "login_id";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_FULL_NAME = "full_name";
    private static final String FIELD_SORTABLE_NAME = "sortable_name";
    private static final String FIELD_SHORT_NAME = "short_name";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_ACCOUNT_ID = "account_id";


    private SisImportUserDao sisImportUserDao;
    private SisImportOrgDao sisImportOrgDao;
    private Org opUserOrg;
    private UserDao userDao;

    public UserReader(Path csvFile, Org opUserOrg, SisImportUserDao sisImportUserDao, SisImportOrgDao sisImportOrgDao, UserDao userDao) {
        super(
                csvFile,
                Set.of(FIELD_USER_ID, FIELD_LOGIN_ID, FIELD_PASSWORD, FIELD_FIRST_NAME, FIELD_LAST_NAME,
                        FIELD_FULL_NAME, FIELD_SORTABLE_NAME, FIELD_SHORT_NAME, FIELD_EMAIL, FIELD_STATUS, FIELD_ACCOUNT_ID),
                Set.of(FIELD_USER_ID, FIELD_LOGIN_ID, FIELD_STATUS)
        );
        this.opUserOrg = opUserOrg;
        this.sisImportOrgDao = sisImportOrgDao;
        this.sisImportUserDao = sisImportUserDao;
        this.userDao = userDao;
    }

    @Override
    protected List<SisImportUser> checkRecord(List<RecordVo<SisImportUser>> recordVos) {
        List<String> importUserId = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportUser::getUserId)
                .collect(Collectors.toList());
        Map<String, SisImportUser> sysImportUserIdMap = sisImportUserDao.findByUserIds(importUserId, opUserOrg.getTreeId())
                .stream().collect(Collectors.toMap(SisImportUser::getUserId, a -> a));

        Set<String> importUserAccountIds = recordVos.stream()
                .map(RecordVo::getRecord)
                .map(SisImportUser::getAccountId)
                .collect(Collectors.toSet());
        Map<String, SisImportOrg> sysAccountIdMap = sisImportOrgDao.findByAccountIds(importUserAccountIds, opUserOrg.getTreeId())
                .stream().collect(Collectors.toMap(SisImportOrg::getAccountId, a -> a));

        List<SisImportUser> validImports = new ArrayList<>(recordVos.size());
        for (RecordVo<SisImportUser> recordVo : recordVos) {
            SisImportUser record = recordVo.getRecord();
            long row = recordVo.getRow();
            SisImportUser sysUser = sysImportUserIdMap.get(record.getUserId());

            SisImportOrg importOrg = sysAccountIdMap.get(record.getAccountId());
            if (importOrg == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_ACCOUNT_ID, record.getAccountId()));
                continue;
            }

            if (Objects.equals(record.getOperation(), OperationTypeEnum.DELETED.getType()) && sysUser == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.ASSOCIATE_VAL_NOT_FOUND, row, FIELD_USER_ID, record.getUserId()));
                continue;
            }

            List<User> users = userDao.find(User.builder().username(record.getLoginId()).build());
            if (users.size() > 0) {
                boolean exists = false;
                for (User user : users) {
                    if (!Objects.equals(user.getSisId(), record.getUserId())
                            || !Objects.equals(user.getOrgTreeId(), importOrg.getOrgTreeId())) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    this.errors.add(new ReaderError(ErrorCodeEnum.FIELD_VALUE_EXISTS, row, FIELD_LOGIN_ID, record.getLoginId()));
                    continue;
                }
            }

            if (sysUser != null) {
                record.setId(sysUser.getId());
            }
            validImports.add(record);
        }

        return validImports;
    }

    @Override
    protected SisImportUser buildRecord(Map<String, String> record, long row) {
        String accountId = record.get(FIELD_ACCOUNT_ID);
        return SisImportUser.builder()
                .accountId(StringUtil.isEmpty(accountId) ? opUserOrg.getSisId() : accountId)
                .userId(record.get(FIELD_USER_ID))
                .loginId(record.get(FIELD_LOGIN_ID))
                .password(record.get(FIELD_PASSWORD))
                .firstName(record.get(FIELD_FIRST_NAME))
                .lastName(record.get(FIELD_LAST_NAME))
                .fullName(record.get(FIELD_FULL_NAME))
                .sortableName(record.get(FIELD_SORTABLE_NAME))
                .shortName(record.get(FIELD_SHORT_NAME))
                .email(record.get(FIELD_EMAIL))
                .operation(record.get(FIELD_STATUS))
                .build();
    }

    @Override
    protected String getUniqueKey(SisImportUser record) {
        return FIELD_USER_ID;
    }

    @Override
    protected String getUniqueKeyVal(SisImportUser record) {
        return record.getUserId();
    }
}
