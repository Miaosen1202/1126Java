package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.mapper.ext.ContentViewRecordExtMapper;
import com.wdcloud.lms.core.base.model.ContentViewRecord;
import com.wdcloud.lms.core.base.vo.ReadAllDTO;
import com.wdcloud.lms.core.base.vo.ReadCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentViewRecordDao extends CommonDao<ContentViewRecord, Long> {
    @Autowired
    private ContentViewRecordExtMapper contentViewRecordExtMapper;
    @Override
    protected Class<ContentViewRecord> getBeanClass() {
        return ContentViewRecord.class;
    }

    public List<Long> findUnreadDiscussionReplyList(ReadAllDTO dto) {
        return contentViewRecordExtMapper.findUnreadDiscussionReplyList(dto);
    }

    public void batchInsert(List<ContentViewRecord> list) {
        contentViewRecordExtMapper.batchInsert(list);
    }

    public List<Long> findUnreadAnnounceReplyList(ReadAllDTO readAllDTO) {
        return contentViewRecordExtMapper.findUnreadAnnounceReplyList(readAllDTO);
    }


    public  List<ReadCountDTO> findAnnounceOrDiscussionReplyUnreadTotal(Long studyGroupId, List<Long> ids, Long userId, OriginTypeEnum originTypeEnum) {
        List<ReadCountDTO> list = null;
        if (originTypeEnum.getType().equals(OriginTypeEnum.ANNOUNCE.getType())) {
            list=contentViewRecordExtMapper.findAnnounceReplyUnreadTotal(ids,userId,studyGroupId);
        }else if (originTypeEnum.getType().equals(OriginTypeEnum.DISCUSSION.getType())) {
            list=contentViewRecordExtMapper.findDiscussionReplyUnreadTotal(ids,userId,studyGroupId);
        }
        return list;
    }
}