package com.wdcloud.lms.business.discussion;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.discussion.dto.DiscussionReplyTree;
import com.wdcloud.lms.core.base.dao.DiscussionReplyDao;
import com.wdcloud.lms.core.base.model.DiscussionReply;
import com.wdcloud.lms.core.base.vo.DiscussionReplyVO;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SuppressWarnings("JavaDoc")
@ResourceInfo(name = Constants.RESOURCE_TYPE_DISCUSSION_REPLY)
public class DiscussionReplyQuery implements IDataQueryComponent<DiscussionReply> {
    @Autowired
    private DiscussionReplyDao discussionReplyDao;

    /**
     * @api {get} /discussionReply/list 讨论回复列表
     * @apiName discussionReplyList
     * @apiGroup Discussion
     * @apiParam {Number} studyGroupId 学习小组ID
     * @apiParam {Number} discussionId 讨论ID
     * @apiParam {String} [content] 讨论回复内容或作者名
     * @apiParam {Number=0,1,2} isRead 是否已读 0:未读,1:已读，2:所有
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.id 评论ID
     * @apiSuccess {Number} entity.discussionId 关联讨论ID
     * @apiSuccess {Number} entity.replyId 回复的回复ID(这个ID表示这是一个回复的回复)
     * @apiSuccess {String} entity.content 内容
     * @apiSuccess {Number} entity.createTime 回复时间
     * @apiSuccess {Number} entity.attachmentFileId 回复附件ID
     * @apiSuccess {Number} entity.fileName 回复附件名称
     * @apiSuccess {Number} entity.fileUrl 回复附件URL
     * @apiSuccess {Number} entity.isRead 0：未读，其他:已读
     * @apiSuccess {Number} entity.userId 评论用户ID
     * @apiSuccess {Number} entity.userNickname 评论用户昵称
     * @apiSuccess {Number} entity.userAvatarUrl 评论用户头像
     */
    @Override
    public List<? extends DiscussionReply> list(Map<String, String> param) {
        //1.查回复列表
        //2.查用户信息 id头像昵称
        //3已读未读标识
        param.put("userId", WebContext.getUserId()+"");
        List<DiscussionReplyVO> list = discussionReplyDao.findReply(param);
        //构造回复树结构
        return DiscussionReplyTree.getList(list);
    }

    /**
     * @api {get} /discussionReply/pageList 讨论回复分页列表
     * @apiName discussionReplyPageList
     * @apiGroup Discussion
     * @apiParam {Number} studyGroupId 学习小组ID
     * @apiParam {Number} discussionId 讨论ID
     * @apiParam {Number} replyId 评论ID（一级评论列表传0，二级列表传一级评论ID）
     * @apiParam {Number} pageIndex 页吗
     * @apiParam {Number} pageSize 页尺寸
     * @apiParam {String} [content] 讨论回复内容或作者名
     * @apiParam {Number=0,1,2} isRead 是否已读 0:未读,1:已读，2:所有
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.list 资源列表
     * @apiSuccess {Number} entity.list.id 评论ID
     * @apiSuccess {Number} entity.list.discussionId 关联讨论ID
     * @apiSuccess {Number} entity.list.replyId 回复的回复ID(这个ID表示这是一个回复的回复)
     * @apiSuccess {String} entity.list.content 内容
     * @apiSuccess {Number} entity.list.createTime 回复时间
     * @apiSuccess {Number} entity.list.attachmentFileId 回复附件ID
     * @apiSuccess {Number} entity.list.fileName 回复附件名称
     * @apiSuccess {Number} entity.list.fileUrl 回复附件URL
     * @apiSuccess {Number} entity.list.isRead 0：未读，其他:已读
     * @apiSuccess {Number} entity.list.userId 评论用户ID
     * @apiSuccess {Number} entity.list.userNickname 评论用户昵称
     * @apiSuccess {Number} entity.list.userAvatarUrl 评论用户头像
     * @apiSuccess {String} entity.pageIndex 当前页
     * @apiSuccess {String} entity.pageSize 每页数据
     * @apiSuccess {String} entity.total 总页数
     */
     @Override
     public PageQueryResult<? extends DiscussionReply> pageList(Map<String, String> param, int pageIndex, int pageSize) {
         param.put("userId", WebContext.getUserId()+"");
         if (!param.containsKey("replyId")) {
             throw new ParamErrorException();
         }
         PageHelper.startPage(pageIndex,pageSize);
         Page<DiscussionReplyVO> list = (Page<DiscussionReplyVO>) discussionReplyDao.findReply(param);
         return new PageQueryResult(list.getTotal(), list.getResult(), pageSize, pageIndex);
     }
}
