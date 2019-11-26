package com.wdcloud.lms.base.service;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StudyGroupService {

    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;

    /**
     * 在删除或移动小组用户时，若该用户是小组长时，将小组中的leaderId置为null
     * @param groupUser
     * @param groupId
     */
    public void updateLeaderINulldByIsLeader(StudyGroupUser groupUser, Long groupId){
        if(Objects.equals(groupUser.getIsLeader(), Status.YES.getStatus())){
            StudyGroup studyGroup = studyGroupDao.get(groupId);
            if (studyGroup == null) {
                throw new PropValueUnRegistryException("studyGroupId", groupId);
            }

            studyGroup.setLeaderId(null);
            studyGroupDao.updateIncludeNull(studyGroup);
        }
    }

    /**
     * 1.更新小组用户2.根据小组长策略，判断是否需要小组长，若需要生成，则更新用户及小组信息
     * @param leaderSetStrategy
     * @param studyGroup
     * @param groupUser
     */
    public void UpdateLeaderAndGroupUser(Integer leaderSetStrategy,
                                                StudyGroup studyGroup, StudyGroupUser groupUser){

        LeaderSetStrategyEnum leaderSetStrategyEnum = LeaderSetStrategyEnum.strategyOf(leaderSetStrategy);
        //小组内的人数
        int count = studyGroupUserDao.count(StudyGroupUser.builder()
                .studyGroupId(studyGroup.getId())
                .build());

        if (leaderSetStrategyEnum == LeaderSetStrategyEnum.FIRST_JOIN) {
            if (count == 0) {
                groupUser.setIsLeader(Status.YES.getStatus());
                studyGroup.setLeaderId(groupUser.getUserId());
            }

            saveOrUpdateGroupUser(groupUser);
        } else if (leaderSetStrategyEnum == LeaderSetStrategyEnum.RANDOM) {
            saveOrUpdateGroupUser(groupUser);

            //人数达到最大值，且小组无小组长时，设置小组长
            if(count == studyGroup.getMaxMemberNumber()-1 && null == studyGroup.getLeaderId()){
                List<StudyGroupUser> groupUsers = studyGroupUserDao.findUserByCourseIdAndGroupId(
                        studyGroup.getCourseId(), studyGroup.getId());

                int random = RandomUtils.randomNum(groupUsers.size());
                StudyGroupUser leader = groupUsers.get(random);
                leader.setIsLeader(Status.YES.getStatus());
                studyGroup.setLeaderId(leader.getUserId());

                studyGroupUserDao.update(leader);
            }
        }else{
            saveOrUpdateGroupUser(groupUser);
        }

        studyGroupDao.update(studyGroup);
    }

    private void saveOrUpdateGroupUser(StudyGroupUser groupUser){
        if(Objects.equals(groupUser.getId(), null)){
            studyGroupUserDao.save(groupUser);
        }else{
            studyGroupUserDao.update(groupUser);
        }
    }
}
