package com.wdcloud.lms.core.base.vo.resource;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ResourceVersionVO {

    private Long id;

    /**
     * 资源分享类型
     */
    private Integer shareType;

    /**
     * 版本
     */
    private Date version;

    /**
     * 描述
     */
    private String description;
}
