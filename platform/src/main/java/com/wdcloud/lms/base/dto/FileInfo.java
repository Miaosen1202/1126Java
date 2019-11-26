package com.wdcloud.lms.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileInfo implements Serializable {
    private Long id;

    private String fileId;

    private String originName;

    private Long fileSize;

    private String fileType;

    private Date createdTime;

    /**
     * 是否转换 0不转换1转换
     */
    private Integer convertNeed;

    /**
     * 转换状态 -1:失败 1:成功
     */
    private Integer convertStatus;

    private String convertType;

    private Date convertTime;

    private String convertResult;

    /**
     * 转换异常
     */
    private String convertErrorMsg;

    private static final long serialVersionUID = 1L;
}