package com.wdcloud.lms.business.quiz;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.enums.QuizeOpenStatusEnum;
import com.wdcloud.lms.business.quiz.question.QuizService;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
import com.wdcloud.lms.core.base.model.QuizItem;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能：实现测验问题项查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_ITEM, description = "测验问题项表")
public class QuizItemQuery implements IDataQueryComponent<QuizItem> {
    @Autowired
    private QuizItemDao quizItemDao;
    @Autowired
    private QuizService quizService;
    @Autowired
    private HttpServletRequest request;

    /**
      * @api {get} /quizItem/list 测验问题项列表
     * @apiDescription 依据id查询对应的测验问题项信息
     * @apiName quizQuestionList
     * @apiGroup Quiz
     * @apiParam {String} quizId 测验ID
     * @apiParam {String} accessCode 访问码 (如果没有设置可以为空)
     * @apiExample {json} 请求示例:
     * http://localhost:8080/quizItem/list?quizId=16
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除问题选项列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": [
     * {
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551455180000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 105,
     * "isCorrect": 4,
     * "questionId": 91,
     * "seq": 5,
     * "updateTime": 1551455180000,
     * "updateUserId": 2
     * }
     * ],
     * "question": {
     * "content": "内容",
     * "correctComment": "4",
     * "courseId": 1,
     * "createTime": 1551455180000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "groupId": 2,
     * "id": 91,
     * "isTemplate": 7,
     * "questionBankId": 8,
     * "questionTemplateId": 9,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551455180000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * }
     * },
     * {
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551455233000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 106,
     * "isCorrect": 4,
     * "questionId": 93,
     * "seq": 5,
     * "updateTime": 1551455233000,
     * "updateUserId": 2
     * }
     * ],
     * "question": {
     * "content": "内容",
     * "correctComment": "4",
     * "courseId": 1,
     * "createTime": 1551455233000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "groupId": 2,
     * "id": 93,
     * "isTemplate": 7,
     * "questionBankId": 8,
     * "questionTemplateId": 9,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551455233000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * }
     * },
     * {
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551455658000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 107,
     * "isCorrect": 4,
     * "questionId": 94,
     * "seq": 5,
     * "updateTime": 1551455658000,
     * "updateUserId": 2
     * }
     * ],
     * "question": {
     * "correctComment": "4",
     * "courseId": 1,
     * "createTime": 1551455658000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "groupId": 2,
     * "id": 94,
     * "isTemplate": 7,
     * "questionBankId": 8,
     * "questionTemplateId": 9,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551455658000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * }
     * },
     * {
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551455689000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 108,
     * "isCorrect": 4,
     * "questionId": 95,
     * "seq": 5,
     * "updateTime": 1551455689000,
     * "updateUserId": 2
     * }
     * ],
     * "question": {
     * "content": "内容",
     * "courseId": 1,
     * "createTime": 1551455688000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "groupId": 2,
     * "id": 95,
     * "isTemplate": 7,
     * "questionBankId": 8,
     * "questionTemplateId": 9,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551455688000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * }
     * },
     * {
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551455720000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 109,
     * "isCorrect": 4,
     * "questionId": 96,
     * "seq": 5,
     * "updateTime": 1551455720000,
     * "updateUserId": 2
     * }
     * ],
     * "question": {
     * "content": "内容",
     * "correctComment": "4",
     * "courseId": 1,
     * "createTime": 1551455720000,
     * "createUserId": 2,
     * "generalComment": "6",
     * "groupId": 2,
     * "id": 96,
     * "isTemplate": 7,
     * "questionBankId": 8,
     * "questionTemplateId": 9,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551455720000,
     * "updateUserId": 2
     * }
     * },
     * {
     * "matchoptions": [],
     * "options": [
     * {
     * "code": "1",
     * "content": "2",
     * "createTime": 1551455749000,
     * "createUserId": 2,
     * "explanation": "3",
     * "id": 110,
     * "isCorrect": 4,
     * "questionId": 97,
     * "seq": 5,
     * "updateTime": 1551455749000,
     * "updateUserId": 2
     * }
     * ],
     * "question": {
     * "content": "内容",
     * "correctComment": "4",
     * "courseId": 1,
     * "createTime": 1551455749000,
     * "createUserId": 2,
     * "groupId": 2,
     * "id": 97,
     * "isTemplate": 7,
     * "questionBankId": 8,
     * "questionTemplateId": 9,
     * "score": 10,
     * "seq": 11,
     * "title": "名称",
     * "type": 1,
     * "updateTime": 1551455749000,
     * "updateUserId": 2,
     * "wrongComment": "5"
     * }
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends QuizItemVO> list(Map<String, String> param) {
        List<QuizItemVO> quizItemVOs = null;
        long quizId = Long.valueOf(param.get("quizId"));
        String accessCode = param.get("accessCode");
        String ip = quizService.getIpAddress(request);
        QuizeOpenStatusEnum status=quizService.getAuthorization(quizId, ip, accessCode);
        if (status== QuizeOpenStatusEnum.PERMIT) {
            quizItemVOs = quizItemDao.getQuerstionAllInfors(quizId);
        }
        else
        {
            throw new BaseException("reason.error",status.getStatus()+"");
        }
        return quizItemVOs;
    }

    @Override
    public PageQueryResult<? extends QuizItem> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @param id
     * @return
     * @api {get} /quizItem/find 测验问题项查找
     * @apiDescription 测验问题项查找
     * @apiName questionBankFind
     * @apiGroup Quiz
     * @apiParam {String} id 题库ID
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
