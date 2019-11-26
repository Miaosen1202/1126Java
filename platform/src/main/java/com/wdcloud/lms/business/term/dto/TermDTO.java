package com.wdcloud.lms.business.term.dto;//

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TermDTO {
    @NotNull(groups = GroupModify.class)
    private Long id;//学期名称
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    @Length(max = 50, groups = {GroupAdd.class, GroupModify.class})
    private String name;//学期名称
    private String sisId;// 学期SIS ID
    private Date termStartTime;// 学期开始时间(使用毫秒值）
    private Date termEndTime;// 学期截止时间(使用毫秒值）
    private Date studentStartTime;// 学生访问生效时间(使用毫秒值）
    private Date studentEndTime;// 学生访问截止时间(使用毫秒值）
    private Date teacherStartTime;// 教师访问生效时间(使用毫秒值）
    private Date teacherEndTime;// 教师访问截止时间(使用毫秒值）
    private Date taStartTime;// 助教访问生效时间(使用毫秒值）
    private Date taEndTime;// 助教访问截止时间(使用毫秒值）
}
