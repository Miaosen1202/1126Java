package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportStudyGroupUserExtMapper;
import com.wdcloud.lms.core.base.model.SisImportStudyGroupUser;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SisImportStudyGroupUserDao extends CommonDao<SisImportStudyGroupUser, Long> {
    @Autowired
    private SisImportStudyGroupUserExtMapper sisImportStudyGroupUserExtMapper;

    @Override
    protected Class<SisImportStudyGroupUser> getBeanClass() {
        return SisImportStudyGroupUser.class;
    }

    public void batchSave(List<SisImportStudyGroupUser> studyGroupUsers) {
        if (ListUtils.isNotEmpty(studyGroupUsers)) {
            sisImportStudyGroupUserExtMapper.batchSave(studyGroupUsers);
        }
    }
}