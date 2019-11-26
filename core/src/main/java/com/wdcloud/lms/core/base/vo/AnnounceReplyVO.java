package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.AnnounceReply;
import com.wdcloud.lms.core.base.model.DiscussionReply;
import lombok.Data;

import java.util.List;

@Data
public class AnnounceReplyVO extends AnnounceReply {
    private List<AnnounceReplyVO> replies;
    private Boolean leaf=false;
    /**
     * 评论附件
     */
    private String fileName;//名称
    private String fileUrl;//评论附件地址

    /***
     * 评论用户头像等信息
     */
    private Long userId;
    private String userNickname;
    private Long userAvatarFileId;
    private String userAvatarName;
    private String userAvatarUrl;
    /**
     * 0：未读：其他已读
     */
    private Long isRead;
}
