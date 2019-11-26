package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceOperationTypeEnum {

    /**
     * 编辑
     */
    EDIT(1),

    /**
     * 移除
     */
    REMOVE(2),

    /**
     * 导入
     */
    IMPORT(3),

    /**
     * 更新
     */
    UPDATE(4),

    /**;
     *第一次分享
     */
    FIRST_SHARE(5);

    private Integer type;

    public static ResourceOperationTypeEnum typeOf(Integer type){
        for(ResourceOperationTypeEnum value : values()){
            if(Objects.equals(value.getType(), value)){
                return value;
            }
        }
        return null;
    }
}
