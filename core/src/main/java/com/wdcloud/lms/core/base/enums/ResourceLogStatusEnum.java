package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceLogStatusEnum {

    /**
     * 正在进行
     */
    PROCESSING(1),

    /**
     * 完成
     */
    DONE(2),

    /**
     * 失败
     */
    FAILED(3);

    private Integer status;

    public static ResourceLogStatusEnum statusOf(Integer status){
        for(ResourceLogStatusEnum value : values()){
            if(Objects.equals(value.getStatus(), value)){
                return value;
            }
        }
        return null;
    }
}
