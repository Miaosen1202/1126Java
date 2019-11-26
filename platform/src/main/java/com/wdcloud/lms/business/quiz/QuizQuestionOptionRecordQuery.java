package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuizQuestionOptionRecordDao;
import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现测验问题选项记录查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_OPTION_RECORD, description = "测验问题选项记录表")
public class QuizQuestionOptionRecordQuery implements IDataQueryComponent<QuizQuestionOptionRecord> {
    @Autowired
    private QuizQuestionOptionRecordDao quizQuestionOptionRecordDao;

    /**
     * @api {get} /quizQuestionOptionRecord/list 测验问题选项记录列表
     * @apiDescription 依据id查询对应的测验问题选项记录信息
     * @apiName quizQuestionOptionRecordList
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
    public List<? extends QuizQuestionOptionRecord> list(Map<String, String> param) {
        QuizQuestionOptionRecord quizQuestionOptionRecord = new QuizQuestionOptionRecord();
        quizQuestionOptionRecord.setId(Long.valueOf(param.get("id")));
        return quizQuestionOptionRecordDao.find(quizQuestionOptionRecord);
    }

    @Override
    public PageQueryResult<? extends QuizQuestionOptionRecord> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /quizQuestionOptionRecord/find 测验问题选项记录查找
     * @apiDescription 测验问题选项记录查找
     * @apiName quizQuestionOptionRecordFind
     * @apiGroup Quiz
     * @apiParam {String} id 测验问题选项记录ID
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
    public QuizQuestionOptionRecord find(String id) {

        return null;
    }
}
