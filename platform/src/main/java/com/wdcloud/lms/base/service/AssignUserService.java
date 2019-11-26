package com.wdcloud.lms.base.service;

import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignUserDataTimeDTO;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能：测验、作业、讨论、公告等分配给个人用户
 * <p>
 * 整体思想：1、开始时间和结束时间为空，依次从班级、
 * 课程、学期中获取时间；（班级没获取到就从课程获取，
 * 课程还没获取到就从学期中获取，如果都未获取到就为空）
 * <p>
 * 如果有个人分配，则个人按个人分配时间分配，如果有班级分配，则按班级分配时间分配，如果只有课程分配则按课程时间分配
 *
 * @author 黄建林
 */
@Slf4j
@Service
public class AssignUserService {
    //课程下所有人，也就是everyone
    @Autowired
    private CourseUserDao courseUserDao;
    //班级下所有人
    @Autowired
    private SectionUserDao sectionUserDao;
    //学习小组下所有人
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    //分配用户表对象
    @Autowired
    private AssignUserDao assignUserDao;

    /*处理分配时间为空的逻辑*/
    @Autowired
    private SectionDao sectionDao;//班级
    @Autowired
    private TermDao termDao;//学期
    @Autowired
    private CourseDao courseDao;//课程

    @Autowired
    private AssignDao assignDao;

    /**
     * 功能：日期比较
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDateByGetTime(Date date1, Date date2) {
        int status = -1;
        if (date1 == null || date2 == null) {
            return status;
        }
        if (date1.getTime() < date2.getTime()) {
            status = 1;//date1小于date2

        } else if (date1.getTime() > date2.getTime()) {
            status = 2;//date1大于date2
        } else {
            status = 0;//date1等于date2
        }
        return status;
    }

    private Assign getAssignDto(Assign assign, AssignUserOparateDTO assignUserOparateDTO) {
        // 分配类型，1: 所有， 2：section(班级), 3: 用户
        assign.setAssignType(3);
        assign.setAssignId(assignUserOparateDTO.getUserId());
        return assign;
    }

    /**
     * 功能：删除班级用户后更新对应用户的分配信息
     *
     * @param assignUserOparateDTO
     */
    private void deleteRemainAssign(AssignUserOparateDTO assignUserOparateDTO) {
        if (assignUserOparateDTO.getSrcSectionId() == null)//说明删除的是课程用户
        {
            assignUserDao.deleteByUserIdAndCourseId(assignUserOparateDTO.getCourseId(), assignUserOparateDTO.getUserId());
            return;
        }
        //获取该用户该课程下所有班级信息
        List<SectionUser> sectionUserList = sectionUserDao.findUserByCourseIdAndUserId(assignUserOparateDTO.getCourseId(), assignUserOparateDTO.getUserId());
        if (sectionUserList == null) {
            assignUserDao.deleteByUserIdAndCourseId(assignUserOparateDTO.getCourseId(), assignUserOparateDTO.getUserId());
            return;
        }
        int totalSectionUsers = sectionUserList.size();
        if (totalSectionUsers == 1) {
            assignUserDao.deleteByUserIdAndCourseId(assignUserOparateDTO.getCourseId(), assignUserOparateDTO.getUserId());
            return;
        }

    }

