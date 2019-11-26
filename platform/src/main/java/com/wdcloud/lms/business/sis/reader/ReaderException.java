package com.wdcloud.lms.business.sis.reader;

import com.wdcloud.lms.business.sis.ReaderError;
import lombok.Getter;

@Getter
public class ReaderException extends RuntimeException {

    private ReaderError readerError;

    public ReaderException(ReaderError error) {
        super();
        this.readerError = error;
    }
}
