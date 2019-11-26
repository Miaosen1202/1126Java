package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_quiz_question_option_record")
public class QuizQuestionOptionRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 问题记录ID
     */
    @Column(name = "quiz_question_record_id")
    private Long quizQuestionRecordId;

    /**
     * 问题选项ID
     */
    @Column(name = "question_option_id")
    private Long questionOptionId;

    /**
     * 题干中占位符
     */
    private String code;

    /**
     * 选择题：是否为正确选项
     */
    @Column(name = "is_correct")
    private Integer isCorrect;

    /**
     * 排序：如果是重排序选项，则是重排序后的选项顺序
     */
    private Integer seq;

    /**
     * 是否选中
     */
    @Column(name = "is_choice")
    private Integer isChoice;

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

    /**
     * 选择该项的提示
     */
    private String explanation;

    public static final String ID = "id";

    public static final String QUIZ_QUESTION_RECORD_ID = "quizQuestionRecordId";

    public static final String QUESTION_OPTION_ID = "questionOptionId";

    public static final String CODE = "code";

    public static final String IS_CORRECT = "isCorrect";

    public static final String SEQ = "seq";

    public static final String IS_CHOICE = "isChoice";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    public static final String EXPLANATION = "explanation";

    private static final long serialVersionUID = 1L;
}