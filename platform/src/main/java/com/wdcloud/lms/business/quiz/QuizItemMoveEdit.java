package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.SeqDTO;
import com.wdcloud.lms.business.quiz.question.SeqMoveService;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_QUIZ_ITEM,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class QuizItemMoveEdit implements ISelfDefinedEdit {
    @Autowired
    private SeqMoveService seqMoveService;

    /**
     * @api {post} /quizItem/move/edit 测验内容项移动
     * @apiName quizItemMove
     * @apiGroup Quiz
     * @apiParam {Number} quizId   测验ID
     * @apiParam {Number} operate   操作类型：1、top:移动到顶部；2、end,移动到底部；4、before 在前
     * @apiParam {Number} questionType 问题类型：1. 问题 2. 问题组
     * @apiParam {Number} moveId 被移动的问题或问题组的ID
     * @apiParam {Number} beforeId 移动到那个ID前面，如果操作类型是1、2该项填写moveId
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动项ID
     */
    @Override
    @ValidationParam(clazz = SeqDTO.class, groups = GroupAdd.class)
    public LinkedInfo edit(DataEditInfo dataEditInfo) {

        final SeqDTO dto = JSON.parseObject(dataEditInfo.beanJson, SeqDTO.class);
        seqMoveService.seqMove(dto.getOperate(), dto.getQuizId(), dto.getQuestionType(), dto.getMoveId(), dto.getBeforeId());
        return new LinkedInfo(String.valueOf(dto.getMoveId()), dataEditInfo.beanJson);
    }
}
