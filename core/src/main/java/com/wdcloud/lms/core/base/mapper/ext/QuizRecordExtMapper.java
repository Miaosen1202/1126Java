package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.lms.core.base.vo.QuizRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 黄建林
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface QuizRecordExtMapper {

    /**
     * 功能：做题次数统计接口定义
     * @param quizId
     * @param createUserId
     * @return
     */
    int getTotalRecord(@Param("quizId") long quizId,@Param("createUserId") long createUserId);

    List<QuizRecordVO> getQuizRecords(QuizRecord quizRecord);

    /**
     * 获取主观题已评分的所有打分版本**/
    List<Map<String, Object>> getQuizQuestionRecordList(@Param("quizId") Long quizId,
                                                        @Param("userId") Long userId);

}
