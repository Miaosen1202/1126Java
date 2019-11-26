package com.wdcloud.lms.core.base.vo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogVo {
    private Long id;
    private String content;
    private Long attachmentFileId;
    private Date createTime;

    private Integer type;

    private Long userId;
    private String username;

    private Long discussionId;
    private String discussionTitle;
    private Long discussionReplyId;

    private Long announceId;
    private Long announceReplyId;
    private String announceTitle;

    private Long assignmentGroupItemId;
    private String assignmentGroupItemTitle;
    private Integer assignmentGroupItemTotalScore;
    private Integer assignmentGroupItemGradeScore;

    @Getter
    @AllArgsConstructor
    public enum DialogTypeEnum {
        DISCUSSION(1),
        GRADE_TASK(2);

        private Integer type;
    }
}
