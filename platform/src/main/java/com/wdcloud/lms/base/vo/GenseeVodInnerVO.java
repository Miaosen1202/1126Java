package com.wdcloud.lms.base.vo;

import lombok.Data;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class GenseeVodInnerVO {
    private String id;
    private String subject;
    private String password;
    private String description;
    private Long createdTime;
    private String attendeeJoinUrl;
    private String webcastId;
    private String screenshot;
    private String creator;
    private String number;
    private String recordId;
    private Long recordStartTime;
    private Long recordEndTime;
    private Integer grType;
    private Long duration;
    private Integer convertResult;
    private String speakerInfo;

}
