package com.wdcloud.lms.business.strategy.add;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.business.discussion.dto.DiscussionAddDTO;
import com.wdcloud.lms.business.discussion.enums.DiscussionTypeEnum;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.resources.dto.DiscussionResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractDiscussionStrategy;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class DiscussionAdd extends AbstractDiscussionStrategy implements AddStrategy {
    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        //0、参数解析
        if (null == moduleItemContentDTO.getIsGrade()) {
            throw new ParamErrorException();
        }
        //1、获取assigns
        List<Assign> assigns = new ArrayList<>();
        assigns.add(Assign.builder().assignType(AssignTypeEnum.ALL.getType()).build());
        //2、拼凑dataEditInfo
        Discussion discussion = Discussion.builder()
                .courseId(moduleItemContentDTO.getCourseId())
                .title(moduleItemContentDTO.getTitle())
                .type(DiscussionTypeEnum.NORMAL.getType())
                .gradeType(1)
                .score(0)
                .status(0)
                .build();
        DiscussionAddDTO discussionAddDTO = BeanUtil.beanCopyProperties(discussion, DiscussionAddDTO.class);
        discussionAddDTO.setAssign(assigns);
        discussionAddDTO.setAssignmentGroupId(moduleItemContentDTO.getAssignmentGroupId());
        discussionAddDTO.setIsGrade(moduleItemContentDTO.getIsGrade());
        String beanJson = JSON.toJSONString(discussionAddDTO);
        DataEditInfo dataEditInfo = new DataEditInfo(beanJson);
        //3、添加讨论
        LinkedInfo linkedInfo = discussionDataEdit.add(dataEditInfo);
        //4、返回讨论ID
        return Long.valueOf(linkedInfo.masterId);
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        DiscussionResourceShareDTO discussionResoource = JSON.parseObject(beanJson, DiscussionResourceShareDTO.class);
        List<CourseImportGenerationDTO> result = new ArrayList<>();

        Long discussionId;
        for(Long courseId : courseIds){
            discussionId = addSingleByResource(discussionResoource, courseId);
            result.add(new CourseImportGenerationDTO(courseId, discussionId,
                    discussionResoource.getId(), OriginTypeEnum.DISCUSSION.getType()));
        }
        return result;
    }

    public Long addSingleByResource(DiscussionResourceShareDTO discussionResoource, Long courseId){
        Long assignmentGroupId = resourceCommonService.getAssignmentGroupId(courseId);
        Long userFileId = null;

        if(Objects.nonNull(discussionResoource.getAttachmentFileId())){
            userFileId = resourceCommonService.copyAndSaveFile(discussionResoource.getAttachmentFileId(), courseId);
        }

        Discussion discussion = Discussion.builder().courseId(courseId).studyGroupId(assignmentGroupId)
                .title(discussionResoource.getTitle()).type(discussionResoource.getType())
                .attachmentFileId(userFileId)
                .content(discussionResoource.getContent()).isGrade(discussionResoource.getIsGrade())
                .score(discussionResoource.getScore()).gradeType(discussionResoource.getGradeType())
                .gradeSchemeId(discussionResoource.getGradeSchemeId()).build();
        discussionDao.save(discussion);

        AssignmentGroupItem assignmentGroupItem = AssignmentGroupItem.builder()
                .originId(discussion.getId()).assignmentGroupId(assignmentGroupId)
                .originType(OriginTypeEnum.DISCUSSION.getType()).title(discussion.getTitle())
                .score(discussion.getScore()).status(Status.NO.getStatus()).build();
        assignmentGroupItemDao.save(assignmentGroupItem);

        assignmentGroupItemChangeRecordDao.discussionAdded(discussion, assignmentGroupItem.getId());

        return discussion.getId();
    }
}
