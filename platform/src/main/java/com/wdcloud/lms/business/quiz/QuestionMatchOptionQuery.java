package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuestionMatchOptionDao;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现匹配问题选项查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_MATCH_OPTION, description = "匹配类问题选项表")
public class QuestionMatchOptionQuery implements IDataQueryComponent<QuestionMatchOption> {
    @Autowired
    private QuestionMatchOptionDao questionMatchOptionDao;

    /**
     * @api {get} /questionMatchOption/list 匹配问题选项列表
     * @apiDescription 依据id查询对应的匹配问题选项信息
     * @apiName questionMatchOptionList
     * @apiGroup Quiz
     * @apiParam {String} id ID
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
    public List<? extends QuestionMatchOption> list(Map<String, String> param) {
        QuestionMatchOption questionMatchOption = new QuestionMatchOption();
        questionMatchOption.setId(Long.valueOf(param.get("id")));
        return questionMatchOptionDao.find(questionMatchOption);
    }

    @Override
    public PageQueryResult<? extends QuestionMatchOption> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /questionMatchOption/find 匹配问题选项查找
     * @apiDescription 匹配问题选项查找
     * @apiName questionMatchOptionFind
     * @apiGroup Quiz
     * @apiParam {String} id 匹配问题选项ID
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
    public QuestionMatchOption find(String id) {

        return null;
    }
}
