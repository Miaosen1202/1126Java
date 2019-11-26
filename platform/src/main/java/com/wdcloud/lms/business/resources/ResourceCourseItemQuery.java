package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.CourseResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.DiscussionResourceShareDTO;
import com.wdcloud.lms.business.resources.dto.QuizResourceShareDTO;
import com.wdcloud.lms.business.resources.vo.ResourceCourseItemVO;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_COURSE_ITEM, description = "课程资源下的列表查询")
public class ResourceCourseItemQuery implements IDataQueryComponent<ResourceCourseItemVO> {

    @Autowired
    private ResourceCommonService resourceCommonService;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resCourseItem/list 课程资源下的列表查询
     * @apiDescription 课程资源下的列表查询
     * @apiName resCourseItemList
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源ID
     * @apiParam {Number} originType 来源：1：作业，2：讨论，3：测验
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {String} entity.title 名称
     * @apiSuccess {Number} entity.score 分数
     * @apiSuccess {Number} entity.questionCount 问题的个数，当来源为测验时有值
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": [
     *         {
     *             "id": 1606,
     *             "questionCount": 0,
     *             "score": 0,  //讨论中分数为0的是不评分，非零是评分
     *             "title": "fg"
     *         }
     *     ],
     *     "message": "success"
     * }
     */
    @Override
    public List<ResourceCourseItemVO> list(Map<String, String> param) {
        roleValidateService.teacherAndAdminValid();

        Long resourceId = Long.valueOf(param.get(Constants.PARAM_RESOURCE_ID));
        String beanJson = resourceCommonService.getResourceData(resourceId);

        List<ResourceCourseItemVO> vos = new ArrayList<>();
        CourseResourceShareDTO dto = JSON.parseObject(beanJson, CourseResourceShareDTO.class);
        OriginTypeEnum origin = OriginTypeEnum.typeOf(Integer.valueOf(param.get(Constants.PARAM_ORIGIN_TYPE)));
        switch (origin){
            case ASSIGNMENT:
                List<AssignmentResourceShareDTO> assigments = dto.getAssignments();
                for(AssignmentResourceShareDTO assigment : assigments){
                    ResourceCourseItemVO assigmentVO = ResourceCourseItemVO.builder().id(assigment.getId())
                            .title(assigment.getTitle()).score(assigment.getScore()).build();
                    vos.add(assigmentVO);
                }
                break;
            case DISCUSSION:
                List<DiscussionResourceShareDTO> discussions = dto.getDiscussions();
                for(DiscussionResourceShareDTO discussion : discussions){
                    //讨论中分数为0的是不评分，非零是评分
                    ResourceCourseItemVO discussionVO = ResourceCourseItemVO.builder().id(discussion.getId())
                            .title(discussion.getTitle()).score(discussion.getScore()).build();
                    vos.add(discussionVO);
                }
                break;
            case QUIZ:
                List<QuizResourceShareDTO> quizzes = dto.getQuizzes();
                for(QuizResourceShareDTO quiz : quizzes){
                    Integer score = 0;
                    for(QuizItemVO quizItemVO : quiz.getQuizItemVOs()){
                        score += quizItemVO.getQuestion().getScore();
                    }

                    ResourceCourseItemVO discussionVO = ResourceCourseItemVO.builder().id(quiz.getId())
                            .title(quiz.getTitle()).score(score).questionCount(quiz.getQuizItemVOs().size()).build();
                    vos.add(discussionVO);
                }
                break;
            default:
                throw new BaseException();
        }

        return vos;
    }
}
