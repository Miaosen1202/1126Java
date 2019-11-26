package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuestionGroupExtMapper;
import com.wdcloud.lms.core.base.model.QuestionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuestionGroupDao extends CommonDao<QuestionGroup, Long> {
    @Autowired
    private QuestionGroupExtMapper questionGroupExtMapper;

    @Override
    public Class<QuestionGroup> getBeanClass() {
        return QuestionGroup.class;
    }



    /**
     * 功能：依据quizId删除对应的问题和问题组
     * @param quizId
     * @return
     */
    public int deleteByquizId(long quizId) {
        return questionGroupExtMapper.deleteByquizId(quizId);
    }


}