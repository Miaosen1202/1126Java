package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.config.SysDictionaryCodeProperties;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseNavDao;
import com.wdcloud.lms.core.base.dao.SysDictionaryDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.CourseNav;
import com.wdcloud.lms.core.base.vo.SysDictionaryAndLocaleValueVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_COURSE_NAV
)
public class CourseNavQuery implements ISelfDefinedSearch<List<CourseNav>> {
    @Autowired
    private CourseNavDao courseNavDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private SysDictionaryCodeProperties dictionaryCodeProperties;

    /**
     * @api {get} /course/nav/query 课程导航查询
     * @apiDescription 课程导航查询
     * @apiName courseNavQuery
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1} [status] 状态
     *
     * @apiExample {curl} 请求示例:
     *  /course/nav/query?courseId=1
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 课程导航菜单列表
     * @apiSuccess {Number} entity.code 菜单编码
     * @apiSuccess {Object[]} entity.name 菜单名称
     * @apiSuccess {Number} entity.seq 排序
     * @apiSuccess {Number=0,1} entity.status 状态
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": [
     *             {
     *                 "code": "course.nav.page",
     *                 "name": "Pages",
     *                 "seq": 1,
     *                 "status": 1
     *             }
     *     ],
     *     "message": "common.success"
     * }
     */
    @Override
    public List<CourseNav> search(Map<String, String> condition) {
        Long courseId = Long.parseLong(condition.get(Constants.PARAM_COURSE_ID));
        Status status = null;
        if (condition.containsKey(Constants.PARAM_STATUS)) {
            status = Status.statusOf(Integer.parseInt(condition.get(Constants.PARAM_STATUS)));
        }

        Example example = courseNavDao.getExample();
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo(CourseNav.COURSE_ID, courseId);
        if (status != null) {
            criteria.andEqualTo(CourseNav.STATUS, status.getStatus());
        }
        example.orderBy(CourseNav.SEQ);
        List<CourseNav> courseNavs = courseNavDao.find(example);


        List<SysDictionaryAndLocaleValueVo> subDictAndLocaleValue
                = sysDictionaryDao.findSubDictionaryAndLocaleValue(dictionaryCodeProperties.getCourseNav(), WebContext.getUserLanguage());
        Map<String, String> dictCodeAndNameMap = subDictAndLocaleValue.stream()
                .collect(Collectors.toMap(SysDictionaryAndLocaleValueVo::getCode, a -> StringUtil.isNotEmpty(a.getLocaleName()) ? a.getLocaleName() : a.getName()));

        for (CourseNav courseNav : courseNavs) {
            courseNav.setName(dictCodeAndNameMap.get(courseNav.getCode()));
        }

        return courseNavs;
    }
}
