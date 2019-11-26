package com.wdcloud.lms.business.message.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageStarDTO {
    private List<Long> subjectIds;
    private Integer status;
}
