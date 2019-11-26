package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author WangYaRong
 */
@Getter
@AllArgsConstructor
public enum ResourceLicenseTypeEnum {

    PUBLIC_DOMAIN(1),

    CC_ATTRIBUTION(2),

    CC_ATTRIBUTION_SHAREALIKE(3),

    CC_ATTRIBUTION_NODERIVS(4),

    CC_ATTRIBUTION_NONCOMMERCIAL(5),

    CC_ATTRIBUTION_NONCOMMERCIAL_SHAREALIKE(6),

    CC_ATTRIBUTION_NONCOMMERCIAL_NODERIVS(7);

    private Integer type;

    public static ResourceLicenseTypeEnum typeOf(Integer typpe){
        for(ResourceLicenseTypeEnum value : values()){
            if(Objects.equals(value.getType(), value)){
                return value;
            }
        }
        return null;
    }
}
