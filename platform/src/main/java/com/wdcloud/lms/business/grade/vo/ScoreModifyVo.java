package com.wdcloud.lms.business.grade.vo;

import lombok.Data;

/**
 * @author zhangxutao
 */
@Data
public class ScoreModifyVo {
    private Long studyGroupId;
    private Long originId;
    private Long recordOriginId;
    private Long userId;
    private String score;
    /**
     * //题号等分  “112_3,113_4”
     */
    private String topicScore;
}