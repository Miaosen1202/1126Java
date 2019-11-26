package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionMatchOptionRecordDTO;
import com.wdcloud.lms.core.base.dao.QuizQuestionMatchOptionRecordDao;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：实现匹配类问题选项选择记录接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_MATCH_OPTION_RECORD)
public class QuizQuestionMatchOptionRecordEdit implements IDataEditComponent {

    @Autowired
    private QuizQuestionMatchOptionRecordDao quizQuestionMatchOptionRecordDao;

    /**
     * @api {post} /quizQuestionMatchOptionRecord/add 匹配类问题选项选择记录添加
     * @apiDescription 匹配类问题选项选择记录添加
     * @apiName quizQuestionMatchOptionRecordAdd
     * @apiGroup Quiz
     * @apiParam {Number} questionMatchOptionId  匹配类问题选项ID
     * @apiParam {String} content 内容
     * @apiParam {Number} quizQuestionRecordId  问题记录ID
     * @apiParam {Number} quizQuestionOptionRecordId 选项ID
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
    @ValidationParam(clazz = QuizQuestionMatchOptionRecordDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizQuestionMatchOptionRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionMatchOptionRecordDTO.class);
        quizQuestionMatchOptionRecordDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionMatchOptionRecord/modify 匹配类问题选项选择记录修改
     * @apiDescription 匹配类问题选项选择记录修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName quizQuestionMatchOptionRecordModify
     * @apiGroup Quiz
     * @apiParam {Number} id 匹配类问题选项选择记录ID
     * @apiParam {Number} questionMatchOptionId  匹配类问题选项ID
     * @apiParam {String} content 内容
     * @apiParam {Number} quizQuestionRecordId  问题记录ID
     * @apiParam {Number} quizQuestionOptionRecordId 选项ID
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
    @ValidationParam(clazz = QuizQuestionMatchOptionRecordDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizQuestionMatchOptionRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionMatchOptionRecordDTO.class);
        quizQuestionMatchOptionRecordDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionMatchOptionRecord/deletes 依据匹配类问题选项选择记录id删除【暂时未使用】
     * @apiDescription 匹配类问题选项选择记录删除
     * @apiName quizQuestionMatchOptionRecordDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 匹配类问题选项选择记录ID
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
            quizQuestionMatchOptionRecordDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));


    }


}
