package com.wdcloud.lms.business.quiz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.vo.QuizVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 功能：实现测验查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_INFOR, description = "测验表")
public class QuizInforQuery implements IDataQueryComponent<Quiz> {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private RoleService roleService;
    /**
     * @param param
     * @return
     * @api {get} /quizInfor/list 测验信息列表
     * @apiDescription 依据id查询对应id的测验信息
     * @apiName quizList
     * @apiGroup Quiz
     * @apiParam {String} id ID列表
     * @apiExample 请求示例:
     * /quizInfor/list?id=52
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除问题选项列表字符串
     * @apiSuccessExample {json} 响应示例：
     * <p>
     * {"code":200,"entity":[{"accessCode":"17","allowAnonymous":6,"allowMultiAttempt":9,"assignmentGroupId":4,"attemptNumber":10,"courseId":2,"createTime":1551110721000,"createUserId":2,"description":"描述","filterIpAddress":"19","id":52,"isFilterIp":18,"isLockRepliedQuestion":15,"isNeedAccessCode":16,"isShuffleAnswer":7,"score":5,"scoreType":11,"showAnswerEndTime":1545103875000,"showAnswerStartTime":1545103875000,"showAnswerStrategy":13,"showQuestionStrategy":14,"showReplyStrategy":12,"status":21,"timeLimit":8,"title":"名称","totalQuestions":22,"totalScore":23,"type":3,"updateTime":1551110721000,"updateUserId":2,"version":20}],"message":"common.success"}
     */
    @Override
    public List<? extends Quiz> list(Map<String, String> param) {
        Quiz quiz = new Quiz();
        quiz.setId(Long.valueOf(param.get("id")));
        return quizDao.find(quiz);
    }

