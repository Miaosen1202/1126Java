package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizDTO;
import com.wdcloud.lms.business.quiz.enums.QuizeOpenStatusEnum;
import com.wdcloud.lms.business.quiz.question.QuizService;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能：学生端判断测验是否能开始答题
 *
 * @author 黄建林
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_QUIZ_ITEM,
        functionName = Constants.FUNCTION_TYPE_OPEN
)
public class QuizIsOpen implements ISelfDefinedEdit {
    @Autowired
    private QuizService quizService;
    @Autowired
    private HttpServletRequest request;

    /**
     * @api {post} /quizItem/open/edit 学生是否能答题
     * @apiName quizItemOpen
     * @apiGroup Quiz
     * @apiParam {Number} id ID
     * @apiParam {String} [accessCode] 访问码（配置后学生测验需要先输入该访问码）
     *
     * @apiExample {json} 请求示例:
     * {
     * 	"id": "159"
     *
     *
     * }
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} status  1:允许答题；2：表示处于锁定状态；3：表示访问码错误
     * 4：表示答题次数已经达到限制；5：IP地址不在设定范围内；6：引用模块处于未解锁状态；
     * 7：当前时间还没达到规定的开始时间；8：当前时间已经超过了结束时间；9：恢复答题；
     *
     * @apiSuccessExample {json} 返回示例:
     *{
     *     "code": 200,
     *     "message": "1"
     * }
     *
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {

        final QuizDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizDTO.class);
        String ip = IpUtils.getIpAdrress(request);
        QuizeOpenStatusEnum openstatus = quizService.getAuthorization(dto.getId(), ip, dto.getAccessCode());
        return new LinkedInfo(String.valueOf(openstatus.getStatus()));
    }
}
