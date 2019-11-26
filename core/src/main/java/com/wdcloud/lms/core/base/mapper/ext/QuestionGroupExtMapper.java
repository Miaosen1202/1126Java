package com.wdcloud.lms.core.base.mapper.ext;

public interface QuestionGroupExtMapper {

    /**
     * 功能：依据quizId删除对应的问题和问题组
     * @param quizId
     * @return
     */
    int deleteByquizId(long quizId);
}
