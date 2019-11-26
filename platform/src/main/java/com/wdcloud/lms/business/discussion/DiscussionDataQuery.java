package com.wdcloud.lms.business.discussion;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.announce.enums.IsReadTypeEnum;
import com.wdcloud.lms.business.discussion.dto.DiscussionAddDTO;
import com.wdcloud.lms.business.discussion.enums.AssignmentGroupItemOriginTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.*;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.*;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_DISCUSSION)
public class DiscussionDataQuery implements IDataQueryComponent<Discussion> {
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private ContentViewRecordDao contentViewRecordDao;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    /**
     * @api {get} /discussion/list 讨论列表
     * @apiName discussionList
     * @apiGroup Discussion
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1,2} isRead 是否已读 0:未读,1:已读，2:所有
     * @apiParam {String} [title] 讨论标题
     * @apiParam {Number} [studyGroupId]小组ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Object[]} entity 讨论列表
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} [entity.content] 讨论内容
     * @apiSuccess {Number=1,2} entity.type 类型，1: 普通讨论， 2： 小组讨论
     * @apiSuccess {Number} [entity.studyGroupSetId] 学习小组集ID
     * @apiSuccess {Number=0,1} entity.isPin 是否置顶
     * @apiSuccess {Number=0,1} entity.isGrade 是否评分
     * @apiSuccess {Number=0,1} entity.enableComment 评论，0: 禁止评论, 1: 允许评论
     * @apiSuccess {Number=0,1} [entity.status] 发布状态
     * @apiSuccess {Number=0,1} [entity.lastActiveTime] 最后一次对此讨论的评论时间
     * @apiSuccess {Number} [entity.score] 分值
     * @apiSuccess {Number=0,1} [entity.allowComment] 允许评论
     * @apiSuccess {Object} [entity.readCountDTO] 评论数统计
     * @apiSuccess {Number} [entity.readCountDTO.replyTotal] 评论总数
     * @apiSuccess {Number} [entity.readCountDTO.replyReadTotal]  评论已读总数
     * @apiSuccess {Number} [entity.readCountDTO.replyNotReadTotal] 评论未读总数
     * @apiSuccess {Object[]} [entity.sectionList] 会话数组 [{班级名称：用户个数}]
     * @apiSuccess {Number} [entity.sectionList.sectionId] 会话班级ID
     * @apiSuccess {String} [entity.sectionList.sectionName] 会话班级名称
     * @apiSuccess {Number} [entity.sectionList.userCount]  会话班级用户个数
     * @apiSuccess {Number} [entity.userCount] 用户总数，所有班级时才有此字段
     * @apiSuccess {Object} entity.warningVO 讨论时间提醒对象
     * @apiSuccess {Number=0,1,2,3} entity.warningVO.warningType 提醒类型 0:不显示 1:Not available until xxx xxx xx,xxxx at xx:xx am/pm 例如 Not available until Jan 4, 2019 at 8:59am 2:Available until xxx xxx xx,xxxx at xx:xx am/pm 例如 Available until Jan 4, 2019 at 8:59am 3:Was locked at xxx xx at xx：xx am/pm 例如 Was locked at Dec 16 at 11:59 pm
     * @apiSuccess {Number} entity.warningVO.warningTime 提醒时间
     * @apiSuccess {Number} entity.maxLimitTime 提醒最大截止时间
     * @apiSuccess {Number} entity.groupId 小组ID 学生如果有此字段则跳转到小组详情页
     */
    @Override
    public List<? extends Discussion> list(Map<String, String> param) {
        param.put("userId", WebContext.getUserId()+"");
        Long studyGroupId = StringUtils.isBlank(param.get("studyGroupId"))?null:Long.valueOf(param.get("studyGroupId"));
        List<DiscussionListVo> discussionList = findDiscussionList(param);
        if (ListUtils.isNotEmpty(discussionList)) {
            //设置列表的提醒时间
            setWarningDTO(discussionList);
            //评论数统计
            List<Long> ids = discussionList.stream().map(DiscussionListVo::getId).collect(Collectors.toList());
            List<ReadCountDTO> unReadlist=contentViewRecordDao.findAnnounceOrDiscussionReplyUnreadTotal(studyGroupId,ids,WebContext.getUserId(),OriginTypeEnum.DISCUSSION);
            Map<Long, ReadCountDTO> unReadMap = unReadlist.stream().collect(Collectors.toMap(ReadCountDTO::getOriginId,a->a));
            discussionList.forEach(vo -> {
                vo.setReadCountDTO(unReadMap.get(vo.getId()));
            });
            //未读过滤
            if (IsReadTypeEnum.NOTREAD.getType().equals(Integer.valueOf(param.get("isRead")))) {
                discussionList = discussionList.stream()
                        .filter(item -> IsReadTypeEnum.NOTREAD.getType().intValue() == item.getIsRead().intValue() || item.getReadCountDTO() != null && item.getReadCountDTO().getReplyNotReadTotal() > 0)
                        .collect(Collectors.toList());
            }
        }
        //列表为空或者小组讨论直接返回
        if (ListUtils.isEmpty(discussionList)||Objects.nonNull(param.get("studyGroupId"))) {
            return discussionList;
        }else{
            // 列表非空+非小组讨论+不计分+非小组集讨论  带出会话节点
            buildSections(param,discussionList, null);
            return discussionList;
        }
    }

