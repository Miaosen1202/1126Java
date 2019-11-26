package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuizQuestionReplyRecordDao;
import com.wdcloud.lms.core.base.model.QuizQuestionReplyRecord;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：填空类问题回答查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_REPLY_RECORD, description = "填空类问题回答")
public class QuizQuestionReplyRecordQuery implements IDataQueryComponent<QuizQuestionReplyRecord> {
    @Autowired
    private QuizQuestionReplyRecordDao quizQuestionReplyRecordDao;

    /**
     * @api {get} /quizQuestionReplyRecord/list 填空类问题回答列表
     * @apiDescription 依据id查询对应的填空类问题回答信息
     * @apiName quizQuestionReplyRecordList
     * @apiGroup Quiz
     * @apiParam {String} id ID列表
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除填空类问题回答列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1,2,3]"
     * }
     */
    @Override
    public List<? extends QuizQuestionReplyRecord> list(Map<String, String> param) {
        QuizQuestionReplyRecord quizQuestionReplyRecord = new QuizQuestionReplyRecord();
        quizQuestionReplyRecord.setId(Long.valueOf(param.get("id")));
        return quizQuestionReplyRecordDao.find(quizQuestionReplyRecord);
    }

    @Override
    public PageQueryResult<? extends QuizQuestionReplyRecord> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /quizQuestionReplyRecord/find 填空类问题回答查找
     * @apiDescription 填空类问题回答查找
     * @apiName quizQuestionRecordFind
     * @apiGroup Quiz
     * @apiParam {String} id 填空类问题回答ID
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
    public QuizQuestionReplyRecord find(String id) {

        return null;
    }
}
