package com.wdcloud.lms.base.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class GenseeVodVO {
    private String code;
    private String message;
    private List<GenseeVodInnerVO> list;
}
