package com.wdcloud.lms.business.course;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.course.vo.CourseConfigVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.BeanUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_COURSE_CONFIG
)
public class CourseConfigQuery implements ISelfDefinedSearch<CourseConfigVo> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseConfigDao courseConfigDao;
    @Autowired
    private DiscussionConfigDao discussionConfigDao;
    @Autowired
    private UserConfigDao userConfigDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private TermDao termDao;

    /**
     * @api {get} /course/config/query 课程配置查询
     * @apiDescription 课程配置查询
     * @apiName courseConfigQuery
     * @apiGroup Course
     *
     * @apiParam {Object} courseId 课程ID
     *
     * @apiExample {curl} 请求示例:
     *  /course/nav/query?courseId=1
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} vo 课程导航配置
     * @apiSuccess {String} [vo.coverColor] 封面颜色
     * @apiSuccess {String} [vo.alias] 用户设置别名
     * @apiSuccess {Integer} [vo.isFavorite] 用户是否添加到了偏好课程中
     * @apiSuccess {Object} vo.course 课程基本信息
     * @apiSuccess {Number} vo.course.id 课程ID
     * @apiSuccess {String} vo.course.name 课程名称
     * @apiSuccess {String} vo.course.code 课程编码
     * @apiSuccess {Number=0,1} vo.course.status 发布状态
     * @apiSuccess {Number=1,2,3} vo.course.visibility 可见性，1: 公开, 2: 课程专属
     * @apiSuccess {Number=0,1} vo.course.isEnd 是否结束
     * @apiSuccess {Number} [vo.course.coverFileId] 封面图ID
     * @apiSuccess {String} [vo.course.description] 描述
     * @apiSuccess {String} [vo.course.termId] 学期ID
     * @apiSuccess {Number} [vo.course.startTime] 开始时间（时间戳）
     * @apiSuccess {Number} [vo.course.endTime] 结束时间
     * @apiSuccess {Number} [vo.course.homepage] 首页
     * @apiSuccess {Number} [vo.course.includePublishIndex] 是否包含到公共课程索引中
     *
     * @apiSuccess {Number=1,2,3} [vo.format] 课程格式: 1. 校内, 2. 在线, 3: 混合
     * @apiSuccess {Number=0,1} [vo.enableGrade] 启用评分
     * @apiSuccess {Number} [vo.gradeSchemeId] 评分策略ID
     * @apiSuccess {Number=0,1} [vo.allowViewBeforeStartTime] 允许开始时间前访问
     * @apiSuccess {Number=0,1} [vo.allowViewAfterEndTime] 允许结束时间后访问
     * @apiSuccess {Number=0,1} [vo.allowOpenRegistry] 开放注册
     * @apiSuccess {String} [vo.openRegistryCode] 开放注册码
     * @apiSuccess {Number=0,1} [vo.enableHomepageAnnounce] 启用课程首页通知
     * @apiSuccess {Number} [vo.homepageAnnounceNumber] 课程首页通知数量
     * @apiSuccess {Object} vo.userConfig 用户配置
     * @apiSuccess {Number=0,1} [vo.userConfig.allowMarkPostStatus] 允许手动将帖子标记为已读
     * @apiSuccess {Number=0,1} [vo.discussionConfig.allowDiscussionAttachFile] 允许学生讨论上传附件
     * @apiSuccess {Number=0,1} [vo.discussionConfig.allowStudentCreateDiscussion] 允许学生自己创建讨论
     * @apiSuccess {Number=0,1} [vo.discussionConfig.allowStudentEditDiscussion] 允许学生修改讨论
     * @apiSuccess {Number=0,1} [vo.allowStudentCreateStudyGroup] 允许学生创建学习小组
     * @apiSuccess {Number=0,1} [vo.hideTotalInStudentGradeSummary] 在学生评分汇总中隐藏总分
     * @apiSuccess {Number=0,1} [vo.hideGradeDistributionGraphs] 对学生隐藏评分分布图
     * @apiSuccess {Number=0,1} [vo.allowAnnounceComment] 允许公告评论
     * @apiSuccess {Number=1,2,3} [vo.coursePageEditType] 课程Page可编辑的类型, 1:教师, 2:教师与学生, 3: 所有人
     * @apiSuccess {String} [vo.timeZone] 时区(预留)
     *
     * @apiSuccess {Object} vo.term 学期
     * @apiSuccess {Number} vo.term.id 学期ID
     * @apiSuccess {String} vo.term.name 学期
     * @apiSuccess {String} vo.term.code 学期
     * @apiSuccess {Number} vo.term.startTime 学期开始时间
     * @apiSuccess {Number} vo.term.endTime 学期结束时间
     *
     * @apiSuccess {Object} [vo.org] 机构
     * @apiSuccess {String} [vo.org.id] 机构ID
     * @apiSuccess {String} [vo.org.sisId] 机构 SIS ID
     * @apiSuccess {String} [vo.org.name] 机构名
     * @apiSuccess {Number=1,2} [vo.org.type] 机构类型, 1: 学校 2: 非学校
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "vo": {
     *         course: {
     *             id: 1
     *         }
     *     }
     * }
     *
     * @param condition
     * @return
     */
    @Override
    public CourseConfigVo search(Map<String, String> condition) {
        Long courseId = null;
        if (condition.containsKey(Constants.PARAM_COURSE_ID)) {
            courseId = Long.parseLong(condition.get(Constants.PARAM_COURSE_ID));
        }

        Course course = courseDao.get(courseId);
        if (course == null) {
            throw new ParamErrorException();
        }

        CourseVo courseVo = BeanUtil.beanCopyProperties(course, CourseVo.class);
        UserFile userFile = userFileDao.get(courseVo.getCoverFileId());
        if (userFile != null) {
            courseVo.setCoverFileUrl(userFile.getFileUrl());
        }

        CourseConfig courseConfig = courseConfigDao.getByCourseId(courseId);
        DiscussionConfig discussionConfig = discussionConfigDao.get(WebContext.getUserId(), courseId);
        UserConfig userConfig = userConfigDao.findByUserId(WebContext.getUserId());
        CourseUser courseUser = courseUserDao.findOne(CourseUser.builder().courseId(courseId).userId(WebContext.getUserId()).build());


        CourseConfigVo result = BeanUtil.beanCopyProperties(courseConfig, CourseConfigVo.class);
        result.setCourse(courseVo);
        result.setDiscussionConfig(discussionConfig);
        result.setUserConfig(userConfig);
        if (courseUser != null) {
            result.setAlias(courseUser.getCourseAlias());
            result.setCoverColor(courseUser.getCoverColor());
            result.setIsFavorite(courseUser.getIsFavorite());
        }

        Term term = termDao.get(course.getTermId());
        result.setTerm(term);

        Org org = orgDao.get(course.getOrgId());
        result.setOrg(org);

        return result;
    }

    @Data
    public static class CourseVo extends Course {
        private String coverFileUrl;
    }
}
