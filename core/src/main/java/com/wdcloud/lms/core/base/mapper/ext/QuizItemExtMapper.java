package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuizItem;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 黄建林
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface QuizItemExtMapper {
    /**
     * 功能：依据targetId AND type AND quizId更新
     * @param quizItem
     * @return
     */
    int updateByTargetIdAndType(QuizItem quizItem);
    /**
     * 功能：依据targetId AND type AND quizId删除
     * @param quizItem
     * @return
     */
    int deleteByTargetIdAndType(QuizItem quizItem);

    /**
     * 功能：依据quizId查出对应的问题
     * @param quizId
     * @return
     */
    List<QuizItem> getByQuizId(long quizId);

    /**
     * 功能：依据quizId查出对应的问题和问题组
     * @param quizId
     * @return
     */
    List<QuizItem> getAllByQuizId(long quizId);

    /**
     * 功能：依据quizId删除对应的问题和问题组
     * @param quizId
     * @return
     */
    int deleteByquizId(long quizId);

    /**
     * 功能：获取当前seq
     * @param quizId
     * @return
     */
    Integer getCurrentSeq(@Param("quizId") long quizId);

    /**
     * 功能：批量更新seq
     * @param quizItemList
     * @return
     */
    int batchUpdateSeq(List<QuizItem> quizItemList);

    /**
     * 功能：查询测验相关信息
     * @param quizId
     * @return
     */
    List<QuizItemVO> getQuerstionAllInfors(@Param("quizId") long quizId);

    /**
     * 功能：依据问题id查询问题相关信息
     * @param id  问题id
     * @return
     */
    List<QuizItemVO> getQuerstionByQuestionId(@Param("id") long id);

    /**
     * 功能：获取主观题数目，用于判断是否全部评分或部分评分
     * @param quizId
     * @return
     */
    int getTotalSubjectiveQuestion(@Param("quizId") long quizId);
}
