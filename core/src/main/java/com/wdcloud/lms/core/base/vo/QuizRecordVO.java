package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Grade;
import com.wdcloud.lms.core.base.model.QuizRecord;
import lombok.Data;

@Data
public class QuizRecordVO extends QuizRecord {
    /**
     * 评分
     * */
    private Grade grade;
}
