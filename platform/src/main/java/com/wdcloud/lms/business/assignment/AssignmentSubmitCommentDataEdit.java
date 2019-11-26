package com.wdcloud.lms.business.assignment;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.assignment.dto.AssignmentSubmitCommentDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_SUBMIT_COMMENT)
public class AssignmentSubmitCommentDataEdit implements IDataEditComponent {

    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    /**
     * @api {get} /assignmentSubmitComment/add 作业提交详情页-添加评论
     * @apiName assignmentSubmitCommentAdd
     * @apiGroup Assignment
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} assignmentId 作业ID
     * @apiParam {Number} comment 评论体
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     */
    @Override
    @AccessLimit
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        AssignmentSubmitCommentDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentSubmitCommentDTO.class);
        Assignment assignment = assignmentDao.get(dto.getAssignmentId());
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(dto.getAssignmentId(), OriginTypeEnum.ASSIGNMENT);

        Long studyGroupSetId = assignment.getStudyGroupSetId();
        if (null == studyGroupSetId) {
            GradeComment comment = GradeComment
                    .builder()
                    .assignmentGroupItemId(assignmentGroupItem.getId())
                    .originId(dto.getAssignmentId())
                    .originType(OriginTypeEnum.ASSIGNMENT.getType())
                    .userId(WebContext.getUserId())
                    .content(dto.getComment())
                    .courseId(dto.getCourseId())
                    .isSendGroupUser(Status.YES.getStatus())//发送到小组
                    .build();
            gradeCommentDao.save(comment);
        } else {
            //0、参数解析
            Long courseId = dto.getCourseId();
            Long userId = WebContext.getUserId();
            //1、获取userIds
            List<Long> userIds = Lists.newArrayList();
            List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                    .studyGroupSetId(studyGroupSetId)
                    .userId(userId)
                    .courseId(courseId)
                    .build());
            List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
            if (ss.size() > 0) {
                List<StudyGroupUser> studyGroupUsers1 = studyGroupUserDao.find(StudyGroupUser.builder()
                        .studyGroupId(ss.get(0))
                        .build());
                List<Long> ss2 = studyGroupUsers1.stream().map(StudyGroupUser::getUserId).collect(Collectors.toList());
                userIds.addAll(ss2);
            }else{
                userIds.add(userId);//没有在小组中
            }
            //2、获取gradeComments
            List<GradeComment> list = Lists.newArrayList();
            for (Long x: userIds) {
                GradeComment comment = GradeComment
                        .builder()
                        .assignmentGroupItemId(assignmentGroupItem.getId())
                        .originId(dto.getAssignmentId())
                        .originType(OriginTypeEnum.ASSIGNMENT.getType())
                        .userId(x)
                        .content(dto.getComment())
                        .courseId(dto.getCourseId())
                        .isSendGroupUser(Status.YES.getStatus())//发送到小组
                        .isDeleted(0)
                        .createUserId(userId)
                        .updateUserId(userId)
                        .build();
                if (ss.size() > 0) {
                    comment.setStudyGroupId(ss.get(0));
                }
                list.add(comment);
            }
            //3、批量插入gradeComments
            gradeCommentDao.batchInsert(list);
        }
        return new LinkedInfo(Code.OK.name);
    }
}
