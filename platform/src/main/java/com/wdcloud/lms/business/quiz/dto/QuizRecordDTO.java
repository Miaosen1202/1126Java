package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@Data
public class QuizRecordDTO extends QuizRecord {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 测验ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizId;

    /**
     * 提交时测验版本
     */
    private Integer quizVersion;

    /**
     * 是否确认提交
     */
    private Integer isSubmit;

    /**
     * 是否最后一次提交（最后一次提交后不再允许进行提交）
     */
    private Integer isLastTime;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 提交时间
     */
     private Date submitTime;

    /**
     * 提交截至时间，开始测验时根据测验的时间限制计算
     */
    private Date dueTime;

    /**
     * 测验人ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long testerId;

 }