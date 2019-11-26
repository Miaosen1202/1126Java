package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.MqSendService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.SectionUserService;
import com.wdcloud.lms.business.people.enums.UserInviteTypeEnum;
import com.wdcloud.lms.business.people.vo.CourseUserAddVo;
import com.wdcloud.lms.business.people.vo.CourseUserDeleteVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryOriginEnum;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PropValueUnRegistryException;
import com.wdcloud.lms.handle.dto.UserJoinedCourseDto;
import com.wdcloud.mq.model.MqConstants;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 课程用户添加、移除
 *
 * 邀请新用户:
 * 1. 添加用户到用户表,课程用户表,班级用户表(发送用户加入课程消息a)
 * 2. [处理消息a]如果课程已发布,发送邀请邮件,否则等待课程发布后,再发送邀请邮件
 * 3. 用户查看邮件,进入邀请页面
 * 4. 用户是新用户(发送用户加入课程消息b),[处理消息b]为用户初始化关联配置信息或
 * 5. 用户关联到已有用户,将用户邮箱信息增加到cos_user_email表,删除用户表,更新课程用户表/班级用户表中用户ID
 * 6. 用户添加完成后,删除cos_user_email表中记录
 */
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_COURSE_USER)
public class CourseUserEdit implements IDataEditComponent {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private MqSendService mqSendService;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SectionUserService sectionUserService;


