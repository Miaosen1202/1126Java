package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.vo.QuestionVO;

import java.util.List;


public interface QuestionMatchOptionExtMapper {
    /**
     * 功能：匹配题选项批量添加
     * @param questionMatchOptionList
     * @return
     */
    int batchInsert(List<QuestionMatchOption> questionMatchOptionList);

    /**
     * 功能：匹配题选项批量更新
     * @param questionMatchOptionList
     * @return
     */
    int batchUpdate(List<QuestionMatchOption> questionMatchOptionList);

    /**
     * 功能：匹配题选项批量删除
     * @param questionMatchOptionList
     * @return
     */
    int batchDelete(List<QuestionMatchOption> questionMatchOptionList);

    /**
     * 功能：依据问题ID查询选项信息
     * @param questionId
     * @return
     */
    QuestionVO getByquestionId(long questionId);
}
