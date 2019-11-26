package com.wdcloud.lms.handle;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.base.service.EmailService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.handle.dto.CoursePublishDto;
import com.wdcloud.mq.handler.IMqMessageHandler;
import com.wdcloud.mq.handler.MQHandler;
import com.wdcloud.mq.model.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程发布消息处理:
 * 1. 查询course_user_join_pending记录表,获取加入课程尚未邀请的用户,给用户发送邀请邮件
 */
@Slf4j
@MQHandler(
        exchangeName = MqConstants.TOPIC_EXCHANGE_COURSE_PUBLIC,
        queueName = MqConstants.QUEUE_COURSE_PUBLIC
)
public class CoursePublishHandler implements IMqMessageHandler {
    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private EmailService emailService;

    @Override
    public void processMsg(Object e) {
        if (e instanceof CoursePublishDto) {
            CoursePublishDto coursePublishDto = (CoursePublishDto) e;

            log.debug("[CoursePublishHandler] receive a course publish msg, msg={}", JSON.toJSONString(e));

            if (coursePublishDto.getPublishStatus() == Status.YES) {
                List<CourseUserJoinPending> courseUserJoinPendings
                        = courseUserJoinPendingDao.find(CourseUserJoinPending
                        .builder()
                        .courseId(coursePublishDto.getCourseId())
                        .publicStatus(Status.NO.getStatus())
                        .build());

                Map<Long, List<CourseUserJoinPending>> userJoinPendings = courseUserJoinPendings.stream().collect(Collectors.groupingBy(CourseUserJoinPending::getUserId));
                for (Long userId : userJoinPendings.keySet()) {
                    emailService.sendInvite(coursePublishDto.getCourseId(), userId);
                }
                courseUserJoinPendingDao.update(coursePublishDto.getCourseId(), Status.YES);
            }
        }
    }
}
