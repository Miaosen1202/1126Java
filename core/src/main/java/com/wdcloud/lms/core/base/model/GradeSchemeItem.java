package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cos_grade_scheme_item")
public class GradeSchemeItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 方案ID
     */
    @Column(name = "grade_scheme_id")
    private Long gradeSchemeId;

    /**
     * Code
     */
    private String code;

    /**
     * 百分比下限，最小值0
     */
    @Column(name = "range_start")
    private BigDecimal rangeStart;

    /**
     * 百分比上限，最大值100
     */
    @Column(name = "range_end")
    private BigDecimal rangeEnd;

    public static final String ID = "id";

    public static final String GRADE_SCHEME_ID = "gradeSchemeId";

    public static final String CODE = "code";

    public static final String RANGE_START = "rangeStart";

    public static final String RANGE_END = "rangeEnd";

    private static final long serialVersionUID = 1L;
}