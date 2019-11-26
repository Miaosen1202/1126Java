package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.vo.UserCourseVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * 查询登录用户下的课程-学习端评分界面
 * @author zhangxutao
 *
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_Grade_SET,
        functionName = Constants.RESOURCE_TYPE_User_Course
)
public class UserCourseDataQuery implements ISelfDefinedSearch<List<UserCourseVo>> {
    @Autowired
    private CourseUserDao courseUserDao;

    /**
     * @api {get} /gradeDataQuery/userCourseQuery/search 查询用户下的课程
     * @apiName userCourseQuery
     * @apiGroup GradeGroup
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 内容基本信息
     * @apiSuccess {Long} entity.id ID
     * @apiSuccess {String} entity.name 名称
     */
    @Override
    public List<UserCourseVo> search(Map<String, String> condition) {
        Long userId = WebContext.getUserId();
        List<UserCourseVo> getUserCourseList  = courseUserDao.getUserCourseList(userId);
        return getUserCourseList;
    }
}
