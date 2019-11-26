package com.wdcloud.lms.business.message;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.MessageDao;
import com.wdcloud.lms.core.base.vo.msg.SubjectInfoVO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MESSAGE,
        functionName = Constants.FUNCTION_TYPE_MESSAGE_SUBJECT
)
public class MessageSubjectQuery implements ISelfDefinedSearch<SubjectInfoVO> {
    @Autowired
    private MessageDao messageDao;

    /**
     * @api {get} /message/subject/query 通过主题Id获取主题信息
     * @apiName messageSubjectQuery
     * @apiGroup message
     * @apiParam {Number} subjectId 消息主题ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 结果
     * @apiSuccess {Number} entity.id 消息主题ID
     * @apiSuccess {String} entity.messageSubject 消息主题标题
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.courseName 课程名称
     */
    @Override
    public SubjectInfoVO search(Map<String, String> condition)  {
        //参数接收
        Integer subjectId=Integer.valueOf(condition.get("subjectId"));
        //1主题信息 通过主题Id获获取主题信息
        SubjectInfoVO subjectInfoVO=messageDao.findSubjectInfoById(subjectId, WebContext.getUserId());
        return subjectInfoVO;
    }

}
