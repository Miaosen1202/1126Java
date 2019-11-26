package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.lms.core.base.vo.QuestionVO;

import java.util.List;

public interface QuestionOptionExtMapper {
    /**
     * 功能：选项题批量添加
     * @param questionOptionList
     * @return
     */
    int batchInsert(List<QuestionOption> questionOptionList);

    /**
     * 功能：选项题批量更新
     * @param questionOptionList
     * @return
     */
    int batchUpdate(List<QuestionOption> questionOptionList);

    /**
     * 功能：选项题批量删除
     * @param questionOptionList
     * @return
     */
    int batchDelete(List<QuestionOption> questionOptionList);

    /**
     * 功能：依据问题ID查询选项信息
     * @param questionId
     * @return
     */
    QuestionVO getByquestionId(long questionId);
}
