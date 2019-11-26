package com.wdcloud.lms.business.course;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.MqSendService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import com.wdcloud.lms.handle.dto.CoursePublishDto;
import com.wdcloud.mq.model.MqConstants;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SelfDefinedFunction(resourceName = Constants.RESOURCE_TYPE_COURSE, functionName = Constants.FUNCTION_TYPE_COURSE_STATUS_TOGGLE)
public class CourseStatusToggleEdit implements ISelfDefinedEdit {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private MqSendService mqSendService;

    /**
     * @api {post} /course/statusToggle/edit 课程状态切换
     * @apiDescription 课程状态切换，如果status字段没有传值，则默认是课程状态切换，否则为更新状态到status指定（如果课程下存在提过过评分内容，则不允许撤销发布）
     * @apiName courseStatusToggle
     * @apiGroup Course
     *
     * @apiParam {Number} id 课程ID
     * @apiParam {Number=0,1} [status] 课程状态
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
        Course course = JSON.parseObject(dataEditInfo.beanJson, Course.class);
        Course oldCourse = courseDao.get(course.getId());
        if (oldCourse == null) {
            throw new ParamErrorException();
        }

        Status status = Status.statusOf(course.getStatus());
        if (status == null) {
            status = oldCourse.getStatus() == Status.YES.getStatus() ? Status.NO : Status.YES;
        }

        int courseGradeItemSubmitCount = userSubmitRecordDao.count(UserSubmitRecord.builder().courseId(oldCourse.getId()).build());
        if (courseGradeItemSubmitCount > 0 && status == Status.NO) {
            throw new BaseException("course.unpublish.forbid.grade-item.submit");
        }

        Course record = Course.builder().id(oldCourse.getId()).status(status.getStatus()).build();
        if (status == Status.YES) {
            record.setPublishTime(DateUtil.now());
        }

        courseDao.update(record);
        sendMsg(course);

        log.info("[CourseStatusToggleEdit] course status toggle success, courseId={}, new status={}, opUserId={}",
                oldCourse.getId(), status, WebContext.getUserId());
        return new LinkedInfo(String.valueOf(oldCourse.getId()));
    }

    private void sendMsg(Course course) {
        log.debug("[CourseStatusToggleEdit] send a course publish msg, msg={}", JSON.toJSONString(course));
        CoursePublishDto coursePublishDto = new CoursePublishDto();
        coursePublishDto.setPublishStatus(Status.statusOf(course.getStatus()));
        coursePublishDto.setCourseId(course.getId());
        mqSendService.sendMessage(MqConstants.QUEUE_COURSE_PUBLIC, MqConstants.TOPIC_EXCHANGE_COURSE_PUBLIC, coursePublishDto);
    }
}
