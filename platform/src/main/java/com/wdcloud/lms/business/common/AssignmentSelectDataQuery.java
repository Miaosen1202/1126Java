package com.wdcloud.lms.business.common;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_SELECT)
public class AssignmentSelectDataQuery implements IDataQueryComponent<Assignment> {

    @Autowired
    private AssignmentDao assignmentDao;

    /**
     * @api {get} /assignmentSelect/list 作业下拉列表
     * @apiName assignmentSelectList
     * @apiGroup Common
     * @apiParam {Number} courseId courseId
     * @apiParam {Number} [assignmentGroupId] assignmentGroupId
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 作业信息
     * @apiSuccess {Number} entity.id
     * @apiSuccess {String} entity.title 标题
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends Assignment> list(Map<String, String> param) {
        String assignmentGroupId = param.get(AssignmentGroupItem.ASSIGNMENT_GROUP_ID);
        String courseId = param.get(Assignment.COURSE_ID);
        if (assignmentGroupId == null || "".equals(assignmentGroupId)) {
            return assignmentDao.find(Assignment.builder().courseId(Long.valueOf(courseId)).build());
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("courseId", courseId);
            map.put("assignmentGroupId", assignmentGroupId);
            return assignmentDao.finAssignmentSelectByGroupId(map);
        }
    }

}
