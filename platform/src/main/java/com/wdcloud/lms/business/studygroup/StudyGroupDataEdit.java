package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.studygroup.enums.LeaderSetStrategyEnum;
import com.wdcloud.lms.business.studygroup.enums.StudyGroupJoinTypeEnum;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.RandomUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_STUDY_GROUP)
public class StudyGroupDataEdit implements IDataEditComponent {
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserFileService userFileService;

    /**
     * @api {post} /studyGroup/add 学习小组添加
     * @apiDescription 如果是学生创建学习小组，则学生自动加入小组
     * @apiName studyGroupAdd
     * @apiGroup StudyGroup
     *
     * @apiParam {String} name 小组名
     * @apiParam {Number} [studyGroupSetId] 所属小组集
     * @apiParam {Number} [groupMemberNumber] 小组成员数量
     * @apiParam {Number=1,2} [joinType] 加入类型，1：无限制 2: 仅限邀请
     * @apiParam {Number[]} [inviteUserIds] 创建小组后同时添加用户到小组内
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 小组ID
     */
    @ValidationParam(clazz = StudyGroupEditVo.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        StudyGroupEditVo studyGroupVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupEditVo.class);
        StudyGroupSet studyGroupSet;

        if (studyGroupVo.getStudyGroupSetId() == null) {
            if (studyGroupVo.getCourseId() == null) {
                throw new ParamErrorException();
            }

            studyGroupSet = studyGroupSetDao.findOne(StudyGroupSet.builder()
                    .courseId(studyGroupVo.getCourseId())
                    .isStudentGroup(Status.YES.getStatus()).build());
        } else {
            studyGroupSet = studyGroupSetDao.get(studyGroupVo.getStudyGroupSetId());
        }

        if (studyGroupSet == null) {
            throw new BaseException("prop.value.not-exists", "studyGroupSetId", String.valueOf(studyGroupVo.getStudyGroupSetId()));
        }

        StudyGroup studyGroup = BeanUtil.beanCopyProperties(studyGroupVo, StudyGroup.class, "sisId,courseId,leaderId");
        studyGroup.setCourseId(studyGroupSet.getCourseId());
        if (roleService.hasStudentRole()) {
            studyGroup.setIsStudentGroup(Status.YES.getStatus());
        }else{
            //非学生创建的小组，最大限制数为null时，说明没有限制，设为Integer最大值
            if(studyGroup.getMaxMemberNumber() == null){
                studyGroup.setMaxMemberNumber(Integer.MAX_VALUE);
            }
        }

        studyGroup.setStudyGroupSetId(studyGroupSet.getId());
        studyGroupDao.save(studyGroup);

        userFileService.initStudyGroupDir(studyGroup.getCourseId(), studyGroup.getId(), studyGroup.getName());

        if (roleService.hasStudentRole()) {
            StudyGroupUser groupUser = StudyGroupUser.builder()
                    .courseId(studyGroup.getCourseId())
                    .studyGroupSetId(studyGroup.getStudyGroupSetId())
                    .studyGroupId(studyGroup.getId())
                    .isLeader(Status.YES.getStatus())
                    .userId(WebContext.getUserId())
                    .build();
            studyGroupUserDao.save(groupUser);

            Set<Long> existsUserIds = new HashSet<>();
            existsUserIds.add(WebContext.getUserId());

            if (ListUtils.isNotEmpty(studyGroupVo.getInviteUserIds())) {
                for (Long inviteUserId : studyGroupVo.getInviteUserIds()) {
                    if (existsUserIds.contains(inviteUserId)) {
                        continue;
                    }
                    StudyGroupUser inviteUser = StudyGroupUser.builder()
                            .courseId(studyGroup.getCourseId())
                            .studyGroupId(studyGroup.getId())
                            .studyGroupSetId(studyGroup.getStudyGroupSetId())
                            .isLeader(Status.NO.getStatus())
                            .userId(inviteUserId)
                            .build();
                    studyGroupUserDao.save(inviteUser);

                    existsUserIds.add(inviteUserId);
                }
            }
        }

