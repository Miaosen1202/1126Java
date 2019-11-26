package com.wdcloud.lms.business.calender.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CalendarItemDTO {
    @NotNull
    private Integer id;
    @NotNull
    @Range(min=1,max=3)
    private Integer calendarType;//日历类型, 1: 个人 2: 课程 3: 学习小组
    @NotBlank
    private String  coverColor;
}
