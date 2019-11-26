package com.wdcloud.lms.base.service;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.ContentViewRecordDao;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.model.ContentViewRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContentViewRecordService {
    @Autowired
    private ContentViewRecordDao contentViewRecordDao;

    /**
     * 插入已读记录
     * @param originId
     * @param originType
     * @return
     */
    public int insert(Long originId,Integer originType) {
        ContentViewRecord contentViewRecord = ContentViewRecord.builder()
                .originType(originType)
                .originId(originId)
                .userId(WebContext.getUserId())
                .build();
        return contentViewRecordDao.save(contentViewRecord);
    }
}
