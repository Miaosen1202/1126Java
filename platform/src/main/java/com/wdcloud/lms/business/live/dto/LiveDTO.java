package com.wdcloud.lms.business.live.dto;

import com.google.common.collect.Lists;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupDelete;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class LiveDTO {
    @NotNull(groups = {GroupDelete.class, GroupModify.class})
    private Long id;

    /**
     * 课程ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long courseId;

    /**
     * 标题
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 主讲人ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long instructor;

    /**
     * 直播开始时间
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long startTime;

    /**
     * 分配
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private List<Assign> assign = Lists.newArrayList();
}
