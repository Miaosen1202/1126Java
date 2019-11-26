package com.wdcloud.lms.core.base.vo;

import lombok.Data;

@Data
public class CalendarItemVo {
    private Integer id;
    private Integer calendarType;//日历类型, 1: 个人 2: 课程 3: 学习小组
    private String  title;
    private String  coverColor;
    private Integer  isCheck;

}
