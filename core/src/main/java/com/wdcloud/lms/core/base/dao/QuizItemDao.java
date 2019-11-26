package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuizItemExtMapper;
import com.wdcloud.lms.core.base.model.QuizItem;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuizItemDao extends CommonDao<QuizItem, Long> {

    @Autowired
    private QuizItemExtMapper quizItemExtMapper;

    @Override
    protected Class<QuizItem> getBeanClass() {
        return QuizItem.class;
    }

    public int updateByTargetIdAndType(QuizItem quizItem) {
        return quizItemExtMapper.updateByTargetIdAndType(quizItem);
    }

    /**
     * 功能：依据targetId AND type AND quizId删除
     * @param quizItem
     * @return
     */

    public int deleteByTargetIdAndType(QuizItem quizItem) {
        return quizItemExtMapper.deleteByTargetIdAndType(quizItem);
    }

    /**
     * 功能：依据quizId查出对应的问题
     * @param quizId
     * @return
     */
    public List<QuizItem> getByQuizId(long quizId) {
        return quizItemExtMapper.getByQuizId(quizId);
    }
    /**
     * 功能：依据quizId查出对应的问题和问题组
     * @param quizId
     * @return
     */
    public List<QuizItem> getAllByQuizId(long quizId) {
        return quizItemExtMapper.getAllByQuizId(quizId);
    }

    /**
     * 功能：依据quizId删除对应的问题和问题组
     * @param quizId
     * @return
     */
    public int deleteByquizId(long quizId) {
        return quizItemExtMapper.deleteByquizId(quizId);
    }

    /**
     * 功能：获取当前seq
     * @param quizId
     * @return
     */
    public int getCurrentSeq(long quizId) {
        return quizItemExtMapper.getCurrentSeq(quizId) + 1;
    }

    /**
     * 功能：查询测验相关信息
     * @param quizId
     * @return
     */
    public List<QuizItemVO> getQuerstionAllInfors(@Param("quizId") long quizId) {
        return quizItemExtMapper.getQuerstionAllInfors(quizId);
    }

    /**
     * 功能：批量更新seq
     * @param quizItemList
     * @return
     */
    public int batchUpdateSeq(List<QuizItem> quizItemList) {
        return quizItemExtMapper.batchUpdateSeq(quizItemList);
    }
    /**
     * 功能：依据问题id查询问题相关信息
     * @param id  问题id
     * @return
     */
    public List<QuizItemVO> getQuerstionByQuestionId(long id) {
        return quizItemExtMapper.getQuerstionByQuestionId(id);
    }

    public int getTotalSubjectiveQuestion(long quizId) {
        return quizItemExtMapper.getTotalSubjectiveQuestion(quizId);
    }
}