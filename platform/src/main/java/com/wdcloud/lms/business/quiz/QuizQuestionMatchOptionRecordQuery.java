package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuizQuestionMatchOptionRecordDao;
import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现匹配类问题选项选择记录查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_MATCH_OPTION_RECORD, description = "匹配类问题选项选择记录表")
public class QuizQuestionMatchOptionRecordQuery implements IDataQueryComponent<QuizQuestionMatchOptionRecord> {
    @Autowired
    private QuizQuestionMatchOptionRecordDao quizQuestionMatchOptionRecordDao;

    /**
     * @api {get} /quizQuestionMatchOptionRecord/list 匹配类问题选项选择记录列表
     * @apiDescription 依据id查询对应的匹配类问题选项选择记录信息
     * @apiName quizQuestionMatchOptionRecordList
     * @apiGroup Quiz
     * @apiParam {String} id ID列表
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除问题选项列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1,2,3]"
     * }
     */
    @Override
    public List<? extends QuizQuestionMatchOptionRecord> list(Map<String, String> param) {
        QuizQuestionMatchOptionRecord quizQuestionMatchOptionRecord = new QuizQuestionMatchOptionRecord();
        quizQuestionMatchOptionRecord.setId(Long.valueOf(param.get("id")));
        return quizQuestionMatchOptionRecordDao.find(quizQuestionMatchOptionRecord);
    }

    @Override
    public PageQueryResult<? extends QuizQuestionMatchOptionRecord> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /quizQuestionMatchOptionRecord/find 匹配类问题选项选择记录查找
     * @apiDescription 匹配类问题选项选择记录查找
     * @apiName quizQuestionMatchOptionRecordFind
     * @apiGroup Quiz
     * @apiParam {String} id 匹配类问题选项选择记录ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1]"
     * }
     */
    @Override
    public QuizQuestionMatchOptionRecord find(String id) {

        return null;
    }
}
