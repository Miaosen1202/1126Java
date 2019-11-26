package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.quiz.dto.QuestionDTO;
import com.wdcloud.lms.business.quiz.enums.QuizItemTypeEnum;
import com.wdcloud.lms.business.quiz.question.QuestionOperation;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.business.quiz.question.SeqMoveService;
import com.wdcloud.lms.business.quiz.quiztype.QuestionOperationConstants;
import com.wdcloud.lms.business.quiz.quiztype.QuizStatisticsParameter;
import com.wdcloud.lms.business.quiz.quiztype.TotalScoreAndquestionFacade;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.QuestionDao;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.model.QuestionOption;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：实现测试问题信表增、删、改调用接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION)
public class  QuestionEdit implements IDataEditComponent {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionOperation questionOperation;

    @Autowired
    private QuizItemDao quizItemDao;
    @Autowired
    private TotalScoreAndquestionFacade totalScoreAndquestionFacade;
    @Autowired
    private SeqMoveService seqMoveService;

    private QuizItem getQuizItemInfor(QuestionDTO dto, int oprate) {
        QuizItem quizItem = new QuizItem();
        Integer seq = null;
        if (oprate == QuestionOperationConstants.ADD) {
            seq = quizItemDao.getCurrentSeq(dto.getQuizId());
        }
        quizItem.setSeq(seq);
        quizItem.setType(QuizItemTypeEnum.QUESTION.getType());
        quizItem.setTargetId(dto.getId());
        quizItem.setQuizId(dto.getQuizId());
        quizItem.setUpdateUserId(WebContext.getUserId());
        return quizItem;
    }

    private QuizItem getQuestionDTOInfor(Question dto) {
        QuizItem quizItem = new QuizItem();
        //quizItem.setSeq(0);//需要专门算法管理seq
        quizItem.setType(QuizItemTypeEnum.QUESTION.getType());
        quizItem.setTargetId(dto.getId());
        return quizItem;
    }

