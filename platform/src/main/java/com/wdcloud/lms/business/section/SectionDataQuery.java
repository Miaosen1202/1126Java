package com.wdcloud.lms.business.section;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.SectionUserDetailVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_SECTION)
public class SectionDataQuery implements IDataQueryComponent<Section> {

    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /section/list 课程班级列表
     * @apiName SectionList
     * @apiGroup Section
     * @apiParam {Number} courseId 课程ID
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 班级列表
     * @apiSuccess {Number} entity.id 班级ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.name 班级名称
     * @apiSuccess {Number} [entity.startTime] 班级开始时间
     * @apiSuccess {Number} [entity.endTime] 班级结束时间
     * @apiSuccess {Object[]} entity.users 学生列表
     * @apiSuccess {Number} entity.users.userId 用户ID
     * @apiSuccess {Number=1,2,3,4} entity.users.roleId 角色ID, 1: 管理员 2: 教师 3: TA 4: 学生
     * @apiSuccess {Number=1,2} entity.users.registryStatus 状态, 1: 未接受 2： 已加入
     */
    @Override
    public List<? extends Section> list(Map<String, String> param) {
        if (!param.containsKey(Constants.PARAM_COURSE_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(param.get(Constants.PARAM_COURSE_ID));
        List<Section> sections = sectionDao.find(Section.builder().courseId(courseId).build());


        List<SectionWithUserVo> sectionWithUserVos = BeanUtil.beanCopyPropertiesForList(sections, SectionWithUserVo.class);

        List<SectionUser> sectionUsers = sectionUserDao.find(SectionUser.builder().courseId(courseId).build());
        Set<Long> userIds = sectionUsers.stream()
                .map(SectionUser::getUserId)
                .collect(Collectors.toSet());
        Map<Long, User> userIdMap = userDao.gets(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, a -> a));

        Map<Long, SectionWithUserVo> sectionMap = sectionWithUserVos.stream()
                .collect(Collectors.toMap(SectionWithUserVo::getId, a -> a));

        for (SectionUser sectionUser : sectionUsers) {
            SectionWithUserVo sectionWithUserVo = sectionMap.get(sectionUser.getSectionId());
            if (sectionWithUserVo == null) {
                continue;
            }
            if (ListUtils.isEmpty(sectionWithUserVo.getUsers())) {
                sectionWithUserVo.setUsers(new ArrayList<>());
            }
            List<UserDetailVo> users = sectionWithUserVo.getUsers();

            UserDetailVo userDetailVo = new UserDetailVo();
            userDetailVo.setUserId(sectionUser.getUserId());
            userDetailVo.setSectionId(sectionUser.getSectionId());
            userDetailVo.setRoleId(sectionUser.getRoleId());
            userDetailVo.setRegistryOrigin(sectionUser.getRegistryOrigin());
            userDetailVo.setRegistryStatus(sectionUser.getRegistryStatus());

            User user = userIdMap.get(sectionUser.getUserId());
            if (user != null) {
                userDetailVo.setUsername(user.getUsername());
                userDetailVo.setNickname(user.getNickname());
            }
            users.add(userDetailVo);
        }

        return sectionWithUserVos;
    }

    @Data
    public static class SectionWithUserVo extends Section {
        private List<UserDetailVo> users;
    }

    @Data
    public class UserDetailVo extends SectionUserDetailVo {
        private Long userId;
        private String username;
        private String nickname;
    }
}
