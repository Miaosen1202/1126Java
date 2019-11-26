package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import com.wdcloud.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：单选、多选、是非题增、删、改、查功能
 *
 * @author 黄建林
 */
@Service
public class QuestionOptionState extends QuestionTypeState {
    @Autowired
    private OptionService optionService;
    @Override
    public int save(QuestionDTO dto) {
        if (CollectionUtil.isNotNullAndEmpty(dto.getOptions())) {
            optionService.batchSave(dto.getOptions(), dto.getId());
        }
        return 0;
    }
    @Override
    public int update(QuestionDTO dto) {
        if (CollectionUtil.isNotNullAndEmpty(dto.getOptions())) {
            optionService.batchUpdate(dto.getOptions());
        }
        return 0;

    }
    @Override
    public int delete(QuestionVO dto) {
        if (CollectionUtil.isNotNullAndEmpty(dto.getOptions())) {
            optionService.batchDelete(dto.getOptions());
        }
        return 0;
    }
    @Override
    public QuestionVO getByquestionId(long questionId) {
        return optionService.getByquestionId(questionId);
    }
    @Override
    public int saveRecord(QuizQuestionRecordDTO dto) {
        if (CollectionUtil.isNotNullAndEmpty(dto.getOptions())) {
            optionService.batchSaveRecord( dto);
        }
        return 0;
    }
    @Override
    public int updateRecord(QuizQuestionRecordDTO dto) {
        if (CollectionUtil.isNotNullAndEmpty(dto.getOptions())) {
            optionService.batchUpdateRecord(dto);
        }
        return 0;
    }

    /**
     * 功能：教师答题记录需要删除
     * @param dto
     * @return
     */
    @Override
    public int deleteRecord(QuizQuestionRecordVO dto)
    {
        if (CollectionUtil.isNotNullAndEmpty(dto.getOptions())) {
            optionService.batchDeleteRecord(dto);
        }
        return 0;
    }

}
