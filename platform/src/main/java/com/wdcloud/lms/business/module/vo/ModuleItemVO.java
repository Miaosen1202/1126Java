package com.wdcloud.lms.business.module.vo;

import com.google.common.collect.Lists;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.ModuleItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemVO extends ModuleItem {
    private Integer leastScore;
    private Integer strategy;
    private List<Assign> assigns = Lists.newArrayList();
    private Integer completeStatus;
    private Date limitTime;
    private String url;
}
