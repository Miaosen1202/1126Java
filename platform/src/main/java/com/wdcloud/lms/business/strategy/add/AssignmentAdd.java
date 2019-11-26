package com.wdcloud.lms.business.strategy.add;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.assignment.dto.AssignmentDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractAssignmentStrategy;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentAdd extends AbstractAssignmentStrategy implements AddStrategy {
    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        //1、获取assigns
        List<Assign> assigns = new ArrayList<>();
        assigns.add(Assign.builder().assignType(AssignTypeEnum.ALL.getType()).build());
        //2、拼凑dataEditInfo
        AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .courseId(moduleItemContentDTO.getCourseId())
                .title(moduleItemContentDTO.getTitle())
                .assignmentGroupId(moduleItemContentDTO.getAssignmentGroupId())
                .showScoreType(2)
                .assign(assigns)
                .build();
        String beanJson = JSON.toJSONString(assignmentDTO);
        DataEditInfo dataEditInfo = new DataEditInfo(beanJson);
        //3、添加作业
        LinkedInfo linkedInfo = assignmentDataEdit.add(dataEditInfo);
        //4、获取作业ID及返回
        String masterId = linkedInfo.masterId;
        String[] results = StringUtils.split(masterId, "_");
        return Long.valueOf(results[0]);
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        AssignmentResourceShareDTO assignResource = JSON.parseObject(beanJson, AssignmentResourceShareDTO.class);
        List<CourseImportGenerationDTO> result = new ArrayList<>();

        Long assignmentId;
        for(Long courseId : courseIds){
            assignmentId = addSingleByResource(assignResource, courseId);
            result.add(new CourseImportGenerationDTO(courseId, assignmentId,
                    assignResource.getId(), OriginTypeEnum.ASSIGNMENT.getType()));
        }
        return result;
    }

    public Long addSingleByResource(AssignmentResourceShareDTO assignResource, Long courseId){
        Long assignmentGroupId = resourceCommonService.getAssignmentGroupId(courseId);
        Assignment assignment = Assignment.builder().courseId(courseId).title(assignResource.getTitle())
                .description(assignResource.getDescription()).score(assignResource.getScore())
                .showScoreType(assignResource.getShowScoreType()).submissionType(assignResource.getSubmissionType())
                .isIncludeGrade(assignResource.getIsIncludeGrade())
                .allowFile(assignResource.getAllowFile()).allowMedia(assignResource.getAllowMedia())
                .allowText(assignResource.getAllowText()).allowUrl(assignResource.getAllowUrl())
                .fileLimit(assignResource.getFileLimit()).status(Status.NO.getStatus()).build();
        assignmentDao.save(assignment);

        AssignmentGroupItem assignmentGroupItem = AssignmentGroupItem.builder()
                .assignmentGroupId(assignmentGroupId).originId(assignment.getId())
                .originType(OriginTypeEnum.ASSIGNMENT.getType()).title(assignment.getTitle())
                .score(assignment.getScore()).status(Status.NO.getStatus()).build();
        assignmentGroupItemDao.save(assignmentGroupItem);

        assignmentGroupItemChangeRecordDao.assignmentAdded(assignment, assignmentGroupItem.getId());

        return assignment.getId();
    }
}
