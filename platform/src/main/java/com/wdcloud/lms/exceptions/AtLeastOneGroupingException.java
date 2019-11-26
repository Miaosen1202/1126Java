package com.wdcloud.lms.exceptions;

import com.wdcloud.base.exception.BaseException;

public class AtLeastOneGroupingException extends BaseException {
    public AtLeastOneGroupingException() {
        super("at_least_one_grouping");
    }
}
