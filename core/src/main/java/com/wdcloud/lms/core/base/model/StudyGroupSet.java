package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_study_group_set")
public class StudyGroupSet implements Serializable {
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
     * 名称
     */
    private String name;

    /**
     * 是否允许学生自行加入
     */
    @Column(name = "allow_self_signup")
    private Integer allowSelfSignup;

    /**
     * 是否是班级小组（小组学生需要在相同班级）
     */
    @Column(name = "is_section_group")
    private Integer isSectionGroup;

    /**
     * 组长设置策略，1: 手动指定； 2: 第一个加入学生； 3: 随机设置
     */
    @Column(name = "leader_set_strategy")
    private Integer leaderSetStrategy;

    /**
     * 组成员限制
     */
    @Column(name = "group_member_number")
    private Integer groupMemberNumber;

    /**
     * 是否学生组，课程允许学生创建分组时，学生创建分组所属组集
     */
    @Column(name = "is_student_group")
    private Integer isStudentGroup;

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

    public static final String NAME = "name";

    public static final String ALLOW_SELF_SIGNUP = "allowSelfSignup";

    public static final String IS_SECTION_GROUP = "isSectionGroup";

    public static final String LEADER_SET_STRATEGY = "leaderSetStrategy";

    public static final String GROUP_MEMBER_NUMBER = "groupMemberNumber";

    public static final String IS_STUDENT_GROUP = "isStudentGroup";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}