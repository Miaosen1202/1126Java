package com.wdcloud.lms.business.message.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageRecReadDTO {

    private List<Long> messageIds;
    private Integer status;
}
