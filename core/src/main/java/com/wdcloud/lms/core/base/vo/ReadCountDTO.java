package com.wdcloud.lms.core.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCountDTO {
    private Long originId;//公告ID或者讨论ID
    private Long replyTotal= 0L;
    private Long replyNotReadTotal= 0L;
    private Long replyReadTotal= 0L;
}
