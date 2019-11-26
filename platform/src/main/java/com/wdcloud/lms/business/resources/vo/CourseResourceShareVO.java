package com.wdcloud.lms.business.resources.vo;

import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.DiscussionResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.QuizResourceShareDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResourceShareVO {

    ResourceCourseItemsVO<AssignmentResourceShareDTO> assignments;

    ResourceCourseItemsVO<DiscussionResourceShareDTO> discussions;

    ResourceCourseItemsVO<QuizResourceShareDTO> quizes;
}
