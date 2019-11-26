package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionOptionRecordDTO;
import com.wdcloud.lms.core.base.dao.QuizQuestionOptionRecordDao;
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
 * 功能：实现测验问题选项记录接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_OPTION_RECORD)
public class QuizQuestionOptionRecordEdit implements IDataEditComponent {

    @Autowired
    private QuizQuestionOptionRecordDao quizQuestionOptionRecordDao;

    /**
     * @api {post} /quizQuestionOptionRecord/add 测验问题选项记录添加
     * @apiDescription 测验问题选项记录添加
     * @apiName quizQuestionOptionRecordAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizQuestionRecordId  问题记录ID
     * @apiParam {Number} questionOptionId 问题选项ID
     * @apiParam {String} code  题干中占位符
     * @apiParam {String} content  内容
     * @apiParam {String} explanation  选择该项的提示
     * @apiParam {Number} isCorrect 选择题：是否为正确选项
     * @apiParam {Number} seq 排序：如果是重排序选项，则是重排序后的选项顺序
     * @apiParam {Number} isChoice 是否选中
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
    @ValidationParam(clazz = QuizQuestionOptionRecordDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizQuestionOptionRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionOptionRecordDTO.class);
        quizQuestionOptionRecordDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionOptionRecord/modify 测验问题选项记录修改
     * @apiDescription 测验问题选项记录修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName quizQuestionOptionRecordModify
     * @apiGroup Quiz
     * @apiParam {Number} id 测验问题选项记录ID
     * @apiParam {Number} quizQuestionRecordId  问题记录ID
     * @apiParam {Number} questionOptionId 问题选项ID
     * @apiParam {String} code  题干中占位符
     * @apiParam {String} content  内容
     * @apiParam {String} explanation  选择该项的提示
     * @apiParam {Number} isCorrect 选择题：是否为正确选项
     * @apiParam {Number} seq 排序：如果是重排序选项，则是重排序后的选项顺序
     * @apiParam {Number} isChoice 是否选中
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
    @ValidationParam(clazz = QuizQuestionOptionRecordDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizQuestionOptionRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionOptionRecordDTO.class);
        quizQuestionOptionRecordDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionOptionRecord/deletes 依据测验问题选项记录id删除 【暂时未使用】
     * @apiDescription 测验问题选项记录删除
     * @apiName quizQuestionOptionRecordDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 测验问题选项记录ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除测验问题选项记录ID列表字符串
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
            quizQuestionOptionRecordDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));


    }


}
