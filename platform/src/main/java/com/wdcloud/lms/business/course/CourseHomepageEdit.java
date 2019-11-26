package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.course.vo.CourseHomepageVo;
import com.wdcloud.lms.core.base.dao.CourseConfigDao;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.model.CourseConfig;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_HOMEPAGE
)
public class CourseHomepageEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseDao courseDao;

    /**
     * @api {post} /course/homepage/edit 课程首页编辑
     * @apiName courseHomepageEdit
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} homepage 课程首页：ACTIVE_STREAM: 活动流, MODULE：单元, ASSIGNMENTS：作业组, SYLLABUS：大纲, PAGE: 页面
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 课程ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseHomepageVo courseHomepageVo = JSON.parseObject(dataEditInfo.beanJson, CourseHomepageVo.class);
        courseDao.updateHomepageByCourseId(courseHomepageVo.getCourseId(), courseHomepageVo.getHomepage().getType());
        log.info("[CourseHomepageEdit] set course homepage, courseId={}, homepage={}, opUserId={}",
                courseHomepageVo.getCourseId(), courseHomepageVo.getHomepage(), WebContext.getUserId());
        return new LinkedInfo(String.valueOf(courseHomepageVo.getCourseId()));
    }
}
