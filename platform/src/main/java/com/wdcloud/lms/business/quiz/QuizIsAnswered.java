package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizDTO;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.dao.QuizRecordDao;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：教师端判断测验是否已经有学生答过题，如果答过则测验基本信息为只读，不能修改
 *
 * @author 黄建林
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_QUIZ_ITEM,
        functionName = Constants.FUNCTION_TYPE_ANSWERED
)
public class QuizIsAnswered implements ISelfDefinedEdit {
    @Autowired
    private QuizRecordDao quizRecordDao;
    @Autowired
    private QuizDao quizDao;


    /**
     * @api {post} /quizItem/answered/edit 是否有学生已经完成过测验
     * @apiName quizItemOpen
     * @apiGroup Quiz
     * @apiParam {Number} id 测验ID
     * @apiExample {json} 请求示例:
     * {
     * "id": "159"
     * <p>
     * <p>
     * }
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} status  0:否（没有学生参与过该测验）；1：是（已经有学生参与过该测验）
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "message": "1"
     * }
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        int status = 0;

        final QuizDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizDTO.class);

        Quiz quiz = quizDao.get(dto.getId());
        if (quiz == null) {
            throw new BaseException("prop.value.not-exists","quizId",dto.getId()+"");
        }
        List<QuizRecord> quizRecordAllList = quizRecordDao.getAllQuizRecord(quiz.getId());
        int quizRecordAllListTotal = quizRecordAllList.size();

        if (quizRecordAllListTotal > 0) {
            status = 1;
        }
        return new LinkedInfo(String.valueOf(status));

    }
}
