package com.wdcloud.lms.business.calender.dto;

import com.wdcloud.lms.core.base.model.Event;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EventAddDTO extends Event {
    @NotNull(groups = GroupModify.class)
    private Long id;
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer calendarType;
    /**
     * 标题
     */
    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    private String title;
    //是否复制
    @NotNull(groups = GroupAdd.class)
    private Integer isDuplicate;
    //复制间隔
    private Integer every;
    //复制间隔单位
    private Integer everyUnit;
    //复制副本个数
    private Integer duplicateNum;
    //标题是否追加序号
    private Integer isCount;


}
