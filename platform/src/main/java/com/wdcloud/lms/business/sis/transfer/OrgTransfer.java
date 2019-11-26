package com.wdcloud.lms.business.sis.transfer;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportOrg;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.TreeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrgTransfer {

    @Autowired
    private OrgDao orgDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;

    public List<Org> transfer(List<SisImportOrg> importOrgs, Long opUserId, Org rootOrg) {
        if (ListUtils.isEmpty(importOrgs)) {
            log.info("[OrgTransfer] import org is empty, skip transfer");
            return new ArrayList<>();
        }

        removeOrg(importOrgs, rootOrg);
        List<Org> addOrgs = addOrg(importOrgs, opUserId, rootOrg);
        return addOrgs;
    }

    // fixme 删除机构，清理关联数据（只有用户存有机构treeId，暂时只清理机构关联用户）
    private void removeOrg(List<SisImportOrg> importOrgs, Org rootOrg) {
        Map<String, SisImportOrg> deleteOrgMap = importOrgs.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toMap(SisImportOrg::getAccountId, a -> a));

        log.info("[OrgTransfer] remove org sisIds={}", JSON.toJSONString(deleteOrgMap.keySet()));
        if (deleteOrgMap.isEmpty()) {
            return;
        }

        List<Org> deleteOrgs = orgDao.findBySisIds(deleteOrgMap.keySet(), rootOrg.getTreeId());

        for (Org deleteOrg : deleteOrgs) {
            Example userExp = userDao.getExample();
            userExp.createCriteria()
                    .andLike("orgTreeId", deleteOrg.getTreeId() + "%");
            userDao.delete(userExp);

            Example example = orgDao.getExample();
            example.createCriteria()
                    .andLike("treeId", deleteOrg.getTreeId() + "%");
            orgDao.delete(example);
        }
    }

    private List<Org> addOrg(List<SisImportOrg> importOrgs, Long opUserId, Org rootOrg) {
        Map<String, SisImportOrg> activeOrgMap = importOrgs.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toMap(SisImportOrg::getAccountId, a -> a));

        if (activeOrgMap.isEmpty()) {
            return new ArrayList<>();
        }

        Set<String> orgSisIds = new HashSet<>();
        for (SisImportOrg importOrg : activeOrgMap.values()) {
            orgSisIds.add(importOrg.getAccountId());
            orgSisIds.add(importOrg.getParentAccountId());
        }
        List<Org> orgs = orgDao.findBySisIds(orgSisIds, rootOrg.getTreeId());
        Map<String, Org> orgSisIdMap = orgs.stream().collect(Collectors.toMap(Org::getSisId, a -> a));

        List<Org> newOrgs = new ArrayList<>();
        for (Map.Entry<String, SisImportOrg> importOrgEntry : activeOrgMap.entrySet()) {
            String accountId = importOrgEntry.getKey();
            SisImportOrg importOrg = importOrgEntry.getValue();

            Org parent = orgSisIdMap.get(importOrg.getParentAccountId());
            if (orgSisIdMap.containsKey(importOrg.getAccountId())) {
                Org existsOrg = orgSisIdMap.get(importOrg.getAccountId());
                existsOrg.setName(importOrg.getName());
                orgDao.update(existsOrg);
            } else {
                String maxTreeId = orgDao.getMaxTreeIdByParentId(parent.getId());
                String treeId;
                if (maxTreeId == null) {
                    treeId = TreeIdUtils.produceTreeIdByParentId(parent.getTreeId(), Constants.TREE_ID_PER_LEVEL_LENGTH);
                } else {
                    treeId = TreeIdUtils.produceTreeId(maxTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH);
                }
                Org activeOrg = Org.builder()
                        .sisId(accountId)
                        .parentId(parent.getId())
                        .treeId(treeId)
                        .name(importOrg.getName())
                        .type(OrgTypeEnum.OTHER.getType())
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                orgDao.save(activeOrg);
                orgSisIdMap.put(accountId, activeOrg);

                newOrgs.add(activeOrg);
            }
        }

        return newOrgs;
    }
}
