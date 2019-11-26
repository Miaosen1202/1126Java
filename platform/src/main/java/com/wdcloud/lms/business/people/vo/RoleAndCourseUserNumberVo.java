package com.wdcloud.lms.business.people.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleAndCourseUserNumberVo {
    private Long roleId;
    private String roleName;
    private int courseUserNumber;
}
