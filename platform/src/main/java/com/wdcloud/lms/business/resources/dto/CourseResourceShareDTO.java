package com.wdcloud.lms.business.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResourceShareDTO {

    List<AssignmentResourceShareDTO> assignments;

    List<DiscussionResourceShareDTO> discussions;

    List<QuizResourceShareDTO> quizzes;
}
