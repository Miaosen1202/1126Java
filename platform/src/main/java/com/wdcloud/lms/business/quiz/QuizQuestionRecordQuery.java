package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.question.QuizService;
import com.wdcloud.lms.core.base.dao.QuizQuestionRecordDao;
import com.wdcloud.lms.core.base.model.QuizQuestionRecord;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：测验问题记录查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_RECORD, description = "测验问题记录")
public class QuizQuestionRecordQuery implements IDataQueryComponent<QuizQuestionRecord> {
    @Autowired
    private QuizQuestionRecordDao quizQuestionRecordDao;
    @Autowired
    private QuizService quizService;

    /**
     * @api {get} /quizQuestionRecord/list 学生查看正误测验问题选项记录列表
     * @apiDescription 依据quizRecordId查询对应的所有测验问题选项记录信息
     * @apiName quizQuestionRecordList
     * @apiGroup Quiz
     * @apiParam String quizRecordId quizRecordID
     * @apiExample {json} 请求示例:
     * http://localhost:8080//quizQuestionRecord/list?quizRecordId=16
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除问题选项列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": [
     * {
     * "correctComment": "4",
     * "createTime": 1551458697000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "gradeScore": 10,
     * "groupId": 2,
     * "id": 4,
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551458697000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 1,
     * "isCorrect": 4,
     * "questionOptionId": 1,
     * "quizQuestionRecordId": 16,
     * "seq": 5,
     * "updateTime": 1551460677000,
     * "updateUserId": 2
     * },
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551458835000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 2,
     * "isCorrect": 4,
     * "questionOptionId": 1,
     * "quizQuestionRecordId": 16,
     * "seq": 5,
     * "updateTime": 1551460677000,
     * "updateUserId": 2
     * }
     * ],
     * "questionId": 16,
     * "quizRecordId": 16,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551458697000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * },
     * {
     * "correctComment": "4",
     * "createTime": 1551458835000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "gradeScore": 10,
     * "groupId": 2,
     * "id": 5,
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551458697000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 1,
     * "isCorrect": 4,
     * "questionOptionId": 1,
     * "quizQuestionRecordId": 16,
     * "seq": 5,
     * "updateTime": 1551460677000,
     * "updateUserId": 2
     * },
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551458835000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 2,
     * "isCorrect": 4,
     * "questionOptionId": 1,
     * "quizQuestionRecordId": 16,
     * "seq": 5,
     * "updateTime": 1551460677000,
     * "updateUserId": 2
     * }
     * ],
     * "questionId": 16,
     * "quizRecordId": 16,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551458937000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends QuizQuestionRecord> list(Map<String, String> param) {
        QuizQuestionRecord quizQuestionRecord = new QuizQuestionRecord();
        quizQuestionRecord.setQuizRecordId(Long.valueOf(param.get("quizRecordId")));
         return quizQuestionRecordDao.getQuerstionRecords(quizQuestionRecord);
    }

    @Override
    public PageQueryResult<? extends QuizQuestionRecord> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @param id
     * @return
     * @api {get} /quizQuestionRecord/find 测验问题选项记录查找
     * @apiDescription 测验问题选项记录查找
     * @apiName quizQuestionRecordFind
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
    public QuizQuestionRecord find(String id) {

        return null;
    }
}
