package com.wdcloud.lms.business.resources.vo;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ResourceCourseItemDetailVO<T> {

    private T detail;
}
