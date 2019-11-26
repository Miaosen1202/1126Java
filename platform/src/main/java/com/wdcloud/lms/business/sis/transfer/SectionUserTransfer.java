package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.permission.model.entities.Role;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SectionUserTransfer {

    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserService sectionUserService;


    public List<SectionUser> transfer(List<SisImportSectionUser> importSectionUsers, Long opUserId, Org rootOrg) {
        delete(importSectionUsers, opUserId, rootOrg);

        return add(importSectionUsers, opUserId, rootOrg);
    }


    private List<SectionUser> add(List<SisImportSectionUser> importSectionUsers, Long opUserId, Org rootOrg) {
        List<SectionUser> result = new ArrayList<>();

        List<SisImportSectionUser> activeSectionUsers = importSectionUsers.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.ACTIVE.getType()) || Objects.equals(a.getOperation(), OperationTypeEnum.INACTIVE.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(activeSectionUsers)) {
            return result;
        }

        Set<String> sectionSisIds = activeSectionUsers.stream()
                .map(SisImportSectionUser::getSectionId)
                .collect(Collectors.toSet());
        Map<String, Section> sectionSisIdMap = sectionDao.findBySisIds(rootOrg.getTreeId(), sectionSisIds)
                .stream()
                .collect(Collectors.toMap(Section::getSisId, a -> a));

        Set<String> userSisIds = activeSectionUsers.stream()
                .map(SisImportSectionUser::getUserId)
                .collect(Collectors.toSet());
        Map<String, User> userSisIdMap = userDao.findBySisIds(userSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(User::getSisId, a -> a));

        Set<Long> importUserCourseIds = new HashSet<>();
        Map<Long, Set<Long>> importUserIdAndCourseIdsMap = new HashMap<>();
        Map<String, Long> importRoleNameAndIdMap = importRoleNameAndIdMap(activeSectionUsers);
        for (SisImportSectionUser activeSectionUser : activeSectionUsers) {
            Section section = sectionSisIdMap.get(activeSectionUser.getSectionId());
            User user = userSisIdMap.get(activeSectionUser.getUserId());
            Long roleId = importRoleNameAndIdMap.get(activeSectionUser.getRole());
            if (section != null && user != null && roleId != null) {
                UserRegistryStatusEnum registryStatus =
                        Objects.equals(activeSectionUser.getOperation(), OperationTypeEnum.ACTIVE.getType())
                                ? UserRegistryStatusEnum.JOINED
                                : UserRegistryStatusEnum.PENDING;
                Long courseId = section.getCourseId();
                Long sectionId = section.getId();
                Long userId = user.getId();

                SectionUser exists = sectionUserDao.findOne(SectionUser.builder().courseId(courseId).sectionId(sectionId).userId(userId).roleId(roleId).build());
                if (exists != null) {
                    exists.setRegistryStatus(registryStatus.getStatus());
                    exists.setCreateUserId(opUserId);
                    exists.setUpdateUserId(opUserId);
                    sectionUserDao.update(exists);
                } else {
                    SectionUser newSectionUser = SectionUser.builder()
                            .courseId(courseId)
                            .sectionId(sectionId)
                            .userId(userId)
                            .roleId(roleId)
                            .registryOrigin(UserRegistryOriginEnum.ADMIN.getOrigin())
                            .registryStatus(registryStatus.getStatus())
                            .createUserId(opUserId)
                            .updateUserId(opUserId)
                            .build();
                    sectionUserService.save(newSectionUser);

                    result.add(newSectionUser);

                    if (!importUserIdAndCourseIdsMap.containsKey(userId)) {
                        importUserIdAndCourseIdsMap.put(userId, new HashSet<>());
                    }
                    importUserIdAndCourseIdsMap.get(userId).add(courseId);
                    importUserCourseIds.add(courseId);
                }
            }
        }

        if (importUserCourseIds.isEmpty()) {
            return result;
        }
        Example courseUserExp = courseUserDao.getExample();
        courseUserExp.createCriteria()
                .andIn("courseId", importUserCourseIds);
        List<CourseUser> courseUsers = courseUserDao.find(courseUserExp);

        Map<Long, Set<Long>> courseIdAndUserIdsMap = new HashMap<>();
        for (CourseUser courseUser : courseUsers) {
            if (!courseIdAndUserIdsMap.containsKey(courseUser.getId())) {
                courseIdAndUserIdsMap.put(courseUser.getCourseId(), new HashSet<>());
            }
            courseIdAndUserIdsMap.get(courseUser.getCourseId()).add(courseUser.getUserId());
        }


        for (Map.Entry<Long, Set<Long>> longSetEntry : importUserIdAndCourseIdsMap.entrySet()) {
            Long userId = longSetEntry.getKey();
            Set<Long> courseIds = longSetEntry.getValue();

            for (Long courseId : courseIds) {
                if (!courseIdAndUserIdsMap.containsKey(courseId)
                        || !courseIdAndUserIdsMap.get(courseId).contains(userId)) {
                    CourseUser newCourseUser = CourseUser.builder()
                            .userId(userId)
                            .courseId(courseId)
                            .createUserId(opUserId)
                            .updateUserId(opUserId)
                            .build();
                    courseUserDao.save(newCourseUser);
                }

            }
        }

        return result;
    }

    private void delete(List<SisImportSectionUser> importSectionUsers, Long opUserId, Org rootOrg) {
        List<SisImportSectionUser> deleteSectionUsers = importSectionUsers.stream()
                .filter(a -> Objects.equals(a.getOperation(), OperationTypeEnum.DELETED.getType()))
                .collect(Collectors.toList());

        if (ListUtils.isEmpty(deleteSectionUsers)) {
            return;
        }

        Set<String> sectionSisIds = deleteSectionUsers.stream()
                .map(SisImportSectionUser::getSectionId)
                .collect(Collectors.toSet());
        Map<String, Section> sectionSisIdMap = sectionDao.findBySisIds(rootOrg.getTreeId(), sectionSisIds)
                .stream()
                .collect(Collectors.toMap(Section::getSisId, a -> a));

        Set<String> userSisIds = deleteSectionUsers.stream()
                .map(SisImportSectionUser::getUserId)
                .collect(Collectors.toSet());
        Map<String, User> userSisIdMap = userDao.findBySisIds(userSisIds, rootOrg.getTreeId())
                .stream()
                .collect(Collectors.toMap(User::getSisId, a -> a));


        Map<String, Long> importRoleNameAndIdMap = importRoleNameAndIdMap(deleteSectionUsers);

        for (SisImportSectionUser deleteSectionUser : deleteSectionUsers) {
            Section section = sectionSisIdMap.get(deleteSectionUser.getSectionId());
            User user = userSisIdMap.get(deleteSectionUser.getUserId());

            if (section != null && user != null) {
                sectionUserService.delete(section.getId(), user.getId(), importRoleNameAndIdMap.get(deleteSectionUser.getRole()));

                courseUserDao.deleteUnjoinSectionUsers(List.of(section.getCourseId()));
            }
        }
    }

    private Map<String, Long> importRoleNameAndIdMap(List<SisImportSectionUser> importSectionUsers) {
        Map<String, Long> roleNameAndIdMap = new HashMap<>();
        Set<String> roles = importSectionUsers.stream()
                .map(SisImportSectionUser::getRole)
                .collect(Collectors.toSet());
        List<Role> allRoles = roleService.findAllRoles();
        for (Role role : allRoles) {
            String name = role.getRoleName();
            Long id = role.getId();
            for (String r : roles) {
                if (r.compareToIgnoreCase(name) == 0) {
                    roleNameAndIdMap.put(r, id);
                    break;
                }
            }
        }
        return roleNameAndIdMap;
    }
}
