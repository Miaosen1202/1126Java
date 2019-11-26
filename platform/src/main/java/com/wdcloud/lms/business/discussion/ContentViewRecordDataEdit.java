package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.dao.ContentViewRecordDao;
import com.wdcloud.lms.core.base.model.ContentViewRecord;
import com.wdcloud.lms.core.base.vo.ReadAllDTO;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JavaDoc")
@ResourceInfo(name = Constants.RESOURCE_TYPE_CONTENT_VIEW_RECORD)
public class ContentViewRecordDataEdit implements IDataEditComponent {
    @Autowired
    private ContentViewRecordDao contentViewRecordDao;

    /**
     * @api {post} /contentViewRecord/add 评论回复全部标记为已读
     * @apiName contentViewRecordAdd
     * @apiGroup Discussion
     * @apiParam {Number} originId 讨论ID或者公告ID
     * @apiParam {Number} [studyGroupId] 小组ID
     * @apiParam {Number} originType 类型，2：讨论回复 4：公告回复
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        ReadAllDTO readAllDTO = JSON.parseObject(dataEditInfo.beanJson, ReadAllDTO.class);
        readAllDTO.setUserId(WebContext.getUserId());
        List<Long> unReadReplyIdList = null;
        if (readAllDTO.getOriginType().equals(ContentViewRecordOrignTypeEnum.DISCUSSION_REPLY.getType())) {
            //1.查询讨论回复的未读Id列表
            unReadReplyIdList = contentViewRecordDao.findUnreadDiscussionReplyList(readAllDTO);
        } else if (readAllDTO.getOriginType().equals(ContentViewRecordOrignTypeEnum.ANNOUNCE_REPLY.getType())) {
            //1.查询公告回复的未读Id列表
            unReadReplyIdList = contentViewRecordDao.findUnreadAnnounceReplyList(readAllDTO);
        }
        //2.批量设置为已读
        batchInsertContentViewRecord(unReadReplyIdList, readAllDTO);
        return new LinkedInfo(JSON.toJSONString(readAllDTO));
    }

    private void batchInsertContentViewRecord(List<Long> unReadReplyIdList, ReadAllDTO readAllDTO) {
        if (ListUtils.isNotEmpty(unReadReplyIdList)) {
            List<ContentViewRecord> list = new ArrayList<>();
            unReadReplyIdList.forEach(replyId -> {
                ContentViewRecord contentViewRecord = ContentViewRecord.builder()
                        .userId(WebContext.getUserId())
                        .originType(readAllDTO.getOriginType())
                        .originId(replyId)
                        .createUserId(WebContext.getUserId())
                        .updateUserId(WebContext.getUserId())
                        .build();
                list.add(contentViewRecord);
            });
            contentViewRecordDao.batchInsert(list);
        }
    }


    /**
     * @api {post} /contentViewRecord/modify 单个评论回复已读未读变更
     * @apiName contentViewRecordModify
     * @apiGroup Discussion
     * @apiParam {Number} originId 来源ID
     * @apiParam {Number} originType 类型，1：讨论话题 2：讨论回复 3：公告话题 4：公告回复
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        ContentViewRecord dto = JSON.parseObject(dataEditInfo.beanJson, ContentViewRecord.class);
        dto.setUserId(WebContext.getUserId());
        ContentViewRecord record = contentViewRecordDao.findOne(dto);
        if (record == null) {
            contentViewRecordDao.save(dto);
        } else {
            contentViewRecordDao.delete(record.getId());
        }
        return new LinkedInfo(String.valueOf(dto.getId()));
    }


    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        return null;
    }
}
