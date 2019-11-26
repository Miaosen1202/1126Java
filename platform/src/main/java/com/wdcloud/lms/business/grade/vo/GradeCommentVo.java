package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangxutao
 * 评分评论信息
 * */
@Data
public class GradeCommentVo {
    private Long id;
    private Integer originType;
    private Long originId;
    private String content;
    private Date createTime;
    private Long userId;
    private String userName;
    private String nickname;
    private String userFile;
}
