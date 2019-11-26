package com.wdcloud.lms.business.strategy.update;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.resources.dto.DiscussionResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractDiscussionStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.utils.ListUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class DiscussionUpdate extends AbstractDiscussionStrategy implements UpdateStrategy {
    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        updateName(baseModuleItemDTO.getId(), baseModuleItemDTO.getName());
    }

    @Override
    public void updateName(Long id, String name) {
        discussionDao.update(Discussion.builder().id(id).title(name).build());
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        valid(id);
        discussionDao.update(Discussion.builder().id(id).status(status).build());
    }

    @Override
    public void updateNameAndScoreAndStatus(Long id, String name, int score, boolean isPublish) {
        valid(id);
        if (isPublish) {
            discussionDao.update(Discussion.builder().id(id).score(score).status(1).title(name).build());
        } else {
            discussionDao.update(Discussion.builder().id(id).score(score).title(name).build());
        }
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        DiscussionResourceShareDTO discussionResoource = JSON.parseObject(beanJson, DiscussionResourceShareDTO.class);
        if(Objects.isNull(discussionResoource)){
            return null;
        }

        Boolean flag = Objects.isNull(discussionResoource.getAttachmentFileId());
        Discussion discussion;
        Long discussionId;
        ResourceImportGeneration importGeneration;
        List<Long> addResourceCourseIds = new ArrayList<>();
        for(Long courseId : courseIds){
            Long userFileId = null;
            importGeneration = resourceCommonService.getResourceImportGenerationByOriginId(resourceId,
                    courseId, discussionResoource.getId(), OriginTypeEnum.DISCUSSION.getType());

            if(Objects.isNull(importGeneration)){
                addResourceCourseIds.add(courseId);
            }else {
                if(!flag){
                    userFileId = resourceCommonService.copyAndSaveFile(discussionResoource.getAttachmentFileId(), courseId);
                }

                discussion = discussionDao.get(importGeneration.getNewId());
                if(Objects.isNull(discussion)){
                    discussionId = discussionAdd.addSingleByResource(discussionResoource, courseId);
                    importGeneration.setNewId(discussionId);
                    resourceImportGenerationDao.update(importGeneration);
                }else {
                    Discussion newDiscussion = Discussion.builder().id(discussion.getId())
                            .title(discussionResoource.getTitle())
                            .attachmentFileId(userFileId).content(discussionResoource.getContent())
                            .isGrade(discussionResoource.getIsGrade())
                            .gradeType(discussionResoource.getGradeType())
                            .gradeSchemeId(discussionResoource.getGradeSchemeId()).build();
                    discussionDao.update(newDiscussion);
                }
            }
        }
        return discussionAdd.addByResource(beanJson, resourceId, addResourceCourseIds);
    }

    /**
     * 判读是否可以修改
     * 如果有学生已经提交　就不允许修改
     *
     * @param id
     */
    private void valid(Long id) {
        if (userSubmitRecordDao.count(UserSubmitRecord.builder()
                .originId(id)
                .originType(support().getType())
                .build()) > 0) {
            //已经提交过
            throw new BaseException("already.submit");
        }
    }
}
