package com.wdcloud.lms.business.resources.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class SingleQuizAddDTO {

    private Long quizId;

    private List<CourseImportGenerationDTO> courseImportGenerationDTOS;
}
