package com.wdcloud.lms.api.dto;

import com.google.common.collect.Lists;
import com.wdcloud.lms.core.base.model.Assign;
import lombok.Data;

import java.util.List;

@Data
public class AssignIsAllDTO {
    private List<Assign> assign = Lists.newArrayList();
    private String courseId;

}
