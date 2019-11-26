package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.CourseNav;
import com.wdcloud.lms.core.base.model.Resource;
import lombok.Data;

@Data
public class CourseNavVO extends CourseNav {
    /**
     * 作者
     */
    private String defaultName;

    /**
     * 缩略图
     */
    private String localeName;

}
