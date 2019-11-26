package com.wdcloud.lms.business.resources.vo;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ResourceCourseItemsVO<T> {

    public List<T> items;

    public Integer count;

    public ResourceCourseItemsVO(Integer count) {
        this.count = count;
    }
}
