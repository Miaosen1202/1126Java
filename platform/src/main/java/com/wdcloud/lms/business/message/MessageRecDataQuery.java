package com.wdcloud.lms.business.message;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.CarlendarService;
import com.wdcloud.lms.core.base.dao.MessageRecDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.vo.UserVo;
import com.wdcloud.lms.core.base.vo.msg.MessageSubjectVO;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE_REC)
public class MessageRecDataQuery implements IDataQueryComponent<MessageSubjectVO> {

    @Autowired
    private MessageRecDao messageRecDao;
    @Autowired
    private CarlendarService carlendarService;
    /**
     * @api {get} /messageRec/pageList 我的收件主题列表 分页
     * @apiName messageRecPageList
     * @apiGroup message
     * @apiParam {String} [searchKey] 搜索关键字
     * @apiParam {Number=0,1} isStar 是否收藏 0:所有 1：收藏
     * @apiParam {Number} courseId 课程Id 所有课程传0
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     *
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
     * @apiSuccess {Number=0,1} entity.list.isStar 是否收藏
     * @apiSuccess {Number} entity.list.msgTotal 消息总个数
     * @apiSuccess {Object} entity.list.msgItemVO 已发送的最新一条消息
     * @apiSuccess {Number=0,1} entity.list.msgItemVO.isRead 是否已读
     * @apiSuccess {Number=0,1} entity.list.msgItemVO.messageId 消息ID
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
    public PageQueryResult<? extends MessageSubjectVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.putAll(param);
        List<Long> courseIds =carlendarService.prepareCourseIds(params);
        params.put("courseIds", courseIds);
        if (ListUtils.isEmpty(courseIds)) {
            return null;
        }
        PageHelper.startPage(pageIndex,pageSize);
        //获取 我的收件主题列表
        Page<MessageSubjectVO> list = (Page<MessageSubjectVO>) messageRecDao.findRecSubjectList(params);
        //批量设置主题下消息总量
        setMsgTotal(list);
        //获取每个主题下，我收到的最新一条消息
        list.getResult().forEach(subject->{
            MsgItemVO msgItemVO= messageRecDao.myLastestRecMsg(subject.getSubjectId(),subject.getRecId());
            //如果是私密消息
            buildIsPrivate(msgItemVO);
            subject.setMsgItemVO(msgItemVO);
        });
        return new PageQueryResult(list.getTotal(), list.getResult(), pageSize, pageIndex);
    }

    private void setMsgTotal(Page<MessageSubjectVO> list) {
        if (ListUtils.isNotEmpty(list)) {
            List<Long> subjectIds=list.getResult().stream().map(MessageSubjectVO::getSubjectId).collect(Collectors.toList());
            Example example = messageRecDao.getExample();
            example.createCriteria()
                    .andEqualTo(MessageRec.REC_ID,WebContext.getUserId())
                    .andIn(MessageRec.SUBJECT_ID,subjectIds)
                    .andEqualTo(MessageRec.IS_DELETE,0);
            List<MessageRec> messageRecList= messageRecDao.find(example);
            Map<Long, Long> map = messageRecList.stream().collect(Collectors.groupingBy(MessageRec::getSubjectId,Collectors.counting()));
            list.forEach(subjectVO->{
                subjectVO.setMsgTotal(map.get(subjectVO.getSubjectId()));
            });
        }
    }

    public void buildIsPrivate(MsgItemVO msgItemVO) {
        //如果是私密消息
        if (msgItemVO.getIsPrivate().equals(Status.YES.getStatus())) {
            // 如果当前用户不是发件人，那么收件人只显示自己。
            if (!WebContext.getUserId().equals(msgItemVO.getSendId())) {
                UserVo userVo=msgItemVO.getRecList().stream().filter(o->o.getId().equals(WebContext.getUserId())).findFirst().get();
                msgItemVO.setRecList(List.of(userVo));
            }
        }
    }
}
