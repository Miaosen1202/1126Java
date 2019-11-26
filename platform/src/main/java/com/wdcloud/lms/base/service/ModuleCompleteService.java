package com.wdcloud.lms.base.service;


import com.google.common.collect.Lists;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.ModuleItemStatusEnum;
import com.wdcloud.lms.core.base.enums.ModuleStatusEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.vo.ModuleItemCompleteVO;
import com.wdcloud.lms.core.base.vo.ModuleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavadocReference", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@Service
public class ModuleCompleteService {

    @Autowired
    private AssignUserDao assignUserDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    @Autowired
    private QuizRecordDao quizRecordDao;
    @Autowired
    private ModuleItemUserDao moduleItemUserDao;
    @Autowired
    private ModuleUserDao moduleUserDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ModuleItemDao moduleItemDao;

    /**
     * 添加单元
     */
    public void addModule(Module module) {
        //1、参数解析
        Long courseId = module.getCourseId();
        Long moduleId = module.getId();
        //2、添加moduleUsers
        addModuleUsersByModule(courseId, moduleId);
    }

    private void addModuleUsersByModule(Long courseId, Long moduleId) {
        Map<Integer, List<ModuleUser>> moduleUserMap = new HashMap<>();
        List<ModuleUser> addModule = Lists.newArrayList();
        List<ModuleUser> updateModule = Lists.newArrayList();
        List<ModuleUser> deleteModule = Lists.newArrayList();
        moduleUserMap.put(1, addModule);
        moduleUserMap.put(2, updateModule);
        moduleUserMap.put(3, deleteModule);
        moduleUserMap = getModuleUserMapByModule(courseId, moduleId, moduleUserMap);
        moduleUserDao.batchInsert(moduleUserMap.get(1));
        moduleUserDao.batchUpdate(moduleUserMap.get(2));
        moduleUserDao.batchDelete(moduleUserMap.get(3));
    }

    /**
     * 添加任务到单元
     */
    public void addAssignmentToModule(Long moduleItemId, Long courseId) {
        //1、获取moduleItem
        ModuleItem moduleItem = moduleItemDao.get(moduleItemId);
        //2、添加moduleItemUsers根据moduleItem
        List<ModuleItem> moduleItems = Lists.newArrayList();
        moduleItems.add(moduleItem);
        addModuleItemUsersByModuleItem(moduleItems, courseId);
        //3、更新moduleUsers根据moduleItem
        updateModuleUsersByModuleItem(moduleItem, courseId);
    }

    private void addModuleItemUsersByModuleItem(List<ModuleItem> moduleItems, Long courseId) {
        Map<Integer, List<ModuleItemUser>> moduleItemUserMap = new HashMap<>();
        List<ModuleItemUser> addModuleItem = Lists.newArrayList();
        List<ModuleItemUser> updateModuleItem = Lists.newArrayList();
        List<ModuleItemUser> deleteModuleItem = Lists.newArrayList();
        moduleItemUserMap.put(1, addModuleItem);
        moduleItemUserMap.put(2, updateModuleItem);
        moduleItemUserMap.put(3, deleteModuleItem);
        for (ModuleItem moduleItem: moduleItems) {
            moduleItemUserMap = getModuleItemUserMapByModuleItem(moduleItem, courseId, moduleItemUserMap);
        }
        moduleItemUserDao.batchInsert(moduleItemUserMap.get(1));
        moduleItemUserDao.batchUpdate(moduleItemUserMap.get(2));
        moduleItemUserDao.batchDelete(moduleItemUserMap.get(3));
    }

