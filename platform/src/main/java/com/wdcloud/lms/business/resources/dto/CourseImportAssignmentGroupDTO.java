package com.wdcloud.lms.business.resources.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class CourseImportAssignmentGroupDTO {

    private Long courseId;

    private Long assignmentGroupId;

    private Long newId;
}
