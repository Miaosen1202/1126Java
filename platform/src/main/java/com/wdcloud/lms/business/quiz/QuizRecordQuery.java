package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.QuizRecordDao;
import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.lms.core.base.vo.QuizRecordVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：测验答题记录查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_RECORD, description = "测验记录")
public class QuizRecordQuery implements IDataQueryComponent<QuizRecordVO> {
    @Autowired
    private QuizRecordDao quizRecordDao;

    /**
     * @api {get} /quizRecord/list 测验答题记录列表
     * @apiDescription 依据id查询对应的测验记录信息
     * @apiName quizRecordList
     * @apiGroup Quiz
     * @apiParam {String} quizId 测验ID
     * @apiExample 请求示例:
     * http://localhost:8080//quizRecord/list?quizId=13
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除测验记录列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": [
     * {
     * "createTime": 1551462661000,
     * "createUserId": 2,
     * "dueTime": 1551429897000,
     * "id": 1,
     * "isLastTime": 0,
     * "isSubmit": 0,
     * "quizId": 13,
     * "quizVersion": 1,
     * "startTime": 1551429897000,
     * "submitTime": 1551429897000,
     * "testerId": 2,
     * "updateTime": 1551462797000,
     * "updateUserId": 2
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends QuizRecordVO> list(Map<String, String> param) {
        QuizRecord quizRecord = new QuizRecord();
        quizRecord.setQuizId(Long.valueOf(param.get("quizId")));
        quizRecord.setCreateUserId(WebContext.getUserId());
        return quizRecordDao.getQuizRecords(quizRecord);

    }

    @Override
    public PageQueryResult<? extends QuizRecordVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /quizRecord/find 测验记录查找
     * @apiDescription 测验记录查找
     * @apiName quizRecordFind
     * @apiGroup Quiz
     * @apiParam {String} id 测验记录ID
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
    public QuizRecordVO find(String id) {

        return null;
    }
}
