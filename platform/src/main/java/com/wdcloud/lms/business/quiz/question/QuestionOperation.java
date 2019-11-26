package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.business.quiz.enums.QuestionTypeEnum;
import com.wdcloud.lms.core.base.dao.QuestionDao;
import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：该类对应状态模式的环境类
 *
 * @author 黄建林
 */
@Service
public class QuestionOperation {
    @Qualifier("QuestionTypeState")
   private  QuestionTypeState questionTypeState;
    @Resource(name="questionOptionState")
    private QuestionOptionState questionOptionState;
    @Resource(name="questionMatchOptionState")
    QuestionMatchOptionState  questionMatchOptionState;
    @Resource(name="questionReplyState")
    QuestionReplyState questionReplyState;
    @Autowired
    private QuestionDao questionDao;

    public int save(QuestionDTO dto ) {
         changeType(dto.getType());
         if(questionTypeState==null)
         {
             return QuestionConstants.ONLY_QUESTION;
         }
      return  questionTypeState.save(dto);
    }
    public int update(QuestionDTO dto ) {
        changeType(dto.getType());
        if(questionTypeState==null)
        {
            return QuestionConstants.ONLY_QUESTION;
        }
        return  questionTypeState.update(dto);
    }
    public int delete(Question question) {

        changeType(question.getType());
        if(questionTypeState==null)
        {
            return QuestionConstants.ONLY_QUESTION;
        }
        final QuestionVO dto = questionTypeState.getByquestionId(question.getId());


        return  questionTypeState.delete(dto);
    }

    /**
     * 功能：学生答题保存
     * @param  dto  测验问题记录dto
     * @return
     */
    public int saveRecord(QuizQuestionRecordDTO dto ) {
        changeType(dto.getType());
        if(questionTypeState==null)
        {
            return QuestionConstants.ONLY_QUESTION;
        }
        return  questionTypeState.saveRecord(dto);
    }

    /**
     * 功能：学生答题更新
     * @param dto
     * @return
     */
    public int updateRecord(QuizQuestionRecordDTO dto ) {
        changeType(dto.getType());
        if(questionTypeState==null)
        {
            return QuestionConstants.ONLY_QUESTION;
        }
        return  questionTypeState.updateRecord(dto);
    }

    /**
     * 功能：答题删除，目前只需要删除教师的答题记录
     * @param dto
     * @return
     */
    public int deleteRecord(QuizQuestionRecordVO dto ) {
        changeType(dto.getType());
        if(questionTypeState==null)
        {
            return QuestionConstants.ONLY_QUESTION;
        }
        return  questionTypeState.deleteRecord(dto);
    }

    /**
     * 功能：依据传入不同类型的问题创建不同问题的对象
     *参数： type 问题类型
     *
     */
    private void changeType(int type) {

        switch(type){
            case QuestionConstants.MULTIPLE_CHOICE:
                //单选题;
                questionTypeState=questionOptionState;
                break;
            case QuestionConstants.MULTIPLE_ANSWERS:
                //多选题;
                questionTypeState=questionOptionState;
                break;
            case QuestionConstants.TRUE_FALSE:
                //判断题;
                questionTypeState=questionOptionState;
                break;
            case QuestionConstants.MATCHING:
                //匹配题;
                questionTypeState=questionMatchOptionState;
                break;
            case QuestionConstants.ESSAY_QUESTION:
                //简答、填空题;
                questionTypeState=questionReplyState;
                break;

            default:
                //;
                questionTypeState=null;
                break;
        }


    }

    /**
     * 功能：学生分数计算
     * @param dto
     * @return
     */
    public  float calculatedScore(QuizQuestionRecordDTO dto)
    {
        float score=0;
        if(dto.getType().equals(QuestionTypeEnum.MULTIPLE_CHOICE.getType()) ||
                dto.getType().equals(QuestionTypeEnum.TRUE_FALSE.getType()))
        {
            List<QuizQuestionOptionRecord> options=dto.getOptions();
            for (QuizQuestionOptionRecord option :options ) {
                if(option.getIsChoice()!=null&&option.getIsCorrect()!=null) {
                    if (option.getIsChoice() == 1 && option.getIsCorrect() == 1) {
                        score = dto.getScore();
                        return score;
                    }
                }

            }
        }
        if(dto.getType().equals(QuestionTypeEnum.MULTIPLE_ANSWERS.getType()))
        {
            List<QuizQuestionOptionRecord> options=dto.getOptions();
            for (QuizQuestionOptionRecord option :options ) {
                if(option.getIsChoice()!=null&&option.getIsCorrect()!=null) {
                    if ((option.getIsChoice() == 0 && option.getIsCorrect() == 1)
                            ||(option.getIsChoice() == 1 && option.getIsCorrect() == 0)) {
                        score = 0;
                        return score;
                    }

                }

            }
            score =dto.getScore();
        }
        if(dto.getType().equals(QuestionTypeEnum.MATCHING.getType()) )
        {
            List<QuizQuestionOptionRecord> options=dto.getOptions();
            int totaloption=options.size();
            int isright=0;
            for (int i=0;i<totaloption;i+=2) {
                if(options.get(i).getQuizQuestionRecordId()+1==options.get(i+1).getQuizQuestionRecordId())
                {
                    isright++;
                }

            }
            score =dto.getScore()/isright;
        }

        return  score;
    }

}
