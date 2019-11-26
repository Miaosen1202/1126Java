package com.wdcloud.lms.base.vo;

import lombok.Data;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class GenseeVodHistoryInnerVO {
    private String vodId;
    private String uid;
    private Long startTime;
    private Long leaveTime;
    private String name;
    private Integer duration;
    private String ip;
    private String area;
    private Integer device;
}
