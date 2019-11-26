package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuestionMatchOptionDTO;
import com.wdcloud.lms.core.base.dao.QuestionMatchOptionDao;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
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
 * 功能：实现匹配问题选项表增、删、改调用接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUESTION_MATCH_OPTION)
public class QuestionMatchOptionEdit implements IDataEditComponent {

    @Autowired
    private QuestionMatchOptionDao questionMatchOptionDao;


    /**
     * @api {post} /questionMatchOption/add 匹配问题选项添加
     * @apiDescription 匹配问题选项添加
     * @apiName questionMatchOptionAdd
     * @apiGroup Quiz
     * @apiParam {Number} questionID  问题ID
     * @apiParam {Number} questionOptionID  匹配选项，空表示是一个错误匹配项
     * @apiParam {String} content 内容
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
    @ValidationParam(clazz = QuestionMatchOptionDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuestionMatchOptionDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionMatchOptionDTO.class);
        questionMatchOptionDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionMatchOption/modify 匹配问题选项修改
     * @apiDescription 匹配问题选项修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName questionMatchOptionModify
     * @apiGroup Quiz
     * @apiParam {Number} id ID
     * @apiParam {Number} questionID  问题ID
     * @apiParam {Number} questionOptionID  匹配选项，空表示是一个错误匹配项
     * @apiParam {String} content 内容
     * @apiParam {Number} seq  排序
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
    @ValidationParam(clazz = QuestionMatchOptionDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuestionMatchOptionDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuestionMatchOptionDTO.class);
        questionMatchOptionDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /questionMatchOption/deletes 依据匹配问题选项id删除
     * @apiDescription 匹配问题选项删除
     * @apiName questionMatchOptionDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 匹配问题选项ID
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
        List<QuestionMatchOption> questionMatchOptionList=new ArrayList<>();
        //组装删除信息
        for (Long id : ids) {
            QuestionMatchOption questionMatchOption=new QuestionMatchOption();
            questionMatchOption.setId(id);
            questionMatchOptionList.add(questionMatchOption);
        }
        //批量删除
        if(questionMatchOptionList.size()>0) {
            questionMatchOptionDao.batchDelete(questionMatchOptionList);
        }
        return new LinkedInfo(JSON.toJSONString(ids));
    }
}
