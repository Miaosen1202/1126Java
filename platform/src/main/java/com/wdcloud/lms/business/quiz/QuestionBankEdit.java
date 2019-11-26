package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuestionBankDTO;
import com.wdcloud.lms.core.base.dao.QuestionBankDao;
import com.wdcloud.lms.core.base.model.QuestionBank;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：实现题库选项表增、删、改调用接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_BANK)
public class QuestionBankEdit implements IDataEditComponent {

    @Autowired
    private QuestionBankDao questionBankDao;

    /**
     * @api {post} /questionBank/add 题库添加
     * @apiDescription 题库添加
     * @apiName questionBankAdd
     * @apiGroup Quiz
     * @apiParam {Number} courseID  课程ID
     * @apiParam {String} name 题库名称
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
    @ValidationParam(clazz = QuestionBankDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        return getQuestionBank(dataEditInfo);
    }

    /**
     * @api {post} /questionBank/modify 题库修改
     * @apiDescription 问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName questionBankModify
     * @apiGroup Quiz
     * @apiParam {Number} id 题库ID
     * @apiParam {Number} courseID  课程ID
     * @apiParam {String} name 题库名称
     * @apiParam {Date} createTime 题库创建时间
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
    @ValidationParam(clazz = QuestionBankDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {

        return getQuestionBank(dataEditInfo);
    }

    private LinkedInfo getQuestionBank(DataEditInfo dataEditInfo) {
        QuestionBankDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionBankDTO.class);
        QuestionBank questionBank = BeanUtil.copyProperties(dto, QuestionBank.class);

        if (questionBank.getId() == null) {
            questionBankDao.save(questionBank);
        } else {
            questionBankDao.update(questionBank);
        }
        return new LinkedInfo(String.valueOf(questionBank.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionBank/deletes 依据题库id删除
     * @apiDescription 题库删除
     * @apiName questionBankDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 题库ID
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
        for (Long id : ids) {
            //删除对应id记录
            questionBankDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));


    }


}
