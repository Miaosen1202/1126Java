package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.AssignmentGroupItemDTO;
import com.wdcloud.lms.base.service.*;
import com.wdcloud.lms.business.discussion.dto.DiscussionAddDTO;
import com.wdcloud.lms.business.discussion.enums.AssignmentGroupItemOriginTypeEnum;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignmentGroupItemChangeOpTypeEnum;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("JavaDoc")
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_DISCUSSION)
public class DiscussionDataEdit implements IDataEditComponent {
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private AssignService assignService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DiscussionConfigDao discussionConfigDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private AssignmentGroupItemService assignmentGroupItemService;
    @Autowired
    private ContentViewRecordService contentViewRecordService;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignmentGroupItemChangeRecordDao groupItemChangeRecordDao;
    @Autowired
    public AssignDao assignDao;
    @Autowired
    public AssignUserDao assignUserDao;
    @Autowired
    private ModuleCompleteService moduleCompleteService;
    /**
     * @api {post} /discussion/add 讨论新建
     * @apiName discussionAdd
     * @apiGroup Discussion
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title 标题（size=500)
     * @apiParam {String} [content] 内容
     * @apiParam {Number=1,2} type 类型，1: 课程里面创建的为普通讨论， 2：小组里面创建的为小组讨论
     * @apiParam {Number} [studyGroupSetId] 学习小组集ID(type=1小组集可有可无)
     * @apiParam {Number} [studyGroupId] 讨论话题副本的学习小组ID(type=2指定小组)
     * @apiParam {String} [fileId] 附件ID
     * @apiParam {Number=0,1} [isPin] 是否置顶
     * @apiParam {Number=0,1} [isGrade] 是否评分 分配为所有班级时可选
     * @apiParam {Number=0,1} [allowComment] 0: 禁止评论, 1: 允许评论
     * @apiParam {Number} [score] 满分值 (isGrade=1时必填)
     * @apiParam {Number} [gradeType] 评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分 (isGrade=1时必填)
     * @apiParam {Number} [gradeSchemeId] 评分方案，评分方式为 3: GPA, 4: Letter时 需要关联 cos_discussion.grade_scheme_id=cos_grade_scheme.id(isGrade=1时必填)
     * @apiParam {Number=0,1} [status] 发布状态
     * @apiParam {Number} [assignmentGroupId] 作业小组ID (isGrade=1时必填) 新增/更新表cos_assignment_group_item. origin_type=2  并且origin_id=讨论ID
     * @apiParam {Object[]} assign 分配
     * @apiParam {Number} [assign.limitTime] 规定截止日期
     * @apiParam {Number} [assign.startTime] 可以参加测验开始日期
     * @apiParam {Number} [assign.endTime] 可以参加测验结束日期
     * @apiParam {Number=1,2,3} assign.assignType 分配类型，1: 所有， 2：section(班级) 3用户
     * @apiParam {Number} [assign.assignId]  分配目标ID 班级Id或者用户Id，分配类型为所有时为空
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz =DiscussionAddDTO.class,groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        DiscussionAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, DiscussionAddDTO.class);
        Long studyGroupId = dto.getStudyGroupId();
        verifyStudentPerm(dto);
        //0.附件上传
        saveFile(dto);
        //1.讨论基本表新增
        discussionDao.save(dto);
        if (studyGroupId == null) { //课程讨论,分配必填。作业小组可有可无
            //2.讨论作业小组关联表 cos_assignment_group_item 非必填
            Long groupItemId = insertAssignmentGroupItem(dto);
            if (groupItemId != null) {
                groupItemChangeRecordDao.discussionAdded(dto, groupItemId);
            }
            //3.讨论分配关联表 cos_assign 必填
            insertAssign(dto);
        }
        contentViewRecordService.insert(dto.getId(), ContentViewRecordOrignTypeEnum.DISCUSSION_TOPIC.getType());

        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * 校验学生能否创建课程讨论
     * @param dto
     */
    private void verifyStudentPerm(DiscussionAddDTO dto) {
        if (dto.getStudyGroupId()==null&&roleService.hasStudentRole()) {
            DiscussionConfig config = discussionConfigDao.findOne(DiscussionConfig.builder()
                    .courseId(dto.getCourseId())
                    .build());
            if (config == null||config.getAllowStudentCreateDiscussion().intValue()==Status.NO.getStatus()) {
                log.info("此学生无权限创建课程讨论!");
                throw new PermissionException();
            }
        }
    }

