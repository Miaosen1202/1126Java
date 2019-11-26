package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.business.quiz.question.QuestionOperation;
import com.wdcloud.lms.core.base.dao.QuizQuestionRecordDao;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 功能：测验答题问题记录接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_RECORD)
public class QuizQuestionRecordEdit implements IDataEditComponent {

    @Autowired
    private QuizQuestionRecordDao quizQuestionRecordDao;
    @Autowired
    private QuestionOperation questionOperation;

    /**
     * @api {post} /quizQuestionRecord/add 测验答题问题记录添加
     * @apiDescription 测验问题记录添加
     * @apiName quizQuestionRecordAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizRecordId  测验记录ID
     * @apiParam {Number} questionId 问题ID
     * @apiParam {Number} groupId  问题组ID
     * @apiParam {Number} type  问题类型
     * @apiParam {String} title  标题
     * @apiParam {String} [correctComment]  正确提示
     * @apiParam {String} [wrongComment]  错误提示
     * @apiParam {String} [generalComment]  通用提示
     * @apiParam {Number} score 分值
     * @apiParam {Number} seq 排序
     * @apiParam {Number} gradeScore 得分
     * @apiParam {Object[]} [options] 选择题（单选、多选、判断题用）
     * @apiParam {Number} [options.questionOptionId] 问题选项ID
     * @apiParam {String} [options.code] 题干中占位符
     * @apiParam {String} [options.content] 选项内容（判断题内容传 True/ False）
     * @apiParam {String} [options.explanation] 选择该项的提示
     * @apiParam {Number=0,1} [options.isCorrect] 选择题：是否为正确选项;0，否；1，是；
     * @apiParam {Number} [options.seq] 排序；
     * @apiParam {Number=0,1} [options.isChoice] 是否选中: 0，否；1，是；
     * @apiParam {Object[]} [matchoptions] 匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）
     * @apiParam {Number} [matchoptions.questionMatchOptionId] 匹配类问题选项ID
     * @apiParam {Number} [matchoptions.quizQuestionRecordId] 问题记录ID
     * @apiParam {Number} [matchoptions.quizQuestionOptionRecordId] 选项ID
     * @apiParam {String} [matchoptions.content] 选项内容（匹配项中的一项为空传【null】)
     * @apiParam {Number} [matchoptions.seq] 排序；
     * @apiParam {Object} [reply] 简答、填空题
     * @apiParam {Number} [reply.quizQuestionRecordId] 问题记录ID
     * @apiParam {String} [reply.reply] 回答内容
     * @apiExample {json} 请求示例:
     * {
     * "quizRecordId": "16",
     * "questionId": "16",
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
     * "gradeScore": "10",
     * "seq": "11",
     * "options":[{"questionOptionId":"1","code":"1","content":"2","explanation":"3","isCorrect":"4","seq":"5"}]
     * <p>
     * <p>
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 新增问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "4"
     * }
     */
    @Override
    @ValidationParam(clazz = QuizQuestionRecordDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizQuestionRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionRecordDTO.class);
        float score = questionOperation.calculatedScore(dto);
        //设置位数
        int scale = 0;
        //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        int roundingMode = 4;
        BigDecimal bd = new BigDecimal((double) score);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        score = bd.floatValue();
        dto.setGradeScore((int) score);
        quizQuestionRecordDao.save(dto);
        /*保存问题附加项*/
        questionOperation.saveRecord(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionRecord/modify 测验答题问题记录修改
     * @apiDescription 测验问题选项记录修改
     * @apiName quizQuestionRecordModify
     * @apiGroup Quiz
     * @apiParam {Number} id 测验问题记录ID
     * @apiParam {Number} quizRecordId  测验记录ID
     * @apiParam {Number} questionId 问题ID
     * @apiParam {Number} groupId  问题组ID
     * @apiParam {Number} type  问题类型
     * @apiParam {String} title  标题
     * @apiParam {String} [correctComment]  正确提示
     * @apiParam {String} [wrongComment]  错误提示
     * @apiParam {String} [generalComment]  通用提示
     * @apiParam {Number} score 分值
     * @apiParam {Number} seq 排序
     * @apiParam {Number} gradeScore 得分
     * @apiParam {Object[]} [options] 选择题（单选、多选、判断题用）
     * @apiParam {Number} [options.questionOptionId] 问题选项ID
     * @apiParam {String} [options.code] 题干中占位符
     * @apiParam {String} [options.content] 选项内容（判断题内容传 True/ False）
     * @apiParam {String} [options.explanation] 选择该项的提示
     * @apiParam {Number=0,1} [options.isCorrect] 选择题：是否为正确选项;0，否；1，是；
     * @apiParam {Number} [options.seq] 排序；
     * @apiParam {Number=0,1} [options.isChoice] 是否选中: 0，否；1，是；
     * @apiParam {Object[]} [matchoptions] 匹配题（总数必须为偶数并且相邻的2条记录为一组匹配项）
     * @apiParam {Number} [matchoptions.questionMatchOptionId] 匹配类问题选项ID
     * @apiParam {Number} [matchoptions.quizQuestionRecordId] 问题记录ID
     * @apiParam {Number} [matchoptions.quizQuestionOptionRecordId] 选项ID
     * @apiParam {String} [matchoptions.content] 选项内容（匹配项中的一项为空传【null】)
     * @apiParam {Number} [matchoptions.seq] 排序；
     * @apiParam {Object} [quizQuestionReplyRecord] 简答、填空题
     * @apiParam {Number} [quizQuestionReplyRecord.quizQuestionRecordId] 问题记录ID
     * @apiParam {String} [quizQuestionReplyRecord.reply] 回答内容
     * @apiExample {json} 请求示例:
     * {
     * "id": "5",
     * "quizRecordId": "16",
     * "questionId": "16",
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
     * "gradeScore": "10",
     * "seq": "11",
     * "options":[{"questionOptionId":"1","code":"1","content":"2","explanation":"3","isCorrect":"4","seq":"5"}]
     * <p>
     * <p>
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "5"
     * }
     */
    @Override
    @ValidationParam(clazz = QuizQuestionRecordDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizQuestionRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionRecordDTO.class);
        float score = questionOperation.calculatedScore(dto);
        //设置位数
        int scale = 0;
        //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        int roundingMode = 4;
        BigDecimal bd = new BigDecimal((double) score);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        score = bd.floatValue();
        dto.setGradeScore((int) score);
        quizQuestionRecordDao.update(dto);
        /*更新问题附加项*/
        questionOperation.updateRecord(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionRecord/deletes 依据测验问题记录id删除【暂时未使用】
     * @apiDescription 测验问题选项记录删除
     * @apiName quizQuestionOptionRecordDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 测验问题记录ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除测验问题记录ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "[49]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {
            //删除对应id记录
            quizQuestionRecordDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));


    }


}
