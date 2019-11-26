package com.wdcloud.lms.exceptions;

import com.wdcloud.base.exception.BaseException;

public class OperateRejectedException extends BaseException {
    public OperateRejectedException() {
        super("operate.rejected");
    }
}