        return new LinkedInfo(String.valueOf(studyGroup.getId()));
    }

    @Data
    public static class StudyGroupEditVo extends StudyGroup {
        private List<Long> inviteUserIds;

        @NotNull(groups = GroupModify.class)
        @Override
        public Long getId() {
            return super.getId();
        }

        @Null(groups = GroupModify.class)
        @Override
        public Long getStudyGroupSetId() {
            return super.getStudyGroupSetId();
        }

        @Null(groups = {GroupModify.class})
        @Override
        public Long getCourseId() {
            return super.getCourseId();
        }
    }

    /**
     * @api {post} /studyGroup/modify 学习小组编辑
     * @apiName studyGroupModify
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} id ID
     * @apiParam {String} name 小组名
     * @apiParam {Number} [maxMemberNumber] 小组成员数量（空表示不限制）
     * @apiParam {Number[]} [inviteUserIds] 创建小组后同时添加用户到小组内
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 小组ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        StudyGroupEditVo studyGroup = JSON.parseObject(dataEditInfo.beanJson, StudyGroupEditVo.class);
        StudyGroup oldStudyGroup = studyGroupDao.get(studyGroup.getId());

        if (!roleService.hasStudentRole()) {

            //如果当前最大人数小于学生数则抛出异常，否则持久化当前最大人数
            Integer maxMemberNumber = studyGroup.getMaxMemberNumber();

            if (maxMemberNumber != null){
                int oldStudyGroupUserNumber = studyGroupUserDao.count(StudyGroupUser.builder().studyGroupId(oldStudyGroup.getId()).build());
                if(maxMemberNumber < oldStudyGroupUserNumber){
                    throw new BaseException("group-member-limit.not.less.than", String.valueOf(oldStudyGroupUserNumber));
                }

                //判断是否生成小组长,人数达到最大人数则生成
                Long leaderId = oldStudyGroup.getLeaderId();
                if((leaderId == null) && (maxMemberNumber == oldStudyGroupUserNumber)){

                    StudyGroupSet studyGroupSet = studyGroupSetDao.get(oldStudyGroup.getStudyGroupSetId());
                    LeaderSetStrategyEnum leaderSetStrategyEnum = LeaderSetStrategyEnum.strategyOf(studyGroupSet.getLeaderSetStrategy());
                    if (leaderSetStrategyEnum == LeaderSetStrategyEnum.RANDOM) {
                        List<StudyGroupUser> groupUsers = studyGroupUserDao.findUserByCourseIdAndGroupId(
                                oldStudyGroup.getCourseId(), oldStudyGroup.getId());

                        int random = RandomUtils.randomNum(groupUsers.size());
                        StudyGroupUser leader = groupUsers.get(random);
                        leader.setIsLeader(Status.YES.getStatus());
                        oldStudyGroup.setLeaderId(leader.getUserId());

                        studyGroupUserDao.update(leader);
                    }
                }
            }else{
                maxMemberNumber = Integer.MAX_VALUE;
            }

            oldStudyGroup.setMaxMemberNumber(maxMemberNumber);
        }

        oldStudyGroup.setJoinType(studyGroup.getJoinType());
        oldStudyGroup.setName(studyGroup.getName());
        studyGroupDao.update(oldStudyGroup);

        if (roleService.hasStudentRole() && ListUtils.isNotEmpty(studyGroup.getInviteUserIds())) {
            Set<Long> keepUserIds = new HashSet<>(studyGroup.getInviteUserIds());

            // 移除小组用户
            List<StudyGroupUser> existsUsers = studyGroupUserDao.find(StudyGroupUser.builder().studyGroupId(oldStudyGroup.getId()).build());
            for (StudyGroupUser existsUser : existsUsers) {
                Long userId = existsUser.getUserId();
                if (Objects.equals(userId, WebContext.getUserId()) || keepUserIds.contains(userId)) {
                    continue;
                }

                studyGroupUserDao.delete(existsUser.getId());
            }

            Set<Long> existsUserIds;
            if (Objects.equals(oldStudyGroup.getIsStudentGroup(), Status.YES.getStatus())) {
                existsUserIds = existsUsers.stream().map(StudyGroupUser::getUserId).collect(Collectors.toSet());
            } else {
                existsUserIds = studyGroupUserDao.find(StudyGroupUser.builder().studyGroupSetId(oldStudyGroup.getStudyGroupSetId()).build())
                        .stream()
                        .map(StudyGroupUser::getUserId)
                        .collect(Collectors.toSet());
            }

            for (Long keepUserId : keepUserIds) {
                if (!existsUserIds.contains(keepUserId)) {
                    StudyGroupUser newGroupUser = StudyGroupUser.builder()
                            .courseId(oldStudyGroup.getCourseId())
                            .studyGroupSetId(oldStudyGroup.getStudyGroupSetId())
                            .studyGroupId(oldStudyGroup.getId())
                            .userId(keepUserId)
                            .isLeader(Status.NO.getStatus())
                            .build();
                    studyGroupUserDao.save(newGroupUser);
                }
            }
        }

        return new LinkedInfo(String.valueOf(oldStudyGroup.getId()));
    }

    /**
     * @api {post} /studyGroup/deletes 学习小组删除
     * @apiName studyGroupDelete
     * @apiGroup StudyGroup
     *
     * @apiParam {Number[]} ids ID列表
     * @apiParamExample 请求示例:
     *      [1, 2, 3]
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity ID列表
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        studyGroupDao.deletes(ids);

        Example example = studyGroupUserDao.getExample();
        example.createCriteria()
                .andIn(StudyGroupUser.STUDY_GROUP_ID, ids);
        studyGroupUserDao.delete(example);
        return new LinkedInfo(dataEditInfo.beanJson);
    }
}
