package com.wdcloud.lms.business.message;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_OWNER
)
public class CourseJoinQuery implements ISelfDefinedSearch<Object> {
    @Autowired
    private CourseDao courseDao;
    /**
     * @api {get} /course/owner/query 所属课程列表查询
     * @apiName courseOwnerQuery
     * @apiGroup message
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 课程集合
     * @apiSuccess {Number} entity.id 课程ID
     * @apiSuccess {String} entity.name 课程名称
     */
    @Override
    public List<CourseJoinedVo> search(Map<String, String> condition)  {
        Map<String, Object> params = new HashMap<>();
//        if (WebContext.isStudent()) {
//            params.put("status", Status.YES.getStatus());
//        }
        params.put("userId", WebContext.getUserId());
        params.put("roleId", WebContext.getRoleId());
        params.put("registryStatus", UserRegistryStatusEnum.JOINED.getStatus());
        List<CourseJoinedVo> courseJoinedVos = courseDao.findCourseJoined(params);
        return courseJoinedVos;
    }

}