    /**
     * 功能：实现用户增加、删除、移动时重新对操作用户进行再分配
     *
     * @param assignUserOparateDTO 分配用户操作DTO
     */
    public void assigUserOparate(AssignUserOparateDTO assignUserOparateDTO) {
        if (assignUserOparateDTO.getOparateType() == null) {
            return;
        }
        List<Assign> assignList = new ArrayList<>();
        int totalAssigns = 0;
        List<Assign> oprateAssignList = new ArrayList<>();
        int oparateType = assignUserOparateDTO.getOparateType();
        switch (oparateType) {
            case 1:
                //添加
                //获取该班级下分配集合
                List<Assign> sectionAssignList = assignDao.find(assignUserOparateDTO.getCourseId(), 2/*表示给班级分配*/, assignUserOparateDTO.getSrcSectionId());
                //获取该课程下的everyone分配集合
                List<Assign> everyOneAssignList = assignDao.find(Assign.builder().courseId(assignUserOparateDTO.getCourseId()).assignType(AssignTypeEnum.EVERYONE.getType()).build());
                //去重，sectionAssignList与everyOneAssignList 同时存在某任务的assign ,则 remove掉everyOneAssignList 中的assign
                assignList = buildAssignList(sectionAssignList, everyOneAssignList);
                totalAssigns = assignList.size();
                if (totalAssigns == 0) {
                    break;
                }
                for (int i = 0; i < totalAssigns; i++) {
                    Assign assigntmp = getAssignDto(assignList.get(i), assignUserOparateDTO);
                    oprateAssignList.add(assigntmp);
                }
                batchSave(oprateAssignList, true);
                break;
            case 2:
                //删除
                deleteRemainAssign(assignUserOparateDTO);
                break;
            case 3:
                //移动
                if (assignUserOparateDTO.getSrcSectionId() == null
                        || assignUserOparateDTO.getDestSectionId() == null) {
                    break;
                }
                //删除需要移出的班级
                deleteRemainAssign(assignUserOparateDTO);
                //增加信息到移入的班级
                //获取该班级和该课程下的所有分配对象
                assignList = assignDao.find(assignUserOparateDTO.getCourseId(), 2/*表示给班级分配*/, assignUserOparateDTO.getDestSectionId());
                totalAssigns = assignList.size();
                if (totalAssigns == 0) {
                    break;
                }
                for (int i = 0; i < totalAssigns; i++) {
                    Assign assigntmp = getAssignDto(assignList.get(i), assignUserOparateDTO);
                    oprateAssignList.add(assigntmp);
                }
                batchSave(oprateAssignList, true);
                break;
            default:
                //;
                break;
        }

    }

    //去重，sectionAssignList与everyOneAssignList 同时存在某任务的assign ,则 remove掉everyOneAssignList 中的assign
    private List<Assign> buildAssignList(List<Assign> sectionAssignList, List<Assign> everyOneAssignList) {
        List<Assign> assignList = new ArrayList<>();
        assignList.addAll(sectionAssignList);

        everyOneAssignList.forEach(assign -> {
            boolean b = sectionAssignList.stream().anyMatch(u -> u.getOriginId().equals(assign.getOriginId()) && u.getOriginType().equals(assign.getOriginType()));
            if (!b) {
                assignList.add(assign);
            }
        });
        return assignList;
    }

    /**
     * 功能：用户是否在用户列表里
     *
     * @param assignUser
     * @param assignUserList
     * @return
     */
    private List<AssignUser> userIsInAssignUserList(AssignUser assignUser, List<AssignUser> assignUserList) {
        int totalUsers = 0;
        if (assignUserList != null) {
            totalUsers = assignUserList.size();
        }
        for (int i = 0; i < totalUsers; i++) {
            if (assignUser.getUserId().longValue() == assignUserList.get(i).getUserId().longValue() &&
                    assignUser.getOriginType().equals(assignUserList.get(i).getOriginType())  &&
                    assignUser.getOriginId().longValue() == assignUserList.get(i).getOriginId().longValue()
            ) {
                assignUserList.remove(i);
                totalUsers = assignUserList.size();
            }
        }
        return assignUserList;

    }


