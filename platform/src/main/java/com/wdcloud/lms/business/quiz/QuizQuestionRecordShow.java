package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizDTO;
import com.wdcloud.lms.business.quiz.enums.QuizeAnswerStatusEnum;
import com.wdcloud.lms.business.quiz.question.QuizService;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能：判断测验答题是否能看正误或看答案
 *
 * @author 黄建林
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_QUIZ_ITEM,
        functionName = Constants.FUNCTION_TYPE_SHOW
)
public class QuizQuestionRecordShow implements ISelfDefinedEdit {
    @Autowired
    private QuizService quizService;
    @Autowired
    private HttpServletRequest request;

    /**
     * @api {post} /quizItem/show/edit 学生是否能看正误和答案
     * @apiName quizItemShow
     * @apiGroup Quiz
     * @apiParam {Number} id 测验ID
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} status  1:表示允许看正误和看答案；
     * 2：允许看正误；
     * 3：表示不允许看正误和看答案
     * 4：教师设置不允许看正误,就不能看答案
     * 5:设置每次提交答案后才能看看正误，但还没提交答案
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {

        final QuizDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizDTO.class);
        QuizeAnswerStatusEnum showstatus = quizService.getRecordAuthorization(dto.getId());

        return new LinkedInfo(String.valueOf(showstatus.getStatus()));
    }
}
