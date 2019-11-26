package com.wdcloud.lms.business.strategy.update;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.*;
import com.wdcloud.lms.business.strategy.AbstractCourseStrategy;
import com.wdcloud.lms.business.strategy.add.AddStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseUpdate extends AbstractCourseStrategy implements UpdateStrategy {

    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        CourseResourceShareDTO courseResource = JSON.parseObject(beanJson, CourseResourceShareDTO.class);

        List<CourseImportGenerationDTO> addResourceCourseIds = new ArrayList<>();
        for(AssignmentResourceShareDTO assignment : courseResource.getAssignments()){
            addResourceCourseIds.addAll(assignmentUpdate.updateByResource(JSON.toJSONString(assignment), resourceId, courseIds));
        }
        for(DiscussionResourceShareDTO discussion : courseResource.getDiscussions()){
            addResourceCourseIds.addAll(discussionUpdate.updateByResource(JSON.toJSONString(discussion), resourceId, courseIds));
        }
        for(QuizResourceShareDTO quiz : courseResource.getQuizzes()){
            addResourceCourseIds.addAll(quizUpdate.updateByResource(JSON.toJSONString(quiz), resourceId, courseIds));
        }
        return addResourceCourseIds;
    }
}
