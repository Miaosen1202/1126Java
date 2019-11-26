package com.wdcloud.lms.business.quiz.vo;

import lombok.Data;

@Data
public class QuizRecordVo {
    private Long quizRecordId;
    private Long quizId;
    private Long userId;
    private boolean hasStudentRole;
    private boolean isNeedAutoRun;
}
