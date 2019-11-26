package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceShareSwitchTypeEnum {

    /**
     * 管理员创建的课程可被分享
     */
    ADMIN_CREATE(1),

    /**
     * 教师创建的课程可被分享
     */
    TEACHER_CREATE(2),

    /**
     * 无限制
     */
    NONE(3);

    private Integer type;

    public static ResourceShareSwitchTypeEnum typeOf(Integer type){
        for(ResourceShareSwitchTypeEnum value : values()){
            if(Objects.equals(value.getType(), type)){
                return value;
            }
        }
        return null;
    }
}
