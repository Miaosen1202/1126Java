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
import com.wdcloud.lms.business.resources.vo.ResourceCourseItemDetailVO;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.ResourceFileDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_RES_COURSE_ITEM,
        functionName = Constants.FUNCTION_TYPE_DETAIL
)
public class ResourceCourseItemSearch implements ISelfDefinedSearch<Object> {

    @Autowired
    private ResourceCommonService resourceCommonService;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resCourseItem/detail/query 查询课程资源下的单个资源详情
     * @apiName resourceCourseItemQuery
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源ID
     * @apiParam {Number} originType 来源：1：作业，2：讨论，3：测验
     * @apiParam {Number} originId 来源id
     *
     * @apiSuccess {String} code 响应码
     * @apiSuccess {String} [message] 消息
     * @apiSuccess {Object} [entity] 详情信息
     * @apiSuccess {Number} entity.id
     */
    public Object search(Map<String, String> condition) {
        roleValidateService.teacherAndAdminValid();

        Long resourceId = Long.valueOf(condition.get(Constants.PARAM_RESOURCE_ID));
        String beanJson = resourceCommonService.getResourceData(resourceId);
        Object object;
        Long originId = Long.valueOf(Long.valueOf(condition.get(Constants.PARAM_ORIGIN_ID)));
        CourseResourceShareDTO dto = JSON.parseObject(beanJson, CourseResourceShareDTO.class);
        OriginTypeEnum origin = OriginTypeEnum.typeOf(Integer.valueOf(condition.get(Constants.PARAM_ORIGIN_TYPE)));
        switch (origin){
            case ASSIGNMENT:
                List<AssignmentResourceShareDTO> assigments = dto.getAssignments();
                Map<Long, AssignmentResourceShareDTO> assigmentMap = assigments.stream().collect(Collectors
                        .toMap(AssignmentResourceShareDTO::getId, Function.identity(),
                        (o,n) ->o));
                object = assigmentMap.get(originId);
                break;
            case DISCUSSION:
                List<DiscussionResourceShareDTO> discussions = dto.getDiscussions();
                Map<Long, DiscussionResourceShareDTO> discussionMap = discussions.stream().collect(Collectors
                        .toMap(DiscussionResourceShareDTO::getId, Function.identity(),
                                (o,n) ->o));
                object = discussionMap.get(originId);
                break;
            case QUIZ:
                List<QuizResourceShareDTO> quizzes = dto.getQuizzes();
                Map<Long, QuizResourceShareDTO> quizMap = quizzes.stream().collect(Collectors
                        .toMap(QuizResourceShareDTO::getId, Function.identity(),
                                (o,n) ->o));
                object = quizMap.get(originId);
                break;
            default:
                throw new BaseException();
        }
        return  object;
    }
}
