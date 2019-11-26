package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 测验正误和答案开放状态
 *
 * @author 黄建林
 */
@Getter
@AllArgsConstructor
public enum QuizeAnswerStatusEnum {

    /**
     * 表示允许看正误和看答案
     */
    PERMIT_ALL(1),

    /**
     * 教师设置不允许看正误,就不能看答案
     */
    TEACHER_SETTING_NOT_PERMIT(2),
    /**
     * 设置每次提交答案后才能看看正误，但还没提交答案
     */
    NO_ANSWER_SUBMIT(3),
    /**
     * 允许看正误，但教师设置不能看答案
     */
    PERMIT_RESULT_TEACHER_SETTING_NOT_ANSWER(4),
    /**
     * 允许看正误，但还未达到看答案的时间
     */
    PERMIT_RESULT_BEFORE_ANSWER_SETTING_TIME(5),
    /**
     * 允许看正误，但当前时间超出了看答案的时间
     */
    PERMIT_RESULT_AFTER_ANSWER_SETTING_TIME(6),
    /**
     * 允许看正误，但答案设置了最后一次提交才能看，当前不是最后一次提交
     */
    PERMIT_RESULT_NOT_LAST_SUBMIT(7),

    /**
     * 不允许看正误，设置了最后一次提交才能看正误，但当前不是最后一次提交,答案教师设置不允许看
     */
   REPLY_NOT_LAST_SUBMIT_ANSWER_TEACHE_NOT_SETTING(8),
    /**
     * 不允许看正误，设置了最后一次提交才能看正误，但当前不是最后一次提交,答案教师设置允许看,但还未达到看答案开始时间
     */
    REPLY_NOT_LAST_SUBMIT_BEFORE_ANSWER_SETTING_TIME(9),
    /**
     * 不允许看正误，设置了最后一次提交才能看正误，但当前不是最后一次提交,答案教师设置允许看,但已超出了看答案开始时间
     */
    REPLY_NOT_LAST_SUBMIT_AFTER_ANSWER_SETTING_TIME(10),
    /**
     * 不允许看正误，设置了最后一次提交才能看正误，但当前不是最后一次提交,答案教师设置允许看,但当前不是最后一次提交
     */
    REPLY_NOT_LAST_SUBMIT_ANSWER_NOT_LAST_SUBMIT(11);


    private Integer status;

    public static QuizeAnswerStatusEnum typeOf(Integer status) {
        for (QuizeAnswerStatusEnum value : values()) {
            if (Objects.equals(value.status, status)) {
                return value;
            }
        }

        return PERMIT_ALL;
    }
}
