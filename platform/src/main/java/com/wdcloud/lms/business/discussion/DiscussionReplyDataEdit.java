package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.UserParticipateOpEnum;
import com.wdcloud.lms.base.service.*;
import com.wdcloud.lms.business.discussion.dto.DiscussionReplyAddDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_DISCUSSION_REPLY)
public class DiscussionReplyDataEdit implements IDataEditComponent {
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private ContentViewRecordService contentViewRecordService;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private UserParticipateDao userParticipateDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private ModuleCompleteService moduleCompleteService;
    /**
     * @api {post} /discussionReply/add 讨论回复添加
     * @apiName discussionReplyAdd
     * @apiGroup Discussion
     * @apiParam {Number} discussionId 讨论ID
     * @apiParam {Number} [studyGroupId] 学习小组ID
     * @apiParam {Number} [discussionReplyId] 回复ID
     * @apiParam {String} content 内容
     * @apiParam {String} [fileId] 附件Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增回复ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = DiscussionReplyAddDTO.class,groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        DiscussionReplyAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, DiscussionReplyAddDTO.class);
        //校验发评论权限
        verifyPermission(dto.getDiscussionId());
        dto.setTreeId(Status.NO.getStatus()+"");
        if (Objects.isNull(dto.getDiscussionReplyId())) {
            dto.setDiscussionReplyId(Long.valueOf(Status.NO.getStatus()));
        }
        //附件上传
        saveFile(dto);
        dto.setRoleId(WebContext.getRoleId());
        int count = discussionReplyDao.save(dto);
        //回复成功后，更新讨论主表的激活时间
        if (count > 0) {
            Discussion discussion =discussionDao.findOne(Discussion.builder().id(dto.getDiscussionId()).build());
            discussion.setLastActiveTime(new Date());
            discussionDao.update(discussion);
            //学生评论时 新增或更新 cos_user_submit_record
            if (roleService.hasStudentRole()) {
                saveOrUpdateUserSubmitRecord(discussion,dto.getStudyGroupId());
                saveUserParticipate(discussion);
                //完成任务
                moduleCompleteService.completeAssignment(discussion.getId(), OriginTypeEnum.DISCUSSION.getType());
            }
        }
        contentViewRecordService.insert(dto.getId(), ContentViewRecordOrignTypeEnum.DISCUSSION_REPLY.getType());



        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    private void saveUserParticipate(Discussion discussion) {
        UserParticipate have=userParticipateDao.findOne(UserParticipate.builder()
                .courseId(discussion.getCourseId()).originType(OriginTypeEnum.DISCUSSION.getType()).originId(discussion.getId())
                .userId(WebContext.getUserId()).build());
        if (have == null) {
            UserParticipate userParticipate = UserParticipate.builder()
                    .courseId(discussion.getCourseId())
                    .originType(OriginTypeEnum.DISCUSSION.getType())
                    .originId(discussion.getId())
                    .operation(UserParticipateOpEnum.SUBMIT.getOp())
                    .userId(WebContext.getUserId())
                    .targetName(discussion.getTitle())
                    .build();
            userParticipateDao.save(userParticipate);
        }
    }

    private void saveOrUpdateUserSubmitRecord(Discussion discussion, Long studyGroupId) {
        UserSubmitRecord record=userSubmitRecordDao.findOne(UserSubmitRecord.builder()
                .userId(WebContext.getUserId())
                .originType(OriginTypeEnum.DISCUSSION.getType())
                .originId(discussion.getId())
                .build());
        if (Objects.isNull(record)){
            record = UserSubmitRecord.builder()
                    .courseId(discussion.getCourseId())
                    .userId(WebContext.getUserId())
                    .studyGroupId(studyGroupId)
                    .originType(OriginTypeEnum.DISCUSSION.getType())
                    .originId(discussion.getId())
                    .lastSubmitTime(new Date())
                    .submitCount(Status.YES.getStatus())
                    .needGrade(discussion.getIsGrade())
                    .isGraded(Status.NO.getStatus())
                    .isOverdue(getIsOverdue(discussion.getId()))
                    .build();

            if (discussion.getIsGrade().equals(Status.YES.getStatus())) {
                //查询此评分讨论的 assignment_group_item_id
                AssignmentGroupItem assignmentGroupItem =assignmentGroupItemDao.findOne(AssignmentGroupItem.builder().originId(discussion.getId()).originType(OriginTypeEnum.DISCUSSION.getType()).build());
                record.setAssignmentGroupItemId(assignmentGroupItem.getId());
            }
            userSubmitRecordDao.save(record);
        }else{
            record.setLastSubmitTime(new Date());
            record.setSubmitCount(record.getSubmitCount()+1);
            userSubmitRecordDao.update(record);
        }
    }

    private int getIsOverdue(Long id) {
        AssignUser assignUser=assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(),OriginTypeEnum.DISCUSSION.getType(),id);
        if (assignUser!= null) {
            if (assignUser.getLimitTime()!=null&&new Date().after(assignUser.getLimitTime())) {
                return Status.YES.getStatus();
            }
        }
        return Status.NO.getStatus();
    }

    private void saveFile(DiscussionReplyAddDTO dto) {
        if (StringUtils.isNotBlank(dto.getFileId())) {
            UserFile userFile =userFileService.saveReplyAttachment((userFileService.getFileInfo(dto.getFileId())));
            dto.setAttachmentFileId(userFile.getId());
        }
    }
    private void verifyPermission(Long discussionId) {
        if (roleService.hasStudentRole()) {
            Discussion discussion=discussionDao.findOne(Discussion.builder().id(discussionId).build());
            if (discussion== null) {
                throw new ParamErrorException();
            }else{
                //评论开关未开启不能操作
                if (Status.NO.getStatus()==discussion.getAllowComment().intValue()) {
                    throw new PermissionException();
                }
                //时间未到或者已过 不能操作
                AssignUser assignUser=assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(),OriginTypeEnum.DISCUSSION.getType(),discussionId);
                if (assignUser != null) {
                    if (assignUser.getStartTime()!=null&&assignUser.getStartTime().after(new Date())) {
                        throw new PermissionException();
                    }
                    if (assignUser.getEndTime()!=null&&assignUser.getEndTime().before(new Date())) {
                        throw new PermissionException();
                    }
                }
            }
        }
    }
    /**
     * @api {post} /discussionReply/modify 讨论回复修改
     * @apiName discussionReplyModify
     * @apiGroup Discussion
     * @apiParam {Number} id 讨论回复ID
     * @apiParam {Number} discussionId 讨论ID
     * @apiParam {String} content 内容
     * @apiParam {Number} [attachmentFileId] 附件Id主键
     * @apiParam {Number} [fileId] 附件Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 回复ID
     */
    @Override
    @ValidationParam(clazz = DiscussionReplyAddDTO.class,groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        DiscussionReplyAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, DiscussionReplyAddDTO.class);
        //校验发评论权限
        verifyPermission(dto.getDiscussionId());
        //附件上传
        saveFile(dto);
        discussionReplyDao.update(dto);

