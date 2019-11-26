package com.wdcloud.lms.business.sis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /**
     * 不识别文件名称（文件名称不是预期的）
     */
    UNKNOWN_FILE(1),

    /**
     * 文件格式错误（文件数据头不是预期的或文件不是csv格式或打开文件报错）
     */
    FILE_FORMAT_ERR(2),

    /**
     * 字段类型不匹配
     */
    FIELD_TYPE_ERR(3),

    /**
     * 字段值为空（字段值不满足非空约束）
     */
    FIELD_VALUE_NULL(4),

    /**
     * 字段值格式错误（字段值格式解析异常）
     */
    FIELD_FORMAT_ERR(5),

    /**
     * 字段关联值不存在（关联字段在对应关联表中不存在）
     */
    ASSOCIATE_VAL_NOT_FOUND(6),

    /**
     * 字段值重复（唯一约束的字段值重复）
     */
    FIELD_VALUE_EXISTS(7),

    /**
     * 文件未找到（通过fileId没有找到文件）
     */
    FILE_NOT_EXISTS(8),

    /**
     * 未知错误
     */
    UNKNOWN_ERR(9),

    /**
     * 拒绝执行（删除用户所在机构等）
     */
    OP_DENY(10);

    private Integer code;

    public static ErrorCodeEnum codeOf(Integer code) {
        for (ErrorCodeEnum value : values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }

        return UNKNOWN_ERR;
    }
}
