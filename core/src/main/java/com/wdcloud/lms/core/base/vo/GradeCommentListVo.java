package com.wdcloud.lms.core.base.vo;
import lombok.Data;

import java.util.Date;

/**
 * @author zxt
 * Des:评分评论信息
 */
@Data
public class GradeCommentListVo {
    private Long id;
    private Integer originType;
    private Long originId;
    private String content;
    private Date createTime;
    private Long userId;
    private String userName;
    private String nickname;
    private String fullName;
    private String userFile;
    private Long studyGroupId;
    private Long assignmentGroupItemId;

}
