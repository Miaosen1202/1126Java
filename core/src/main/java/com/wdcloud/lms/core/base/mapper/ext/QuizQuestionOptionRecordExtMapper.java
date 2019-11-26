package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;

import java.util.List;

public interface QuizQuestionOptionRecordExtMapper {
    /**
     * 功能：选项题批量添加接口
     * @param quizQuestionOptionRecordList
     * @return
     */
    int batchInsert(List<QuizQuestionOptionRecord> quizQuestionOptionRecordList);

    /**
     * 功能：选项题批量更新接口
     * @param quizQuestionOptionRecordList
     * @return
     */
    int batchUpdate(List<QuizQuestionOptionRecord> quizQuestionOptionRecordList);

    /**
     * 功能：选项题批量删除接口
     * @param quizQuestionOptionRecordList
     * @return
     */
    int batchDelete(List<QuizQuestionOptionRecord> quizQuestionOptionRecordList);

}
