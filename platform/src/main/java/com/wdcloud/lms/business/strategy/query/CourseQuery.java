package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.CourseResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.DiscussionResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.QuizResourceShareDTO;
import com.wdcloud.lms.business.resources.vo.CourseResourceShareVO;
import com.wdcloud.lms.business.resources.vo.ResourceCourseItemVO;
import com.wdcloud.lms.business.resources.vo.ResourceCourseItemsVO;
import com.wdcloud.lms.business.strategy.AbstractCourseStrategy;
import com.wdcloud.lms.core.base.enums.ResourceFileTypeEnum;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.utils.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class CourseQuery  extends AbstractCourseStrategy implements QueryStrategy {
    @Override
    public OriginData query(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object queryDetail(Long moduleItemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String queryResourceShareInfo(Long id, Long resourceId, String resourceName) {
        Assignment assignment = Assignment.builder().courseId(id).build();
        List<Assignment> assignments = assignmentDao.find(assignment);
        List<AssignmentResourceShareDTO> assignmentDTOs = BeanUtil.beanCopyPropertiesForList(assignments, AssignmentResourceShareDTO.class);

        Long attachmentFileId = null;
        Discussion discussion = Discussion.builder().courseId(id).build();
        List<Discussion> discussions = discussionDao.find(discussion);
        for(Discussion temp : discussions){
            if(Objects.nonNull(temp.getAttachmentFileId())){
                attachmentFileId = resourceCommonService.copyFileToResourceFile(temp.getAttachmentFileId(), resourceId, ResourceFileTypeEnum.ATTACHMENT.getType());
                temp.setAttachmentFileId(attachmentFileId);
            }
        }
        List<DiscussionResourceShareDTO> discussionDTOs = BeanUtil.beanCopyPropertiesForList(discussions, DiscussionResourceShareDTO.class);

        List<QuizResourceShareDTO> quizDTOs = new ArrayList<>();
        Quiz quiz = Quiz.builder().courseId(id).build();
        List<Quiz> quizzes = quizDao.find(quiz);
        for(Quiz temp : quizzes){
            QuizResourceShareDTO quizDTO = BeanUtil.copyProperties(temp, QuizResourceShareDTO.class);
            List<QuizItemVO> quizItemVOs = quizItemDao.getQuerstionAllInfors(temp.getId());
            quizDTO.setQuizItemVOs(quizItemVOs);
            quizDTOs.add(quizDTO);
        }

        CourseResourceShareDTO dto = new CourseResourceShareDTO(assignmentDTOs, discussionDTOs, quizDTOs);
        return JSON.toJSONString(dto);
    }

    @Override
    public Object convertResourceObject(String beanJson) {
        CourseResourceShareDTO dto = JSON.parseObject(beanJson, CourseResourceShareDTO.class);
        ResourceCourseItemsVO assignments = ResourceCourseItemsVO.builder().count(dto.getAssignments().size()).build();
        ResourceCourseItemsVO discussions = ResourceCourseItemsVO.builder().count(dto.getDiscussions().size()).build();
        ResourceCourseItemsVO quizes = ResourceCourseItemsVO.builder().count(dto.getQuizzes().size()).build();

        if(assignments.getCount() > 0){
            assignments.setItems(BeanUtil.beanCopyPropertiesForList(dto.getAssignments(), ResourceCourseItemVO.class));
        }else if(discussions.getCount() > 0){
            discussions.setItems(BeanUtil.beanCopyPropertiesForList(dto.getDiscussions(), ResourceCourseItemVO.class));
        }else if(quizes.getCount() > 0){
            List<ResourceCourseItemVO> resourceCourseItemVOs = new ArrayList<>();
            for(QuizResourceShareDTO quiz : dto.getQuizzes()){
                Integer score = 0;
                List<QuizItemVO> quizItemVOs = quiz.getQuizItemVOs();
                for(QuizItemVO quizItemVO : quizItemVOs){
                    score += quizItemVO.getQuestion().getScore();
                }
                 resourceCourseItemVOs.add(ResourceCourseItemVO.builder().score(score).questionCount(quiz.getQuizItemVOs().size()).build());
            }
            quizes.setItems(resourceCourseItemVOs);
        }

        CourseResourceShareVO vo = CourseResourceShareVO.builder().assignments(assignments)
                .discussions(discussions).quizes(quizes).build();
        return vo;
    }
}
