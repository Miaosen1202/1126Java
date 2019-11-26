package com.wdcloud.lms.business.syllabus.vo;

import com.wdcloud.lms.core.base.model.Module;
import lombok.Data;

import java.util.List;

@Data
public class SyllabusListVO{
    //公共返回
    private Integer syllabusType; //大纲类型:1:任务;2:事件
    private String title;
    private Long limitTime;
    private Integer isBule; //是否标蓝:1:标蓝;0:不标蓝

    //大纲类型为任务
    private Long originId;
    private Integer originType;

    //大纲类型为事件
    private Long eventId; //事件ID
    private Long startTime;

}
