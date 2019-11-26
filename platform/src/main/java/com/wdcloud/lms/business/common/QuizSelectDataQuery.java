package com.wdcloud.lms.business.common;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_SELECT)
public class QuizSelectDataQuery implements IDataQueryComponent<Quiz> {

    @Autowired
    private QuizDao quizDao;

    /**
     * @api {get} /quizSelect/list 测验下拉列表
     * @apiName quizSelectList
     * @apiGroup Common
     * @apiParam {Number} courseId courseId
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 作业信息
     * @apiSuccess {Number} entity.id
     * @apiSuccess {String} entity.title 标题
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends Quiz> list(Map<String, String> param) {
        return quizDao.find(Quiz.builder().courseId(Long.valueOf(param.get(Assignment.COURSE_ID))).build());
    }

}
