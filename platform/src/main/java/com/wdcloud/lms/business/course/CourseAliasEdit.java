package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.course.vo.CourseAliasVo;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.model.CourseUser;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户参与课程别名设置
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_ALIAS
)
public class CourseAliasEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseUserDao courseUserDao;

    /**
     * @api {post} /course/alias/edit 用户参与课程别名设置
     * @apiDescription 用户为自己参与的课程设置别名
     * @apiName courseAliasEdit
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} alias 别名
     * @apiParam {String} [coverColor]　颜色
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 课程ID
     *
     */
    @Override
    @ValidationParam(clazz = CourseAliasVo.class)
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        CourseAliasVo courseAliasVo = JSON.parseObject(dataEditInfo.beanJson, CourseAliasVo.class);
        CourseUser record = CourseUser.builder()
                .coverColor(courseAliasVo.getCoverColor())
                .courseAlias(courseAliasVo.getAlias())
                .build();
        courseUserDao.update(courseAliasVo.getCourseId(), WebContext.getUserId(), record);
        log.info("[CourseAliasEdit] update user course alias, userId={}, courseId={}, alias={}",
                WebContext.getUserId(), courseAliasVo.getCourseId(), courseAliasVo.getAlias());
        return new LinkedInfo(String.valueOf(courseAliasVo.getCourseId()));
    }
}
