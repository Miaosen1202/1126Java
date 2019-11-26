package com.wdcloud.lms.business.assignmentgroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.assignmentgroup.dto.AssignmentGroupWeightEditDTO;
import com.wdcloud.lms.core.base.dao.AssignmentGroupDao;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP,
        functionName = Constants.FUNCTION_TYPE_WEIGHT
)
public class AssignmentGroupWeightQuery implements ISelfDefinedSearch<AssignmentGroupWeightEditDTO> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private AssignmentGroupDao assignmentGroupDao;
    /**
     * @api {get} /assignmentGroup/weight/query 作业组权重设置详情
     * @apiName AssignmentGroupWeightQuery
     * @apiGroup Assignment
     * @apiParam {Number} courseId 课程ID
     * @apiSuccess {Object} entity 结果
     * @apiSuccess {Number} entity.isWeightGrade 评分时是否涉及权重
     * @apiSuccess {Object[]} entity.weights 权重
     * @apiSuccess {Number} entity.weights.id 作业组id
     * @apiSuccess {Object} [entity.weights.weight] 权重
     */
    @Override
    public AssignmentGroupWeightEditDTO search(Map<String, String> param) {
        //参数解析
        Long courseId = Long.parseLong(param.get(Constants.PARAM_COURSE_ID));
        //结果初始化
        AssignmentGroupWeightEditDTO result = new AssignmentGroupWeightEditDTO();
        result.setIsWeightGrade(0);
        //查询course
        Course course = courseDao.get(courseId);
        if (course.getIsWeightGrade() != null) {
            result.setIsWeightGrade(course.getIsWeightGrade());
        }
        //查询assignmentGroup
        Example example = assignmentGroupDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(AssignmentGroup.COURSE_ID, courseId);
        List<AssignmentGroup> assignmentGroups = assignmentGroupDao.find(example);
        result.setWeights(assignmentGroups);
        return result;
    }

}
