package com.wdcloud.lms.business.resources;

import com.wdcloud.lms.business.resources.enums.ResourceModuleTypeEnum;
import com.wdcloud.lms.business.resources.enums.ResourceLogOperationTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceLog {

    ResourceModuleTypeEnum module ();

    ResourceLogOperationTypeEnum operation();
}
