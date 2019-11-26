package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CourseFavoriteVo extends Course {
    private String courseAlias;
    private String coverColor;

    private String coverFileUrl;

    private Integer seq;

    private Integer isActiveToUser;

    private Integer statusToUser;

    private Term term;

    private CourseFavoriteExt favoriteExt;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class CourseFavoriteExt {
        private int assignmentTodoNumber;
        private int discussionTodoNumber;
        private int quizTodoNumber;
    }
}