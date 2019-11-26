package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.business.grade.vo.OriginInfoVo;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 获取作业、讨论、测验的基础信息
 *
 * @author zhangxutao
 * @Date 05-16
 */
@Slf4j
@Service
public class OriginInfoService {
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private QuizDao quizDao;

    /**
     * 获取作业、讨论、测验的基础信息
     *
     * @param originId
     * @return
     */
    public OriginInfoVo getOriginInfo(Long originId, Integer originType) {
        OriginInfoVo model = new OriginInfoVo();
        if (originType.equals(OriginTypeEnum.ASSIGNMENT.getType())) {
            Assignment assignment = assignmentDao.get(originId);
            model.setStudyGroupSetId(assignment.getStudyGroupSetId());
            model.setCourseId(assignment.getCourseId());
        }
        if (originType.equals(OriginTypeEnum.DISCUSSION.getType())) {
            Discussion discussion = discussionDao.get(originId);
            model.setStudyGroupSetId(discussion.getStudyGroupSetId());
            model.setCourseId(discussion.getCourseId());
        }
        if (originType.equals(OriginTypeEnum.QUIZ.getType())) {
            Quiz quiz = quizDao.get(originId);
            model.setCourseId(quiz.getCourseId());
        }
        return model;

    }
}
