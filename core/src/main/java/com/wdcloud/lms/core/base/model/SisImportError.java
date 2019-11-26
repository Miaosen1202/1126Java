package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_sis_import_error")
public class SisImportError implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 导入批次号
     */
    @Column(name = "batch_code")
    private String batchCode;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 错误代码, 1: 不识别文件名称 2: 文件格式错误 3. 字段类型不匹配 4. 字段值为空 5. 字段值格式错误 6. 字段关联值不存在 7. 字段值重复
     */
    @Column(name = "error_code")
    private Integer errorCode;

    /**
     * 错误所在行
     */
    @Column(name = "row_number")
    private Long rowNumber;

    /**
     * 字段名称
     */
    @Column(name = "field_name")
    private String fieldName;

    /**
     * 字段值
     */
    @Column(name = "field_value")
    private String fieldValue;

    /**
     * 导入任务执行用户
     */
    @Column(name = "op_user_id")
    private Long opUserId;

    /**
     * 执行任务用户所在机构 tree_id
     */
    @Column(name = "op_user_org_tree_id")
    private String opUserOrgTreeId;

    @Column(name = "create_time")
    private Date createTime;

    public static final String ID = "id";

    public static final String BATCH_CODE = "batchCode";

    public static final String FILE_NAME = "fileName";

    public static final String ERROR_CODE = "errorCode";

    public static final String ROW_NUMBER = "rowNumber";

    public static final String FIELD_NAME = "fieldName";

    public static final String FIELD_VALUE = "fieldValue";

    public static final String OP_USER_ID = "opUserId";

    public static final String OP_USER_ORG_TREE_ID = "opUserOrgTreeId";

    public static final String CREATE_TIME = "createTime";

    private static final long serialVersionUID = 1L;
}