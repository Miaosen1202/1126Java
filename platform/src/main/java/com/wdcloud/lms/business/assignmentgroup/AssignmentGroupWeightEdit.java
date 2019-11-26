package com.wdcloud.lms.business.assignmentgroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.assignmentgroup.dto.AssignmentGroupWeightEditDTO;
import com.wdcloud.lms.core.base.dao.AssignmentGroupDao;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 作业组权重
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP,
        functionName = Constants.FUNCTION_TYPE_WEIGHT
)
public class AssignmentGroupWeightEdit implements ISelfDefinedEdit {
    @Autowired
    private AssignmentGroupDao assignmentGroupDao;
    @Autowired
    private CourseDao courseDao;
    /**
     * @api {post} /assignmentGroup/weight/edit 作业组权重编辑
     * @apiName assignmentGroupWeight
     * @apiGroup Assignment
     * @apiParam {Number} isWeightGrade 评分时是否涉及权重，0:评分时不涉及权重；1:评分时涉及权重
     * @apiParam {Object[]} [weights] 权重
     * @apiParam {Number} id 作业组id
     * @apiParam {Object} weight 权重
     * 
     * @apiParamExample 请求示例:
     * {
     * 	"isWeightGrade":1,
     * 	"weights":[
     * 		{
     * 			"id":492,
     * 			"weight":100
     * 		},
     * 		{
     * 			"id":558,
     * 			"weight":100
     * 		},
     * 		{
     * 			"id":585,
     * 			"weight":100
     * 		}
     * 	],
     * 	"courseId":304
     * }
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动项ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        //参数解析
        AssignmentGroupWeightEditDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentGroupWeightEditDTO.class);
        List<AssignmentGroup> list = dto.getWeights();
        Integer isWeightGrade = dto.getIsWeightGrade();
        long courseId = Long.parseLong(dto.getCourseId());
        //更新权重
        list.forEach(o-> assignmentGroupDao.update(AssignmentGroup.builder().id(o.getId()).weight(o.getWeight()).build()));
        //更新课程权重设置
        Course oldCourse = courseDao.get(courseId);
        oldCourse.setIsWeightGrade(isWeightGrade);
        courseDao.update(oldCourse);
        return new LinkedInfo(Code.OK.name);
    }
}
