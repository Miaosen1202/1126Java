package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_question_match_option")
public class QuestionMatchOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 问题ID
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * 匹配选项，空表示是一个错误匹配项
     */
    @Column(name = "question_option_id")
    private Long questionOptionId;

    /**
     * 排序
     */
    private Integer seq;

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

    public static final String QUESTION_ID = "questionId";

    public static final String QUESTION_OPTION_ID = "questionOptionId";

    public static final String SEQ = "seq";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    private static final long serialVersionUID = 1L;
}