package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;

import java.util.List;

public interface QuizQuestionMatchOptionRecordExtMapper {
    /**
     * 功能：批量插入匹配题选项接口
     * @param quizQuestionMatchOptionRecordList
     * @return
     */
    int batchInsert(List<QuizQuestionMatchOptionRecord> quizQuestionMatchOptionRecordList);

    /**
     * 功能：批量更新匹配题选项接口
     * @param quizQuestionMatchOptionRecordList
     * @return
     */
    int batchUpdate(List<QuizQuestionMatchOptionRecord> quizQuestionMatchOptionRecordList);

    /**
     * 功能：批量删除匹配题选项接口
     * @param quizQuestionMatchOptionRecordList
     * @return
     */
    int batchDelete(List<QuizQuestionMatchOptionRecord> quizQuestionMatchOptionRecordList);

    /**
     * 功能：依据问题ID查询选项信息
     */
    // QuestionVO getByquestionId(long questionId);
}
