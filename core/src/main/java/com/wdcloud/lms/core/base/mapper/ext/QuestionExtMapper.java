package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.QuestionVO;

import java.util.List;

public interface QuestionExtMapper {

    /**
     * 功能：依据问题ID查询选项信息
     * @param questionId
     * @return
     */
    QuestionVO getByquestionId(long questionId);

    /**
     * 功能：依据groupId查询问题信息
     * @param groupId
     * @return
     */
    List<QuestionVO> getByGroupId(long groupId);
}
