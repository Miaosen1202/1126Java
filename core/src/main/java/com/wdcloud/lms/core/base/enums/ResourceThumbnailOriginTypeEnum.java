package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 缩略图来源类型
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceThumbnailOriginTypeEnum {

    /**
     * 课程
     */
    COURSE(1),

    /**
     * 作业
     */
    ASSIGNMENT(2),

    /**
     * 测验
     */
    QUIZ(3),

    /**
     * 讨论
     */
    DISCUSSION(4),

    /**
     * 封面
     */
    COVER(5);

    private Integer type;

    public static ResourceThumbnailOriginTypeEnum typeOf(Integer type){
        for(ResourceThumbnailOriginTypeEnum value : values()){
            if(Objects.equals(value.getType(), type)){
                return value;
            }
        }
        return null;
    }
}
