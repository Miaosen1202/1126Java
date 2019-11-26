package com.wdcloud.lms.business.assignment;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.vo.ContentLinkInfoVo;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ASSIGNMENT,
        functionName = Constants.FUNCTION_TYPE_LINK_INFO
)
public class AssignmentLinkInfoQuery implements ISelfDefinedSearch<List<ContentLinkInfoVo>> {
    @Autowired
    private AssignmentDao assignmentDao;

    /**
     * @api {get} /assignment/linkInfo/query 作业链接信息查询
     * @apiName assignmentLinkInfo
     * @apiGroup Common
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} entity 链接信息
     * @apiSuccess {Long} entity.courseId 课程
     * @apiSuccess {Long} entity.id 作业ID
     * @apiSuccess {Long} entity.name 名称
     */
    @Override
    public List<ContentLinkInfoVo> search(Map<String, String> condition) {
        if (!condition.containsKey("courseId")) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get("courseId"));
        List<ContentLinkInfoVo> result = new ArrayList<>();

        List<Assignment> assignments = assignmentDao.find(Assignment.builder().courseId(courseId).build());
        for (Assignment assignment : assignments) {
            ContentLinkInfoVo linkInfoVo = ContentLinkInfoVo.builder()
                    .courseId(courseId)
                    .id(assignment.getId())
                    .name(assignment.getTitle())
                    .build();
            result.add(linkInfoVo);
        }

        return result;
    }
}
