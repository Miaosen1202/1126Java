package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.business.strategy.add.AssignmentAdd;
import com.wdcloud.lms.business.strategy.add.DiscussionAdd;
import com.wdcloud.lms.business.strategy.add.QuizAdd;
import com.wdcloud.lms.business.strategy.update.AssignmentUpdate;
import com.wdcloud.lms.business.strategy.update.DiscussionUpdate;
import com.wdcloud.lms.business.strategy.update.QuizUpdate;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractCourseStrategy {

    @Autowired
    public QuizDao quizDao;
    @Autowired
    public QuizItemDao quizItemDao;
    @Autowired
    public AssignmentDao assignmentDao;
    @Autowired
    public DiscussionDao discussionDao;
    @Autowired
    public ResourceCommonService resourceCommonService;
    @Autowired
    public AssignmentAdd assignmentAdd;
    @Autowired
    public DiscussionAdd discussionAdd;
    @Autowired
    public QuizAdd quizAdd;
    @Autowired
    public AssignmentUpdate assignmentUpdate;
    @Autowired
    public DiscussionUpdate discussionUpdate;
    @Autowired
    public QuizUpdate quizUpdate;

    public OriginTypeEnum support() {
        return OriginTypeEnum.COURSE;
    }
}
