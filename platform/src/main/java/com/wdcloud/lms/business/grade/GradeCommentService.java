package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.grade.enums.ReleaseTypeEnum;
import com.wdcloud.lms.business.grade.vo.GradeEditVo;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公共的评分评论提交方法
 *
 * @author  zhangxutao
 * @Date 05-16
 */
@Slf4j
@Service
public class GradeCommentService {
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private GradeCommentDao gradeCommentDao;


    /**
     * 添加评论-方法
     * @param gradeCommentEditVo
     * @param userId
     */
    public void addGradeCommentInfo(GradeEditVo gradeCommentEditVo, Long userId) {
        Long groupId = null;
        Long studyGroupSetId = null;
        if (gradeCommentEditVo.getReleaseType().equals(ReleaseTypeEnum.GROUP.getValue())) {
            if (OriginTypeEnum.ASSIGNMENT.getType().equals(gradeCommentEditVo.getOriginType())) {
                Assignment assignment = assignmentDao.get(gradeCommentEditVo.getOriginId());
                studyGroupSetId = assignment.getStudyGroupSetId();
            }
            if (OriginTypeEnum.DISCUSSION.getType().equals(gradeCommentEditVo.getOriginType())) {
                Discussion discussion = discussionDao.get(gradeCommentEditVo.getOriginId());
                studyGroupSetId = discussion.getStudyGroupSetId();
            }
            if (!OriginTypeEnum.QUIZ.getType().equals(gradeCommentEditVo.getOriginType())) {
                StudyGroupUser studyGroupUser = studyGroupUserDao.findOne(StudyGroupUser.builder()
                        .courseId(gradeCommentEditVo.getCourseId())
                        .userId(userId)
                        .studyGroupSetId(studyGroupSetId)
                        .build());
                if (studyGroupUser != null) {
                    groupId = studyGroupUser.getStudyGroupId();
                }
            }
        }
        GradeComment gradeComment = GradeComment.builder()
                .courseId(gradeCommentEditVo.getCourseId())
                .assignmentGroupItemId(Long.parseLong(gradeCommentEditVo.getAssignmentGroupItemId()))
                .originType(gradeCommentEditVo.getOriginType())
                .originId(gradeCommentEditVo.getOriginId())
                .content(gradeCommentEditVo.getContent())
                .studyGroupId(groupId)
                .userId(userId)
                .isSendGroupUser(gradeCommentEditVo.getIsSendGroupUser())
                .createUserId(WebContext.getUserId())
                .updateUserId(WebContext.getUserId())
                .build();
        if (gradeComment.getContent() != null && gradeComment.getContent() != "") {
            gradeCommentDao.save(gradeComment);
        }
    }
}
