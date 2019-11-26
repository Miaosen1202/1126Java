package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuestionOptionDao;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现问题选项表信息查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_OPTION)
public class QuestionOptionQuery implements IDataQueryComponent<QuestionOption> {

    @Autowired
    private QuestionOptionDao questionOptionDao;

    /**
     * @api {get} /questionOption/list 问题选项列表
     * @apiDescription 问题选项列表
     * @apiName questionOptionList
     * @apiGroup Quiz
     * @apiParam {String[]} ids ID列表
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
    public List<? extends QuestionOption> list(Map<String, String> param) {
        QuestionOption questionOption = new QuestionOption();
        questionOption.setId(Long.valueOf(param.get("id")));
        return questionOptionDao.find(questionOption);
    }

    @Override
    public PageQueryResult<? extends QuestionOption> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        return null;
    }

    /**
     * @api {get} /questionOption/find 问题选项查找
     * @apiDescription 问题选项查找
     * @apiName questionOptionFind
     * @apiGroup Quiz
     * @apiParam {String} id 问题选项ID
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
    public QuestionOption find(String id) {
        return null;
    }
}
