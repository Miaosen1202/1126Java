package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.UserTodo;
import lombok.Data;

@Data
public class UserTodoVo extends UserTodo {
    private String userName;
    private String courseName;
}
