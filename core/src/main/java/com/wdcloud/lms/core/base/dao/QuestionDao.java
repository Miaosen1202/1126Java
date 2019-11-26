package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuestionExtMapper;
import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuestionDao extends CommonDao<Question, Long> {

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Override
    protected Class<Question> getBeanClass() {
        return Question.class;
    }

    /**
     * 功能：依据问题ID查询选项信息
     */
    public QuestionVO getByquestionId(long questionId) {
        return questionExtMapper.getByquestionId(questionId);
    }

    public List<QuestionVO> getByGroupId(long groupId) {
        return questionExtMapper.getByGroupId(groupId);
    }
}