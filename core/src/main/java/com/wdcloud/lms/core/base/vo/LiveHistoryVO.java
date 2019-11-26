package com.wdcloud.lms.core.base.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class LiveHistoryVO {
    private Long id;
    private String fullName;
    private String username;
    private Date joinTime;
    private Date leaveTime;
    private Long length;
    private Long historyId;
    private Integer status;
    private Integer reviewTimes;
}
