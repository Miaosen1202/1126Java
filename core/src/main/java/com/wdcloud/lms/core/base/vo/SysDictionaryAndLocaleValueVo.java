package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.SysDictionary;
import lombok.Data;

@Data
public class SysDictionaryAndLocaleValueVo extends SysDictionary {
    private String locale;
    private String localeName;
}