    /**
     * 构造会话数  //获取此课程各个班级的 名称-->人数
     * @param param
     * @param discussionList
     * @param dto
     */
    private void buildSections(Map<String, String> param, List<DiscussionListVo> discussionList, DiscussionAddDTO dto) {
        List<SectionVo> sectionList = sectionDao.findSectionListByCourseId(param);
        Map<Long, SectionVo>  sectionMap = sectionList.stream().collect(Collectors.toMap(SectionVo::getSectionId, a->a));
        Long userCount = sectionList.stream().map(SectionVo::getUserCount).reduce(Long::sum).get();
        if (ListUtils.isNotEmpty(discussionList)) {
            discussionList.forEach(discussionListVo -> {
                //非小组+不计分+非小组集 才带出会话
                if (Objects.isNull(discussionListVo.getStudyGroupId())&&discussionListVo.getIsGrade().intValue()== Status.NO.getStatus()&&Objects.isNull(discussionListVo.getStudyGroupSetId())) {
                    discussionListVo.getAssign().forEach(assign -> {
                        if (assign.getAssignType().equals(AssignTypeEnum.ALL.getType())) {
                            discussionListVo.setUserCount(userCount);
                        }else if (assign.getAssignType().equals(AssignTypeEnum.SECTION.getType())) {
                            discussionListVo.getSectionList().add(sectionMap.get(assign.getAssignId()));
                        }
                    });
                }
            });
        }else{
            dto.getAssign().forEach(assign1 -> {
                if (assign1.getAssignType().equals(AssignTypeEnum.ALL.getType())) {
                    dto.setUserCount(userCount);
                } else if (assign1.getAssignType().equals(AssignTypeEnum.SECTION.getType())) {
                    dto.getSectionList().add(sectionMap.get(assign1.getAssignId()));
                }
            });
        }

    }

    /**
     * 设置列表的提醒时间
     * @param discussionList
     */
    private void setWarningDTO(List<DiscussionListVo> discussionList) {
        if (ListUtils.isNotEmpty(discussionList)) {
            discussionList.forEach(discussionListVo -> {
                List<Assign> assignList = discussionListVo.getAssign();
                if (ListUtils.isNotEmpty(assignList)) {
                    Assign minStartTimeAssign = assignList.stream()
                            .min(Comparator.comparing(a -> a.getStartTime(),Comparator.nullsFirst(Date::compareTo))).get();
                    Assign maxEndTimeAssign = assignList.stream()
                            .max(Comparator.comparing(a -> a.getEndTime(),Comparator.nullsLast(Date::compareTo))).get();
                    if (discussionListVo.getMaxLimitTime() == null) {
                        Assign maxLimitTimeAssign = assignList.stream()
                                .max(Comparator.comparing(a -> a.getLimitTime(),Comparator.nullsLast(Date::compareTo))).get();
                        discussionListVo.setMaxLimitTime(maxLimitTimeAssign.getLimitTime());
                    }
                    Date startTime = minStartTimeAssign.getStartTime();
                    Date endTime = maxEndTimeAssign.getEndTime();
                    discussionListVo.setWarningVO(buildWarningVo(startTime,endTime));
                }
            });
        }
    }

