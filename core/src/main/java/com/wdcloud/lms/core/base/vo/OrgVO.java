package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Org;
import lombok.Data;

@Data
public class OrgVO extends Org {
    private Long coursesCount;
    private Long subCount;
}
