package com.wdcloud.lms.business.sis;

import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReaderError {
    private ErrorCodeEnum code;
    private Long rowNumber;
    private String fieldName;
    private String fieldValue;

    public ReaderError(ErrorCodeEnum code, Long rowNumber) {
        this(code, rowNumber, null);
    }
    public ReaderError(ErrorCodeEnum code, Long rowNumber, String fieldName) {
        this(code, rowNumber, fieldName, null);
    }
}
