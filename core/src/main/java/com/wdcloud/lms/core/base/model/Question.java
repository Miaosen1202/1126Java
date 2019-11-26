package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_question")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 问题组ID
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否为问题模板
     */
    @Column(name = "is_template")
    private Integer isTemplate;

    /**
     * 为问题模板时表示题库ID
     */
    @Column(name = "question_bank_id")
    private Long questionBankId;

    /**
     * 问题来源模板
     */
    @Column(name = "question_template_id")
    private Long questionTemplateId;

    /**
     * 分值
     */
    private Integer score;

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

    /**
     * 正确提示
     */
    @Column(name = "correct_comment")
    private String correctComment;

    /**
     * 错误提示
     */
    @Column(name = "wrong_comment")
    private String wrongComment;

    /**
     * 通用提示
     */
    @Column(name = "general_comment")
    private String generalComment;

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String GROUP_ID = "groupId";

    public static final String TYPE = "type";

    public static final String TITLE = "title";

    public static final String IS_TEMPLATE = "isTemplate";

    public static final String QUESTION_BANK_ID = "questionBankId";

    public static final String QUESTION_TEMPLATE_ID = "questionTemplateId";

    public static final String SCORE = "score";

    public static final String SEQ = "seq";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String CONTENT = "content";

    public static final String CORRECT_COMMENT = "correctComment";

    public static final String WRONG_COMMENT = "wrongComment";

    public static final String GENERAL_COMMENT = "generalComment";

    private static final long serialVersionUID = 1L;
}