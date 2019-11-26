package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.SisImportUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.PasswordUtil;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserTransfer {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private SectionUserService sectionUserService;

    public List<User> transfer(List<SisImportUser> importUsers, Long opUserId, Org rootOrg) {
        delete(importUsers, opUserId, rootOrg);
        return add(importUsers, opUserId, rootOrg);
    }

    private List<User> add(List<SisImportUser> importUsers, Long opUserId, Org rootOrg) {
        List<User> addNewUsers = new ArrayList<>();

        List<SisImportUser> activeUsers = importUsers.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(activeUsers)) {
            return addNewUsers;
        }

        Set<String> orgSisIds = activeUsers.stream()
                .map(SisImportUser::getAccountId)
                .collect(Collectors.toSet());
        Map<String, Org> orgSisIdMap = orgDao.findBySisIds(orgSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(Org::getSisId, a -> a));

        List<String> userSisIds = activeUsers.stream().map(SisImportUser::getUserId).collect(Collectors.toList());
        Map<String, User> userSisIdMap = userDao.findBySisIds(userSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(User::getSisId, a -> a));

        for (SisImportUser activeUser : activeUsers) {
            Org org = orgSisIdMap.get(activeUser.getAccountId());
            if (org == null) {
                continue;
            }

            String password = StringUtil.isEmpty(activeUser.getPassword())
                    ? PasswordUtil.haxPassword(Constants.USER_DEFAULT_PASSWORD)
                    : PasswordUtil.haxPassword(activeUser.getPassword());
            String fullName = activeUser.getFullName();
            if (StringUtil.isEmpty(fullName)) {
                if (StringUtil.isNotEmpty(activeUser.getFirstName())) {
                    fullName = activeUser.getFirstName();
                }

                if (StringUtil.isNotEmpty(fullName) && StringUtil.isNotEmpty(activeUser.getLastName())) {
                    fullName += activeUser.getLastName();
                }
            }

            if (userSisIdMap.containsKey(activeUser.getUserId())) {
                User existsUser = userSisIdMap.get(activeUser.getUserId());

                if (!Objects.equals(existsUser.getUsername(), activeUser.getLoginId()) && userDao.existsUsername(activeUser.getLoginId())) {
                    log.info("[UserTransfer] username '{}' exists, skip add", activeUser.getLoginId());
                    continue;
                }

                existsUser.setUsername(activeUser.getLoginId());
                existsUser.setEmail(activeUser.getEmail());
                existsUser.setFirstName(activeUser.getFirstName());
                existsUser.setLastName(activeUser.getLastName());
                existsUser.setFullName(activeUser.getFullName());
                existsUser.setNickname(activeUser.getShortName());
                existsUser.setUpdateUserId(opUserId);
                userDao.updateIncludeNull(existsUser);
            } else {
                if (userDao.existsUsername(activeUser.getLoginId())) {
                    log.info("[UserTransfer] username '{}' exists, skip add", activeUser.getLoginId());
                    continue;
                }

                User newUser = User.builder()
                        .sisId(activeUser.getUserId())
                        .orgId(org.getId())
                        .orgTreeId(org.getTreeId())
                        .username(activeUser.getLoginId())
                        .email(activeUser.getEmail())
                        .password(password)
                        .firstName(activeUser.getFirstName())
                        .lastName(activeUser.getLastName())
                        .fullName(fullName)
                        .nickname(activeUser.getShortName())
                        .isRegistering(Status.NO.getStatus())
                        .status(Status.YES.getStatus())
                        .createUserId(opUserId)
                        .updateUserId(opUserId)
                        .build();
                userDao.save(newUser);

                userFileService.initPersonalSpaceDir(newUser.getId());

                addNewUsers.add(newUser);
            }
        }

        return addNewUsers;
    }

    private void delete(List<SisImportUser> importUsers, Long opUserId, Org rootOrg) {
        List<SisImportUser> deleteUsers = importUsers.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(deleteUsers)) {
            return;
        }

        List<String> userSisIds = deleteUsers.stream().map(SisImportUser::getUserId).collect(Collectors.toList());
        Map<String, User> userSisIdMap = userDao.findBySisIds(userSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(User::getSisId, a -> a));

        List<Long> userIds = userSisIdMap.values().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        userDao.deletes(userIds);

        userFileService.removePersonalSpaceDirs(userIds);

        Example courseUserExp = courseUserDao.getExample();
        courseUserExp.createCriteria()
                .andIn("userId", userIds);
        courseUserDao.delete(courseUserExp);

        sectionUserService.deleteByUserIds(userIds);

    }
}
