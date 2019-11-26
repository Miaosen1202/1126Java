package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuizQuestionRecord;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QuizQuestionRecordExtMapper {

    List<QuizQuestionRecordVO> getQuerstionRecords(QuizQuestionRecord quizQuestionRecord);

    /**
     * 功能：答题总分计算
     *
     * @param quizRecordId
     * @return
     */
    int getTotalRecordScores(@Param("quizRecordId") long quizRecordId);



}
