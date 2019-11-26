package com.wdcloud.lms.business.sis.reader;

import com.csvreader.CsvReader;
import com.wdcloud.lms.business.sis.ReaderError;
import com.wdcloud.lms.business.sis.enums.ErrorCodeEnum;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import top.shareall.charset.detector.CharsetDetector;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

@Slf4j
public abstract class AbstractSisCsvReader<T> implements ISisCsvReader<T> {
    protected final static String STATUS_FIELD = "status";
    protected static final String[] DATE_TIME_FORMATS = new String[]{"YYYY-MM-DD HH:mm:ss", "YYYY-MM-DD'T'HH:mm:ss",
            "YYYY-MM-DD HH:mm:ss.SSS", "YYYY-MM-DD HH:mm:ss.SSSZZ"};


    private Path csvFile;
    private Set<String> headerFields = new LinkedHashSet<>();
    private Set<String> headerRequiredFields = new LinkedHashSet<>();

    protected CsvReader reader;
    protected List<String> headers = new ArrayList<>();
    protected List<ReaderError> errors = new ArrayList<>();
    protected List<T> result = new ArrayList<>();

    protected boolean canRead;
    protected boolean initialized;


    /**
     * 子类实现，校验数据内容，错误添加errors中，只返回有效行记录
     * @param recordVos 读取的数据
     * @return 读取到的有效对象集合
     */
    abstract protected List<T> checkRecord(List<RecordVo<T>> recordVos);


    /**
     * 子类实现，map 为读取到的记录行，构建具体实例
     * @param record 读取到的数据
     * @param row 行号
     * @return 需导入对象
     * @throws ReaderException 读取异常
     */
    abstract protected T buildRecord(Map<String, String> record, long row) throws ReaderException;

    /**
     * 获取记录唯一键名称
     * @param record 记录对象
     * @return 键的名称
     */
    abstract protected String getUniqueKey(T record);

    /**
     * 获取记录唯一键值
     * @param record 记录对象
     * @return 键值
     */
    abstract protected String getUniqueKeyVal(T record);

    protected Set<String> getHeaderFields() {
        return headerFields;
    }

    protected boolean isRequiredField(String field) {
        return headerRequiredFields.contains(field);
    }


    protected AbstractSisCsvReader(Path csvFile, Collection<String> headerFields, Collection<String> headerRequiredFields) {
        this.csvFile = csvFile;
        this.headerFields.addAll(headerFields);
        this.headerRequiredFields.addAll(headerRequiredFields);
    }

    @Override
    public List<T> read() {
        if (this.initialized) {
            return this.result;
        }

        init();

        return this.result;
    }

