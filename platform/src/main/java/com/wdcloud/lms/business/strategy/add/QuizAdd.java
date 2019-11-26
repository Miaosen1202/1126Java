package com.wdcloud.lms.business.strategy.add;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.quiz.dto.QuizDTO;
import com.wdcloud.lms.business.quiz.enums.QuizItemTypeEnum;
import com.wdcloud.lms.business.quiz.enums.ShowReplyStrategyEnum;
import com.wdcloud.lms.business.quiz.enums.ShuffleAnswersEnum;
import com.wdcloud.lms.business.quiz.quiztype.QuestionOperationConstants;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.resources.dto.QuizResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.SingleQuizAddDTO;
import com.wdcloud.lms.business.strategy.AbstractQuizStrategy;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class QuizAdd extends AbstractQuizStrategy implements AddStrategy {
    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        //0、参数解析
        if (null == moduleItemContentDTO.getType()) {
            throw new ParamErrorException();
        }
        //1、获取assigns
        List<Assign> assigns = new ArrayList<>();
        assigns.add(Assign.builder().assignType(AssignTypeEnum.ALL.getType()).build());
        //2、拼凑dataEditInfo
        Quiz quiz = Quiz.builder()
                .courseId(moduleItemContentDTO.getCourseId())
                .title(moduleItemContentDTO.getTitle())
                .type(moduleItemContentDTO.getType())
                .assignmentGroupId(moduleItemContentDTO.getAssignmentGroupId())
                .isShuffleAnswer(ShuffleAnswersEnum.IS_NOT_SHUFFLE_ANSWER.getType())
                .showReplyStrategy(ShowReplyStrategyEnum.NONE.getStrategy())
                .showAnswerStrategy(ShowReplyStrategyEnum.NONE.getStrategy())
                .showQuestionStrategy(ShowReplyStrategyEnum.NONE.getStrategy())
                .isLockRepliedQuestion(Status.NO.getStatus())
                .isNeedAccessCode(Status.NO.getStatus())
                .isFilterIp(0)
                .version(1)
                .status(Status.NO.getStatus())
                .totalQuestions(0)
                .totalScore(0)
                .build();
        QuizDTO quizDTO = BeanUtil.beanCopyProperties(quiz, QuizDTO.class);
        quizDTO.setAssign(assigns);
        String beanJson = JSON.toJSONString(quizDTO);
        DataEditInfo dataEditInfo = new DataEditInfo(beanJson);
        //3、添加测验
        LinkedInfo linkedInfo = quizEdit.add(dataEditInfo);
        //4、返回测验ID
        return Long.valueOf(linkedInfo.masterId);
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        QuizResourceShareDTO quizResource = JSON.parseObject(beanJson, QuizResourceShareDTO.class);
        List<CourseImportGenerationDTO> result = new ArrayList<>();
        SingleQuizAddDTO singleQuizAddDTO;

        for(Long courseId : courseIds){
            singleQuizAddDTO = addSingleByResource(quizResource, courseId);
            result.add(new CourseImportGenerationDTO(courseId, singleQuizAddDTO.getQuizId(),
                    quizResource.getId(), OriginTypeEnum.QUIZ.getType()));
            result.addAll(singleQuizAddDTO.getCourseImportGenerationDTOS());
        }
        return result;
    }

    public SingleQuizAddDTO addSingleByResource(QuizResourceShareDTO quizResource, Long courseId){
        List<QuizItemVO> quizItemVOs = quizResource.getQuizItemVOs();
        List<CourseImportGenerationDTO> importGenerationDTOS = new ArrayList<>();
        QuestionVO question;
        LinkedInfo linkedInfo;

        Long assignmentGroupId = resourceCommonService.getAssignmentGroupId(courseId);

        Quiz quiz = Quiz.builder().courseId(courseId).title(quizResource.getTitle()).type(quizResource.getType())
                .isShuffleAnswer(quizResource.getIsShuffleAnswer()).showReplyStrategy(quizResource.getShowReplyStrategy())
                .isNeedAccessCode(quizResource.getIsNeedAccessCode()).assignmentGroupId(assignmentGroupId)
                .description(quizResource.getDescription()).timeLimit(quizResource.getTimeLimit())
                .allowMultiAttempt(quizResource.getAllowMultiAttempt()).attemptNumber(quizResource.getAttemptNumber())
                .showAnswerStrategy(quizResource.getShowAnswerStrategy()).accessCode(quizResource.getAccessCode())
                .scoreType(quizResource.getScoreType()).build();
        quizDao.save(quiz);

        for(QuizItemVO quizItemVO : quizItemVOs){
            question = quizItemVO.getQuestion();
            List<QuestionOption> originOptions = question.getOptions();

            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuizId(quiz.getId());
            questionDTO.setCourseId(courseId);
            questionDTO.setGroupId(0L);
            questionDTO.setType(question.getType());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setContent(question.getContent());
            questionDTO.setCorrectComment(question.getCorrectComment());
            questionDTO.setWrongComment(question.getWrongComment());
            questionDTO.setGeneralComment(question.getGeneralComment());
            questionDTO.setIsTemplate(Status.NO.getStatus());
            questionDTO.setQuestionBankId(0l);
            questionDTO.setQuestionTemplateId(0L);
            questionDTO.setSeq(0);
            questionDTO.setScore(question.getScore());
            questionDTO.setOptions(originOptions);
            questionDTO.setMatchoptions(quizItemVO.getMatchoptions());

            DataEditInfo dataEditInfo = new DataEditInfo(JSON.toJSONString(questionDTO));
            linkedInfo = questionEdit.add(dataEditInfo);

            importGenerationDTOS.addAll(getImportGenerationInQuestion(
                    Long.valueOf(linkedInfo.masterId), question.getId(), courseId, originOptions));
        }

        AssignmentGroupItem assignmentGroupItem = AssignmentGroupItem.builder()
                .assignmentGroupId(assignmentGroupId).originId(quiz.getId())
                .originType(OriginTypeEnum.QUIZ.getType()).title(quiz.getTitle())
                .score(quiz.getScore()).status(Status.NO.getStatus()).build();
        assignmentGroupItemDao.save(assignmentGroupItem);

        assignmentGroupItemChangeRecordDao.quizAdded(quiz, assignmentGroupItem.getId());

        return new SingleQuizAddDTO(quiz.getId(), importGenerationDTOS);
    }

    public List<CourseImportGenerationDTO> getImportGenerationInQuestion(Long newQuestionId, Long originQuestionId,
                                                                         Long courseId,  List<QuestionOption> originOptions){
        List<CourseImportGenerationDTO> importGenerationDTOS = new ArrayList<>();

        importGenerationDTOS.add(new CourseImportGenerationDTO(courseId, Long.valueOf(newQuestionId),
                originQuestionId, OriginTypeEnum.QUIZ_QUESTION.getType()));

        QuestionOption questionOption = new QuestionOption();
        questionOption.setQuestionId(newQuestionId);
        List<QuestionOption> questionOptions = questionOptionDao.find((questionOption));
        if(ListUtils.isNotEmpty(questionOptions)){
            for(int i=0; i<originOptions.size(); i++){
                importGenerationDTOS.add(new CourseImportGenerationDTO(courseId, questionOptions.get(i).getId(),
                        originOptions.get(i).getId(), OriginTypeEnum.QUIZ_QUESTION_OPTION.getType()));
            }
        }

        return importGenerationDTOS;
    }
}
