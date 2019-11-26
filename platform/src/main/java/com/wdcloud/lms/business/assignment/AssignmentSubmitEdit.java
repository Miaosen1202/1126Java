package com.wdcloud.lms.business.assignment;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.base.enums.UserParticipateOpEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.assignment.dto.AssignmentSubmitCommentDTO;
import com.wdcloud.lms.business.assignment.dto.AssignmentSubmitDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignmentReplyTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.SubmissionTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_SUBMIT, description = "作业提交")
public class AssignmentSubmitEdit implements IDataEditComponent {
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private AssignmentReplyAttachmentDao assignmentReplyAttachmentDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserParticipateDao userParticipateDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private ModuleCompleteService moduleCompleteService;
    @Autowired
    private AssignmentSubmitCommentDataEdit assignmentSubmitCommentDataEdit;

    /**
     * @api {post} /assignmentSubmit/add 作业提交
     * @apiName assignmentSubmitAdd
     * @apiGroup Assignment
     * @apiParam {Number} assignmentId 课程ID
     * @apiParam {Number=1,2,3,4} replyType 提交方式 1: 文本　2: 文件 3: URL 4: 媒体
     * @apiParam {String} content 提交内容 文件/媒体方式为: 内容为fileId,多个fileId用分号分割 其余方式为文本
     * @apiParam {String} [comment] 评论
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        AssignmentSubmitDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentSubmitDTO.class);
        Assignment assignment = assignmentDao.get(dto.getAssignmentId());
        if (!Objects.equals(SubmissionTypeEnum.ONLINE.getType(), assignment.getSubmissionType())) {
            throw new BaseException(Code.ERROR.name);
        }
        Date now = new Date();
        //写content
        AssignmentReply reply = AssignmentReply
                .builder()
                .courseId(assignment.getCourseId())
                .assignmentId(assignment.getId())
                .content(dto.getContent())
                .replyType(dto.getReplyType())
                .studyGroupId(getStudyGroupId(assignment))
                .submitTime(now)
                .userId(WebContext.getUserId())
                .build();
        assignmentReplyDao.save(reply);

        Course course = courseDao.get(assignment.getCourseId());

        //文件 或媒体
        if (Objects.equals(AssignmentReplyTypeEnum.FILE.getType(), dto.getReplyType())
                || Objects.equals(AssignmentReplyTypeEnum.MATE.getType(), dto.getReplyType())) {
            Iterable<String> fileIds = Splitter.on(",").trimResults().omitEmptyStrings().split(dto.getContent());
            fileIds.forEach(o -> {
                FileInfo file = userFileService.getFileInfo(o);
                if (StringUtil.isNotEmpty(assignment.getFileLimit())
                        && Objects.equals(AssignmentReplyTypeEnum.FILE.getType(), dto.getReplyType())
                        && !assignment.getFileLimit().contains(file.getFileType())) {
                    //合法后缀
                    throw new BaseException("assignment.reply.file.invalid");
                }
//                    保存
                UserFile userFile = userFileService.saveSubmission(file, course);
                AssignmentReplyAttachment attachment = AssignmentReplyAttachment
                        .builder()
                        .assignmentId(assignment.getId())
                        .assignmentReplyId(reply.getId())
                        .fileId(userFile.getId())
                        .build();
                assignmentReplyAttachmentDao.save(attachment);
            });
        }
        AssignmentGroupItem item = assignmentGroupItemDao.findByOriginIdAndType(assignment.getId(), OriginTypeEnum.ASSIGNMENT);

        //写评论
        if (StringUtils.hasText(dto.getComment())) {
            AssignmentSubmitCommentDTO commentDto = new AssignmentSubmitCommentDTO();
            commentDto.setAssignmentId(dto.getAssignmentId());
            commentDto.setComment(dto.getComment());
            commentDto.setCourseId(assignment.getCourseId());
            String beanJson = JSON.toJSONString(commentDto);
            DataEditInfo dataEditInfo2 = new DataEditInfo(beanJson);
            assignmentSubmitCommentDataEdit.add(dataEditInfo2);
        }

        //用户活动列表
        userParticipateDao.save(UserParticipate.builder()
                .userId(WebContext.getUserId())
                .courseId(assignment.getCourseId())
                .originId(assignment.getId())
                .originType(OriginTypeEnum.ASSIGNMENT.getType())
                .operation(UserParticipateOpEnum.SUBMIT.getOp())
                .targetName(assignment.getTitle())
                .build());

        //用户提交记录
        UserSubmitRecord userSubmitRecord = userSubmitRecordDao.findOne(UserSubmitRecord.builder()
                .originId(assignment.getId())
                .originType(OriginTypeEnum.ASSIGNMENT.getType())
                .userId(WebContext.getUserId())
                .build());
        if (userSubmitRecord == null) {
            //判断是否逾期提交
            Date firstSubmitTime = now;
            AssignUser assignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), OriginTypeEnum.ASSIGNMENT.getType(), assignment.getId());
            Date due = assignUser.getLimitTime();
            Boolean isLate = false;
            if (due!=null) {
                isLate = firstSubmitTime.after(due);
            }
            UserSubmitRecord.UserSubmitRecordBuilder recordBuilder = UserSubmitRecord.builder()
                    .courseId(assignment.getCourseId())
                    .assignmentGroupItemId(item.getId())
                    .originId(assignment.getId())
                    .originType(OriginTypeEnum.ASSIGNMENT.getType())
                    .userId(WebContext.getUserId())
                    .needGrade(Status.YES.getStatus())
                    .lastSubmitTime(now)
                    .isOverdue(isLate?1:0);
            Long studyGroupId = getStudyGroupId(assignment);
            if (studyGroupId != null) {
                recordBuilder.studyGroupId(studyGroupId);
            }
            userSubmitRecordDao.save(recordBuilder.build());
        } else {
            userSubmitRecord.setSubmitCount(userSubmitRecord.getSubmitCount() + 1);
            userSubmitRecord.setLastSubmitTime(now);
            userSubmitRecordDao.update(userSubmitRecord);
        }
        //完成进度
        moduleCompleteService.completeAssignment(dto.getAssignmentId(), OriginTypeEnum.ASSIGNMENT.getType());
        return new LinkedInfo(Code.OK.name);
    }

    private Long getStudyGroupId(Assignment assignment) {
        if (assignment.getStudyGroupSetId() != null) {
            //查找小组
            StudyGroupUser groupUser = studyGroupUserDao.findJoined(assignment.getStudyGroupSetId(), WebContext.getUserId());
            if (groupUser != null) {
                return groupUser.getStudyGroupId();
            }
        }
        return null;
    }

}
