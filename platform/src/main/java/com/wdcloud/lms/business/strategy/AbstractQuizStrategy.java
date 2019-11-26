package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.business.quiz.QuestionEdit;
import com.wdcloud.lms.business.quiz.QuizEdit;
import com.wdcloud.lms.business.quiz.QuizInforQuery;
import com.wdcloud.lms.business.quiz.QuizItemQuery;
import com.wdcloud.lms.business.strategy.add.QuizAdd;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractQuizStrategy {
    @Autowired
    public QuizDao quizDao;
    @Autowired
    public QuizRecordDao quizRecordDao;
    @Autowired
    public AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    public AssignDao assignDao;
    @Autowired
    public AssignUserDao assignUserDao;
    @Autowired
    public UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    public QuizEdit quizEdit;
    @Autowired
    public QuizInforQuery quizInforQuery;
    @Autowired
    public ModuleItemDao moduleItemDao;
    @Autowired
    public QuizItemDao quizItemDao;
    @Autowired
    public ResourceCommonService resourceCommonService;
    @Autowired
    public AssignmentGroupItemChangeRecordDao assignmentGroupItemChangeRecordDao;
    @Autowired
    public QuestionEdit questionEdit;
    @Autowired
    public QuizAdd quizAdd;
    @Autowired
    public ResourceImportGenerationDao resourceImportGenerationDao;
    @Autowired
    public QuestionDao questionDao;
    @Autowired
    public QuestionOptionDao questionOptionDao;

    public OriginTypeEnum support() {
        return OriginTypeEnum.QUIZ;
    }
}
