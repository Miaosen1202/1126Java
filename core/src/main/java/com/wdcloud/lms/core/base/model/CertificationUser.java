package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_certification_user")
public class CertificationUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "certification_id")
    private Long certificationId;

    /**
     * 用户证书状态：0:Assigned 1:PendingApproval 2:Certified 3:Expired 4:Unenrolled
     */
    private Integer status;

    /**
     * 最近注册时间
     */
    @Column(name = "enroll_time")
    private Date enrollTime;

    /**
     * 最近一次证书标记完成时间
     */
    @Column(name = "complete_time")
    private Date completeTime;

    /**
     * 附件Id(外部认证用户上传的证书)
     */
    @Column(name = "proof_file_id")
    private Long proofFileId;

    /**
     * 外部证书上传时间
     */
    @Column(name = "upload_time")
    private Date uploadTime;

    /**
     * 最近一次注销时间
     */
    @Column(name = "unenrolll_time")
    private Date unenrolllTime;

    /**
     * 最近一次拒绝时间
     */
    @Column(name = "reject_time")
    private Date rejectTime;

    /**
     * 最近一次绝句原因
     */
    @Column(name = "reject_desc")
    private String rejectDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    @Column(name = "expire_time")
    private Date expireTime;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String CERTIFICATION_ID = "certificationId";

    public static final String STATUS = "status";

    public static final String ENROLL_TIME = "enrollTime";

    public static final String COMPLETE_TIME = "completeTime";

    public static final String PROOF_FILE_ID = "proofFileId";

    public static final String UPLOAD_TIME = "uploadTime";

    public static final String UNENROLLL_TIME = "unenrolllTime";

    public static final String REJECT_TIME = "rejectTime";

    public static final String REJECT_DESC = "rejectDesc";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String EXPIRE_TIME = "expireTime";

    private static final long serialVersionUID = 1L;
}