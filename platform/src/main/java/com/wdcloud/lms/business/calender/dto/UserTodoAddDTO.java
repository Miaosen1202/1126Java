package com.wdcloud.lms.business.calender.dto;

import com.wdcloud.lms.core.base.model.UserTodo;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class UserTodoAddDTO extends UserTodo {
    @NotNull(groups = { GroupAdd.class})
    private Date doTime;
    @NotNull(groups = { GroupAdd.class})
    private String title;
    @NotNull(groups = { GroupModify.class})
    private Long id;

    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    @Range(min=1, max=2,groups = {GroupAdd.class,GroupModify.class})
    private Integer calendarType;
}
