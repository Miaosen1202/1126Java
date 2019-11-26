package com.wdcloud.lms.exceptions;

import com.wdcloud.base.exception.BaseException;

public class PermissionException extends BaseException {
    private static String msgCode = "operate.permission.denied";

    public PermissionException() {
        super(msgCode);
    }
}
