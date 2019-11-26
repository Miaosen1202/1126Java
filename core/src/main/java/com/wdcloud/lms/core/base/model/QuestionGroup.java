package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_question_group")
public class QuestionGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_id")
    private Long quizId;

    /**
     * 组名
     */
    private String name;

    /**
     * 组内每个问题得分
     */
    @Column(name = "each_question_score")
    private Integer eachQuestionScore;

    /**
     * 挑选问题个数
     */
    @Column(name = "pick_question_number")
    private Integer pickQuestionNumber;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String QUIZ_ID = "quizId";

    public static final String NAME = "name";

    public static final String EACH_QUESTION_SCORE = "eachQuestionScore";

    public static final String PICK_QUESTION_NUMBER = "pickQuestionNumber";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}