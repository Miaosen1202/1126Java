package com.wdcloud.lms.handle;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.EmailService;
import com.wdcloud.lms.business.people.enums.UserInviteTypeEnum;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.handle.dto.UserJoinedCourseDto;
import com.wdcloud.mq.handler.IMqMessageHandler;
import com.wdcloud.mq.handler.MQHandler;
import com.wdcloud.mq.model.MqConstants;
import com.wdcloud.utils.idgenerate.IdGenerateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 课程用户添加消息处理:
 * 1. 课程已发布,保存邀请信息到course_user_join_pending记录表中,发送邀请邮件或
 * 2. 课程未发布,保存邀请信息到course_user_join_pending记录表中,待课程发布后,再发送邀请邮件
 */
@Slf4j
@MQHandler(
        exchangeName = MqConstants.TOPIC_EXCHANGE_USER_JOINED_COURSE,
        queueName = MqConstants.QUEUE_USER_JOINED_COURSE,
        isFanout = false,
        description = "课程添加用户处理"
)
public class CourseUserJoinedHandler implements IMqMessageHandler {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private EmailService emailService;

    @Override
    public void processMsg(Object e) {
        log.info("[CourseUserJoinedHandler] handle course user joined. msg={}", JSON.toJSONString(e));
        if (e instanceof UserJoinedCourseDto) {
            UserJoinedCourseDto joinedCourseDto = (UserJoinedCourseDto) e;
            Long courseId = joinedCourseDto.getCourseId();
            Long sectionId = joinedCourseDto.getSectionId();
            Long userId = joinedCourseDto.getUserId();
            Long roleId = joinedCourseDto.getRoleId();

            Course course = courseDao.get(joinedCourseDto.getCourseId());

            CourseUserJoinPending userJoinPending = CourseUserJoinPending.builder()
                    .courseId(courseId)
                    .sectionId(sectionId)
                    .roleId(roleId)
                    .userId(userId)
                    .sectionUserId(joinedCourseDto.getSectionUserId())
                    .publicStatus(course.getStatus())
                    .code(IdGenerateUtils.getId(Constants.COURSE_INVITE_CODE_PRE, Constants.COURSE_INVITE_CODE_SEED))
                    .build();
            courseUserJoinPendingDao.save(userJoinPending);

            if (Objects.equals(course.getStatus(), Status.YES.getStatus()) && Objects.equals(joinedCourseDto.getInviteType(), UserInviteTypeEnum.EMAIL)) {
                emailService.sendInvite(joinedCourseDto.getSectionUserId());
            }

            log.info("[CourseUserJoinedHandler] invite user, user is pending, userId={}, courseId={}, courseStatus={}, sectionId={}, roleId={}",
                    userId, courseId, Status.statusOf(course.getStatus()), sectionId, roleId);
        }
    }
}
