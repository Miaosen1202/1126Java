package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.course.vo.CourseNavVo;
import com.wdcloud.lms.config.SysDictionaryCodeProperties;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseNavDao;
import com.wdcloud.lms.core.base.dao.SysDictionaryDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.CourseNav;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_COURSE_NAV
)
public class CourseNavEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseNavDao courseNavDao;

    /**
     * @api {post} /course/nav/edit 课程导航编辑
     * @apiDescription 课程导航编辑
     * @apiName courseNavEdit
     * @apiGroup Course
     *
     * @apiParam {Object} courseId 课程ID
     * @apiParam {Object[]} navList 导航列表
     * @apiParam {String} navList.code 菜单Code
     * @apiParam {Number=0,1} navList.status 启用状态
     *
     * @apiExample {json} 请求示例:
     * {
     *     "id": 1,
     *     "navList": [{
     *          code: "course.nav.module",
     *          status: 1
     *     }]
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改课程ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "vo": "1"
     * }
     *
     * @param dataEditInfo
     * @return
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseNavVo courseNavVo = JSON.parseObject(dataEditInfo.beanJson, CourseNavVo.class);
        Long courseId = courseNavVo.getCourseId();
        List<CourseNav> newNavList = courseNavVo.getNavList();

        List<CourseNav> oldCourseNavs = courseNavDao.find(CourseNav.builder().courseId(courseId).build());
        if (ListUtils.isEmpty(oldCourseNavs)) {
            return new LinkedInfo(String.valueOf(courseId));
        }

        Set<String> addedCodes = new HashSet<>();
        int index = 1;
        List<CourseNav> updatedCourseNaves = new ArrayList<>(oldCourseNavs.size());
        Map<String, CourseNav> oldCourseNavCodeMap = oldCourseNavs.stream().collect(Collectors.toMap(CourseNav::getCode, a -> a));
        for (CourseNav nav : newNavList) {
            CourseNav oldCourseNav = oldCourseNavCodeMap.get(nav.getCode());
            Status status = Status.statusOf(nav.getStatus());
            oldCourseNav.setStatus(status == null ? Status.NO.getStatus() : status.getStatus());
            oldCourseNav.setSeq(index++);
            oldCourseNav.setUpdateUserId(WebContext.getUserId());

            addedCodes.add(oldCourseNav.getCode());
            updatedCourseNaves.add(oldCourseNav);
        }

        for (CourseNav oldCourseNav : oldCourseNavs) {
            if (!addedCodes.contains(oldCourseNav.getCode())) {
                oldCourseNav.setSeq(index++);
                oldCourseNav.setStatus(Status.NO.getStatus());
                oldCourseNav.setUpdateUserId(WebContext.getUserId());
                updatedCourseNaves.add(oldCourseNav);
            }
        }

        courseNavDao.batchInsertOrUpdate(updatedCourseNaves);

        log.info("[CourseNavEdit] update course nav, courseId={}, navs={}, opUserId={}",
                courseId, JSON.toJSONString(updatedCourseNaves), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(courseId));
    }
}