    /**
     * 功能：组装用户分配信息
     *
     * @param assign
     * @param assignUser
     * @param userId
     * @return
     */
    private AssignUser getAssignUserDto(Assign assign, AssignUser assignUser, Long userId, AssignUserDataTimeDTO assignUserDataTimeDTO) {
        //开始时间为空需要从班级、课程、学期中获取开始时间
        if (assign.getStartTime() == null) {
            if (assign.getAssignType().equals(AssignTypeEnum.COURSESECTION.getType())) {
                if (assignUserDataTimeDTO.getSectionStartTime() != null) {
                    assign.setStartTime(assignUserDataTimeDTO.getSectionStartTime());
                } else if (assignUserDataTimeDTO.getCourseStartTime() != null) {
                    assign.setStartTime(assignUserDataTimeDTO.getCourseStartTime());
                } else {
                    assign.setStartTime(assignUserDataTimeDTO.getTermStartTime());
                }
            } else if (assign.getAssignType().equals(AssignTypeEnum.EVERYONE.getType()) ) {
                if (assignUserDataTimeDTO.getCourseStartTime() != null) {
                    assign.setStartTime(assignUserDataTimeDTO.getCourseStartTime());
                } else {
                    assign.setStartTime(assignUserDataTimeDTO.getTermStartTime());
                }
            }

        }
        //结束时间为空需要从班级、课程、学期中获取结束时间
        if (assign.getEndTime() == null) {
            if (assignUserDataTimeDTO.getSectionEndTime() != null) {
                assign.setEndTime(assignUserDataTimeDTO.getSectionEndTime());
            } else if (assignUserDataTimeDTO.getCourseEndTime() != null) {
                assign.setEndTime(assignUserDataTimeDTO.getCourseEndTime());
            } else {
                assign.setEndTime(assignUserDataTimeDTO.getTermEndTime());
            }
            if (assign.getAssignType().equals(AssignTypeEnum.COURSESECTION.getType())) {
                if (assignUserDataTimeDTO.getSectionEndTime() != null) {
                    assign.setEndTime(assignUserDataTimeDTO.getSectionEndTime());
                } else if (assignUserDataTimeDTO.getCourseEndTime() != null) {
                    assign.setEndTime(assignUserDataTimeDTO.getCourseEndTime());
                } else {
                    assign.setEndTime(assignUserDataTimeDTO.getTermEndTime());
                }
            } else {
                if (assignUserDataTimeDTO.getCourseEndTime() != null) {
                    assign.setEndTime(assignUserDataTimeDTO.getCourseEndTime());
                } else {
                    assign.setEndTime(assignUserDataTimeDTO.getTermEndTime());
                }
            }
        }
        if (assignUser == null) {
            assignUser = new AssignUser();
        }
        assignUser.setCourseId(assign.getCourseId());
        assignUser.setOriginType(assign.getOriginType());
        assignUser.setOriginId(assign.getOriginId());
        assignUser.setStartTime(assign.getStartTime());
        assignUser.setLimitTime(assign.getLimitTime());
        assignUser.setEndTime(assign.getEndTime());
        assignUser.setUserId(userId);

        assignUser.setCreateUserId(WebContext.getUserId());
        assignUser.setUpdateUserId(WebContext.getUserId());
        return assignUser;
    }

