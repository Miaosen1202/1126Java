package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.DiscussionResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractDiscussionStrategy;
import com.wdcloud.lms.core.base.enums.ResourceFileTypeEnum;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.utils.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class DiscussionQuery extends AbstractDiscussionStrategy implements QueryStrategy {

    @SuppressWarnings("Duplicates")
    @Override
    public OriginData query(Long id) {
        Discussion discussion = discussionDao.get(id);
        if (discussion != null) {
            return OriginData.builder()
                    .status(discussion.getStatus())
                    .originId(discussion.getId())
                    .score(discussion.getScore())
                    .originType(support().getType())
                    .title(discussion.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    public Object queryDetail(Long moduleItemId) {
        ModuleItem moduleItem = moduleItemDao.get(moduleItemId);
        return moduleItem;
    }

    @Override
    public String queryResourceShareInfo(Long id, Long resourceId, String resourceName) {
        Discussion discussion = discussionDao.get(id);
        Long attachmentFileId = discussion.getAttachmentFileId();
        if(Objects.nonNull(attachmentFileId)){
            attachmentFileId = resourceCommonService.copyFileToResourceFile(attachmentFileId, resourceId, ResourceFileTypeEnum.ATTACHMENT.getType());
            discussion.setAttachmentFileId(attachmentFileId);
        }
        DiscussionResourceShareDTO dto = BeanUtil.copyProperties(discussion, DiscussionResourceShareDTO.class);
        dto.setTitle(resourceName);
        return JSON.toJSONString(dto);
    }

    @Override
    public DiscussionResourceShareDTO convertResourceObject(String beanJson) {
        return JSON.parseObject(beanJson,DiscussionResourceShareDTO.class);
    }
}
