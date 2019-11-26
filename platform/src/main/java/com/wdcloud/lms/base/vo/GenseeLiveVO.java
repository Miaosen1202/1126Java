package com.wdcloud.lms.base.vo;

import com.wdcloud.lms.core.base.model.GenseeLive;
import lombok.Data;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class GenseeLiveVO {
    private String code;
    private String message;
    private String organizerJoinUrl;
    private String panelistJoinUrl;
    private String attendeeJoinUrl;
    private String id;
    private String number;
    private String attendeeAShortJoinUrl;
    private String organizerToken;
    private String panelistToken;
    private String attendeeToken;
}
