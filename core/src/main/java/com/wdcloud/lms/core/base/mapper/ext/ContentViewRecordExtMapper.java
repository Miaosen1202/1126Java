package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ContentViewRecord;
import com.wdcloud.lms.core.base.vo.ReadAllDTO;
import com.wdcloud.lms.core.base.vo.ReadCountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContentViewRecordExtMapper {
    List<Long> findUnreadDiscussionReplyList(ReadAllDTO readAllDTO);

    void batchInsert(List<ContentViewRecord> list);

    List<Long> findUnreadAnnounceReplyList(ReadAllDTO readAllDTO);

    List<ReadCountDTO> findAnnounceReplyUnreadTotal(@Param("ids") List<Long> announceIds, @Param("userId") Long userId,@Param("studyGroupId") Long studyGroupId);

    List<ReadCountDTO> findDiscussionReplyUnreadTotal(@Param("ids") List<Long> announceIds, @Param("userId") Long userId,@Param("studyGroupId") Long studyGroupId);
}
