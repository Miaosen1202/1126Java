package com.wdcloud.lms.core.base.vo.msg;

import com.wdcloud.lms.core.base.vo.UserVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MsgItemVO {

    private Integer isRead;//消息是否已读
    /**
     * 消息内容
     */
    private Long subjectId;//消息主题ID
    private Long messageId;//消息ID
    private Long courseId;//课程ID
    private String messageSubject;//消息主题
    private String messageText;//消息内容
    private Integer isPrivate;//是否私密消息
    /**
     * 发件人信息
     */
    private Long sendId; //发件人用户ID
    private String sendUsername; //发件人用户名
    private String sendAvatarFileId; //发件人头像
    /**
     * 收件人列表
     */
    private List<UserVo> recList; //收件人列表

    /**
     * 公共字段
     */
    private Date createTime;
    private Date updateTime;
    private Long createUserId;
    private Long updateUserId;
}
