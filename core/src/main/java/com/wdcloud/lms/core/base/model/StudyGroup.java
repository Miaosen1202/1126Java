package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_study_group")
public class StudyGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sis_id")
    private String sisId;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 所属学习小组集ID
     */
    @Column(name = "study_group_set_id")
    private Long studyGroupSetId;

    /**
     * 是否学生组，课程允许学生创建分组时，学生创建分组所属组集
     */
    @Column(name = "is_student_group")
    private Integer isStudentGroup;

    /**
     * 小组名称
     */
    private String name;

    /**
     * 成员数量限制
     */
    @Column(name = "max_member_number")
    private Integer maxMemberNumber;

    /**
     * 组长ID
     */
    @Column(name = "leader_id")
    private Long leaderId;

    /**
     * 学生加入小组类型, 1: 无限制 2: 仅限邀请
     */
    @Column(name = "join_type")
    private Integer joinType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String SIS_ID = "sisId";

    public static final String COURSE_ID = "courseId";

    public static final String STUDY_GROUP_SET_ID = "studyGroupSetId";

    public static final String IS_STUDENT_GROUP = "isStudentGroup";

    public static final String NAME = "name";

    public static final String MAX_MEMBER_NUMBER = "maxMemberNumber";

    public static final String LEADER_ID = "leaderId";

    public static final String JOIN_TYPE = "joinType";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}