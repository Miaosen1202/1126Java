package com.wdcloud.lms.business.strategy.add;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.*;
import com.wdcloud.lms.business.strategy.AbstractCourseStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseAdd extends AbstractCourseStrategy implements AddStrategy {
    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        CourseResourceShareDTO courseResource = JSON.parseObject(beanJson, CourseResourceShareDTO.class);
        List<AssignmentResourceShareDTO> assignments = courseResource.getAssignments();
        List<DiscussionResourceShareDTO> discussions = courseResource.getDiscussions();
        List<QuizResourceShareDTO> quizzes = courseResource.getQuizzes();
        List<CourseImportGenerationDTO> result = new ArrayList<>();

        for(AssignmentResourceShareDTO assignment : assignments){
            result.addAll(assignmentAdd.addByResource(JSON.toJSONString(assignment), resourceId, courseIds));
        }
        for(DiscussionResourceShareDTO discussion : discussions){
            result.addAll(discussionAdd.addByResource(JSON.toJSONString(discussion), resourceId, courseIds));
        }
        for(QuizResourceShareDTO quiz : quizzes){
            result.addAll(quizAdd.addByResource(JSON.toJSONString(quiz), resourceId, courseIds));
        }

        return result;
    }
}
