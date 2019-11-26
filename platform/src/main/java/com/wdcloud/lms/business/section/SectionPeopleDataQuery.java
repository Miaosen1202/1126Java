package com.wdcloud.lms.business.section;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.section.vo.SectionDetailVo;
import com.wdcloud.lms.business.user.vo.SectionUserVo;
import com.wdcloud.lms.core.base.dao.SectionDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.Section;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_SECTION,
        functionName = Constants.FUNCTION_TYPE_PEOPLE
)
public class SectionPeopleDataQuery implements ISelfDefinedSearch<List<SectionDetailVo>> {

    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /section/people/query 班级及用户列表
     * @apiName SectionPeopleQuery
     * @apiGroup Section
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 班级列表
     * @apiSuccess {Number} entity.id 班级ID
     * @apiSuccess {Number} entity.courseId 课程
     * @apiSuccess {String} entity.name 名称
     * @apiSuccess {Number} [entity.startTime] 开始时间
     * @apiSuccess {Number} [entity.endTime] 结束时间
     * @apiSuccess {Object[]} [entity.teacherUsers] 教师用户列表（暂无实现）
     * @apiSuccess {Object[]} [entity.activeUsers] 已激活用户列表
     * @apiSuccess {Number} entity.activeUsers.userId 用户ID
     * @apiSuccess {Number} entity.activeUsers.roleId 用户角色ID
     * @apiSuccess {String} entity.activeUsers.roleName 用户角色名称
     * @apiSuccess {Number=1,2} entity.activeUsers.registryStatus 注册状态（1: 邀请中，2:　已加入）
     * @apiSuccess {Number=1,2,3} entity.activeUsers.registryOrigin 注册来源（1: 管理员添加，2: 邀请加入 3: 自行注册）
     *
     * @apiSuccess {Object[]} [entity.pendingUsers] 邀请中用户列表
     * @apiSuccess {Number} entity.pendingUsers.userId 用户ID
     * @apiSuccess {Number} entity.pendingUsers.roleId 用户角色ID
     * @apiSuccess {String} entity.pendingUsers.roleName 用户角色名称
     * @apiSuccess {Number=1,2} entity.pendingUsers.registryStatus 注册状态（1: 邀请中，2:　已加入）
     * @apiSuccess {Number=1,2,3} entity.pendingUsers.registryOrigin 注册来源（1: 管理员添加，2: 邀请加入 3: 自行注册）
     *
     */
    @Override
    public List<SectionDetailVo> search(Map<String, String> param) {
        if (!param.containsKey(Constants.PARAM_COURSE_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(param.get(Constants.PARAM_COURSE_ID));
        List<Section> sections = sectionDao.find(Section.builder().courseId(courseId).build());
        List<SectionDetailVo> result = BeanUtil.beanCopyPropertiesForList(sections, SectionDetailVo.class);

        List<SectionUser> allSectionUsers = sectionUserDao.find(SectionUser.builder().courseId(courseId).build());
        List<SectionUserVo> allSectionUserVos = BeanUtil.beanCopyPropertiesForList(allSectionUsers, SectionUserVo.class);

        Map<Long, String> roleIdAndNameMap = roleService.findRoleIdAndNameMap();

        for (SectionUserVo userVo : allSectionUserVos) {
            userVo.setRoleName(roleIdAndNameMap.get(userVo.getRoleId()));
        }

        Map<Long, List<SectionUserVo>> eachSectionUsers = allSectionUserVos.stream().collect(Collectors.groupingBy(SectionUser::getSectionId));
        for (SectionDetailVo sectionDetailVo : result) {
            if (sectionDetailVo.getPendingUsers() == null) {
                sectionDetailVo.setPendingUsers(new ArrayList<>());
            }
            if (sectionDetailVo.getActiveUsers() == null) {
                sectionDetailVo.setActiveUsers(new ArrayList<>());
            }

            Long sectionId = sectionDetailVo.getId();
            List<SectionUserVo> sectionUsers = eachSectionUsers.get(sectionId);
            if (ListUtils.isNotEmpty(sectionUsers)) {
                Map<Integer, List<SectionUserVo>> statusSectionUserMap = sectionUsers.stream().collect(Collectors.groupingBy(SectionUser::getRegistryStatus));

                List<SectionUserVo> pendingUsers = statusSectionUserMap.get(UserRegistryStatusEnum.PENDING.getStatus());
                if (ListUtils.isNotEmpty(pendingUsers)) {
                    sectionDetailVo.setPendingUsers(pendingUsers);
                }

                List<SectionUserVo> activeUsers = statusSectionUserMap.get(UserRegistryStatusEnum.JOINED.getStatus());
                if (ListUtils.isNotEmpty(activeUsers)) {
                    sectionDetailVo.setActiveUsers(activeUsers);
                }
            }
        }
        return result;
    }

}