    private void updateModuleUsersByModuleItem(ModuleItem moduleItem, Long courseId) {
        //0、参数解析
        Map<Long, ModuleUser> moduleUserMap = new HashMap<Long, ModuleUser>();
        List<ModuleUser> newModuleUsers = Lists.newArrayList();
        //1、求userIds
        List<Long> userIds = getAssignUserIds(moduleItem, courseId);
        //2、求moduleId, moduleItemId
        Long moduleId = moduleItem.getModuleId();
        //3、批量获取moduleUsers
        Example example1 = moduleUserDao.getExample();
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andIn(ModuleUser.USER_ID, userIds);
        criteria1.andEqualTo(ModuleUser.MODULE_ID, moduleId);
        List<ModuleUser> moduleUsers = moduleUserDao.find(example1);
        for (ModuleUser moduleUser: moduleUsers) {
            Long userId = moduleUser.getUserId();
            moduleUserMap.put(userId, moduleUser);
        }
        //4、遍历userIds
        for (Long userId: userIds) {
            //5、求学生该单元状态
            Integer moduleStatus = getModuleStatus(moduleId, userId);
            //6、构造moduleUsers
            ModuleUser moduleUser = moduleUserMap.get(userId);
            if (null != moduleUser) {
                moduleUser.setStatus(moduleStatus);
                newModuleUsers.add(moduleUser);
            }
        }
        //7、批量更新moduleUser
        moduleUserDao.batchUpdate(newModuleUsers);
    }

    private List<Long> getAssignUserIds(ModuleItem moduleItem, Long courseId) {
        //0、参数解析
        Integer originType = moduleItem.getOriginType();
        Long originId = moduleItem.getOriginId();
        //1、获取userIds
        List<Long> userIds = Lists.newArrayList();
        if (OriginTypeEnum.ASSIGNMENT.getType().equals(originType) || OriginTypeEnum.DISCUSSION.getType().equals(originType) || OriginTypeEnum.QUIZ.getType().equals(originType)) {
            Example example = assignUserDao.getExample();
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("originId", originId);
            criteria.andEqualTo("originType", originType);
            List<AssignUser> assignUsers = assignUserDao.find(example);
            userIds = assignUsers.stream().map(AssignUser::getUserId).collect(Collectors.toList());
        } else {
            List<CourseUser> courseUserList = courseUserDao.findUserByCourseId(courseId);
            userIds = courseUserList.stream().map(CourseUser::getUserId).collect(Collectors.toList());
        }
        return userIds;
    }

    private Integer getModuleStatus(Long moduleId, Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("moduleId", moduleId);
        map.put("userId", userId);
        Integer count1 = moduleItemUserDao.getModuleItemUserCountByModule(map);
        Integer count2 = moduleItemUserDao.getInCompleteModuleItemUserCountByModule(map);
        Integer moduleStatus = ModuleStatusEnum.INCOMPLETE.getType();
        if (count1 > 0 &&  0 == count2) {
            moduleStatus = ModuleStatusEnum.COMPLETE.getType();
        }
        return moduleStatus;
    }

