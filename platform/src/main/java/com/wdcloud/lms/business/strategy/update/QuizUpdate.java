package com.wdcloud.lms.business.strategy.update;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.resources.dto.QuizResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.SingleQuizAddDTO;
import com.wdcloud.lms.business.strategy.AbstractQuizStrategy;
import com.wdcloud.lms.core.base.dao.QuestionDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class QuizUpdate extends AbstractQuizStrategy implements UpdateStrategy {
    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        updateName(baseModuleItemDTO.getId(), baseModuleItemDTO.getName());
    }

    @Override
    public void updateName(Long id, String name) {
        quizDao.update(Quiz.builder().id(id).title(name).build());
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        valid(id);
        quizDao.update(Quiz.builder().id(id).status(status).build());
    }

    @Override
    public void updateNameAndScoreAndStatus(Long id, String name, int score, boolean isPublish) {
        valid(id);
        if (isPublish) {
            quizDao.update(Quiz.builder().id(id).score(score).status(1).title(name).build());
        } else {
            quizDao.update(Quiz.builder().id(id).score(score).title(name).build());
        }
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        QuizResourceShareDTO quizResource = JSON.parseObject(beanJson, QuizResourceShareDTO.class);
        if(Objects.isNull(quizResource) || Objects.isNull(quizResource.getId())){
            return null;
        }

        Question question = null;
        QuestionVO originQuestion;
        List<QuizItemVO> quizItemVOs;
        SingleQuizAddDTO singleQuizAddDTO;
        List<QuestionOption> questionOptions;
        ResourceImportGeneration importGeneration;
        ResourceImportGeneration optionImmportGeneration;
        List<Long> addResourceCourseIds = new ArrayList<>();
        List<CourseImportGenerationDTO> courseImportGenerationDTOS = new ArrayList<>();
        for(Long courseId : courseIds) {
            importGeneration = resourceCommonService.getResourceImportGenerationByOriginId(resourceId,
                    courseId, quizResource.getId(), OriginTypeEnum.QUIZ.getType());
            //若资源是课程的形式，则存在测验以前未被导入的情况
            if(Objects.isNull(importGeneration)){
                addResourceCourseIds.add(courseId);
            }else{
                final Quiz quiz = quizDao.get(importGeneration.getNewId());
                //导入人将测验资源删除
                if(Objects.isNull(quiz)){
                    singleQuizAddDTO = quizAdd.addSingleByResource(quizResource, courseId);
                    importGeneration.setNewId(singleQuizAddDTO.getQuizId());
                    resourceImportGenerationDao.update(importGeneration);
                    courseImportGenerationDTOS.addAll(singleQuizAddDTO.getCourseImportGenerationDTOS());
                }else{
                    Quiz newQuiz = initQuiz(quiz.getId(), quizResource);
                    quizDao.update(newQuiz);

                    quizItemVOs = quizResource.getQuizItemVOs();
                    for (QuizItemVO quizItemVO : quizItemVOs) {
                        originQuestion = quizItemVO.getQuestion();
                        List<QuestionOption> originOptions = originQuestion.getOptions();
                        importGeneration = resourceCommonService.getResourceImportGenerationByOriginId(resourceId,
                                courseId, originQuestion.getId(), OriginTypeEnum.QUIZ_QUESTION.getType());

                        if(Objects.nonNull(importGeneration)){
                            question = questionDao.get(importGeneration.getNewId());
                            //问题已被导入人删除，此时删除相关导入更新记录表
                            if(Objects.isNull(question)){
                                resourceImportGenerationDao.delete(importGeneration.getId());
                                if(ListUtils.isNotEmpty(originOptions)){
                                    List<Long> optionImmportGenerationIds = new ArrayList<>();
                                    for(QuestionOption temp : originOptions){
                                        optionImmportGeneration = resourceCommonService.getResourceImportGenerationByOriginId(
                                                resourceId, courseId, temp.getId(), OriginTypeEnum.QUIZ_QUESTION_OPTION.getType());
                                        if(Objects.nonNull(optionImmportGeneration)){
                                            optionImmportGenerationIds.add(optionImmportGeneration.getId());
                                        }
                                    }
                                    resourceImportGenerationDao.deletes(optionImmportGenerationIds);
                                }
                            }
                        }

                        QuestionDTO questionDTO = new QuestionDTO();
                        //问题是新加的，或者问题已被导入人删除
                        questionDTO.setSeq(originQuestion.getSeq());
                        if(Objects.isNull(importGeneration) || Objects.isNull(question)){
                            questionDTO = initNoIdQuestionDTO(questionDTO,  originOptions);
                        }else{
                            question = questionDao.get(importGeneration.getNewId());
                            questionDTO = initWithIdQuestionDTO(questionDTO, question);
                            //查询原有问题的选项
                            QuestionOption questionOption = new QuestionOption();
                            questionOption.setQuestionId(Long.valueOf(question.getId()));
                            questionOptions = questionOptionDao.find((questionOption));
                            Map<Long,QuestionOption> map = questionOptions.stream().collect(Collectors.toMap(QuestionOption::getId, a -> a));

                            List<QuestionOption> savedQuestionOptions = new ArrayList<>();
                            List<QuestionOption> nonDeletedQuestionOptions = new ArrayList<>();
                            if(ListUtils.isNotEmpty(originOptions)){
                                for(QuestionOption temp : originOptions){
                                    optionImmportGeneration = resourceCommonService.getResourceImportGenerationByOriginId(
                                            resourceId, courseId, temp.getId(), OriginTypeEnum.QUIZ_QUESTION_OPTION.getType());
                                    if(Objects.isNull(optionImmportGeneration)){
                                        temp.setId(null);
                                        savedQuestionOptions.add(temp);
                                    }else {
                                        QuestionOption newOption = questionOptionDao.get(optionImmportGeneration.getNewId());
                                        if(Objects.isNull(newOption)){
                                            temp.setId(null);
                                            temp.setQuestionId(question.getId());
                                            questionOptionDao.save(temp);
                                            optionImmportGeneration.setNewId(temp.getId());
                                            resourceImportGenerationDao.update(optionImmportGeneration);
                                            savedQuestionOptions.add(temp);
                                        }else{
                                            newOption.setCode(temp.getCode());
                                            newOption.setContent(temp.getContent());
                                            newOption.setExplanation(temp.getExplanation());
                                            newOption.setIsCorrect(temp.getIsCorrect());
                                            newOption.setSeq(temp.getSeq());
                                            savedQuestionOptions.add(newOption);
                                            nonDeletedQuestionOptions.add(map.get(newOption.getId()));

                                        }
                                    }
                                }
                            }

                            //从导入人的问题选项中删除分享人已删除的问题选项和导入人自己加入的问题选项
                            if(ListUtils.isNotEmpty(questionOptions)){
                                questionOptions.removeAll(nonDeletedQuestionOptions);
                                for(QuestionOption temp : questionOptions){
                                    final ResourceImportGeneration deleteImportGeneration = resourceCommonService.getResourceImportGenerationByNewId(
                                            resourceId, courseId, temp.getId(), OriginTypeEnum.QUIZ_QUESTION_OPTION.getType());
                                    if(Objects.nonNull(deleteImportGeneration)){
                                        resourceImportGenerationDao.delete(deleteImportGeneration.getId());
                                    }
                                    questionOptionDao.delete(temp.getId());
                                }
                            }
                            questionDTO.setOptions(savedQuestionOptions);
                        }

                        questionDTO = updateQuestionDTO(questionDTO,  quiz.getId(), courseId, originQuestion, quizItemVO.getMatchoptions());
                        DataEditInfo dataEditInfo = new DataEditInfo(JSON.toJSONString(questionDTO));

                        if(Objects.isNull(importGeneration) || Objects.isNull(question)){
                            LinkedInfo linkedInfo = questionEdit.add(dataEditInfo);
                            courseImportGenerationDTOS.addAll(quizAdd.getImportGenerationInQuestion(
                                    Long.valueOf(linkedInfo.masterId), originQuestion.getId(), courseId, originQuestion.getOptions()));
                        }else{
                            questionEdit.update(dataEditInfo);
                        }
                    }
                }
            }
        }
        courseImportGenerationDTOS.addAll(quizAdd.addByResource(beanJson, resourceId, addResourceCourseIds));
        return courseImportGenerationDTOS;
    }

    /**
     * 判读是否可以修改
     * 如果有学生已经提交　就不允许修改
     *
     * @param id
     */
    private void valid(Long id) {
        if (userSubmitRecordDao.count(UserSubmitRecord.builder()
                .originId(id)
                .originType(support().getType())
                .build()) > 0) {
            //已经提交过
            throw new BaseException("already.submit");
        }
    }

    private Quiz initQuiz(Long quizId, QuizResourceShareDTO quizResource){
        Quiz newQuiz = Quiz.builder().id(quizId).title(quizResource.getTitle()).type(quizResource.getType())
                .description(quizResource.getDescription()).timeLimit(quizResource.getTimeLimit())
                .allowMultiAttempt(quizResource.getAllowMultiAttempt()).attemptNumber(quizResource.getAttemptNumber())
                .isShuffleAnswer(quizResource.getIsShuffleAnswer()).showReplyStrategy(quizResource.getShowReplyStrategy())
                .isNeedAccessCode(quizResource.getIsNeedAccessCode()).accessCode(quizResource.getAccessCode())
                .showAnswerStrategy(quizResource.getShowAnswerStrategy()).scoreType(quizResource.getScoreType()).build();

        return newQuiz;
    }

    private QuestionDTO initNoIdQuestionDTO(QuestionDTO questionDTO, List<QuestionOption> originOptions){
        questionDTO.setGroupId(0L);
        questionDTO.setIsTemplate(Status.NO.getStatus());
        questionDTO.setQuestionBankId(0l);
        questionDTO.setQuestionTemplateId(0L);
        questionDTO.setOptions(originOptions);

        return questionDTO;
    }

    private QuestionDTO initWithIdQuestionDTO(QuestionDTO questionDTO, Question question){
        questionDTO.setId(question.getId());
        questionDTO.setGroupId(question.getGroupId());
        questionDTO.setIsTemplate(question.getIsTemplate());
        questionDTO.setQuestionBankId(question.getQuestionBankId());
        questionDTO.setQuestionTemplateId(question.getQuestionTemplateId());

        return questionDTO;
    }

    private QuestionDTO updateQuestionDTO(QuestionDTO questionDTO, Long quizId, Long courseId,
                                          QuestionVO originQuestion, List<QuestionMatchOption> matchoptions){
        questionDTO.setQuizId(quizId);
        questionDTO.setCourseId(courseId);
        questionDTO.setType(originQuestion.getType());
        questionDTO.setTitle(originQuestion.getTitle());

        if(Objects.isNull(originQuestion.getContent())){
            questionDTO.setContent("");
        }else{
            questionDTO.setContent(originQuestion.getContent());
        }

        questionDTO.setCorrectComment(originQuestion.getCorrectComment());
        questionDTO.setWrongComment(originQuestion.getWrongComment());

        if(Objects.isNull(originQuestion.getGeneralComment())){
            questionDTO.setGeneralComment("");
        }else{
            questionDTO.setGeneralComment(originQuestion.getGeneralComment());
        }
        questionDTO.setScore(originQuestion.getScore());
        questionDTO.setMatchoptions(matchoptions);

        return questionDTO;
    }
}
