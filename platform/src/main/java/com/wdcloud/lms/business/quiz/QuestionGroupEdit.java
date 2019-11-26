package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.quiz.dto.QuestionGroupDTO;
import com.wdcloud.lms.business.quiz.enums.QuizItemTypeEnum;
import com.wdcloud.lms.business.quiz.question.QuestionOperation;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.business.quiz.question.SeqMoveService;
import com.wdcloud.lms.business.quiz.quiztype.QuestionOperationConstants;
import com.wdcloud.lms.business.quiz.quiztype.QuizStatisticsParameter;
import com.wdcloud.lms.business.quiz.quiztype.TotalScoreAndquestionFacade;
import com.wdcloud.lms.core.base.dao.QuestionDao;
import com.wdcloud.lms.core.base.dao.QuestionGroupDao;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.model.QuestionGroup;
import com.wdcloud.lms.core.base.model.QuizItem;
import com.wdcloud.lms.core.base.vo.QuestionVO;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * 功能：实现试题组表增、删、改调用接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_GROUP)
public class QuestionGroupEdit implements IDataEditComponent {

    @Autowired
    private QuestionGroupDao questionGroupDao;

    @Autowired
    private QuizItemDao quizItemDao;
    @Qualifier("TotalScoreAndquestionFacade")
    private TotalScoreAndquestionFacade totalScoreAndquestionFacade;

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionOperation questionOperation;
    @Autowired
    private SeqMoveService seqMoveService;

    private QuizItem getQuizItemInfor(QuestionGroupDTO dto, int oprate) {
        QuizItem quizItem = new QuizItem();
        int seq = 0;
        if (oprate == QuestionOperationConstants.ADD) {
            seq = quizItemDao.getCurrentSeq(dto.getQuizId());
        } else {
            seq = dto.getSeq();
        }
        quizItem.setSeq(seq);
        quizItem.setType(QuizItemTypeEnum.QUESTION_GROUP.getType());
        quizItem.setTargetId(dto.getId());
        quizItem.setQuizId(dto.getQuizId());
        quizItem.setUpdateUserId(WebContext.getUserId());
        return quizItem;


    }

    private QuizItem getQuestionDTOInfor(Question dto) {
        QuizItem quizItem = new QuizItem();

        quizItem.setSeq(0);
        quizItem.setType(QuizItemTypeEnum.QUESTION.getType());
        quizItem.setTargetId(dto.getId());
        return quizItem;


    }

