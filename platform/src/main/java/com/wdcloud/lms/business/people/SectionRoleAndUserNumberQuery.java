package com.wdcloud.lms.business.people;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.people.vo.RoleAndCourseUserNumberVo;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.model.SectionUser;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ROLE,
        functionName = Constants.FUNCTION_TYPE_COURSE_USER_NUM
)
public class SectionRoleAndUserNumberQuery implements ISelfDefinedSearch<List<RoleAndCourseUserNumberVo>> {
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /role/courseUserNumber/query 角色列表及个角色课程下用户数量
     * @apiName roleSectionUserNumberQuery
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     *
     * @apiSuccess {Number} roleId 角色ID
     * @apiSuccess {String} roleName 角色名称
     * @apiSuccess {Number} userNumber 用户数量
     */
    @Override
    public List<RoleAndCourseUserNumberVo> search(Map<String, String> condition) {
        if (!condition.containsKey(Constants.PARAM_COURSE_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get(Constants.PARAM_COURSE_ID));

        List<SectionUser> allSectionUsers = sectionUserDao.find(SectionUser.builder().courseId(courseId).build());
        Map<Long, List<SectionUser>> roleIdAndSectionUserMap = allSectionUsers.stream().collect(Collectors.groupingBy(SectionUser::getRoleId));

        Map<Long, String> roleIdAndNameMap = roleService.findRoleIdAndNameMap();

        List<RoleAndCourseUserNumberVo> result = new ArrayList<>();
        for (Map.Entry<Long, String> entry : roleIdAndNameMap.entrySet()) {
            List<SectionUser> sectionUsers = roleIdAndSectionUserMap.get(entry.getKey());
            int roleUserNumber = sectionUsers == null ? 0 : sectionUsers.size();
            result.add(RoleAndCourseUserNumberVo.builder()
                    .roleId(entry.getKey())
                    .roleName(entry.getValue())
                    .courseUserNumber(roleUserNumber)
                    .build());
        }
        return result;
    }
}
