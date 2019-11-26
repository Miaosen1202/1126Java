package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuestionBankDao;
import com.wdcloud.lms.core.base.model.QuestionBank;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现题库信息查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_BANK, description = "题库信息表")
public class QuestionBankQuery implements IDataQueryComponent<QuestionBank> {
    @Autowired
    private QuestionBankDao questionBankDao;

    /**
     * @api {get} /questionBank/list 题库列表
     * @apiDescription 依据id查询对应的题库信息
     * @apiName questionOptionList
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
    public List<? extends QuestionBank> list(Map<String, String> param) {
        QuestionBank questionBank = new QuestionBank();
        questionBank.setId(Long.valueOf(param.get("id")));
        return questionBankDao.find(questionBank);
    }

    @Override
    public PageQueryResult<? extends QuestionBank> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /questionBank/find 题库查找
     * @apiDescription 题库查找
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
    public QuestionBank find(String id) {

        return null;
    }
}
