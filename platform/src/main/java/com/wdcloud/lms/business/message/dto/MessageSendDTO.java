package com.wdcloud.lms.business.message.dto;

import com.wdcloud.lms.core.base.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class MessageSendDTO extends Message {
   /** 收件人对象
     {
       allTeachers:1,
       allStudents:1,
       sectionIds:[1,2],
       groupIds:[1,2],
       userIds:[1,2]
     }
    */
    private RecDTO recDTO;
    //是否私密发送
    private Integer isPrivate;
    //消息引用Ids
    private List<Long> messageRelatedIds;

 }