    /**
     * 功能：获取assignUser状态信息
     *
     * @param userId     用户Id
     * @param originType 来源类型
     * @param originId   来源ID
     * @return AssignStatusEnum
     */
    public AssignStatusEnum getAssignUserStatus(long userId, int originType, long originId) {
        AssignStatusEnum status = AssignStatusEnum.NOAUTHORITY;//用户没有权限

        List<AssignUser> assignUserList = assignUserDao.getByUseIdAndTypeAndOriginId(userId, originType, originId);
        if (assignUserList != null) {
            if (assignUserList.size() > 0) {
                if (assignUserList.get(0).getStartTime() == null && assignUserList.get(0).getLimitTime() == null && assignUserList.get(0).getEndTime() == null) {
                    status = AssignStatusEnum.UNLIMITED;//当前时间限制都为空，没有任何时间限制，任何时候都有权限；
                    return status;
                }
                if (assignUserList.get(0).getStartTime() == null && assignUserList.get(0).getLimitTime() == null) {
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getEndTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDENDTIME;//当前时间已经超过了结束时间
                        return status;
                    }
                }
                if (assignUserList.get(0).getStartTime() == null && assignUserList.get(0).getEndTime() == null) {
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getLimitTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDDEADLINE;//当前时间已经超过了截止时间
                        return status;
                    }
                }
                if (assignUserList.get(0).getLimitTime() == null && assignUserList.get(0).getEndTime() == null) {
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getStartTime()) == 1) {
                        status = AssignStatusEnum.NOTBEGIN;//当前时间还没达到规定的开始时间
                        return status;
                    }
                }
                if (assignUserList.get(0).getStartTime() == null) {
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getEndTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDENDTIME;//当前时间已经超过了结束时间
                        return status;
                    }
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getLimitTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDDEADLINE;//当前时间已经超过了截止时间
                        return status;
                    }


                }
                if (assignUserList.get(0).getLimitTime() == null) {
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getStartTime()) == 1) {
                        status = AssignStatusEnum.NOTBEGIN;//当前时间还没达到规定的开始时间
                        return status;
                    }

                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getEndTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDENDTIME;//当前时间已经超过了结束时间
                        return status;
                    }
                }
                if (assignUserList.get(0).getEndTime() == null) {
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getStartTime()) == 1) {
                        status = AssignStatusEnum.NOTBEGIN;//当前时间还没达到规定的开始时间
                        return status;
                    }
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getEndTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDENDTIME;//当前时间已经超过了结束时间
                        return status;
                    }
                    if (compareDateByGetTime(new Date(), assignUserList.get(0).getLimitTime()) == 2) {
                        status = AssignStatusEnum.EXCEEDEDDEADLINE;//当前时间已经超过了截止时间
                        return status;
                    }
                }
                if (compareDateByGetTime(new Date(), assignUserList.get(0).getStartTime()) == 1) {
                    status = AssignStatusEnum.NOTBEGIN;//当前时间还没达到规定的开始时间
                    return status;
                }
                if (compareDateByGetTime(new Date(), assignUserList.get(0).getEndTime()) == 2) {
                    status = AssignStatusEnum.EXCEEDEDENDTIME;//当前时间已经超过了结束时间
                    return status;
                }
                if (compareDateByGetTime(new Date(), assignUserList.get(0).getLimitTime()) == 2) {
                    status = AssignStatusEnum.EXCEEDEDDEADLINE;//当前时间已经超过了截止时间
                    return status;
                }
                status = AssignStatusEnum.NORMAL;//当前时间在规定限定的时间范围内

            }

        }

        return status;
    }

    /**
     * 功能：比较是新增用户，还是更新用户
     *
     * @param assignUserList
     * @param userId
     * @return null为新增，否则为更新
     */
    private AssignUser getAssignUser(List<AssignUser> assignUserList, Long userId) {
        AssignUser assignUser = null;
        int total = assignUserList.size();
        for (int i = 0; i < total; i++) {
            if (userId.equals(assignUserList.get(i).getUserId()) ) {
                assignUser = assignUserList.get(i);
                return assignUser;
            }
        }
        return assignUser;
    }

    /**
     * 功能：获取需要删除用户的列表
     *
     * @param assignUserList
     * @param assignUserUpdateList
     * @return
     */
    private List<AssignUser> getDeleteAssignUser(List<AssignUser> assignUserList, List<AssignUser> assignUserUpdateList) {
        List<AssignUser> assignUserDeleteList = new ArrayList<>();//删除用户列表
        if (assignUserList == null) {
            return null;
        }
        int totalAssignUserList = assignUserList.size();
        int totalUserUpdateList = assignUserUpdateList.size();
        boolean isfind = false;
        for (int i = 0; i < totalAssignUserList; i++) {
            isfind = false;
            for (int j = 0; j < totalUserUpdateList; j++) {
                if (assignUserList.get(i).getUserId().equals(assignUserUpdateList.get(j).getUserId()) ) {
                    isfind = true;
                    break;
                }

            }
            //没有找到就添加到删除列表里
            if (!isfind) {
                assignUserDeleteList.add(assignUserList.get(i));

            }
        }
        return assignUserDeleteList;
    }

    /**
     * 功能：获取assignUser基本信息
     *
     * @param userId     用户Id
     * @param originType 来源类型
     * @param originId   来源ID
     * @return
     */
    public AssignUser getAssignUserByUseIdAndTypeAndOriginId(long userId, int originType, long originId) {
        AssignUser assignUser = null;
        List<AssignUser> assignUserList = assignUserDao.getByUseIdAndTypeAndOriginId(userId, originType, originId);
        if (assignUserList != null) {
            if (assignUserList.size() > 0) {
                assignUser = assignUserList.get(0);
            }
        }
        return assignUser;
    }

    /**
     * 功能：获取需要增加或更新到数据库的记录
     *
     * @param assignStudentUser
     * @param assignGroupUser
     * @param assignSectionUser
     * @param assignCourseUser
     * @return
     */
    private List<AssignUser> getAssignUserAddList(List<AssignUser> assignStudentUser,
                                                  List<AssignUser> assignGroupUser,
                                                  List<AssignUser> assignSectionUser,
                                                  List<AssignUser> assignCourseUser) {
        List<AssignUser> assignUserList = new ArrayList<>();//用户列表

        int tottalUser = assignStudentUser.size();
        //给单独学生分配了，就排除组、班级、课程中存在的用户
        for (int i = 0; i < tottalUser; i++) {
            assignGroupUser = userIsInAssignUserList(assignStudentUser.get(i), assignGroupUser);
            assignSectionUser = userIsInAssignUserList(assignStudentUser.get(i), assignSectionUser);
            assignCourseUser = userIsInAssignUserList(assignStudentUser.get(i), assignCourseUser);
        }
        //给小组分配了，就排除班级、课程中存在的用户
        tottalUser = assignGroupUser.size();
        for (int i = 0; i < tottalUser; i++) {
            assignSectionUser = userIsInAssignUserList(assignGroupUser.get(i), assignSectionUser);
            assignCourseUser = userIsInAssignUserList(assignGroupUser.get(i), assignCourseUser);

        }
        //给班级分配了，就排除课程中存在的用户
        tottalUser = assignSectionUser.size();
        for (int i = 0; i < tottalUser; i++) {
            assignCourseUser = userIsInAssignUserList(assignSectionUser.get(i), assignCourseUser);
        }
        //组装排除后的记录
        //学生个人
        tottalUser = assignStudentUser.size();
        for (int i = 0; i < tottalUser; i++) {
            assignUserList.add(assignStudentUser.get(i));
        }
        //小组
        tottalUser = assignGroupUser.size();
        for (int i = 0; i < tottalUser; i++) {
            assignUserList.add(assignGroupUser.get(i));
        }
        //班级
        tottalUser = assignSectionUser.size();
        for (int i = 0; i < tottalUser; i++) {
            assignUserList.add(assignSectionUser.get(i));
        }
        //课程
        tottalUser = assignCourseUser.size();
        for (int i = 0; i < tottalUser; i++) {
            assignUserList.add(assignCourseUser.get(i));
        }
        return assignUserList;

    }

    /**
     * 功能：用户分配表信息维护
     *
     * @param assigns    分配记录对象
     * @param isRabbitMq
     */
    public void batchSave(List<Assign> assigns, boolean isRabbitMq) {
        List<AssignUser> assignUserAddList = new ArrayList<>();//新增用户列表
        List<AssignUser> assignUserUpdateList = new ArrayList<>();//更新用户列表
        List<AssignUser> assignUserDeleteList = new ArrayList<>();//删除用户列表
        AssignUserDataTimeDTO assignUserDataTimeDTO = new AssignUserDataTimeDTO();

        List<AssignUser> assignCourseUserAddList = new ArrayList<>();//新增课程用户列表
        List<AssignUser> assignCourseUserUpdateList = new ArrayList<>();//更新课程用户列表

        List<AssignUser> assignSectionUserAddList = new ArrayList<>();//新增班级用户列表
        List<AssignUser> assignSectionUserUpdateList = new ArrayList<>();//更新班级程用户列表

        List<AssignUser> assignGroupUserAddList = new ArrayList<>();//新增小组用户列表
        List<AssignUser> assignGroupUserUpdateList = new ArrayList<>();//更新小组程用户列表

        List<AssignUser> assignStudentUserAddList = new ArrayList<>();//新增学生用户列表
        List<AssignUser> assignStudentUserUpdateList = new ArrayList<>();//更新学生程用户列表

        //查询当前分配对象所有被分配的用户
        if (assigns == null) {
            return;
        }
        Course course = null;
        Term term = null;
        List<AssignUser> assignUserList = null;
        //课程用户
        List<CourseUser> courseUserList = null;
        //班级用户
        List<SectionUser> sectionUserList = null;
        //小组用户
        List<StudyGroupUser> studyGroupUserList = null;
        int totalAssign = assigns.size();
        for (int i = 0; i < totalAssign; i++) {

            course = courseDao.get(assigns.get(i).getCourseId());
            term = termDao.get(course.getTermId());

            /**
             * 课程开始日期
             */
            assignUserDataTimeDTO.setCourseStartTime(course.getStartTime());
            /**
             * 课程结束日期
             */
            assignUserDataTimeDTO.setCourseEndTime(course.getEndTime());
            /**
             * 学期开始日期
             */
            assignUserDataTimeDTO.setTermStartTime(term.getStartTime());
            /**
             * 学期结束日期
             */
            assignUserDataTimeDTO.setTermEndTime(term.getEndTime());
            assignUserDataTimeDTO.setUserStartTime(null);
            assignUserDataTimeDTO.setUserEndTime(null);
            assignUserDataTimeDTO.setUserLimitTime(null);

            assignUserList = assignUserDao.find(assigns.get(i).getOriginType(), assigns.get(i).getOriginId());
            if (assigns.get(i).getAssignType().equals(AssignTypeEnum.EVERYONE.getType()) ) {
                courseUserList = courseUserDao.findUserByCourseId(assigns.get(i).getCourseId());
                int totalcourseUserList = courseUserList.size();
                for (int j = 0; j < totalcourseUserList; j++) {

                    AssignUser assignUser = getAssignUser(assignUserList, courseUserList.get(j).getUserId());
                    if (assignUser == null) {
                        assignUser = getAssignUserDto(assigns.get(i), assignUser, courseUserList.get(j).getUserId(), assignUserDataTimeDTO);
                        assignCourseUserAddList.add(assignUser);
                    } else {


                        assignUser = getAssignUserDto(assigns.get(i), assignUser, courseUserList.get(j).getUserId(), assignUserDataTimeDTO);
                        assignCourseUserUpdateList.add(assignUser);
                    }
                }
                continue;
            }
            if (assigns.get(i).getAssignType().equals(AssignTypeEnum.COURSESECTION.getType()) ) {
                Section section = sectionDao.get(assigns.get(i).getAssignId());
                /**
                 * 班级开始日期
                 */
                assignUserDataTimeDTO.setSectionStartTime(section.getStartTime());
                /**
                 * 班级结束日期
                 */
                assignUserDataTimeDTO.setSectionEndTime(section.getStartTime());
                sectionUserList = sectionUserDao.findUserByCourseIdAndSectionId(assigns.get(i).getCourseId(), assigns.get(i).getAssignId());
                int totalSectionUserList = sectionUserList.size();
                for (int j = 0; j < totalSectionUserList; j++) {
                    AssignUser assignUser = getAssignUser(assignUserList, sectionUserList.get(j).getUserId());
                    if (assignUser == null) {
                        assignUser = getAssignUserDto(assigns.get(i), assignUser, sectionUserList.get(j).getUserId(), assignUserDataTimeDTO);
                        assignSectionUserAddList.add(assignUser);
                    } else {
                        assignUser = getAssignUserDto(assigns.get(i), assignUser, sectionUserList.get(j).getUserId(), assignUserDataTimeDTO);
                        assignSectionUserUpdateList.add(assignUser);
                    }
                }
                continue;
            }
            if (assigns.get(i).getAssignType().equals(AssignTypeEnum.STUDENT.getType())) {

                AssignUser assignUser = getAssignUser(assignUserList, assigns.get(i).getAssignId());
                if (assignUser == null) {
                    assignUser = getAssignUserDto(assigns.get(i), assignUser, assigns.get(i).getAssignId(), assignUserDataTimeDTO);
                    assignStudentUserAddList.add(assignUser);
                } else {
                    assignUser = getAssignUserDto(assigns.get(i), assignUser, assigns.get(i).getAssignId(), assignUserDataTimeDTO);
                    assignStudentUserUpdateList.add(assignUser);
                }
                continue;
            }
            if (assigns.get(i).getAssignType().equals(AssignTypeEnum.GROUPS.getType())) {
                studyGroupUserList = studyGroupUserDao.findUserByCourseIdAndGroupId(assigns.get(i).getCourseId(), assigns.get(i).getAssignId());
                int totalStudyGroupUserList = studyGroupUserList.size();
                for (int j = 0; j < totalStudyGroupUserList; j++) {
                    AssignUser assignUser = getAssignUser(assignUserList, studyGroupUserList.get(j).getUserId());
                    if (assignUser == null) {
                        assignUser = getAssignUserDto(assigns.get(i), assignUser, studyGroupUserList.get(j).getUserId(), assignUserDataTimeDTO);
                        assignGroupUserAddList.add(assignUser);
                    } else {

                        assignUser = getAssignUserDto(assigns.get(i), assignUser, studyGroupUserList.get(j).getUserId(), assignUserDataTimeDTO);
                        assignGroupUserUpdateList.add(assignUser);
                    }
                }
                continue;
            }
        }
        //班级用户可能存在重复记录，需要去重
        assignSectionUserAddList = duplicateRemovalAssignUser(assignSectionUserAddList);
        //获取增加用户信息
        assignUserAddList = getAssignUserAddList(assignStudentUserAddList,
                assignGroupUserAddList,
                assignSectionUserAddList,
                assignCourseUserAddList);
        //获取更新用户信息
        assignUserUpdateList = getAssignUserAddList(assignStudentUserUpdateList,
                assignGroupUserUpdateList,
                assignSectionUserUpdateList,
                assignCourseUserUpdateList);


        assignUserDeleteList = getDeleteAssignUser(assignUserList, assignUserUpdateList);
        if (assignUserAddList.size() > 0) {
            assignUserAddList = duplicateRemovalAssignUser(assignUserAddList);
            assignUserDao.batchInsert(assignUserAddList);
        }

        if (assignUserUpdateList.size() > 0 && !isRabbitMq) {
            assignUserUpdateList = duplicateRemovalAssignUser(assignUserUpdateList);
            assignUserDao.batchUpdate(assignUserUpdateList);
        }
        if (assignUserDeleteList != null && !isRabbitMq) {
            if (assignUserDeleteList.size() > 0) {
                assignUserDao.batchDelete(assignUserDeleteList);
            }
        }
    }

    /**
     * 功能：去除重复数据，只有新增加的用户需要去重
     *
     * @param assignUserList
     * @return
     */
    List<AssignUser> duplicateRemovalAssignUser(List<AssignUser> assignUserList) {
        int total = assignUserList.size();
        int cnt = 0;
        for (int i = 0; i < total; i++) {
            cnt = 0;
            for (int j = i; j < total; j++) {
                if (assignUserList.get(i).getOriginId().longValue() == assignUserList.get(j).getOriginId().longValue() &&
                        assignUserList.get(i).getOriginType().equals(assignUserList.get(j).getOriginType())  &&
                        assignUserList.get(i).getUserId().longValue() == assignUserList.get(j).getUserId().longValue()) {
                    cnt++;
                }
                if (cnt > 1) {
                    //去重时需要对去重时间比较处理
                    if (compareDateByGetTime(assignUserList.get(j).getStartTime(), assignUserList.get(i).getStartTime()) == 1) {
                        assignUserList.get(i).setStartTime(assignUserList.get(j).getStartTime());

                    }
                    if (compareDateByGetTime(assignUserList.get(j).getEndTime(), assignUserList.get(i).getEndTime()) == 2) {
                        assignUserList.get(i).setEndTime(assignUserList.get(j).getEndTime());

                    }
                    if (compareDateByGetTime(assignUserList.get(j).getLimitTime(), assignUserList.get(i).getLimitTime()) == 2) {
                        assignUserList.get(i).setLimitTime(assignUserList.get(j).getLimitTime());
                    }
                    assignUserList.remove(j);
                    total = assignUserList.size();
                    j--;
                    cnt--;
                }
            }
        }

        return assignUserList;
    }

}
