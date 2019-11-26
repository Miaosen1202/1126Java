package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizItemDTO;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
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
 * 功能：实现测验问题项表增、删、改调用接口
 *
 * @author 黄建林
 */
@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_ITEM)
public class QuizItemEdit implements IDataEditComponent {

    @Autowired
    private QuizItemDao quizItemDao;

    /**
     * @api {post} /quizItem/add 测验问题项添加
     * @apiDescription 测验问题项添加
     * @apiName quizItemAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizId 测验ID
     * @apiParam {Number=1,2} type 类别，1：问题，2：问题组
     * @apiParam {Number} targetId 问题/问题组ID
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 新增内容项ID
     */
    @Override
    @ValidationParam(clazz = QuizItemDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizItemDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizItemDTO.class);
        quizItemDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizItem/modify 测验问题项修改
     * @apiDescription 测验问题项修改（内容项类型，所属测验禁止修改）
     * @apiName quizItemModify
     * @apiGroup Quiz
     * @apiParam {Number} id ID
     * @apiParam {Number} quizId 测验ID
     * @apiParam {Number=1,2} type 类别，1：问题，2：问题组
     * @apiParam {Number} targetId 问题/问题组ID
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 新增内容项ID
     */
    @Override
    @ValidationParam(clazz = QuizItemDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizItemDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizItemDTO.class);
        quizItemDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizItem/deletes 测验问题项删除【该接口目前未使用】
     * @apiName quizItemDeletes
     * @apiGroup Quiz
     * @apiParam {String[]} ids ID列表
     * @apiParamExample 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 删除ID列表
     * @apiSuccessExample 响应示例：
     * {
     * "code": 200,
     * "entity": "[1, 2, 3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {
            //删除对应id记录
            quizItemDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));
    }
}
