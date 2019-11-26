package com.wdcloud.lms.business.quiz;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuestionGroupDao;
import com.wdcloud.lms.core.base.model.QuestionGroup;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现试题组信息查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_GROUP, description = "试题组表")
public class QuestionGroupQuery implements IDataQueryComponent<QuestionGroup> {
    @Autowired
    private QuestionGroupDao questionGroupDao;

    /**
     * @api {get} /questionGroup/list 试题组列表
     * @apiDescription 依据id查询对应的试题组信息
     * @apiName questionGroupList
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
    public List<? extends QuestionGroup> list(Map<String, String> param) {
        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setId(Long.valueOf(param.get("id")));
        return questionGroupDao.find(questionGroup);
    }

    @Override
    public PageQueryResult<? extends QuestionGroup> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /questionGroup/find 题库查找
     * @apiDescription 题库查找
     * @apiName questionGroupFind
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
    public QuestionGroup find(String id) {

        return null;
    }
}