    // 读取表头，并按初始化的 headerFields 来校验表头是否匹配
    protected void readHeader() {
        String[] headers = null;
        try {
            if (reader.readHeaders()) {
                headers = reader.getHeaders();
            }
            if (headers == null) {
                this.errors.add(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, null));
            }
        } catch (IOException e) {
            log.error("[AbstractSisCsvReader] read file header failed, err={}", e.getMessage(), e);
            this.errors.add(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, null));
        }

        // 头部长度是否一直
        if (errors.isEmpty() && !Objects.equals(headers.length, getHeaderFields().size())) {
            this.errors.add(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, 1L));
        }

        // 头部字段是否重复
        if (errors.isEmpty()) {
            Set<String> headerSet = new HashSet<>();
            for (String header : headers) {
                if (headerSet.contains(header)) {
                    this.errors.add(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, 1L, "", header));
                    break;
                } else {
                    headerSet.add(header);
                }
            }
        }

        // 头部字段是否与导入记录头匹配
        if (errors.isEmpty()) {
            for (String header : headers) {
                if (!getHeaderFields().contains(header)) {
                    this.errors.add(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, 1L, "", header));
                    break;
                }
            }
        }

        if (errors.isEmpty()) {
            this.headers.addAll(Arrays.asList(headers));
        } else {
            this.canRead = false;
        }
    }

    // 读取当前行所有字段值到 map 中
    protected Map<String, String> readAllField() throws ReaderException {
        Map<String, String> result = new HashMap<>();
        for (String field : getHeaderFields()) {
            result.put(field, readField(field));
        }
        return result;
    }

    // 按字段名称读取值，并校验必填字段值
    protected String readField(String field) throws ReaderException {
        String val;
        try {
            val = this.reader.get(field);
        } catch (IOException e) {
            log.error("[AbstractSisCsvReader] read field '{}' is failed, err={}", field, e.getMessage(), e);
            throw new ReaderException(new ReaderError(ErrorCodeEnum.UNKNOWN_ERR, getCurrentRow(), field, null));
        }

        if (isRequiredField(field) && StringUtil.isEmpty(val)) {
            throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_VALUE_NULL, getCurrentRow(), field));
        }

        if (Objects.equals(STATUS_FIELD, field)) {
            OperationTypeEnum opType = OperationTypeEnum.typeOf(val);
            if (opType == null || !getSupportOps().contains(opType)) {
                throw new ReaderException(new ReaderError(ErrorCodeEnum.FIELD_FORMAT_ERR, getCurrentRow(), getOpFieldName(), val));
            }
        }
        return val == null ? "" : val;
    }

    // 表中操作行，用于匹配操作类型校验
    protected String getOpFieldName() {
        return STATUS_FIELD;
    }

    // 表数据操作行允许值
    protected Set<OperationTypeEnum> getSupportOps() {
        return Set.of(OperationTypeEnum.ACTIVE, OperationTypeEnum.DELETED);
    }


    private void init() {
        if (this.initialized) {
            return;
        }

        String charset = null;
        try {
            charset = CharsetDetector.detect(csvFile.toFile());
        } catch (IOException e) {
            charset = "UTF-8";
            log.debug("[AbstractSisCsvReader] detect csv file charset error, use utf-8, file={}", csvFile.toFile().getName());
        }
        try {
            this.reader = new CsvReader(new FileReader(csvFile.toFile(), Charset.forName(charset)));
            this.reader.setDelimiter(',');
            readHeader();
            this.canRead = true;
        } catch (IOException e) {
            this.errors.add(new ReaderError(ErrorCodeEnum.FILE_FORMAT_ERR, null));
            log.error("[AbstractSisCsvReader] open file is failed, err={}", e.getMessage(), e);
            this.canRead = false;
        }

        readData();

        this.initialized = true;
    }

    // 读取数据，并校验唯一字段值是否重复，重复字段不添加到结果中
    private void readData() {
        if (this.headers.isEmpty()) {
            return;
        }

        List<RecordVo<T>> recordVos = new ArrayList<>();
        Set<String> uniqueKeyValCache = new HashSet<>();
        try {
            while (this.reader.readRecord()) {
                long currentRecordRow = getCurrentRow();
                try {
                    Map<String, String> allField = readAllField();

                    T record = buildRecord(allField, currentRecordRow);
                    String uniqueKey = getUniqueKey(record);
                    String uniqueKeyVal = getUniqueKeyVal(record);
                    if (uniqueKeyValCache.contains(uniqueKeyVal)) {
                        this.errors.add(new ReaderError(ErrorCodeEnum.FIELD_VALUE_EXISTS, currentRecordRow, uniqueKey, uniqueKeyVal));
                        continue;
                    }
                    uniqueKeyValCache.add(uniqueKeyVal);

                    recordVos.add(new RecordVo<>(record, currentRecordRow));
                } catch (ReaderException e) {
                    this.errors.add(e.getReaderError());
                }
            }
        } catch (IOException e) {
            log.error("[AbstractSisCsvReader] read file is failed, row={}, err={}", getCurrentRow(), e.getMessage(), e);
            this.errors.add(new ReaderError(ErrorCodeEnum.UNKNOWN_FILE, getCurrentRow()));
        } finally {
            destroy();
        }

        if (!recordVos.isEmpty()) {
            List<T> result = checkRecord(recordVos);
            this.result.addAll(result);
        }
    }

    // 获取行号，表头占一行，索引再加一，为最终行号
    private Long getCurrentRow() {
        // 行索引 + 1 + 头部占用一行
        if (this.canRead && this.reader != null) {
            return this.reader.getCurrentRecord() + 2;
        }
        return null;
    }

    public void destroy() {
        if (this.reader != null) {
            this.reader.close();
        }
        this.reader = null;
    }

    public List<ReaderError> getErrors() {
        if (!this.initialized) {
            init();
        }
        return errors;
    }

    public List<String> getHeaders() {
        if (!this.initialized) {
            read();
        }
        return headers;
    }

    public List<T> getResult() {
        if (!this.initialized) {
            read();
        }
        return result;
    }

    // 解析日期
    protected Date parseDate(String date) throws ParseException {
        if (StringUtil.isNotEmpty(date)) {
            Date parseDate = DateUtils.parseDate(date, DATE_TIME_FORMATS);

            return parseDate;
        }
        return null;
    }

    class CsvField {
        private String name;
        private String value;
        private CsvFieldValidator validator;
    }

    interface CsvFieldValidator {
        default boolean validate() {
            return true;
        }
    }

    /**
     * 读取记录实体
     *
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    protected class RecordVo<T> {
        T record;
        long row;
    }
}
