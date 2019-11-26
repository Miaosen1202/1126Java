package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.Quiz;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class QuizVO extends Quiz {
    private List<Assign> assigns;
    private AssignUser assignUsers;
    //为了给前端校验时间，返回系统当前时间
     Date serverTime;
}
