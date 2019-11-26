package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_module_requirement")
public class ModuleRequirement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 单元ID
     */
    @Column(name = "module_id")
    private Long moduleId;

    /**
     * 单元项ID
     */
    @Column(name = "module_item_id")
    private Long moduleItemId;

    /**
     * 1: view, 2: mark done, 3: submit, 4: score least; 5: contribute(参与)
     */
    private Integer strategy;

    /**
     * 至少得分
     */
    @Column(name = "least_score")
    private Integer leastScore;

    /**
     * 顺序
     */
    private Integer seq;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public static final String ID = "id";

    public static final String MODULE_ID = "moduleId";

    public static final String MODULE_ITEM_ID = "moduleItemId";

    public static final String STRATEGY = "strategy";

    public static final String LEAST_SCORE = "leastScore";

    public static final String SEQ = "seq";

    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String UPDATE_USER_ID = "updateUserId";

    private static final long serialVersionUID = 1L;
}