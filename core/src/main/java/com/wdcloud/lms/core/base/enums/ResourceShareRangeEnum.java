package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 分享范围枚举
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceShareRangeEnum {

    /**
     * 仅自己
     */
    ONLY_ME(1),

    /**
     * 机构
     */
    INSTITUTE(2),

    /**
     * 公开
     */
    PUBLIC(3);

    private Integer range;

    public static ResourceShareRangeEnum rangeOf(Integer range){
        for(ResourceShareRangeEnum value : values()){
            if(Objects.equals(value.getRange(), range)){
                return value;
            }
        }
        return null;
    }
}
