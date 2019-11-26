package com.wdcloud.lms.business.message;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.message.dto.MessageSendDTO;
import com.wdcloud.lms.business.message.dto.RecDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_MESSAGE)
public class MessageDataEdit implements IDataEditComponent {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private MessageSendDao messageSendDao;
    @Autowired
    private MessageRecDao messageRecDao;
    @Autowired
    private MessageRelatedDao messageRelatedDao;
    @Autowired
    private SectionUserDao sectionUserDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;

    /**
     * @api {post} /message/add 消息发送
     * @apiName messageAdd 消息发送
     * @apiGroup message
     * @apiParam {Number} subjectId 消息主题ID，没有主题时默认传0
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number=0,1} isPrivate 是否私密发送
     * @apiParam {String} messageSubject 消息主题
     * @apiParam {String} messageText 消息内容
     * @apiParam {Object[]} [messageRelatedIds] 消息引用IDs
     * @apiParam {Object} recDTO 收件人对象
     * @apiParam {Number=0,1} recDTO.allStudents  All Students
     * @apiParam {Object[]} recDTO.sectionIds 收件班级Ids
     * @apiParam {Object[]} recDTO.userIds 收件人用户Ids
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = MessageSendDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        MessageSendDTO dto = JSON.parseObject(dataEditInfo.beanJson, MessageSendDTO.class);
        //1消息内容表保存
        messageDao.save(dto);
        if(dto.getSubjectId()==0){//主题消息把主题Id关联进去
            dto.setIsSubject(Status.YES.getStatus());
            dto.setSubjectId(dto.getId());
            messageDao.update(dto);
        }else{
            //更新主题消息的更新时间
            messageDao.updateByExample(Message.builder().updateTime(new Date()).build(),List.of(dto.getSubjectId()));
        }
        //2消息发送表保存
        messageSendDao.save(MessageSend.builder().subjectId(dto.getSubjectId()).messageId(dto.getId()).sendId(WebContext.getUserId()).build());
        //3批量消息引用表保存
        if (ListUtils.isNotEmpty(dto.getMessageRelatedIds())) {
            List<MessageRelated> messageRelatedList = new ArrayList<>();
            for (Long messageRelatedId : dto.getMessageRelatedIds()) {
                MessageRelated messageRelated = MessageRelated.builder().messageRelatedId(messageRelatedId).messageId(dto.getId()).build();
                messageRelatedList.add(messageRelated);
            }
            messageRelatedDao.batchSave(messageRelatedList);
        }
        //5收件人保存
        saveMsgRec(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * 消息接收者保存
     * @param dto
     */
    private void saveMsgRec(MessageSendDTO dto) {
        Set<Long> recIds = getRecIds(dto);
        int isDefault = 0;
        if (!recIds.contains(WebContext.getUserId())) {
            recIds.add(WebContext.getUserId());
            isDefault = 1;
        }
        //当前消息接收者批量保存
        List<MessageRec> messageRecList = new ArrayList<>();
        int finalIsDefault = isDefault;
        recIds.forEach(recId->{
            MessageRec rec = MessageRec.builder()
                    .sendId(WebContext.getUserId())
                    .recId(recId)
                    .messageId(dto.getId())
                    .subjectId(dto.getSubjectId())
                    .isDefault(recId.equals(WebContext.getUserId()) ? finalIsDefault : 0)
                    .isRead(recId.equals(WebContext.getUserId()) ? 1 : 0)
                    .build();
            messageRecList.add(rec);
        });

        //引用消息处理
       if (ListUtils.isNotEmpty(dto.getMessageRelatedIds()))
        {
            List<Long> relatedIds =dto.getMessageRelatedIds();
            Example example = messageRecDao.getExample();
            example.createCriteria()
                    .andIn(MessageRec.MESSAGE_ID,relatedIds)
                    .andIn(MessageRec.REC_ID, recIds);
            List<MessageRec> messageRecHis=messageRecDao.find(example);
            Map<Long, Set<Long>> messageRecHisMap = messageRecHis.stream()
                    .collect(Collectors.groupingBy(MessageRec::getRecId,Collectors.mapping(MessageRec::getMessageId, Collectors.toSet())));
            //消息ID-发件人ID映射
            Map<Long, MessageRec> messageToSenderMap = messageRecHis.stream()
                    .collect(Collectors.toMap(MessageRec::getMessageId, Function.identity(), (o, n) ->o));

            recIds.forEach(recId->{
                Set<Long>   hisMsgIds=messageRecHisMap.get(recId);
                relatedIds.forEach(msgId->{
                    //此人此历史消息以前是否收到过，如果否，则入库，否则不处理
                    if(CollectionUtil.isNullOrEmpty(hisMsgIds)||!hisMsgIds.contains(msgId)){
                        MessageRec rec = MessageRec.builder()
                                .sendId(messageToSenderMap.get(msgId).getSendId())
                                .recId(recId)
                                .messageId(msgId)
                                .subjectId(dto.getSubjectId())
                                .isDefault(recId.equals(WebContext.getUserId())? finalIsDefault :0)
                                .isRead(recId.equals(WebContext.getUserId())? 1 :0)
                                .build();
                        messageRecList.add(rec);
                    }
                });
            });
        }
        if (ListUtils.isNotEmpty(messageRecList)) {
            messageRecDao.batchSave(messageRecList);
        }
    }

    /**
     * 构建接收者ids
     * @param messageSendDTO
     * @return
     */
    private Set<Long> getRecIds(MessageSendDTO messageSendDTO) {
        RecDTO recDTO = messageSendDTO.getRecDTO();
        Set<Long> recIds = new HashSet<>();
        //选中课程下所有学生 ,查询所有学生IDs
        if(recDTO.getAllStudents()!=null&&Status.YES.getStatus()==recDTO.getAllStudents()){
            List<User> studentList=sectionUserDao.getUsersByCourseIdAndRoleId(messageSendDTO.getCourseId().longValue(), RoleEnum.STUDENT.getType());
            List<Long> studentIds=studentList.stream().map(User::getId).collect(Collectors.toList());
            recIds.addAll(studentIds);
        }
        //班级IDs不空，通过班级Ids查找班级下所有用户
        if (ListUtils.isNotEmpty(recDTO.getSectionIds())) {
            List<User> studentList=sectionUserDao.getUserBySectionId(messageSendDTO.getCourseId(),recDTO.getSectionIds());
            List<Long> studentIds=studentList.stream().map(User::getId).collect(Collectors.toList());
            recIds.addAll(studentIds);
        }
        //小组IDs不空，通过小组IDS查找小组下所有用户
      /*  if (ListUtils.isNotEmpty(recDTO.getGroupIds())) {
            List<StudyGroupUser> studentList = studyGroupUserDao.findGroupMemberList(recDTO.getGroupIds());
            List<Long> studentIds=studentList.stream().map(StudyGroupUser::getUserId).collect(Collectors.toList());
            recIds.addAll(studentIds);
        }*/
        //用户Ids
        recIds.addAll(recDTO.getUserIds());
        return recIds;
    }


    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        return null;
    }

    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        return null;
    }
}
