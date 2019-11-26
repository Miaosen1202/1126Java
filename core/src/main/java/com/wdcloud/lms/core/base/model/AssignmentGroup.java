package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_assignment_group")
public class AssignmentGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程ID
     */
    @Column(name = "course_id")
    private Long courseId;

    /**
     * 小组名称
     */
    private String name;

    /**
     * 权重
     */
    private BigDecimal weight;

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

    public static final String ID = "id";

    public static final String COURSE_ID = "courseId";

    public static final String NAME = "name";

    public static final String WEIGHT = "weight";

    public static final String SEQ = "seq";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}