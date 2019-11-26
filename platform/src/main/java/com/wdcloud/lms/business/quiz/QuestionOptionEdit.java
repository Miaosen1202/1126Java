package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuestionOptionDTO;
import com.wdcloud.lms.core.base.dao.QuestionOptionDao;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：实现问题选项表增、删、改调用接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_OPTION)
public class QuestionOptionEdit implements IDataEditComponent {

    @Autowired
    private QuestionOptionDao questionOptionDao;

    /**
     * @api {post} /questionOption/add 问题选项添加
     * @apiDescription 问题选项添加
     * @apiName questionOptionAdd
     * @apiGroup Quiz
     * @apiParam {Number} questionID 问题ID
     * @apiParam {String} code 题干中占位符
     * @apiParam {String} content 选项内容
     * @apiParam {String} explanation 选择该项的提示
     * @apiParam {Number=0,1} isCorrect 选择题：是否为正确选项;0，否；1，是；
     * @apiParam {Number} seq  排序
     * @apiExample {json} 请求示例:
     * {
     * "name": "Thinking In Java"
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 新增问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    @ValidationParam(clazz = QuestionOptionDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuestionOptionDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionOptionDTO.class);
        questionOptionDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionOption/modify 问题选项修改
     * @apiDescription 问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName questionOptionModify
     * @apiGroup Quiz
     * @apiParam {Number} id 问题选项ID
     * @apiParam {Number} questionID 问题ID
     * @apiParam {String} code 题干中占位符
     * @apiParam {String} content 选项内容
     * @apiParam {String} explanation 选择该项的提示
     * @apiParam {Number=0,1} isCorrect 选择题：是否为正确选项;0，否；1，是；
     * @apiParam {Number} seq  排序
     * @apiParam {Date} createTime 问题选项创建时间
     * @apiParam {Date} updateTime 问题选项更新时间
     * @apiExample {json} 请求示例:
     * {
     * "id": 1,
     * "name": "Thinking In Java"
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    @ValidationParam(clazz = QuestionOptionDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuestionOptionDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionOptionDTO.class);
        questionOptionDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionOption/deletes 问题选项删除
     * @apiDescription 依据问题选项id删除对应id的问题选项
     * @apiName questionOptionDeletes
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 问题选项ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除问题选项ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        List<QuestionOption> questionOptionList = new ArrayList<>();
        for (Long id : ids) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setId(id);
            questionOptionList.add(questionOption);
        }
        if (questionOptionList.size() > 0) {
            questionOptionDao.batchDelete(questionOptionList);
        }
        return new LinkedInfo(JSON.toJSONString(ids));


    }


}
