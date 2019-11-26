package com.wdcloud.lms.business.message;

import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MESSAGE,
        functionName = Constants.FUNCTION_TYPE_MESSAGE_UNREAD
)
public class MessageUnReadQuery implements ISelfDefinedSearch<Object> {
    @Autowired
    private MessageRecDao messageRecDao;
    @Autowired
    private CarlendarService carlendarService;
    /**
     * @api {get} /message/unread/query 未读数统计
     * @apiName messageUnreadQuery
     * @apiGroup message
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 未读数
     */
    @Override
    public Integer search(Map<String, String> condition)  {
        //获取此角色下的课程IDS
        List<Long> courseIds=carlendarService.prepareCourseIds(Maps.newHashMap());
        if (ListUtils.isEmpty(courseIds)) {
            return 0;
        }
        //获取未读数字
        int count=messageRecDao.getUnreadNum(courseIds,WebContext.getUserId());
        //int count=messageRecDao.count(MessageRec.builder().recId(WebContext.getUserId()).isRead(Status.NO.getStatus()).build());
        return count;
    }

}
