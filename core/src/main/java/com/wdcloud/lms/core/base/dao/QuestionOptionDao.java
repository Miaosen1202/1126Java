package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuestionOptionExtMapper;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuestionOptionDao extends CommonDao<QuestionOption, Long> {

    @Autowired
    private QuestionOptionExtMapper questionOptionExtMapper;

    @Override
    protected Class<QuestionOption> getBeanClass() {
        return QuestionOption.class;
    }

    public void batchInsert(List<QuestionOption> questionOptionList) {
        if (ListUtils.isNotEmpty(questionOptionList)) {
            questionOptionExtMapper.batchInsert(questionOptionList);
        }
    }

    public void batchUpdate(List<QuestionOption> questionOptionList) {
        if (ListUtils.isNotEmpty(questionOptionList)) {
            questionOptionExtMapper.batchUpdate(questionOptionList);
        }
    }

    public void batchDelete(List<QuestionOption> questionOptionList) {
        if (ListUtils.isNotEmpty(questionOptionList)) {
            questionOptionExtMapper.batchDelete(questionOptionList);
        }
    }

    /**
     * 功能：依据问题ID查询选项信息
     */
    public QuestionVO getByquestionId(long questionId) {
        return questionOptionExtMapper.getByquestionId(questionId);
    }

}