    /**
     * @api {post} /courseUser/add 课程用户添加
     * @apiDescription 课程用户添加，可以通过用户邮箱、SIS ID、登录ID(username)来添加，如果用户在系统中已存在，邀请只需要提交userId
     *                  如果用户尚不存在系统中，则需要email(邮箱邀请),sisId(SIS ID邀请), username(登录ID邀请)，新用户必须上传nickname
     * @apiName courseUserAdd
     * @apiGroup People
     *
     * @apiParam {Number} courseId　课程
     * @apiParam {Number} sectionId 班级
     * @apiParam {Number} roleId 角色
     * @apiParam {Object[]} invites 邀请列表
     * @apiParam {Number=1,2,3} invites.inviteType 邀请方式, 1: 邮箱 2: 登录ID 3: sisId
     * @apiParam {String} [invites.email] 用户邮箱
     * @apiParam {String} [invites.loginId] 用户loginId
     * @apiParam {String} [invites.sisId] SIS ID
     * @apiParam {String} [invites.nickname] 新用户昵称（新用户必填）
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {String} entity 成功邀请用户数量
     *
     */
    @ValidationParam(clazz = CourseUserAddVo.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        CourseUserAddVo courseUserAddVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserAddVo.class);
        Long courseId = courseUserAddVo.getCourseId();
        Course course = courseDao.get(courseId);
        if (course == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_COURSE_ID, courseId);
        }
        Long sectionId = courseUserAddVo.getSectionId();
        Section section = sectionDao.get(sectionId);
        if (section == null) {
            throw new PropValueUnRegistryException(Constants.PARAM_SECTION_ID, sectionId);
        }
        Long roleId = courseUserAddVo.getRoleId();

        List<CourseUserAddVo.InviteInfo> inviteInfoList = courseUserAddVo.getInvites();
        UserInviteTypeEnum inviteType = UserInviteTypeEnum.typeOf(inviteInfoList.get(0).getInviteType());
        List<User> users;

        //根据邀请方式，查询user
        switch (inviteType) {
            //fixme 可能会使用邮箱邀请。之前有代码实现邮箱邀请，但目前页面没此功能
            case LOGIN_ID:
                List<String> loginIds = inviteInfoList.stream()
                        .map(CourseUserAddVo.InviteInfo::getLoginId)
                        .collect(Collectors.toList());
                users = userDao.findUsers(loginIds, null, null,null);
                break;
            case SIS_ID:
                Org rootOrg = orgDao.get(WebContext.getOrgId());
                while (!Objects.equals(rootOrg.getType(), OrgTypeEnum.SCHOOL.getType())
                        && !Objects.equals(rootOrg.getParentId(), Constants.TREE_ROOT_PARENT_ID)) {
                    rootOrg = orgDao.get(rootOrg.getParentId());
                }

                List<String> sisIds = inviteInfoList.stream()
                        .map(CourseUserAddVo.InviteInfo::getSisId)
                        .collect(Collectors.toList());

                users = userDao.findUsers(null, null, sisIds,rootOrg.getTreeId());
                break;
            default:
                throw new PropValueUnRegistryException(Constants.PARAM_INVITE_TYPE, inviteType.getType());
        }

        int addUserNumber = 0;
        for(User user : users){
            SectionUser sectionUser = courseSectionAddUser(inviteType, user.getId(), courseId, sectionId, roleId);
            if (sectionUser != null) {
                addUserNumber++;
            }
        }

        return new LinkedInfo(String.valueOf(addUserNumber));
    }

    /**
     * @api {post} /courseUser/deletes 课程用户移除
     * @apiName CourseUserDelete
     * @apiGroup People
     *
     * @apiParam {Number} courseId 课程
     * @apiParam {Number} userId 用户
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {String} entity 用户ID
     */
    @ValidationParam(clazz = CourseUserDeleteVo.class)
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        CourseUserDeleteVo courseDeleteVo = JSON.parseObject(dataEditInfo.beanJson, CourseUserDeleteVo.class);
        courseUserDao.deleteByField(CourseUser.builder().courseId(courseDeleteVo.getCourseId()).userId(courseDeleteVo.getUserId()).build());

        List<SectionUser> sectionUsers = sectionUserDao.find(SectionUser.builder().courseId(courseDeleteVo.getCourseId()).userId(courseDeleteVo.getUserId()).build());
        for (SectionUser sectionUser : sectionUsers) {
            sectionUserService.delete(sectionUser);
        }

        studyGroupUserDao.removeUser(courseDeleteVo.getCourseId(), courseDeleteVo.getUserId());

        log.info("[CourseUserEdit] remove user from course, userId={}, courseId={}", courseDeleteVo.getUserId(), courseDeleteVo.getCourseId());
        return new LinkedInfo(String.valueOf(courseDeleteVo.getUserId()));
    }

    private void sendUserJoinedMsg(SectionUser sectionUser, UserInviteTypeEnum inviteType) {
        UserJoinedCourseDto userJoinedDto = UserJoinedCourseDto.builder()
                .userId(sectionUser.getUserId())
                .courseId(sectionUser.getCourseId())
                .sectionId(sectionUser.getSectionId())
                .roleId(sectionUser.getRoleId())
                .sectionUserId(sectionUser.getId())
                .inviteType(inviteType)
                .build();
        mqSendService.sendMessage(MqConstants.QUEUE_USER_JOINED_COURSE, MqConstants.TOPIC_EXCHANGE_USER_JOINED_COURSE, userJoinedDto);
    }

    private SectionUser courseSectionAddUser(UserInviteTypeEnum inviteType, Long userId, Long courseId, Long sectionId, Long roleId) {

        boolean userExistsSection = sectionUserDao.count(SectionUser.builder()
                .userId(userId)
                .courseId(courseId)
                .sectionId(sectionId)
                .roleId(roleId)
                .build()) > 0;
        if (userExistsSection) {
            return null;
        }

        CourseUser courseUser = courseUserDao.findOne(CourseUser.builder()
                .courseId(courseId)
                .userId(userId)
                .build());
        if (courseUser == null) {
            courseUser = CourseUser.builder()
                    .courseId(courseId)
                    .userId(userId)
                    .isFavorite(Status.YES.getStatus())
                    .build();
            courseUserDao.save(courseUser);
        }

        // 邀请用户
        SectionUser.SectionUserBuilder sectionUserBuilder = SectionUser.builder()
                .courseId(courseId)
                .sectionId(sectionId)
                .userId(userId)
                .roleId(roleId);
        if (roleService.isAdmin()) {
            sectionUserBuilder.registryStatus(UserRegistryStatusEnum.JOINED.getStatus())
                    .registryOrigin(UserRegistryOriginEnum.ADMIN.getOrigin());
        } else {
            sectionUserBuilder.registryStatus(UserRegistryStatusEnum.PENDING.getStatus())
                    .registryOrigin(UserRegistryOriginEnum.INVITE.getOrigin());
        }
        SectionUser sectionUser = sectionUserBuilder.build();
        sectionUserService.save(sectionUser);

        if (!roleService.isAdmin()) {
            sendUserJoinedMsg(sectionUser, inviteType);
        }

        return sectionUser;
    }
}
