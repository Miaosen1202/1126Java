package com.wdcloud.lms.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CKFileInfo extends FileInfo {

    private Integer uploaded;
    private String url;
}