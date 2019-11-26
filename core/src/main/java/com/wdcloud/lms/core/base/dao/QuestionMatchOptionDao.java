package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuestionMatchOptionExtMapper;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuestionMatchOptionDao extends CommonDao<QuestionMatchOption, Long> {

    @Autowired
    private QuestionMatchOptionExtMapper questionMatchOptionExtMapper;

    @Override
    public Class<QuestionMatchOption> getBeanClass() {
        return QuestionMatchOption.class;
    }

    public void batchInsert(List<QuestionMatchOption> questionMatchOptionList) {
        if (ListUtils.isNotEmpty(questionMatchOptionList)) {
            questionMatchOptionExtMapper.batchInsert(questionMatchOptionList);
        }
    }

    public void batchUpdate(List<QuestionMatchOption> questionMatchOptionList) {
        if (ListUtils.isNotEmpty(questionMatchOptionList)) {
            questionMatchOptionExtMapper.batchUpdate(questionMatchOptionList);
        }
    }

    public void batchDelete(List<QuestionMatchOption> questionMatchOptionList) {
        if (ListUtils.isNotEmpty(questionMatchOptionList)) {
            questionMatchOptionExtMapper.batchDelete(questionMatchOptionList);
        }
    }

    /**
     * 功能：依据问题ID查询选项信息
     */
    public QuestionVO getByquestionId(long questionId) {
        return questionMatchOptionExtMapper.getByquestionId(questionId);
    }

}