    private Map<Long, Boolean> getSubmitStatus(List<Long> userIds, Integer originType, Long originId) {
        Map<Long, Boolean> map = new HashMap<>();
        for (Long userId : userIds) {
            if (originType == 1) {
                Assignment assignment = assignmentDao.get(originId);
                if (null == assignment) {
                    continue;
                }
                Example example = assignmentReplyDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(Constants.PARAM_ASSIGNMENT_ID, originId);
                if (StringUtils.isEmpty(assignment.getStudyGroupSetId())) {
                    criteria.andEqualTo(AssignmentReply.USER_ID, userId);//不是组作业
                } else {
                    //获取小组Ids
                    List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                            .studyGroupSetId(assignment.getStudyGroupSetId())
                            .userId(userId)
                            .courseId(assignment.getCourseId()).build());
                    List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
                    if (ss.size() > 0) {
                        criteria.andIn(AssignmentReply.STUDY_GROUP_ID, ss);
                    }else{
                        criteria.andEqualTo(AssignmentReply.USER_ID, userId);//没有在小组中
                    }
                }
                AssignmentReply reply = assignmentReplyDao.findOne(example);
                map.put(userId, reply != null);
            } else if (originType == 2) {
                Discussion discussion = discussionDao.get(originId);
                Example example = discussionReplyDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(DiscussionReply.DISCUSSION_ID, originId);
                criteria.andEqualTo(DiscussionReply.IS_DELETED, 0);
                criteria.andEqualTo(DiscussionReply.ROLE_ID, 4);
                if (StringUtils.isEmpty(discussion.getStudyGroupSetId())) {
                    criteria.andEqualTo(DiscussionReply.CREATE_USER_ID, userId);//不是组作业
                } else {
                    //获取小组Ids
                    List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                            .studyGroupSetId(discussion.getStudyGroupSetId())
                            .userId(userId)
                            .courseId(discussion.getCourseId()).build());
                    List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
                    if (ss.size() > 0) {
                        criteria.andIn(DiscussionReply.CREATE_USER_ID, ss);
                    }else{
                        criteria.andEqualTo(DiscussionReply.CREATE_USER_ID, userId);//没有在小组中
                    }
                }
                DiscussionReply reply = discussionReplyDao.findOne(example);
                map.put(userId, reply != null);
            } else if (originType == 3) {
                Example example = quizRecordDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(QuizRecord.QUIZ_ID, originId);
                criteria.andEqualTo(QuizRecord.CREATE_USER_ID, userId);
                QuizRecord reply = quizRecordDao.findOne(example);
                map.put(userId, reply != null);
            } else {
                map.put(userId, false);
            }
        }
        return map;
    }

    /**
     * 完成/查看任务
     */
    public void completeAssignment(Long originId, Integer originType) {
        //1、获取moduleItem
        Example example = moduleItemDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItem.ORIGIN_TYPE, originType);
        criteria.andEqualTo(ModuleItem.ORIGIN_ID, originId);
        List<ModuleItem> moduleItems = moduleItemDao.find(example);
        if (moduleItems.size() == 0) {
            return;
        }
        ModuleItem moduleItem = moduleItems.get(0);
        //2、参数解析
        Long moduleId = moduleItem.getModuleId();
        Long moduleItemId = moduleItem.getId();
        Long userId = WebContext.getUserId();
        //3、判断是否需要更新完成状态
        List<ModuleItemUser> list = getModuleItemUsersByModuleItemAndUser(moduleId, moduleItemId, userId);
        if (list.size() == 0) {
            return;
        }
        //4、更新状态moduleItemUser
        updateModuleItemUsersByModuleItemAndUser(moduleId, moduleItemId, userId, originId, originType);
        //5、更新状态moduleUser
        updateModuleUserStatusByModuleAndUser(moduleId, userId);
    }

    private List<ModuleItemUser> getModuleItemUsersByModuleItemAndUser(Long moduleId, Long moduleItemId, Long userId){
        Example example = moduleItemUserDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItemUser.MODULE_ID, moduleId);
        criteria.andEqualTo(ModuleItemUser.MODULE_ITEM_ID, moduleItemId);
        criteria.andEqualTo(ModuleItemUser.USER_ID, userId);
        List<ModuleItemUser> moduleItemUsers = moduleItemUserDao.find(example);
        return moduleItemUsers;
    }

    private List<ModuleItemUser> getModuleItemUsersByModuleItemAndUserAndGroup(Long moduleId, Long moduleItemId, Long userId, Long originId, Integer originType){
        List<Long> userIds = Lists.newArrayList();
        if (originType == 1) {
            Assignment assignment = assignmentDao.get(originId);
            if (null == assignment) {
                return null;
            }
            if (StringUtils.isEmpty(assignment.getStudyGroupSetId())) {
                userIds.add(userId);
            } else {
                List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                        .studyGroupSetId(assignment.getStudyGroupSetId())
                        .userId(userId)
                        .courseId(assignment.getCourseId()).build());
                List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
                if (ss.size() > 0) {
                    List<StudyGroupUser> studyGroupUsers1 = studyGroupUserDao.find(StudyGroupUser.builder()
                            .studyGroupId(ss.get(0))
                            .build());
                    List<Long> ss2 = studyGroupUsers1.stream().map(StudyGroupUser::getUserId).collect(Collectors.toList());
                    userIds.addAll(ss2);
                }else{
                    userIds.add(userId);//没有在小组中
                }
            }
        } else if (originType == 2) {
            Discussion discussion = discussionDao.get(originId);
            if (null == discussion) {
                return null;
            }
            if (StringUtils.isEmpty(discussion.getStudyGroupSetId())) {
                userIds.add(userId);
            } else {
                List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                        .studyGroupSetId(discussion.getStudyGroupSetId())
                        .userId(userId)
                        .courseId(discussion.getCourseId()).build());
                List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
                if (ss.size() > 0) {
                    List<StudyGroupUser> studyGroupUsers1 = studyGroupUserDao.find(StudyGroupUser.builder()
                            .studyGroupId(ss.get(0))
                            .build());
                    List<Long> ss2 = studyGroupUsers1.stream().map(StudyGroupUser::getUserId).collect(Collectors.toList());
                    userIds.addAll(ss2);
                }else{
                    userIds.add(userId);//没有在小组中
                }
            }
        } else {
            userIds.add(userId);
        }
        Example example = moduleItemUserDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItemUser.MODULE_ID, moduleId);
        criteria.andEqualTo(ModuleItemUser.MODULE_ITEM_ID, moduleItemId);
        criteria.andIn(ModuleItemUser.USER_ID, userIds);
        List<ModuleItemUser> moduleItemUsers = moduleItemUserDao.find(example);
        return moduleItemUsers;
    }

    private void updateModuleItemUsersByModuleItemAndUser(Long moduleId, Long moduleItemId, Long userId, Long originId, Integer originType) {
        List<ModuleItemUser> moduleItemUsers = getModuleItemUsersByModuleItemAndUserAndGroup(moduleId, moduleItemId, userId, originId, originType);
        if (null == moduleItemUsers || 0 == moduleItemUsers.size()) {
            throw new BaseException("complete.assignment.moduleItemUser.nonExists");
        }
        for (ModuleItemUser x: moduleItemUsers) {
            x.setStatus(ModuleItemStatusEnum.COMPLETE.getType());
        }
        moduleItemUserDao.batchUpdate(moduleItemUsers);
    }

    private List<ModuleUser> getModuleUsersByModuleAndUser(Long moduleId, Long userId) {
        Example example1 = moduleUserDao.getExample();
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo(ModuleUser.MODULE_ID, moduleId);
        criteria1.andEqualTo(ModuleUser.USER_ID, userId);
        List<ModuleUser> moduleUsers = moduleUserDao.find(example1);
        return moduleUsers;
    }

    private void updateModuleUserStatusByModuleAndUser(Long moduleId, Long userId) {
        List<ModuleUser> moduleUsers = getModuleUsersByModuleAndUser(moduleId, userId);
        if (null == moduleUsers || 0 == moduleUsers.size()) {
            throw new BaseException("complete.assignment.moduleUser.nonExists");
        }
        ModuleUser moduleUser = moduleUsers.get(0);
        Integer moduleStatus = getModuleStatus(moduleId, userId);
        moduleUser.setStatus(moduleStatus);
        moduleUserDao.update(moduleUser);
    }

    /**
     * 删除单元项
     */
    public void deleteModuleItem(Long moduleItemId) {
        ModuleItem moduleItem = moduleItemDao.get(moduleItemId);
        //0、参数解析
        Long moduleId = moduleItem.getModuleId();
        //1、删除cos_module_item_user
        deleteModuleItemUsersByModuleItem(moduleItem);
        //2、更新cos_module_user状态
        updateModuleUserStatusByModule(moduleId);
    }

    /**
     * 删除单元项
     */
    public void deleteModuleItemByOriginId(Long originId, Integer originType) {
        Example example = moduleItemDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItem.ORIGIN_ID, originId);
        criteria.andEqualTo(ModuleItem.ORIGIN_TYPE, originType);
        List<ModuleItem> moduleItems = moduleItemDao.find(example);
        for (ModuleItem moduleItem: moduleItems) {
            deleteModuleItem(moduleItem.getId());
        }
    }

    private void deleteModuleItemUsersByModuleItem(ModuleItem moduleItem) {
        //0、参数解析
        Long moduleId = moduleItem.getModuleId();
        Long moduleItemId = moduleItem.getId();
        //1、获取需要删除的cos_module_item_user
        List<ModuleItemUser> moduleItemUsers = getModuleItemUserByModuleItem(moduleId, moduleItemId);
        //2、删除cos_module_item_user
        if (moduleItemUsers.size() != 0) {
            moduleItemUserDao.batchDelete(moduleItemUsers);
        }
    }

    private List<ModuleItemUser> getModuleItemUserByModuleItem(Long moduleId, Long moduleItemId) {
        Example example = moduleItemUserDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItemUser.MODULE_ID, moduleId);
        criteria.andEqualTo(ModuleItemUser.MODULE_ITEM_ID, moduleItemId);
        List<ModuleItemUser> moduleItemUsers = moduleItemUserDao.find(example);
        return moduleItemUsers;
    }

    private List<ModuleUser> getModuleUsersByModule(Long moduleId) {
        Example example1 = moduleUserDao.getExample();
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo(ModuleUser.MODULE_ID, moduleId);
        List<ModuleUser> moduleUsers = moduleUserDao.find(example1);
        return moduleUsers;
    }

    /**
     * 更新单元进度状态通过单元id
     */
    public void updateModuleUserStatusByModule(Long moduleId) {
        //1、获取单元相关cos_module_user
        List<ModuleUser> moduleUsers = getModuleUsersByModule(moduleId);
        //2、批量修改状态cos_module_user
        for (ModuleUser moduleUser: moduleUsers) {
            Integer moduleStatus = getModuleStatus(moduleId, moduleUser.getUserId());
            moduleUser.setStatus(moduleStatus);
        }
        moduleUserDao.batchUpdate(moduleUsers);
    }

    /**
     * 移动单元项
     */
    public void moveModuleItem(Long moduleItemOriginId, Long moduleIdNow) {
        ModuleItem moduleItemOrigin = moduleItemDao.get(moduleItemOriginId);
        //1、更新moduleItemUser为移动单元项根据moduleItem
        updateModuleItemUserForMoveByModuleItem(moduleItemOrigin, moduleIdNow);
        //2、参数解析
        Long moduleId = moduleItemOrigin.getModuleId();
        //3、修改原来单元状态cos_module_user
        updateModuleUserStatusByModule(moduleId);
        //4、修改现在单元状态cos_module_user
        updateModuleUserStatusByModule(moduleIdNow);
    }

    private void updateModuleItemUserForMoveByModuleItem(ModuleItem moduleItemOrigin, Long moduleIdNow) {
        //0、参数解析
        Long moduleId = moduleItemOrigin.getModuleId();
        Long moduleItemId = moduleItemOrigin.getId();
        //1、获取相关cos_module_item_user
        List<ModuleItemUser> moduleItemUsers = getModuleItemUserByModuleItem(moduleId, moduleItemId);
        //2、修改cos_module_item_user的moduleId
        for (ModuleItemUser moduleItemUser: moduleItemUsers) {
            moduleItemUser.setModuleId(moduleIdNow);
        }
        moduleItemUserDao.batchUpdate(moduleItemUsers);
    }

    /**
     * 移动单元项
     */
    public void moveModuleItems(Long moduleId, Long moduleIdNow) {
        //1、获取moduleItems
        Example example = moduleItemDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItem.MODULE_ID, moduleId);
        List<ModuleItem> moduleItems = moduleItemDao.find(example);
        for (ModuleItem moduleItemOrigin: moduleItems) {
            //2、更新moduleItemUser为移动单元项根据moduleItem
            updateModuleItemUserForMoveByModuleItem(moduleItemOrigin, moduleIdNow);
        }
        //3、修改原来单元状态cos_module_user
        updateModuleUserStatusByModule(moduleId);
        //4、修改现在单元状态cos_module_user
        updateModuleUserStatusByModule(moduleIdNow);
    }

    /**
     * 编辑任务分配
     */
    public void updateAssign(Long courseId, Long originId, Integer originType) {
        //0、获取moduleItems
        Example example = moduleItemDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItem.ORIGIN_ID, originId);
        criteria.andEqualTo(ModuleItem.ORIGIN_TYPE, originType);
        List<ModuleItem> moduleItems = moduleItemDao.find(example);
        if (moduleItems.size() == 0) {
            return;
        }
        //1、处理moduleItemUsers根据moduleItem
        addModuleItemUsersByModuleItem(moduleItems, courseId);
        //2、更新状态moduleUser
        for (ModuleItem moduleItem: moduleItems) {
            updateModuleUserStatusByModule(moduleItem.getModuleId());
        }
    }

    /**
     * 删除单元
     */
    public void deleteModule(Long moduleId) {
        //1、根据moduleId删除moduleItemUser
        deleteModuleItemUsersByModule(moduleId);
        //2、根据moduleId删除moduleUser
        deleteModuleUsersByModule(moduleId);
    }

    private void deleteModuleItemUsersByModule(Long moduleId) {
        //1、根据moduleId获取moduleItemUser
        List<ModuleItemUser> moduleItemUsers = getModuleItemUsersByModule(moduleId);
        //2、删除moduleItemUser
        moduleItemUserDao.batchDelete(moduleItemUsers);
    }

    private List<ModuleItemUser> getModuleItemUsersByModule(Long moduleId) {
        Example example = moduleItemUserDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItemUser.MODULE_ID, moduleId);
        List<ModuleItemUser> moduleItemUsers = moduleItemUserDao.find(example);
        return moduleItemUsers;
    }

    private void deleteModuleUsersByModule(Long moduleId) {
        //1、根据moduleId获取moduleUser
        List<ModuleUser> moduleUsers = getModuleUsersByModule(moduleId);
        //2、删除moduleUser
        moduleUserDao.batchDelete(moduleUsers);
    }

    /**
     * 班級添加/删除人员
     */
    public void addSectionUser (AssignUserOparateDTO dto) {
        //0、参数解析
        Long courseId = dto.getCourseId();
        Long userId = dto.getUserId();
        Integer oparateType = dto.getOparateType();
        Map<Long, List<ModuleItem>> map = new HashMap<>();
        //1、获取该课程的所有单元
        Example example = moduleDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Module.COURSE_ID, courseId);
        List<Module> modules = moduleDao.find(example);
        //2、获取每个单元的所有单元项
        List<ModuleItem> moduleItems = moduleItemDao.getModuleItemByCourse(courseId);
        for (ModuleItem moduleItem: moduleItems) {
            Long moduleId = moduleItem.getModuleId();
            if (null == map.get(moduleId)) {
                List<ModuleItem> list = Lists.newArrayList();
                list.add(moduleItem);
                map.put(moduleId, list);
            } else {
                List<ModuleItem> list = map.get(moduleId);
                list.add(moduleItem);
                map.put(moduleId, list);
            }
        }
        for (Module module: modules) {
            Long moduleId = module.getId();
            List<ModuleItem> list = map.get(moduleId);
            if (list == null) {
                continue;
            }
            //3、处理moduleItemUser根据moduleItem(作业、讨论、测验)
            List<ModuleItem> list_1 = list.stream().filter(o -> 1 == o.getOriginType() || 2 == o.getOriginType() || 3 == o.getOriginType()).collect(Collectors.toList());
            addModuleItemUsersByModuleItem(list_1, courseId);
            //4、处理moduleItemUser根据moduleItem(文件、标题、网址)
            List<ModuleItem> list_2 = list.stream().filter(o -> 4 == o.getOriginType() || 13 == o.getOriginType() || 14 == o.getOriginType()).collect(Collectors.toList());
            for (ModuleItem moduleItem: list_2) {
                Integer originType = moduleItem.getOriginType();
                //4.1、查询moduleItemUser根据单元项和user
                Long moduleItemId = moduleItem.getId();
                List<ModuleItemUser> moduleItemUsers = getModuleItemUsersByModuleItemAndUser(moduleId, moduleItemId, userId);
                //4.2、删除moduleItemUser或者添加moduleItemUser
                if (AssignUserOparateDTO.OperateType.ADD.getType().equals(oparateType)) {
                    if (moduleItemUsers.size() == 0) {
                        ModuleItemUser record = ModuleItemUser.builder()
                                .moduleId(moduleId)
                                .moduleItemId(moduleItemId)
                                .userId(userId)
                                .status(0)
                                .build();
                        moduleItemUserDao.insert(record);
                    }
                } else {
                    moduleItemUserDao.batchDelete(moduleItemUsers);
                }
            }
            //5、添加或删除moduleUser根据module
            addModuleUsersByModule(courseId, moduleId);
        }
    }

    /**
     * 处理上线前历史数据
     */
    public void initHistoryData() {
        //0、初始化数据
        Map<Integer, List<ModuleItemUser>> moduleItemUserMap = new HashMap<>();
        List<ModuleItemUser> addModuleItem = Lists.newArrayList();
        List<ModuleItemUser> updateModuleItem = Lists.newArrayList();
        List<ModuleItemUser> deleteModuleItem = Lists.newArrayList();
        moduleItemUserMap.put(1, addModuleItem);
        moduleItemUserMap.put(2, updateModuleItem);
        moduleItemUserMap.put(3, deleteModuleItem);
        Map<Integer, List<ModuleUser>> moduleUserMap = new HashMap<>();
        List<ModuleUser> addModule = Lists.newArrayList();
        List<ModuleUser> updateModule = Lists.newArrayList();
        List<ModuleUser> deleteModule = Lists.newArrayList();
        moduleUserMap.put(1, addModule);
        moduleUserMap.put(2, updateModule);
        moduleUserMap.put(3, deleteModule);
        //1、获取数据库所有的module
        Example example = moduleDao.getExample();
        List<Module> modules = moduleDao.find(example);
        //2、针对每个module获取该module下的moduleItem
        for (Module module: modules) {
            Long moduleId = module.getId();
            Example example1 = moduleItemDao.getExample();
            Example.Criteria criteria = example1.createCriteria();
            criteria.andEqualTo(ModuleItem.MODULE_ID, module.getId());
            List<ModuleItem> moduleItems = moduleItemDao.find(example1);
            //2.1、针对每个moduleItem
            for (ModuleItem moduleItem: moduleItems) {
                Long courseId = module.getCourseId();
                //2.2、获取需要添加、更新和删除的moduleItemUsers
                moduleItemUserMap = getModuleItemUserMapByModuleItem(moduleItem, courseId, moduleItemUserMap);
            }
        }
        //3、添加获取到的moduleItemUsers
        moduleItemUserDao.batchInsert(moduleItemUserMap.get(1));
        //4、更新获取到的moduleItemUsers
        moduleItemUserDao.batchUpdate(moduleItemUserMap.get(2));
        //5、删除获取到的moduleItemUsers
        moduleItemUserDao.batchDelete(moduleItemUserMap.get(3));
        //6、针对每个module
        for (Module module: modules) {
            Long courseId = module.getCourseId();
            Long moduleId = module.getId();
            //6.1、获取需要添加、更新和删除的moduleUsers
            moduleUserMap = getModuleUserMapByModule(courseId, moduleId, moduleUserMap);
        }
        //7、添加获取到的moduleUsers
        moduleUserDao.batchInsert(moduleUserMap.get(1));
        //8、更新获取到的moduleUsers
        moduleUserDao.batchUpdate(moduleUserMap.get(2));
        //9、删除获取到的moduleUsers
        moduleUserDao.batchDelete(moduleUserMap.get(3));
    }

    private Map<Integer, List<ModuleUser>> getModuleUserMapByModule(Long courseId, Long moduleId, Map<Integer, List<ModuleUser>> map) {
        //0、参数解析
        List<ModuleUser> addModuleUsers = map.get(1);
        List<ModuleUser> updateModuleUsers = map.get(2);
        List<ModuleUser> deleteModuleUsers = map.get(3);
        //1、获取该课程下的userIds
        List<CourseUser> courseUserList = courseUserDao.findUserByCourseId(courseId);
        List<Long> userIds = courseUserList.stream().map(CourseUser::getUserId).collect(Collectors.toList());
        //2、构造moduleUsers
        for (Long userId: userIds) {
            //3、该单元状态对于userId
            Integer moduleStatus = getModuleStatus(moduleId, userId);
            //4、验证是否重复
            List<ModuleUser> list = getModuleUsersByModuleAndUser(moduleId, userId);
            if (list.size() > 0) {
                ModuleUser x = list.get(0);
                x.setStatus(moduleStatus);
                updateModuleUsers.add(x);
                continue;
            }
            List<ModuleUser> list2 = addModuleUsers.stream().filter(o -> moduleId.equals(o.getModuleId()) && userId.equals(o.getUserId())).collect(Collectors.toList());
            if (list2.size() > 0) {
                continue;
            }
            //5、获取moduleUser
            ModuleUser moduleUser = new ModuleUser();
            moduleUser.setCourseId(courseId);
            moduleUser.setModuleId(moduleId);
            moduleUser.setUserId(userId);
            moduleUser.setStatus(moduleStatus);
            moduleUser.setCreateUserId(WebContext.getUserId());
            moduleUser.setUpdateUserId(WebContext.getUserId());
            addModuleUsers.add(moduleUser);
        }
        //6、获取需要删除的moduleUsers
        List<ModuleUser> list3 = getModuleUsersByModule(moduleId);
        List<ModuleUser> list4 = list3.stream().filter(o -> !userIds.contains(o.getUserId())).collect(Collectors.toList());
        deleteModuleUsers.addAll(list4);
        map.put(1, addModuleUsers);
        map.put(2, updateModuleUsers);
        map.put(3, deleteModuleUsers);
        return map;
    }

    private Map<Integer, List<ModuleItemUser>> getModuleItemUserMapByModuleItem(ModuleItem moduleItem, Long courseId, Map<Integer, List<ModuleItemUser>> map) {
        //0、参数解析
        Long moduleItemId = moduleItem.getId();
        Long moduleId = moduleItem.getModuleId();
        List<ModuleItemUser> addModuleItemUsers = map.get(1);
        List<ModuleItemUser> updateModuleItemUsers = map.get(2);
        List<ModuleItemUser> deleteModuleItemUsers = map.get(3);
        //1、获取userIds
        List<Long> userIds = getAssignUserIds(moduleItem, courseId);
        //2、获取内容项完成状态对于userIds
        Map<Long, Boolean> submitStatusMap = getSubmitStatus(userIds, moduleItem.getOriginType(), moduleItem.getOriginId());
        //3、获取需要添加的moduleItemUsers
        for (Long userId: userIds) {
            //4、求学生任务状态
            Integer status = ModuleItemStatusEnum.INCOMPLETE.getType();
            if (null == submitStatusMap.get(userId)) {
                continue;
            }
            if (submitStatusMap.get(userId)) {
                status = ModuleItemStatusEnum.COMPLETE.getType();
            }
            //5、获取需要更新的moduleItemUsers
            List<ModuleItemUser> list = getModuleItemUsersByModuleItemAndUser(moduleId, moduleItemId, userId);
            if (list.size() > 0) {
                ModuleItemUser x = list.get(0);
                x.setStatus(status);
                updateModuleItemUsers.add(x);
                continue;
            }
            //6、排重
            List<ModuleItemUser> list2 = addModuleItemUsers.stream().filter(o -> moduleItemId.equals(o.getModuleItemId()) && userId.equals(o.getUserId())).collect(Collectors.toList());
            if (list2.size() > 0) {
                continue;
            }
            //7、获取需要添加的moduleItemUsers
            ModuleItemUser moduleItemUser = new ModuleItemUser();
            moduleItemUser.setModuleId(moduleId);
            moduleItemUser.setModuleItemId(moduleItemId);
            moduleItemUser.setUserId(userId);
            moduleItemUser.setStatus(status);
            addModuleItemUsers.add(moduleItemUser);
        }
        //8、获取需要删除的moduleItemUsers
        List<ModuleItemUser> list3 = getModuleItemUserByModuleItem(moduleId, moduleItemId);
        List<ModuleItemUser> list4 = list3.stream().filter(o -> !userIds.contains(o.getUserId())).collect(Collectors.toList());
        deleteModuleItemUsers.addAll(list4);
        map.put(1, addModuleItemUsers);
        map.put(2, updateModuleItemUsers);
        map.put(3, deleteModuleItemUsers);
        return map;
    }

    public Map<Long, Integer> getModuleCompleteStatusByModuleAndUser(Long courseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("userId", WebContext.getUserId());
        List<ModuleVO> statusLists = moduleDao.getModuleCompleteStatusByModuleAndUser(params);
        Map<Long, Integer> statusMap = new HashMap<>();
        for (ModuleVO o: statusLists) {            ;
            statusMap.put(o.getId(), o.getCompleteStatus());
        }
        return statusMap;
    }

    public Map<Long, Integer> getModuleItemCompleteStatusByModuleAndUser(Long moduleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("moduleId", moduleId);
        params.put("userId", WebContext.getUserId());
        List<ModuleItemCompleteVO> statusLists = moduleItemDao.getModuleItemCompleteStatusByModuleAndUser(params);
        Map<Long, Integer> statusMap = new HashMap<>();
        for (ModuleItemCompleteVO o: statusLists) {            ;
            statusMap.put(o.getModuleItemId(), o.getCompleteStatus());
        }
        return statusMap;
    }

}
