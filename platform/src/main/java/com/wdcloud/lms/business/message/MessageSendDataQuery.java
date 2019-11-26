package com.wdcloud.lms.business.message;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.core.base.dao.MessageSendDao;
import com.wdcloud.lms.core.base.vo.msg.MessageSentVO;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE_SENT)
public class MessageSendDataQuery implements IDataQueryComponent<MessageSentVO> {

    @Autowired
    private MessageSendDao messageSendDao;
    @Autowired
    private CarlendarService carlendarService;
    /**
     * @api {get} /messageSent/pageList 我的已发送主题列表分页接口
     * @apiName messageSentPageList
     * @apiGroup message
     * @apiParam {String} [searchKey] 搜索关键字
     * @apiParam {Number} courseId 课程ID 所有传0
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 列表
     * @apiSuccess {Number} entity.list.subjectId 消息主题ID
     * @apiSuccess {String} entity.list.messageSubject 消息主题标题
     * @apiSuccess {Number} entity.list.courseId 课程ID
     * @apiSuccess {String} entity.list.courseName 课程名称
     * @apiSuccess {String} entity.list.msgTotal 消息总个数
     * @apiSuccess {Object} entity.list.msgItemVO 已发送的最新一条消息
     * @apiSuccess {Number} entity.list.msgItemVO.messageId 消息ID
     * @apiSuccess {Number} entity.list.msgItemVO.sendId 发件人ID
     * @apiSuccess {String} entity.list.msgItemVO.sendUsername 发件人用户名
     * @apiSuccess {String} entity.list.msgItemVO.sendAvatarFileId 发件人头像
     * @apiSuccess {Number} entity.list.msgItemVO.createTime 消息发送时间
     * @apiSuccess {String} entity.list.msgItemVO.messageText 消息内容
     * @apiSuccess {Object[]} entity.list.msgItemVO.recList 收件人集合
     * @apiSuccess {Number} entity.list.msgItemVO.recList.id 收件人ID
     * @apiSuccess {String} entity.list.msgItemVO.recList.username 收件人用户名
     */
    @Override
    public PageQueryResult<? extends MessageSentVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.putAll(param);
        List<Long> courseIds =carlendarService.prepareCourseIds(params);
        params.put("courseIds", courseIds);
        if (ListUtils.isEmpty(courseIds)) {
            return null;
        }
        PageHelper.startPage(pageIndex,pageSize);
        Page<MessageSentVO> list = (Page<MessageSentVO>) messageSendDao.findMsgSentList(params);
        //获取每个主题下，我发送的最新一条消息
        list.getResult().forEach(subject->{
            MsgItemVO msgItemVO= messageSendDao.myLastestSentMsg(subject);
            subject.setMsgItemVO(msgItemVO);
        });
        return new PageQueryResult(list.getTotal(), list.getResult(), pageSize, pageIndex);
    }

    @Override
    public MessageSentVO find(String id) {
        return null;
    }
}