    /**
     * @api {get} /quizInfor/pageList 测验分页信息显示
     * @apiName quizPageList
     * @apiGroup Quiz
     * @apiParam {String} [title] 标题（可选填）
     * @apiParam {String} [description] 描述（可选填）
     * @apiParam {Number} courseId 课程ID（必填）
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     * @apiExample 请求示例:
     * /quizInfor/pageList?courseId=1
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccessExample {json} 响应示例：
     * <p>
     * {
     * "code": 200,
     * "entity": {
     * "list": [
     * {
     * "accessCode": "cc",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 97,
     * "originId": 161,
     * "originType": 3
     * }
     * ],
     * "attemptNumber": 4,
     * "createTime": 1551451128000,
     * "description": "<p>测试修改2</p>",
     * "filterIpAddress": "",
     * "id": 161,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "score": 34,
     * "scoreType": 1,
     * "showAnswerEndTime": 1551628920000,
     * "showAnswerStartTime": 1551456120000,
     * "showAnswerStrategy": 2,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 2,
     * "status": 0,
     * "timeLimit": 4,
     * "title": "测试修改234323",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "type": 3,
     * "updateTime": 1551462301000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1553702400000,
     * "id": 90,
     * "originId": 160,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551450731000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 160,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "ererwer",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551450731000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 87,
     * "originId": 156,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551439963000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 156,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "12313",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551439963000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551283320000,
     * "id": 85,
     * "originId": 154,
     * "originType": 3,
     * "startTime": 1549382402000
     * }
     * ],
     * "createTime": 1551375762000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 154,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "rr",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551375762000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 84,
     * "originId": 153,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551375223000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 153,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "rreer",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551379732000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 81,
     * "originId": 150,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551373536000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 150,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "rtrt",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551380139000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 80,
     * "originId": 149,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551372736000,
     * "description": "<p>qweqwe</p>",
     * "filterIpAddress": "",
     * "id": 149,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "qweqwe",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551375334000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551196800000,
     * "id": 78,
     * "limitTime": 1551110400000,
     * "originId": 146,
     * "originType": 3,
     * "startTime": 1550592000000
     * }
     * ],
     * "createTime": 1551366461000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 146,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "ww",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551375427000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551196800000,
     * "id": 77,
     * "originId": 145,
     * "originType": 3,
     * "startTime": 1550592000000
     * }
     * ],
     * "createTime": 1551366447000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 145,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "ww",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551366447000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 76,
     * "originId": 144,
     * "originType": 3,
     * "startTime": 1550592000000
     * }
     * ],
     * "createTime": 1551366425000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 144,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "ww",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551375688000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551283200000,
     * "id": 75,
     * "limitTime": 1551196800000,
     * "originId": 143,
     * "originType": 3,
     * "startTime": 1551110400000
     * }
     * ],
     * "createTime": 1551363736000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 143,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "测试",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551363736000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "17",
     * "allowAnonymous": 6,
     * "allowMultiAttempt": 9,
     * "assignmentGroupId": 4,
     * "assigns": [
     * {
     * "assignId": 2,
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1546924104000,
     * "id": 74,
     * "limitTime": 1546906104000,
     * "originId": 142,
     * "originType": 3,
     * "startTime": 1546823304000
     * }
     * ],
     * "attemptNumber": 10,
     * "createTime": 1551359243000,
     * "description": "描述",
     * "filterIpAddress": "19",
     * "id": 142,
     * "isFilterIp": 18,
     * "isLockRepliedQuestion": 15,
     * "isNeedAccessCode": 16,
     * "isShuffleAnswer": 7,
     * "score": 5,
     * "scoreType": 11,
     * "showAnswerEndTime": 1545103875000,
     * "showAnswerStartTime": 1545103875000,
     * "showAnswerStrategy": 13,
     * "showQuestionStrategy": 14,
     * "showReplyStrategy": 12,
     * "status": 21,
     * "timeLimit": 8,
     * "title": "名称",
     * "totalQuestions": 22,
     * "totalScore": 22,
     * "type": 3,
     * "updateTime": 1551359243000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 73,
     * "originId": 136,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551351477000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 136,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "123123",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551351477000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 72,
     * "originId": 135,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551351451000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 135,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "123123",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551351451000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 71,
     * "originId": 134,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551349880000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 134,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 22,
     * "title": "uu",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551349880000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 70,
     * "originId": 133,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551349239000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 133,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 22,
     * "title": "12123",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551349239000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 69,
     * "originId": 132,
     * "originType": 3
     * }
     * ],
     * "createTime": 1551349225000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 132,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 22,
     * "title": "12123",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "updateTime": 1551349225000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "tt",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 1,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551110400000,
     * "id": 68,
     * "limitTime": 1551024000000,
     * "originId": 127,
     * "originType": 3,
     * "startTime": 1550937600000
     * }
     * ],
     * "attemptNumber": 7,
     * "createTime": 1551349086000,
     * "description": "<p>1312</p>",
     * "filterIpAddress": "333",
     * "id": 127,
     * "isFilterIp": 1,
     * "isLockRepliedQuestion": 1,
     * "isNeedAccessCode": 1,
     * "isShuffleAnswer": 1,
     * "scoreType": 2,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 1,
     * "showReplyStrategy": 0,
     * "status": 22,
     * "timeLimit": 2,
     * "title": "1213",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "type": 1,
     * "updateTime": 1551349086000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "uu",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551283200000,
     * "id": 67,
     * "limitTime": 1551196800000,
     * "originId": 117,
     * "originType": 3,
     * "startTime": 1551024000000
     * }
     * ],
     * "createTime": 1551348589000,
     * "description": "<p>测试专用1</p>",
     * "filterIpAddress": "123",
     * "id": 117,
     * "isFilterIp": 1,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 1,
     * "isShuffleAnswer": 1,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 22,
     * "timeLimit": 7,
     * "title": "测试专用1",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "type": 1,
     * "updateTime": 1551348589000,
     * "updateUserId": 2,
     * "version": 20
     * },
     * {
     * "accessCode": "uu",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1551283200000,
     * "id": 66,
     * "limitTime": 1551196800000,
     * "originId": 116,
     * "originType": 3,
     * "startTime": 1551024000000
     * }
     * ],
     * "createTime": 1551348569000,
     * "description": "<p>测试专用1</p>",
     * "filterIpAddress": "123",
     * "id": 116,
     * "isFilterIp": 1,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 1,
     * "isShuffleAnswer": 1,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 22,
     * "timeLimit": 7,
     * "title": "测试专用1",
     * "totalQuestions": 2,
     * "totalScore": 23,
     * "type": 1,
     * "updateTime": 1551348569000,
     * "updateUserId": 2,
     * "version": 20
     * }
     * ],
     * "pageIndex": 1,
     * "pageSize": 20,
     * "total": 25
     * },
     * "message": "common.success"
     * }
     */
    @Override
    public PageQueryResult<? extends QuizVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> dataParam = Maps.newHashMap();
        final String title = param.get("title");
        if (StringUtil.isNotEmpty(title)) {
            dataParam.put("title", title);
        }
        final String description = param.get("description");
        if (StringUtil.isNotEmpty(description)) {
            dataParam.put("description", description);
        }
        final String courseId = param.get("courseId");
        if (StringUtil.isNotEmpty(courseId)) {
            dataParam.put("courseId", courseId);
        }
        dataParam.put("type", OriginTypeEnum.QUIZ.getType());
        dataParam.put("createUserId", WebContext.getUserId());
        PageHelper.startPage(pageIndex, pageSize);
        Page<QuizVO> resources = (Page<QuizVO>) quizDao.search(dataParam);
        return new PageQueryResult<>(resources.getTotal(), resources.getResult(), pageSize, pageIndex);


    }

    /**
     * @api {get} /quizInfor/get 测验信息查找
     * @apiDescription 测验信息查找
     * @apiName quizInforFind
     * @apiGroup Quiz
     * @apiParam {String} id 测验ID
     * @apiExample {json} 请求示例:
     * http://localhost:8080/quizInfor/get?data=492
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "id": 427,
     * "originId": 492,
     * "originType": 3
     * }
     * ],
     * "createTime": 1552408640000,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 492,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 0,
     * "title": "Unnamed quiz",
     * "totalQuestions": 0,
     * "totalScore": 23,
     * "type": 1,
     * "updateTime": 1552408640000,
     * "updateUserId": 1,
     * "version": 20
     * },
     * "message": "common.success"
     * }
     */
    @Override
    public QuizVO find(String id) {
        Long quizId = Long.valueOf(id);
        QuizVO quizVO=new QuizVO();
        if (quizId == null) {
            throw new BaseException("quiz.id.null");
        }

        if(roleService.hasStudentRole())
        {
            quizVO= quizDao.getStudentById(quizId,WebContext.getUserId());
            quizVO.setServerTime(new Date());
            return quizVO;
        }

        quizVO= quizDao.getById(quizId);
        quizVO.setServerTime(new Date());
        return quizVO;

    }
}