    private void insertAssign(DiscussionAddDTO dto) {
        if (ListUtils.isEmpty(dto.getAssign())) {
            log.info("课程讨论分配不能为空！");
            throw new BaseException("discussion.course.assign.null");
        }
        dto.getAssign().forEach(assign -> {
            if (Status.YES.getStatus() == assign.getAssignType().intValue()) {
                assign.setAssignId(0L);
            }
        });
        assignService.batchSave(dto.getAssign(), dto.getCourseId(), OriginTypeEnum.DISCUSSION, dto.getId());
    }


    /**
     * @api {post} /discussion/modify 讨论修改
     * @apiName discussionModify讨论修改
     * @apiGroup Discussion
     * @apiParam {Number} id 讨论ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title 标题（size=500)
     * @apiParam {String} [content] 内容
     * @apiParam {Number=1,2} type 类型，1: 普通讨论， 2：小组讨论 时需要创建小组讨论副本
     * @apiParam {Number} [studyGroupSetId] 学习小组集ID(type=2指定小组集)
     * @apiParam {Number} [studyGroupId] 讨论话题副本的学习小组ID(type=2指定小组)
     * @apiParam {Number} [attachmentFileId] 附件主键ID
     * @apiParam {String} [fileId] 附件fileId
     * @apiParam {Number=0,1} [isPin] 是否置顶
     * @apiParam {Number=0,1} [isGrade] 是否评分 分配为所有班级时可选
     * @apiParam {Number=0,1} [allowComment] 0: 禁止评论, 1: 允许评论
     * @apiParam {Number} [score] 满分值 (isGrade=1时必填)
     * @apiParam {Number} [gradeType] 评分方式，1: 分值(points), 2: 百分比(percentage), 3: GPA, 4: Letter, 5: 不评分 (isGrade=1时必填)
     * @apiParam {Number} [gradeSchemeId] 评分方案，评分方式为 3: GPA, 4: Letter时 需要关联 cos_discussion.grade_scheme_id=cos_grade_scheme.id(isGrade=1时必填)
     * @apiParam {Number=0,1} [status] 发布状态
     * @apiParam {Number} [assignmentGroupItemID] 作业小组ID (isGrade=1时必填) 新增/更新表cos_assignment_group_item. origin_type=2  并且origin_id=讨论ID
     * @apiParam {Object[]} assign 分配 为所有班级时
     * @apiParam {Number} [assign.limitTime] 规定截止日期
     * @apiParam {Number} [assign.startTime] 可以参加测验开始日期
     * @apiParam {Number} [assign.endTime] 可以参加测验结束日期
     * @apiParam {Number=1,2,3} assign.assignType 分配类型，1: 所有， 2：section(班级) 3用户
     * @apiParam {Number} [assign.assignId]  分配目标ID 班级Id或者用户Id，分配类型为所有时为空
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    @ValidationParam(clazz =DiscussionAddDTO.class,groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        DiscussionAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, DiscussionAddDTO.class);
        buildZeroDateToNull(dto);

        //Long studyGroupId = dto.getStudyGroupId();
        verifyStudentPerm(dto);
        //0.附件上传
        saveFile(dto);

        Discussion oldDiscussion = discussionDao.get(dto.getId());

        //1.更新讨论基本表
        dto.setType(oldDiscussion.getType());
        discussionDao.update(dto);

        if (oldDiscussion.getStudyGroupId() == null||oldDiscussion.getStudyGroupId()==0L) { //课程讨论,分配必填。作业小组可有可无
            //2.更新  作业小组关联表
            Long groupItemId = updateAssignmentGroupItem(dto);
            if (groupItemId != null && !Objects.equals(oldDiscussion.getContent(), dto.getContent())) {
                groupItemChangeRecordDao.changed(dto, groupItemId, AssignmentGroupItemChangeOpTypeEnum.UPDATE_CONTENT);
            }

            //3.讨论分配关联表 cos_assign 必填
            insertAssign(dto);
            //编辑任务分配
            moduleCompleteService.updateAssign(dto.getCourseId(), dto.getId(), OriginTypeEnum.DISCUSSION.getType());
        }
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    private void buildZeroDateToNull(DiscussionAddDTO dto) {
        if (ListUtils.isNotEmpty(dto.getAssign())) {
            dto.getAssign().forEach(a->{
                if (a.getStartTime()!=null&&a.getStartTime().getTime()==0) {
                    a.setStartTime(null);
                }
                if (a.getEndTime()!=null&&a.getEndTime().getTime()==0) {
                    a.setEndTime(null);
                }
                if (a.getLimitTime()!=null&&a.getLimitTime().getTime()==0) {
                    a.setLimitTime(null);
                }
            });
        }
    }

    private void saveFile(DiscussionAddDTO dto) {
        if (StringUtils.isNotBlank(dto.getFileId())) {
            UserFile userFile = null;
            if (Objects.isNull(dto.getStudyGroupId())) {//课程讨论文件
                userFile= userFileService.saveCourseContentAttachment(userFileService.getFileInfo(dto.getFileId()), dto.getCourseId());
            }else{
                userFile=userFileService.saveStudyGroupContentAttachment(userFileService.getFileInfo(dto.getFileId()), dto.getCourseId(),dto.getStudyGroupId());
            }
            dto.setAttachmentFileId(userFile.getId());
        }
    }
    private Long updateAssignmentGroupItem(DiscussionAddDTO dto) {
        AssignmentGroupItem params = AssignmentGroupItem.builder()
                .originId(dto.getId())
                .originType(AssignmentGroupItemOriginTypeEnum.DISCUSSION.getType())
                .build();
        AssignmentGroupItem oldData=assignmentGroupItemDao.findOne(params);
        if (Objects.nonNull(oldData)) {//以前关联过
            if (dto.getAssignmentGroupId() == null) {//现在删除
                assignmentGroupItemService.delete(oldData.getId());
            }else if (!oldData.getId().equals(dto.getAssignmentGroupId())) { //现在变更
                assignmentGroupItemService.delete(oldData.getId());
                Long groupItemId = insertAssignmentGroupItem(dto);
                return groupItemId;
            }
        }else{//以前未关联过并且现在勾选
            if (Objects.nonNull(dto.getAssignmentGroupId())) {
                Long groupItemId = insertAssignmentGroupItem(dto);
                return groupItemId;
            }
        }

        return null;
    }

    private Long insertAssignmentGroupItem(DiscussionAddDTO dto) {
        //计分时 作业小组必填
        if (dto.getIsGrade()!=null&&(Status.YES.getStatus()==dto.getIsGrade().intValue())&&dto.getAssignmentGroupId()==null) {
            log.error("计分讨论,作业小组必填");
            throw new ParamErrorException();
        }
        if (dto.getAssignmentGroupId() != null) {
            dto.setIsGrade(Status.YES.getStatus());
            AssignmentGroupItemDTO item = AssignmentGroupItemDTO.builder()
                    .assignmentGroupId(dto.getAssignmentGroupId())
                    .originId(dto.getId())
                    .originType(OriginTypeEnum.DISCUSSION)
                    .title(dto.getTitle())
                    .score(dto.getScore())
                    .status(dto.getStatus())
                    .build();
            Long id = assignmentGroupItemService.save(item);
            return id;
        }
        return null;
    }

    /**
     * @api {post} /discussion/deletes 讨论删除
     * @apiName discussionDeletes
     * @apiGroup Discussion
     * @apiParam {Number[]} ids ID列表
     * @apiParamExample {json} 请求示例:
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] ID列表
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);

        idList.forEach(id->{
            moduleCompleteService.deleteModuleItemByOriginId(id, OriginTypeEnum.DISCUSSION.getType());
        });

        Discussion discussion = Discussion.builder().isDeleted(Status.YES.getStatus()).build();
        int count=discussionDao.updateByExample(discussion, idList);
        //讨论关系表删除   作业小组
        idList.forEach(id->{
            // 分配记录表
            assignDao.deleteByField(Assign.builder().originId(id).originType(OriginTypeEnum.DISCUSSION.getType()).build());
            //分配学生关系表
            assignUserDao.deleteByField(AssignUser.builder().originId(id).originType(OriginTypeEnum.DISCUSSION.getType()).build());
           assignmentGroupItemDao.deleteByField(AssignmentGroupItem.builder().originId(id).originType(OriginTypeEnum.DISCUSSION.getType()).build());
        });
        return new LinkedInfo(String.valueOf(count));
    }
}
