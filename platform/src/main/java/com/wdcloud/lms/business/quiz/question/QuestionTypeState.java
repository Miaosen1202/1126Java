package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;

/**
 * 功能：该类对应状态模式的抽象状态类
 *
 * @author 黄建林
 */
public abstract class QuestionTypeState {


    /**
     * 功能：教师出题，问题附加项添加（比如选择题的选项）
     * @param dto
     * @return
     */
    public abstract int save(QuestionDTO dto);

    /**
     * 功能：教师出题，问题附加项更新（比如选择题的选项）
     * @param dto
     * @return
     */
    public abstract int update(QuestionDTO dto);

    /**
     * 功能：教师出题，问题附加项删除（比如选择题的选项）
     * @param dto
     * @return
     */
    public abstract int delete(QuestionVO dto);

    /**
     * 功能：教师出题，问题附加项查询（比如选择题的选项）
     * @param questionId
     * @return
     */
    public abstract QuestionVO getByquestionId(long questionId);

    /**
     * 功能：学生答题附加项记录添加
     * @param dto
     * @return
     */
    public abstract int saveRecord(QuizQuestionRecordDTO dto);
    /**
     * 功能：学生答题附加项记录更新
     * @param dto
     * @return
     */
    public abstract int updateRecord(QuizQuestionRecordDTO dto);

    /**
     * 功能：教师答题记录需要删除
     * @param dto
     * @return
     */
    public abstract int deleteRecord(QuizQuestionRecordVO dto);
}
