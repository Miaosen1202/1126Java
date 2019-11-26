package com.wdcloud.lms.business.strategy.update;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.business.assignment.dto.AssignmentDTO;
import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractAssignmentStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.ResourceImportGeneration;
import com.wdcloud.lms.core.base.model.UserSubmitRecord;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
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
public class AssignmentUpdate extends AbstractAssignmentStrategy implements UpdateStrategy {


    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        updateName(baseModuleItemDTO.getId(), baseModuleItemDTO.getName());
    }

    @Override
    public void updateName(Long id, String name) {
        assignmentDao.update(Assignment.builder().id(id).title(name).build());
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        valid(id);
        assignmentDao.update(Assignment.builder().id(id).status(status).build());
    }

    @Override
    public void updateNameAndScoreAndStatus(Long id, String name, int score, boolean isPublish) {
        valid(id);
        if (isPublish) {
            assignmentDao.update(Assignment.builder().id(id).score(score).status(1).title(name).build());
        } else {
            assignmentDao.update(Assignment.builder().id(id).score(score).title(name).build());
        }
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        AssignmentResourceShareDTO assignResource = JSON.parseObject(beanJson, AssignmentResourceShareDTO.class);
        if(Objects.isNull(assignResource)){
            return null;
        }

        Long assignmentId;
        Assignment assignment;
        ResourceImportGeneration importGeneration;
        List<Long> addResourceCourseIds = new ArrayList<>();
        for(Long courseId : courseIds){
            importGeneration = resourceCommonService.getResourceImportGenerationByOriginId(resourceId,
                    courseId, assignResource.getId(), OriginTypeEnum.ASSIGNMENT.getType());
            if(Objects.isNull(importGeneration)){
                addResourceCourseIds.add(courseId);
            }else {
                assignment = assignmentDao.get(importGeneration.getNewId());
                if(Objects.isNull(assignment)){
                    assignmentId = assignmentAdd.addSingleByResource(assignResource, courseId);
                    importGeneration.setNewId(assignmentId);
                    resourceImportGenerationDao.update(importGeneration);
                }else {
                    Assignment newAssignment = Assignment.builder().id(assignment.getId()).title(assignResource.getTitle())
                            .description(assignResource.getDescription()).score(assignResource.getScore())
                            .showScoreType(assignResource.getShowScoreType()).submissionType(assignResource.getSubmissionType())
                            .isIncludeGrade(assignResource.getIsIncludeGrade())
                            .allowFile(assignResource.getAllowFile()).allowMedia(assignResource.getAllowMedia())
                            .allowText(assignResource.getAllowText()).allowUrl(assignResource.getAllowUrl())
                            .fileLimit(assignResource.getFileLimit()).build();

                    assignmentDao.update(newAssignment);
                }
            }
        }
        return assignmentAdd.addByResource(beanJson, resourceId, addResourceCourseIds);
    }

    /**
     * 判读是否可以修改
     * 如果有学生已经提交作业　就不允许修改
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
