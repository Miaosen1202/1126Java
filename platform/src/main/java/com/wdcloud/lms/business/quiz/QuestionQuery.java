package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
import com.wdcloud.lms.core.base.model.QuizItem;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现测试问题表信息查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION)
public class QuestionQuery implements IDataQueryComponent<QuizItem> {

    @Autowired
    private QuizItemDao quizItemDao;

    /**
     * @api {get} /question/list 测验问题列表
     * @apiDescription 测验问题列表
     * @apiName questionList
     * @apiGroup Quiz
     * @apiParam {String} id 问题ID
     * @apiExample {json} 请求示例:
     * http://localhost:8080/question/list?id=16
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除测验问题列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": [
     * {
     * "createTime": 1552085638000,
     * "createUserId": 1,
     * "id": 154,
     * "question": {
     * "content": "<p>45464</p>",
     * "correctComment": "",
     * "courseId": 1,
     * "createTime": 1552085638000,
     * "createUserId": 1,
     * "generalComment": "",
     * "groupId": 2,
     * "id": 211,
     * "isTemplate": 0,
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "0",
     * "content": "555",
     * "createTime": 1552085638000,
     * "createUserId": 1,
     * "explanation": "0",
     * "id": 245,
     * "isCorrect": 0,
     * "questionId": 211,
     * "seq": 0,
     * "updateTime": 1552085638000,
     * "updateUserId": 1
     * }
     * ],
     * "questionBankId": 0,
     * "questionTemplateId": 0,
     * "score": 5,
     * "seq": 0,
     * "title": "Unnamed quiz",
     * "type": 1,
     * "updateTime": 1552085638000,
     * "updateUserId": 1,
     * "wrongComment": ""
     * },
     * "quizId": 463,
     * "seq": 1,
     * "targetId": 211,
     * "type": 1,
     * "updateTime": 1552085638000,
     * "updateUserId": 1
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends QuizItem> list(Map<String, String> param) {

        return quizItemDao.getQuerstionByQuestionId(Long.valueOf(param.get("id")));
    }

    @Override
    public PageQueryResult<? extends QuizItem> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        return null;
    }

    /**
     * @api {get} /question/find 测验问题查找
     * @apiDescription 测验问题查找
     * @apiName questionFind
     * @apiGroup Quiz
     * @apiParam {String} id 测验问题ID
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
    public QuizItem find(String id) {
        return null;
    }
}
