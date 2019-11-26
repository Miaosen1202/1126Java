package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.business.studygroup.vo.StudyGroupSetEditVo;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.server.frame.interfaces.IDataLinkedHandle;
import com.wdcloud.server.frame.interfaces.LinkedHandler;
import com.wdcloud.server.frame.interfaces.OperateType;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 小组集创建后初始化创建小组
 */
@LinkedHandler(
        dependResourceName = Constants.RESOURCE_TYPE_STUDY_GROUP_SET,
        operateType = OperateType.ADD
)
public class StudyGroupAfterGroupSetAddHandler implements IDataLinkedHandle {
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private UserFileService userFileService;

    @Override
    public LinkedInfo linkedHandle(LinkedInfo linkedInfo) {
        long studyGroupSetId = Long.parseLong(linkedInfo.masterId);
        StudyGroupSetEditVo studyGroupSetEditVo = JSON.parseObject(linkedInfo.beanJson, StudyGroupSetEditVo.class);

        Integer createNGroup = studyGroupSetEditVo.getCreateNGroup();
        if (createNGroup == null || createNGroup <= 0) {
            return linkedInfo;
        }

        // 创建N个小组
        List<StudyGroup> studyGroups = new ArrayList<>(createNGroup);
        Long courseId = studyGroupSetEditVo.getCourseId();
        for (int i = 0; i < createNGroup; i++) {
            StudyGroup studyGroup = StudyGroup
                    .builder()
                    .courseId(courseId)
                    .studyGroupSetId(studyGroupSetId)
                    .isStudentGroup(studyGroupSetEditVo.getIsStudentGroup())
                    .maxMemberNumber(studyGroupSetEditVo.getGroupMemberNumber() == null ?
                            Integer.MAX_VALUE : studyGroupSetEditVo.getGroupMemberNumber())
                    .name(studyGroupSetEditVo.getName() + " " + (i + 1))
                    .createUserId(WebContext.getUserId())
                    .updateUserId(WebContext.getUserId())
                    .build();
            studyGroups.add(studyGroup);
            studyGroupDao.save(studyGroup);

            userFileService.initStudyGroupDir(studyGroup.getCourseId(), studyGroup.getId(), studyGroup.getName());
        }

        // 允许自行注册时不需要划分学生到组中
        if (Objects.equals(studyGroupSetEditVo.getAllowSelfSignup(), Status.YES.getStatus())) {
            return linkedInfo;
        }

        Map<StudyGroup, List<StudyGroupUser>> groupUserMap = new HashMap<>();
        if (Objects.equals(Status.NO.getStatus(), studyGroupSetEditVo.getAllowSelfSignup())) {

            List<SectionUser> allSectionStudents = sectionUserDao.find(SectionUser.builder().courseId(courseId).roleId(RoleEnum.STUDENT.getType()).build());
            //如果该课程下没有学生，则返回
            if(allSectionStudents.isEmpty()){
                return linkedInfo;
            }

            if (Objects.equals(studyGroupSetEditVo.getIsSectionGroup(), Status.YES.getStatus())) {
                // 按班级分派 fixme 限制同组学生在相同班级
                Map<Long, List<SectionUser>> sectionUserMap = allSectionStudents.stream().collect(Collectors.groupingBy(SectionUser::getSectionId));

                int ind = 0;
                for (Map.Entry<Long, List<SectionUser>> sectionIdAndSectionUsers : sectionUserMap.entrySet()) {
                    List<SectionUser> sectionUsers = sectionIdAndSectionUsers.getValue();

                    if (ind < studyGroups.size()) {
                        StudyGroup group = studyGroups.get(ind++);
                        for (SectionUser sectionUser : sectionUsers) {
                            StudyGroupUser groupUser = StudyGroupUser
                                    .builder()
                                    .userId(sectionUser.getUserId())
                                    .studyGroupSetId(group.getStudyGroupSetId())
                                    .studyGroupId(group.getId())
                                    .isLeader(Status.NO.getStatus())
                                    .build();
                            if (!groupUserMap.containsKey(group)) {
                                groupUserMap.put(group, new ArrayList<>());
                            }
                            groupUserMap.get(group).add(groupUser);

                            studyGroupUserDao.save(groupUser);
                        }
                    }
                }
            } else {
                Set<Long> studentIds = allSectionStudents .stream()
                        .map(SectionUser::getUserId)
                        .collect(Collectors.toSet());

                //将学生平均分配到组里
                int i = 0;
                for(Long studentId : studentIds){
                    //将学生依次放入小组内
                    StudyGroup group = studyGroups.get(i++);
                    StudyGroupUser groupUser = StudyGroupUser
                            .builder()
                            .userId(studentId)
                            .courseId(group.getCourseId())
                            .studyGroupSetId(group.getStudyGroupSetId())
                            .studyGroupId(group.getId())
                            .isLeader(Status.NO.getStatus())
                            .build();
                    if (!groupUserMap.containsKey(group)) {
                        groupUserMap.put(group, new ArrayList<>());
                    }
                    groupUserMap.get(group).add(groupUser);

                    studyGroupUserDao.save(groupUser);

                    //重新开始往每个组里放入学生
                    if(i == createNGroup) {
                        i=0;
                    }
                }
            }
        }

        // 划分学生到小组
        LeaderSetStrategyEnum leaderSetStrategyEnum = LeaderSetStrategyEnum.strategyOf(studyGroupSetEditVo.getLeaderSetStrategy());

        // 选择小组leader
        for (Map.Entry<StudyGroup, List<StudyGroupUser>> studyGroupListEntry : groupUserMap.entrySet()) {
            StudyGroup group = studyGroupListEntry.getKey();
            List<StudyGroupUser> groupUsers = studyGroupListEntry.getValue();
            if (!groupUsers.isEmpty()) {
                StudyGroupUser leader = null;
                if (leaderSetStrategyEnum == LeaderSetStrategyEnum.FIRST_JOIN) {
                    leader = groupUsers.get(0);
                } else if (leaderSetStrategyEnum == LeaderSetStrategyEnum.RANDOM) {

                    /**
                     * 1.不允许自行注册时，小组长随机分配；
                     * 2.允许注册时，当小组人数达到最大值且无小组长时设置;且允许自行注册时 ，
                     * 刚创建的小组内不存在学生
                     */
                    if(Objects.equals(Status.NO.getStatus(), studyGroupSetEditVo.getAllowSelfSignup())){
                        int random = RandomUtils.randomNum(groupUsers.size());
                        leader = groupUsers.get(random);
                    }
                }
                if (leader != null) {
                    leader.setIsLeader(Status.YES.getStatus());
                    group.setLeaderId(leader.getUserId());

                    studyGroupDao.update(group);
                    studyGroupUserDao.update(leader);
                }
            }
        }
        return linkedInfo;
    }
}
