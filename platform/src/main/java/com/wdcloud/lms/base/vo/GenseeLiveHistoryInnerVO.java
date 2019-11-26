package com.wdcloud.lms.base.vo;

import lombok.Data;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class GenseeLiveHistoryInnerVO {
    private String nickname;
    private Long joinTime;
    private Long leaveTime;
    private String ip;
    private Long uid;
    private String area;
    private String mobile;
    private String company;
    private Long joinType;
}
