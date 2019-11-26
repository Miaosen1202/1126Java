package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.core.base.dao.CourseUserJoinPendingDao;
import com.wdcloud.lms.core.base.dao.SectionUserDao;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.vo.CourseUserJoinPendingVO;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能：实现用户接受和拒接邀请接口
 *
 * @author 黄建林
 */
@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_JOIN_PENDING)
public class CourseUserJoinPendingEdit implements IDataEditComponent {
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private CourseUserJoinPendingDao courseUserJoinPendingDao;
    @Autowired
    private SectionUserService sectionUserService;

    /**
     * @api {post} /userJoinPending/add   用户接受和拒接邀请接口
     * @apiDescription 用户接受和拒接邀请接口
     * @apiName userJoinPendingAdd
     * @apiGroup People
     * @apiParam {Number} id 邀请Id
     * @apiParam {Number} courseId 课程Id
     * @apiParam {Number} userId 用户Id
     * @apiParam {Number} sectionId 班级Id
     * @apiParam {Number} roleId 角色Id
     * @apiParam {Number} registryStatus 邀请状态 0：拒接；1：接受
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息描述
     * @apiSuccess {String} entity 用户ID
     * @apiExample {json} 请求示例:
     * {
     * "id": "2",
     * "courseId": "102",
     * "userId": "3",
     * "sectionId": "3",
     * "roleId": "4",
     * "registryStatus": "1"
     * }
     * @apiExample {json} 返回示例:
     * {
     * "code": 200,
     * "message": "2"
     * }
     */
    @ValidationParam(clazz = CourseUserJoinPendingVO.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        CourseUserJoinPendingVO courseUserJoinPending = JSON.parseObject(dataEditInfo.beanJson, CourseUserJoinPendingVO.class);
        //删除邀请表中记录
        courseUserJoinPendingDao.delete(courseUserJoinPending.getId());
        if (courseUserJoinPending.getRegistryStatus() == 0) {
            //拒接邀请
            sectionUserService.delete(courseUserJoinPending.getCourseId(),
                    courseUserJoinPending.getSectionId(), courseUserJoinPending.getUserId(),
                    courseUserJoinPending.getRoleId());
        } else {
            //接受邀请
            sectionUserDao.updateSectionUserJoinStatus(courseUserJoinPending.getCourseId(),
                    courseUserJoinPending.getSectionId(), courseUserJoinPending.getUserId(),
                    courseUserJoinPending.getRoleId(), UserRegistryStatusEnum.JOINED, WebContext.getUserId());
        }

        return new LinkedInfo(String.valueOf(courseUserJoinPending.getId()));
    }
}
