package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_quiz_question_match_option_record")
public class QuizQuestionMatchOptionRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 匹配类问题选项
     */
    @Column(name = "question_match_option_id")
    private Long questionMatchOptionId;

    /**
     * 问题记录ID
     */
    @Column(name = "quiz_question_record_id")
    private Long quizQuestionRecordId;

    /**
     * 选项ID
     */
    @Column(name = "quiz_question_option_record_id")
    private Long quizQuestionOptionRecordId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 内容
     */
    private String content;

    public static final String ID = "id";

    public static final String QUESTION_MATCH_OPTION_ID = "questionMatchOptionId";

    public static final String QUIZ_QUESTION_RECORD_ID = "quizQuestionRecordId";

    public static final String QUIZ_QUESTION_OPTION_RECORD_ID = "quizQuestionOptionRecordId";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}