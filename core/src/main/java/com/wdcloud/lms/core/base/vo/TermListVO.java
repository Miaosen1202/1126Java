package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.core.base.model.TermConfig;
import lombok.Data;

import java.util.List;

@Data
public class TermListVO extends Term {
    private List<TermConfig> termConfigs;
    private Long coursesCount;
}
