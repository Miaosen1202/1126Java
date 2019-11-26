package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.course.enums.CourseVisibilityEnum;
import com.wdcloud.lms.business.course.vo.CourseConfigVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_COURSE,
        functionName = Constants.FUNCTION_TYPE_COURSE_CONFIG
)
public class CourseConfigEdit implements ISelfDefinedEdit {
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
    private UserFileService userFileService;
    @Autowired
    private RoleService roleService;

    /**
     * @api {post} /course/config/edit 课程配置编辑
     * @apiDescription 课程配置编辑，每个课程只存在一条配置信息
     * @apiName courseConfigEdit
     * @apiGroup Course
     *
     * @apiParam {Object} course 课程信息
     * @apiParam {Number} course.id 课程IDlinux
     * @apiParam {String} course.name 课程名称
     * @apiParam {String} course.code 课程编码
     * @apiParam {Number=0,1} [course.status] 课程发布状态
     * @apiParam {Number=1,2} [course.visibility] 可见性: 1. 公开，2. 课程，3. 机构(预留)
     * @apiParam {String} [course.description] 描述
     * @apiParam {String} [course.termId] 学期ID
     * @apiParam {Number} course.startTime 开始时间（时间戳）
     * @apiParam {Number} course.endTime 结束时间
     *
     * @apiParam {String} [coverFileUrl] 更新封面图地址
     * @apiParam {Number=1,2,3} [format] 课程格式: 1. 校内, 2. 在线, 3: 混合
     * @apiParam {Number=0,1} [enableGrade] 启用评分
     * @apiParam {Number} [gradeSchemeId] 评分策略ID
     * @apiParam {Number=0,1} allowViewBeforeStartTime 允许开始时间前访问
     * @apiParam {Number=0,1} allowViewAfterEndTime 允许结束时间后访问
     * @apiParam {Number=0,1} allowOpenRegistry 开放注册
     * @apiParam {String} openRegistryCode 开放注册码
     * @apiParam {Number=0,1} enableHomepageAnnounce 启用课程首页通知
     * @apiParam {Number} homepageAnnounceNumber 课程首页通知数量
     * @apiParam {Object} [userConfig] 用户配置
     * @apiParam {Number=0,1} [userConfig.allowMarkPostStatus] 允许手动将帖子标记为已读
     * @apiParam {Object} [discussionConfig] 讨论配置
     * @apiParam {Number=0,1} [discussionConfig.allowDiscussionAttachFile] 允许学生讨论上传附件
     * @apiParam {Number=0,1} [discussionConfig.allowStudentCreateDiscussion] 允许学生自己创建讨论
     * @apiParam {Number=0,1} [discussionConfig.allowStudentEditDiscussion] 允许学生修改讨论
     * @apiParam {Number=0,1} [allowStudentCreateStudyGroup] 允许学生创建学习小组
     * @apiParam {Number=0,1} [hideTotalInStudentGradeSummary] 在学生评分汇总中隐藏总分
     * @apiParam {Number=0,1} [hideGradeDistributionGraphs] 对学生隐藏评分分布图
     * @apiParam {Number=0,1} [allowAnnounceComment] 允许公告评论
     * @apiParam {Number=1,2,3} [coursePageEditType] 课程Page可编辑的类型, 1:教师, 2:教师与学生, 3: 所有人
     * @apiParam {String} [timeZone] 时区(预留)
     * @apiParam {String} [subAccount] 子账号(预留)
     *
     *
     * @apiExample {json} 请求示例:
     * {
     *     "id": 1,
     *     "status": 0
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
        CourseConfigVo courseConfigVo = JSON.parseObject(dataEditInfo.beanJson, CourseConfigVo.class);
        Course course = courseConfigVo.getCourse();
        CourseConfig courseConfig = BeanUtil.beanCopyProperties(courseConfigVo, CourseConfig.class);

        Course oldCourse = courseDao.get(course.getId());
        if (oldCourse == null) {
            throw new PropValueUnRegistryException("course.id", course.getId());
        }

        if (StringUtil.isNotEmpty(courseConfigVo.getCoverFileUrl())) {
            FileInfo fileInfo = userFileService.getFileInfo(courseConfigVo.getCoverFileUrl());
            if (fileInfo != null) {
                UserFile userFile = userFileService.saveCourseContentAttachment(fileInfo, oldCourse.getId());
                oldCourse.setCoverFileId(userFile.getId());
            }
        }

        oldCourse.setIncludePublishIndex(course.getIncludePublishIndex());
        oldCourse.setName(course.getName());
        oldCourse.setCode(course.getCode());
        oldCourse.setStartTime(course.getStartTime());
        oldCourse.setEndTime(course.getEndTime());
        if (course.getDescription() != null) {
            oldCourse.setDescription(course.getDescription());
        }
        Status status = Status.statusOf(course.getStatus());
        if (status != null) {
            oldCourse.setStatus(status.getStatus());
        }
        CourseVisibilityEnum visibilityEnum = CourseVisibilityEnum.visibilityOf(course.getVisibility());
        if (visibilityEnum != null) {
            oldCourse.setVisibility(visibilityEnum.getVisibility());
        }
        if (course.getTermId() != null) {
            oldCourse.setTermId(course.getTermId());
        }
        courseDao.updateIncludeNull(oldCourse);

        courseConfig.setCourseId(course.getId());
        courseConfigDao.updateByCourseId(courseConfig, course.getId());

        // 课程讨论配置
        DiscussionConfig discussionConfig = BeanUtil.beanCopyProperties(courseConfigVo.getDiscussionConfig(), DiscussionConfig.class);
        discussionConfig.setCourseId(course.getId());
        discussionConfig.setUpdateUserId(WebContext.getUserId());
        discussionConfigDao.update(WebContext.getUserId(), course.getId(), discussionConfig);

        if (roleService.hasTeacherOrTutorRole()) {
            // 用户配置
            UserConfig oldUserConfig = userConfigDao.findByUserId(WebContext.getUserId());
            UserConfig userConfig = BeanUtil.beanCopyProperties(courseConfigVo.getUserConfig(), UserConfig.class);
            if (oldUserConfig == null) {
                userConfig.setId(null);
                userConfig.setUserId(WebContext.getUserId());
                userConfigDao.save(userConfig);
            } else {
                userConfig.setId(oldUserConfig.getId());
                userConfig.setUserId(oldUserConfig.getUserId());
                userConfigDao.update(userConfig);
            }

            CourseUser courseUser = courseUserDao.findOne(CourseUser.builder().courseId(course.getId()).userId(WebContext.getUserId()).build());
            courseUser.setCoverColor(courseConfigVo.getCoverColor());
            courseUser.setCourseAlias(courseConfigVo.getAlias());
            courseUserDao.update(courseUser);
        }

        log.info("[CourseConfigEdit] update course & config & discussionConfig, course={}, config={}, discussionConfig={}",
                JSON.toJSONString(course), JSON.toJSONString(courseConfig), JSON.toJSONString(discussionConfig));
        return new LinkedInfo(String.valueOf(course.getId()));
    }

}
