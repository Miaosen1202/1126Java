package com.wdcloud.lms.core.base.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_dictionary_value")
public class SysDictionaryValue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dictionary_id")
    private Long dictionaryId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 区域
     */
    private String locale;

    public static final String ID = "id";

    public static final String DICTIONARY_ID = "dictionaryId";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String LOCALE = "locale";

    private static final long serialVersionUID = 1L;
}