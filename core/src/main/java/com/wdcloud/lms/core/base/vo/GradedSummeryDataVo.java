package com.wdcloud.lms.core.base.vo;



import lombok.Data;

@Data
public class GradedSummeryDataVo {
    private Long   userId;//
    private String pointsPossible;//满分分值
    private String averageScore;//平均分
    private String highScore;//最高分
    private String lowScore;//最低分
    private String totalStu;//统计学生数量
}