    /**
     * @api {post} /questionGroup/add 试题组添加
     * @apiDescription 试题组添加
     * @apiName questionGroupAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizID  测试ID
     * @apiParam {String} name 组名
     * @apiParam {Number} eachQuestionScore  每个问题的得分
     * @apiParam {Number} pickQuestionNumber 挑选问题个数
     * @apiExample {json} 请求示例:
     * {
     * "name": "Thinking In Java"
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 新增问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    @ValidationParam(clazz = QuestionGroupDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuestionGroupDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionGroupDTO.class);
        questionGroupDao.save(dto);
        quizItemDao.save(getQuizItemInfor(dto, QuestionOperationConstants.ADD));
        //更新测验表中的问题总数和分值
        QuizStatisticsParameter quizStatisticsParameter = new QuizStatisticsParameter();
        quizStatisticsParameter.setLastQuestions(0);
        quizStatisticsParameter.setCurrentQuestions(dto.getPickQuestionNumber());
        quizStatisticsParameter.setLastScore(0);
        quizStatisticsParameter.setCurrentScore(dto.getEachQuestionScore() * dto.getPickQuestionNumber());
        totalScoreAndquestionFacade.questionsAndScoresUpdate(dto.getQuizId(), QuestionOperationConstants.ADD, quizStatisticsParameter);

        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionGroup/modify 试题组修改
     * @apiDescription 问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName questionGroupModify
     * @apiGroup Quiz
     * @apiParam {Number} id 试题组ID
     * @apiParam {Number} quizID  测试ID
     * @apiParam {String} name 组名
     * @apiParam {Number} eachQuestionScore  每个问题的得分
     * @apiParam {Number} pickQuestionNumber 挑选问题个数
     * @apiExample {json} 请求示例:
     * {
     * "id": 1,
     * "name": "Thinking In Java"
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    @ValidationParam(clazz = QuestionGroupDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuestionGroupDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionGroupDTO.class);

        //更新测验表中的问题总数和分值
        final QuestionGroup questionGroup = questionGroupDao.get(dto.getId());
        QuizStatisticsParameter quizStatisticsParameter = new QuizStatisticsParameter();
        quizStatisticsParameter.setLastQuestions(questionGroup.getPickQuestionNumber());
        quizStatisticsParameter.setCurrentQuestions(dto.getPickQuestionNumber());
        quizStatisticsParameter.setLastScore(questionGroup.getPickQuestionNumber() * questionGroup.getEachQuestionScore());
        quizStatisticsParameter.setCurrentScore(dto.getEachQuestionScore() * dto.getPickQuestionNumber());
        totalScoreAndquestionFacade.questionsAndScoresUpdate(dto.getQuizId(), QuestionOperationConstants.MODIFY, quizStatisticsParameter);

        questionGroupDao.update(dto);
        quizItemDao.updateByTargetIdAndType(getQuizItemInfor(dto, QuestionOperationConstants.MODIFY));
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionGroup/deletes 依据试题组id删除
     * @apiDescription 试题组删除
     * @apiName questionGroupDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 试题组ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除问题选项ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {

            //更新测验表中的问题总数和分值
            final QuestionGroup questionGroup = questionGroupDao.get(id);
            QuizStatisticsParameter quizStatisticsParameter = new QuizStatisticsParameter();
            quizStatisticsParameter.setLastQuestions(questionGroup.getPickQuestionNumber());
            quizStatisticsParameter.setCurrentQuestions(0);
            quizStatisticsParameter.setLastScore(questionGroup.getPickQuestionNumber() * questionGroup.getEachQuestionScore());
            quizStatisticsParameter.setCurrentScore(0);
            totalScoreAndquestionFacade.questionsAndScoresUpdate(questionGroup.getQuizId(), QuestionOperationConstants.DELETE, quizStatisticsParameter);

            /*删除组里的所有问题信息开始*/
            List<QuestionVO> questionVOList = questionDao.getByGroupId(id);
            for (QuestionVO questionVO : questionVOList) {
                //更新测验表中的问题总数和分值
                quizStatisticsParameter.setLastQuestions(1);
                quizStatisticsParameter.setCurrentQuestions(0);
                quizStatisticsParameter.setLastScore(questionVO.getScore());
                quizStatisticsParameter.setCurrentScore(0);
                totalScoreAndquestionFacade.questionsAndScoresUpdate(questionVO.getQuizItem().getQuizId(), QuestionOperationConstants.DELETE, quizStatisticsParameter);
                Question question = questionDao.get(id);
                //删除问题对应子项记录
                questionOperation.delete(question);
                //删除问题组后，所有问题和问题组重新排序
                seqMoveService.seqMove(SeqConstants.DELETE, questionVO.getQuizItem().getQuizId(), SeqConstants.QUESTION_TYPE_GROUP, id, id);
                //删除测验问题项记录
                quizItemDao.deleteByTargetIdAndType(getQuestionDTOInfor(question));
                //删除问题对应id记录
                questionDao.delete(id);

            }
            /*删除组里的所有问题信息结束*/


            //删除测验问题项关联记录
            QuizItem quizItem = new QuizItem();
            quizItem.setTargetId(id);
            quizItem.setType(QuizItemTypeEnum.QUESTION_GROUP.getType());
            quizItemDao.deleteByTargetIdAndType(quizItem);

            //删除对应id的问题组记录
            questionGroupDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));
    }


}