        //学生评论时  新增或更新 cos_user_submit_record
        if (roleService.hasStudentRole()) {
            Discussion discussion = discussionDao.findOne(Discussion.builder().id(dto.getDiscussionId()).build());
            if (discussion!=null) {
                saveOrUpdateUserSubmitRecord(discussion, dto.getStudyGroupId());
            }
        }
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * @api {post} /discussionReply/deletes 讨论回复刪除
     * @apiName discussionReplyDelete
     * @apiGroup Discussion
     * @apiParam {Number[]} ids ID列表
     * @apiParamExample {json} 请求示例：
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 删除ID列表
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        if (ListUtils.isEmpty(idList)) {
            throw new ParamErrorException();
        }
        //校验发评论权限
        DiscussionReply discussionReply=discussionReplyDao.findOne(DiscussionReply.builder().id(idList.get(0)).build());
        verifyPermission(discussionReply.getDiscussionId());

        DiscussionReply reply = DiscussionReply.builder().isDeleted(Status.YES.getStatus()).build();
        discussionReplyDao.updateByExample(reply, idList);
        idList.forEach(id->{
            List<DiscussionReply> children=discussionReplyDao.find(DiscussionReply.builder().discussionReplyId(id).build());
            if (ListUtils.isNotEmpty(children)) {
                List<Long> childrenIdList=children.stream().map(DiscussionReply::getId).collect(Collectors.toList());
                discussionReplyDao.updateByExample(reply, childrenIdList);
            }
        });
        return new LinkedInfo(idList + "");
    }
}
