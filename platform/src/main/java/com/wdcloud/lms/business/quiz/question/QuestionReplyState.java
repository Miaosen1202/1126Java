package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：简答、填空题增、删、改、查功能
 *
 * @author 黄建林
 */
@Service
public class QuestionReplyState extends QuestionTypeState {
    @Autowired
    private ReplyQuestionService replyQuestionService;



    /**
     * 功能：教师出题
     * @param dto
     * @return
     */
    @Override
    public int save(QuestionDTO dto) {
        return 0;
    }
    @Override
    public int update(QuestionDTO dto) {
        return 0;
    }
    @Override
    public int delete(QuestionVO dto) {
        return 0;
    }
    @Override
    public QuestionVO getByquestionId(long questionId) {
        return null;
    }
    @Override
    public int saveRecord(QuizQuestionRecordDTO dto) {
        if (dto.getReply() != null) {
            replyQuestionService.saveRecord(dto.getReply(), dto.getId());
        }
        return 0;
    }
    @Override
    public int updateRecord(QuizQuestionRecordDTO dto) {
        if (dto.getReply() != null) {
            replyQuestionService.updateRecord(dto.getReply());
        }
        return 0;
    }



    /**
     * 功能：教师答题记录需要删除
     * @param dto
     * @return
     */

    @Override
    public  int deleteRecord(QuizQuestionRecordVO dto)
    {
        if (dto.getReply() != null) {
            replyQuestionService.deleteRecord(dto.getReply());
        }
        return 0;
    }

}