    /**
     * 功能：在做更新操作时，获取新增加的选项信息
     * @param dto
     * @return
     */
   private QuestionDTO getSaveOption(QuestionDTO dto)
   {
       /**
        * 选择题选项
        */
        List<QuestionOption> options=new ArrayList<>();
       /**
        * 匹配题
        */
        List<QuestionMatchOption> matchoptions=new ArrayList<>();

        if(dto.getOptions()!=null)
        {
            int totalOptions=dto.getOptions().size();
            for(int i=0;i<totalOptions;i++)
            {
                if(dto.getOptions().get(i).getId()==null)
                {
                    options.add(dto.getOptions().get(i)) ;
                }
            }

        }
       if(dto.getMatchoptions()!=null)
       {
           int totalMatchoptions=dto.getMatchoptions().size();
           for(int i=0;i<totalMatchoptions;i++)
           {
               if(dto.getMatchoptions().get(i).getId()==null)
               {
                   matchoptions.add(dto.getMatchoptions().get(i)) ;
               }
           }

       }
        dto.setOptions(options);
       dto.setMatchoptions(matchoptions);
        return  dto;
   }
    /**
     * @api {post} /question/add 测验问题添加
     * @apiDescription 测验问题添加
     * @apiName questionAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizID  测试ID
     * @apiParam {Number} courseID  课程ID
     * @apiParam {Number} groupID   问题组ID   0值表示没有组
     * @apiParam {Number} type     问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题
     * @apiParam {String} title   标题
     * @apiParam {String} [content] 内容
     * @apiParam {String} [correctComment] 正确提示
     * @apiParam {String} [wrongComment] 错误提示
     * @apiParam {String} [generalComment] 通用提示
     * @apiParam {Number} isTemplate 是否为问题模板 0：否， 1:是；
     * @apiParam {Number} questionBankID 为问题模板时表示题库ID  default value 0
     * @apiParam {Number} questionTemplateId 问题来源模板  default value 0
     * @apiParam {Number} score 分值
     * @apiParam {Number} seq 序号   default value 0 （可以随便填写一个数值就行，后台会自动计算序号）
     * @apiParam {Object[]} [options] 选择题（单选、多选、判断题用）
     * @apiParam {String} [options.code] 题干中占位符
     * @apiParam {String} [options.content] 选项内容（判断题内容传 True/ False）
     * @apiParam {String} [options.explanation] 选择该项的提示
     * @apiParam {Number=0,1} [options.isCorrect] 选择题：是否为正确选项;0，否；1，是；
     * @apiParam {Number} [options.seq]   选项序号值
     * @apiParam {Object[]} [matchoptions] 匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）
     * @apiParam {String} [matchoptions.content] 选项内容（匹配项中的一项为空传【null】)
     * @apiParam {String} [matchoptions.explanation] 选择该项的提示
     * @apiParam {String} [matchoptions.seq] 选择该项的提示
     * @apiExample {json} 请求示例:
     * {
     * "quizId": "16",
     * "courseId": "1",
     * "groupID": "2",
     * "type": "1",
     * "title": "名称",
     * "content": "内容",
     * "correctComment": "4",
     * "wrongComment": "5",
     * "generalComment": "6",
     * "isTemplate": "7",
     * "questionBankID": "8",
     * "questionTemplateId": "9",
     * "score": "10",
     * "seq": "11",
     * "options":[{"code":"1","content":"2","explanation":"3","isCorrect":"4","seq":"5"}]
     * <p>
     * <p>
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 新增测验问题ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = QuestionDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuestionDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionDTO.class);
        //问题基本信息添加
        questionDao.save(dto);
        //添加测验问题项表
        quizItemDao.save(getQuizItemInfor(dto, QuestionOperationConstants.ADD));
        //问题附加项信息添加
        questionOperation.save(dto);
        //更新测验表中的问题总数和分值
        QuizStatisticsParameter quizStatisticsParameter = new QuizStatisticsParameter();
        quizStatisticsParameter.setLastQuestions(0);
        quizStatisticsParameter.setCurrentQuestions(1);
        quizStatisticsParameter.setLastScore(0);
        quizStatisticsParameter.setCurrentScore(dto.getScore());
        totalScoreAndquestionFacade.questionsAndScoresUpdate(dto.getQuizId(), QuestionOperationConstants.ADD, quizStatisticsParameter);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /question/modify 测验问题修改
     * @apiDescription 测验问题修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName questionModify
     * @apiGroup Quiz
     * @apiParam {Number} id 测验问题ID
     * @apiParam {Number} quizID  测试ID
     * @apiParam {Number} courseID  课程ID
     * @apiParam {Number} groupID   问题组ID    0值表示没有组
     * @apiParam {Number} type     问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题
     * @apiParam {String} title   标题
     * @apiParam {String} [content] 内容
     * @apiParam {String} [correctComment] 正确提示
     * @apiParam {String} [wrongComment] 错误提示
     * @apiParam {String} [generalComment] 通用提示
     * @apiParam {Number} isTemplate 是否为问题模板 0：否， 1:是；
     * @apiParam {Number} questionBankID 为问题模板时表示题库ID  default value 0
     * @apiParam {Number} questionTemplateId 问题来源模板  default value 0
     * @apiParam {Number} score 分值
     * @apiParam {Number} seq 序号   default value 0 （可以随便填写一个数值就行，后台会自动计算序号）
     * @apiParam {Object[]} [options] 选择题（单选、多选、判断题用）
     * @apiParam {String} [options.code] 题干中占位符
     * @apiParam {String} [options.content] 选项内容（判断题内容传 True/ False）
     * @apiParam {String} [options.explanation] 选择该项的提示
     * @apiParam {Number=0,1} [options.isCorrect] 选择题：是否为正确选项;0，否；1，是；
     * @apiParam {Number} [options.seq]   选项序号值
     * @apiParam {Object[]} [matchoptions] 匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）
     * @apiParam {String} [matchoptions.content] 选项内容（匹配项中的一项为空传【null】)
     * @apiParam {String} [matchoptions.explanation] 选择该项的提示
     * @apiParam {String} [matchoptions.seq] 选项序号值
     * @apiExample {json} 请求示例:
     * {
     * "id": "99",
     * "quizId": "16",
     * "courseId": "1",
     * "groupID": "2",
     * "type": "1",
     * "title": "名称",
     * "content": "内容",
     * "correctComment": "4",
     * "wrongComment": "5",
     * "generalComment": "6",
     * "isTemplate": "7",
     * "questionBankID": "8",
     * "questionTemplateId": "9",
     * "score": "10",
     * "seq": "11",
     * "options":[{"code":"1","content":"2","explanation":"3","isCorrect":"4","seq":"5"}]
     * <p>
     * <p>
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改测验问题ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "99"
     * }
     */
    @Override
    @ValidationParam(clazz = QuestionDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuestionDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionDTO.class);
        //更新测验表中的问题总数和分值
        Question question = questionDao.get(dto.getId());
        if (question == null) {
            throw new BaseException("prop.value.not-exists","question",dto.getId()+"");
        }
        QuizStatisticsParameter quizStatisticsParameter = new QuizStatisticsParameter();
        quizStatisticsParameter.setLastQuestions(1);
        quizStatisticsParameter.setCurrentQuestions(1);
        quizStatisticsParameter.setLastScore(question.getScore());
        quizStatisticsParameter.setCurrentScore(dto.getScore());
        totalScoreAndquestionFacade.questionsAndScoresUpdate(dto.getQuizId(), QuestionOperationConstants.MODIFY, quizStatisticsParameter);
        questionDao.update(dto);
        quizItemDao.updateByTargetIdAndType(getQuizItemInfor(dto, QuestionOperationConstants.MODIFY));
        questionOperation.update(dto);
        //检查新增的选项信息，新增选项添加操作
        QuestionDTO savedto= getSaveOption( dto);
        questionOperation.save(savedto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /question/deletes 测验问题删除
     * @apiDescription 测验问题删除
     * @apiName questionDeletes
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 测验问题ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除测验问题ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "[73,74,75]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {

            QuestionVO questionVO = questionDao.getByquestionId(id);
            if (questionVO == null) {
                throw new BaseException("prop.value.not-exists","questionId",id+"");
            }
            //更新测验表中的问题总数和分值
            QuizStatisticsParameter quizStatisticsParameter = new QuizStatisticsParameter();
            quizStatisticsParameter.setLastQuestions(1);
            quizStatisticsParameter.setCurrentQuestions(0);
            quizStatisticsParameter.setLastScore(questionVO.getScore());
            quizStatisticsParameter.setCurrentScore(0);
            totalScoreAndquestionFacade.questionsAndScoresUpdate(questionVO.getQuizItem().getQuizId(), QuestionOperationConstants.DELETE, quizStatisticsParameter);
            Question question = questionDao.get(id);
            //删除问题对应子项记录
            questionOperation.delete(question);
            //删除问题后所有问题重新编排序号
            seqMoveService.seqMove(SeqConstants.DELETE, questionVO.getQuizItem().getQuizId(), SeqConstants.QUESTION_TYPE_QUESTION, id, id);
            //删除测验问题项记录
            quizItemDao.deleteByTargetIdAndType(getQuestionDTOInfor(question));
            //删除问题对应id记录
            questionDao.delete(id);

        }
        return new LinkedInfo(JSON.toJSONString(ids));

    }
}
