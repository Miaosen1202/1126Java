package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionReplyRecordDTO;
import com.wdcloud.lms.core.base.dao.QuizQuestionReplyRecordDao;
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
 * 功能：填空类问题回答接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_QUESTION_REPLY_RECORD)
public class QuizQuestionReplyRecordEdit implements IDataEditComponent {

    @Autowired
    private QuizQuestionReplyRecordDao quizQuestionReplyRecordDao;

    /**
     * @api {post} /quizQuestionReplyRecord/add 填空类问题回答添加
     * @apiDescription 填空类问题回答添加
     * @apiName quizQuestionReplyRecordAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizQuestionRecordId  问题记录ID
     * @apiParam {String} reply  回答内容
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
    @ValidationParam(clazz = QuizQuestionReplyRecordDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizQuestionReplyRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionReplyRecordDTO.class);
        quizQuestionReplyRecordDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionReplyRecord/modify 填空类问题回答修改
     * @apiDescription 填空类问题回答修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName quizQuestionReplyRecordModify
     * @apiGroup Quiz
     * @apiParam {Number} id 填空类问题回答ID
     * @apiParam {Number} quizQuestionRecordId  问题记录ID
     * @apiParam {String} reply  回答内容
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
    @ValidationParam(clazz = QuizQuestionReplyRecordDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizQuestionReplyRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizQuestionReplyRecordDTO.class);
        quizQuestionReplyRecordDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizQuestionReplyRecord/deletes 填空类问题回答id删除【暂时未使用】
     * @apiDescription 填空类问题回答删除
     * @apiName quizQuestionReplyRecordDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 测验问题记录ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除填空类问题回答ID列表字符串
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
            quizQuestionReplyRecordDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));


    }


}
