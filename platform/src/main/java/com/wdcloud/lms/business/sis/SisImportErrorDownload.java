package com.wdcloud.lms.business.sis;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.core.base.dao.SisImportErrorDao;
import com.wdcloud.lms.core.base.model.SisImportError;
import com.wdcloud.server.frame.interfaces.IDirectComponent;
import com.wdcloud.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Slf4j
@IDirectComponent.DirectHandler(
        resource = Constants.RESOURCE_TYPE_SIS_IMPORT_ERROR,
        function = Constants.FUNCTION_TYPE_DOWNLOAD
)
public class SisImportErrorDownload implements IDirectComponent {

    @Autowired
    private SisImportErrorDao sisImportErrorDao;

    /**
     * @api {get} /sisImport/error/download/direct
     * @apiName SisImportErrorDownload
     * @apiGroup SIS
     *
     * @apiParam {String} batchCode 批次号
     *
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        String batchCode = request.getParameter(Constants.PARAM_BATCH_CODE);

        if (StringUtil.isEmpty(batchCode)) {
            return;
        }

        List<SisImportError> errors = sisImportErrorDao.find(SisImportError.builder().batchCode(batchCode).build());
        StringBuilder sb = new StringBuilder();
        for (SisImportError error : errors) {
            sb.append(error.getFileName())
                    .append(" - ")
                    .append(getErrorMsg(error))
                    .append("\r\n");
        }

        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new BufferedOutputStream(response.getOutputStream()));
            writer.write(sb.toString());
        } catch (Exception e) {
            log.error("[SisImportErrorDownload] write file error, {}", ExceptionUtils.getThrowableCount(e), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private String getErrorMsg(SisImportError readerError) {
        String errMsg;
        ErrorCodeEnum code = ErrorCodeEnum.codeOf(readerError.getErrorCode());
        switch (code) {
            case UNKNOWN_FILE:
            case FILE_FORMAT_ERR:
            case FIELD_TYPE_ERR:
                errMsg = "Skipping unknown file type";
                break;
            case FIELD_VALUE_NULL:
                errMsg = readerError.getFieldName() + " value is empty";
                break;
            case FIELD_FORMAT_ERR:
                errMsg = readerError.getFieldName() + " '" + readerError.getFieldValue() + "' format is wrong";
                break;
            case ASSOCIATE_VAL_NOT_FOUND:
                errMsg = "Could not find " + readerError.getFieldName() + " " + readerError.getFieldValue();
                break;
            case FIELD_VALUE_EXISTS:
                errMsg = readerError.getFieldName() + " " + readerError.getFieldValue() + " is exists";
                break;
            case FILE_NOT_EXISTS:
                errMsg = readerError.getFieldName() + " could not found";
                break;
            case UNKNOWN_ERR:
            default:
                errMsg = "Other error";
        }
        return errMsg;
    }
}
