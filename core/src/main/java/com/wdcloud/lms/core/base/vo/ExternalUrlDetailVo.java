package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.ModuleItem;
import lombok.Data;

@Data
public class ExternalUrlDetailVo extends ModuleItem {
    private Long externalUrlId;
    private String pageName;
    private String url;
}