    private WarningVO buildWarningVo(Date startTime, Date endTime) {
        WarningVO warningVO = new WarningVO();
        Date now = new Date();
        if (startTime !=null&&endTime!=null) {
            if (now.before(startTime)) {
                warningVO.setWarningType(WarningTypeEnum.NOT_AVAILABLE.getType());
                warningVO.setWarningTime(startTime);
            }else if(now.after(startTime)&&now.before(endTime)){
                warningVO.setWarningType(WarningTypeEnum.AVAILABLE.getType());
                warningVO.setWarningTime(endTime);
            }else if(now.after(endTime)){
                warningVO.setWarningType(WarningTypeEnum.LOCKED.getType());
                warningVO.setWarningTime(endTime);
            }
        }else if(startTime==null&&endTime==null){
            warningVO.setWarningType(WarningTypeEnum.NONE.getType());
        }else if(startTime==null&&endTime!=null){
            if (now.before(endTime)) {
                warningVO.setWarningType(WarningTypeEnum.AVAILABLE.getType());
                warningVO.setWarningTime(startTime);
            }else if(now.after(endTime)){
                warningVO.setWarningType(WarningTypeEnum.LOCKED.getType());
                warningVO.setWarningTime(endTime);
            }
        }else if(startTime!=null&&endTime==null){
            if (now.before(startTime)) {
                warningVO.setWarningType(WarningTypeEnum.NOT_AVAILABLE.getType());
                warningVO.setWarningTime(startTime);
            }else if(now.after(startTime)){
                warningVO.setWarningType(WarningTypeEnum.NONE.getType());
            }
        }
        return warningVO;
    }

    /**
     * 我的讨论列表
     * @param param
     * @return
     */
    private List<DiscussionListVo> findDiscussionList(Map<String, String> param) {
        List<DiscussionListVo> discussionList = null;
        //studyGroupId 非空时为小组讨论
        Long studyGroupId = StringUtils.isBlank(param.get("studyGroupId"))?null:Long.valueOf(param.get("studyGroupId"));
        boolean studentRole = roleService.hasStudentRole();
        param.put("studentRole", studentRole+"");
        if (studyGroupId == null) { //课程讨论
            if (!studentRole) {
                discussionList =  discussionDao.searchCourseDiscussionByTeacher(param);
            }else {
                discussionList = discussionDao.searchCourseDiscussionByStudent(param);
                discussionList=discussionList.stream().filter(o->!(o.getStudyGroupSetId()!=null&&o.getGroupId()==null)).collect(Collectors.toList());
            }
        }else{//小组讨论
            //查询 studyGroupId所在的小组集  studyGroupSetId
            StudyGroup group=studyGroupDao.findOne(StudyGroup.builder().id(studyGroupId).build());
            param.put("studyGroupSetId", group.getStudyGroupSetId()+"");
            discussionList = discussionDao.searchGroupDiscussion(param);
        }
        return discussionList;
    }



