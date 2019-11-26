package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_calendar_user_check")
public class CalendarUserCheck implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 日历类型, 1: 个人 2: 课程 3: 学习小组
     */
    @Column(name = "calendar_type")
    private Integer calendarType;

    /**
     * 选中ID
     */
    @Column(name = "check_id")
    private Long checkId;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String CALENDAR_TYPE = "calendarType";

    public static final String CHECK_ID = "checkId";

    private static final long serialVersionUID = 1L;
}