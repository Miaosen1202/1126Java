package com.wdcloud.lms.exceptions;

import com.wdcloud.base.exception.BaseException;

public class PropValueUnRegistryException extends BaseException {
    public static final String msgKey = "prop.value.unregistered";
    public PropValueUnRegistryException(Object prop, Object value) {
        super(msgKey, String.valueOf(prop), String.valueOf(value));
    }
}
