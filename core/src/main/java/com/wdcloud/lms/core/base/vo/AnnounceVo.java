package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.UserFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnnounceVo extends Announce {
    private List<Assign> assign;
    private Long userCount;
//    private Long replyTotal;//公告评论数
//    private Long replyNotReadTotal;//公告评论未读数
//    private Long replyReadTotal;
    private List<SectionVo> sectionList = new ArrayList<>();
    //公告是否已读
    private Long isRead;

    //创建用户昵称
    private String createUserNickname;
    private String createUserAvatar;

    /**
     * 详情字段
     */
    //创建者
    private UserVo author;
    //评论数
    private ReadCountDTO readCountDTO;
    //会话：所有班级|2个会话
    private String session;
    //公告附件
    private UserFile attachmentFile;
    //登陆者Id
    private Long loginUserId;

}
