package com.wdcloud.lms.exceptions;

import com.wdcloud.base.exception.BaseException;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException() {
        super("resource.not-found");
    }
}