    /**
     * @api {get} /discussion/pageList 讨论分页
     * @apiName discussionPageList
     * @apiGroup Discussion
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1,2} isRead 是否已读 0:未读,1:已读，2:所有
     * @apiParam {String} [title] 讨论标题
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 讨论列表
     * @apiSuccess {Number} entity.list.id ID
     * @apiSuccess {Number} entity.list.courseId 课程ID
     * @apiSuccess {String} entity.list.title 标题
     * @apiSuccess {String} [entity.list.content] 标题
     * @apiSuccess {Number=1,2} entity.list.type 类型，1: 普通讨论， 2： 小组讨论
     * @apiSuccess {Number} [entity.list.studyGroupSetId] 学习小组集ID
     * @apiSuccess {Number=0,1} entity.list.isPin 是否置顶
     * @apiSuccess {Number=0,1} entity.list.isGrade 是否评分
     * @apiSuccess {Number=0,1} entity.list.enableComment 评论，0: 禁止评论, 1: 允许评论
     * @apiSuccess {Number=0,1} [entity.list.status] 发布状态
     * @apiSuccess {Number} [entity.list.score] 分值
     * @apiSuccess {Number=0,1} [entity.list.allowComment] 允许评论
     * @apiSuccess {Number} [entity.list.replyTotal] 评论总数
     * @apiSuccess {Number} [entity.list.replyReadTotal]  评论已读总数
     * @apiSuccess {Number} [entity.list.replyNotReadTotal] 评论未读总数
     * @apiSuccess {Object[]} [entity.list.sectionList] 会话数组 [{班级名称：用户个数}]
     * @apiSuccess {Number} [entity.list.sectionList.sectionId] 会话班级ID
     * @apiSuccess {String} [entity.list.sectionList.sectionName] 会话班级名称
     * @apiSuccess {Number} [entity.list.sectionList.userCount]  会话班级用户个数
     * @apiSuccess {Number} [entity.list.userCount] 用户总数，所有班级时才有此字段
     */
    @Override
    public PageQueryResult<DiscussionListVo> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        return null;
    }


    /**
     * @api {get} /discussion/get 讨论详情
     * @apiName discussionGet
     * @apiGroup Discussion
     *
     * @apiParam {Number} data 讨论ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} [entity.content] 标题
     * @apiSuccess {Number=1,2} entity.type 类型，1: 普通讨论， 2： 小组讨论
     * @apiSuccess {Number} [entity.studyGroupSetId] 学习小组集ID
     * @apiSuccess {Number=0,1} entity.isPin 是否置顶
     * @apiSuccess {Number} entity.pinSeq 置顶顺序
     * @apiSuccess {Number=0,1} entity.allowComment 评论，0: 禁止评论, 1: 允许评论
     * @apiSuccess {Number=0,1} [entity.status] 发布状态
     * @apiSuccess {Number} [entity.score] 分值
     * @apiSuccess {Number} [entity.seq] 排序
     * @apiSuccess {Number} [entity.updateTime] 更新时间
     * @apiSuccess {Object} [entity.attachmentFile] 讨论话题附件
     * @apiSuccess {String} [entity.attachmentFile.fileName] 附件名称
     * @apiSuccess {String} [entity.attachmentFile.fileUrl] 附件url
     * @apiSuccess {Object[]} entity.assign 分配数组
     * @apiSuccess {Number} entity.assign.limitTime 规定截止日期
     * @apiSuccess {Number} entity.assign.startTime可以参加测验开始日期
     * @apiSuccess {Number} entity.assign.endTime 可以参加测验结束日期
     * @apiSuccess {Number=1,2,3} entity.assign.assignType 分配类型，1: 所有， 2：section(班级) 3用户
     * @apiSuccess {Number} entity.ssign.assignId]  分配目标ID 班级Id或者用户Id，分配类型为所有时为空
     * @apiSuccess {Object} entity.author 讨论话题作者
     * @apiSuccess {String} entity.author.nickname 讨论话题作者昵称
     * @apiSuccess {String} entity.author.avatarUrl 讨论话题作者头像
     * @apiSuccess {Object} entity.readCountDTO 评论总数未读数统计
     * @apiSuccess {Number} entity.readCountDTO.replyTotal 评论总数
     * @apiSuccess {Number} entity.readCountDTO.replyReadTotal  评论已读总数
     * @apiSuccess {Number} entity.readCountDTO.replyNotReadTotal 评论未读总数
     * @apiSuccess {Object[]} entity.sectionList 会话数组 [{班级名称：用户个数}]
     * @apiSuccess {Number} entity.sectionList.sectionId 会话班级ID
     * @apiSuccess {String} entity.sectionList.sectionName 会话班级名称
     * @apiSuccess {Number} entity.sectionList.userCount  会话班级用户个数
     * @apiSuccess {Number} entity.userCount 用户总数，所有班级时才有此字段
     * @apiSuccess {Number} entity.maxDueTime 讨论最晚截止时间
     * @apiSuccess {Number} entity.isSubmited 学生是否提交过评论
     * @apiSuccess {Object[]} entity.assignUser 学生分配明细
     * @apiSuccess {Number} entity.assignUser.startTime 学生分配开始时间
     * @apiSuccess {Number} entity.assignUser.endTime 学生分配开始时间
     *
     */
    @Override
    public DiscussionAddDTO find(String id) {
        String[] did_groupId = id.split("_");
        Long did = Long.valueOf(did_groupId[0]);
        Long studyGroupId =did_groupId.length==2?Long.valueOf(did_groupId[1]):null;
        Discussion discussion=discussionDao.findOne(Discussion.builder().id(did).build());
        if (discussion == null) {
            log.info("讨论不存在{}",id);
            throw new ParamErrorException();
        }
        DiscussionAddDTO dto = BeanUtil.beanCopyProperties(discussion, DiscussionAddDTO.class);
        //附件
        if (Objects.nonNull(dto.getAttachmentFileId())) {
            UserFile userFile = userFileDao.findOne(UserFile.builder().id(dto.getAttachmentFileId()).build());
            dto.setAttachmentFile(userFile);
        }

        if (dto.getIsGrade().equals(Status.YES.getStatus())) {
            //2计分时 作业小组查询关联表
            AssignmentGroupItem assignmentGroupItemParam = AssignmentGroupItem.builder()
                    .originType(AssignmentGroupItemOriginTypeEnum.DISCUSSION.getType())
                    .originId(dto.getId())
                    .build();
            AssignmentGroupItem assignmentGroupItem=assignmentGroupItemDao.findOne(assignmentGroupItemParam);
            dto.setAssignmentGroupId(assignmentGroupItem.getAssignmentGroupId());
        }
        if (Objects.nonNull(discussion.getStudyGroupSetId())) {
             List<StudyGroupVO> studyGroupList=studyGroupDao.findStudyGroupListBySetId(discussion.getStudyGroupSetId());
            studyGroupList.forEach(studyGroupVO -> {
                List<ReadCountDTO> unReadlist=contentViewRecordDao.findAnnounceOrDiscussionReplyUnreadTotal(studyGroupVO.getId(), List.of(dto.getId()),WebContext.getUserId(),OriginTypeEnum.DISCUSSION);
                Map<Long, ReadCountDTO> unReadMap = unReadlist.stream().collect(Collectors.toMap(ReadCountDTO::getOriginId,a->a));
                studyGroupVO.setReadCountDTO(unReadMap.get(dto.getId()));
            });
             dto.setStudyGroupList(studyGroupList);
        }
        boolean isGroup = Objects.nonNull(discussion.getStudyGroupId());
        //课程讨论  需查询分配关联表
        if (!isGroup) {
            List<Assign> assignList= assignDao.find(Assign.builder().originType(OriginTypeEnum.DISCUSSION.getType()).originId(dto.getId()).build());
            dto.setAssign(assignList);
            if (roleService.hasStudentRole()) {
                AssignUser assignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(),OriginTypeEnum.DISCUSSION.getType(),did);
                dto.setMaxDueTime(assignUser.getLimitTime()==null?null:assignUser.getLimitTime().getTime());
                dto.setAssignUser(assignUser);
            }else{
                Date maxDueTime= assignList.stream()
                        .max(Comparator.comparing(a -> a.getLimitTime(),Comparator.nullsLast(Date::compareTo))).get()
                        .getLimitTime();
                dto.setMaxDueTime(maxDueTime==null?null:maxDueTime.getTime());
            }
        }
        //作者昵称头像
        UserVo author = userDao.findUserById(dto.getCreateUserId());
        dto.setAuthor(author);
        //评论数统计
        List<ReadCountDTO> unReadlist=contentViewRecordDao.findAnnounceOrDiscussionReplyUnreadTotal(studyGroupId, List.of(dto.getId()),WebContext.getUserId(),OriginTypeEnum.DISCUSSION);
        Map<Long, ReadCountDTO> unReadMap = unReadlist.stream().collect(Collectors.toMap(ReadCountDTO::getOriginId,a->a));
        dto.setReadCountDTO(unReadMap.get(dto.getId()));
        //非小组+不计分+非小组集  带出会话节点
        if (!isGroup&&Status.NO.getStatus()==dto.getIsGrade().intValue()&&Objects.isNull(dto.getStudyGroupSetId())) {
            buildSections(Map.of("courseId",dto.getCourseId().toString()),null,dto);
        }
        int count=discussionReplyDao.count(DiscussionReply.builder().discussionId(dto.getId()).roleId(RoleEnum.STUDENT.getType()).isDeleted(0).build());
        dto.setIsSubmited(Long.valueOf(count));
        return dto;
    }
}
