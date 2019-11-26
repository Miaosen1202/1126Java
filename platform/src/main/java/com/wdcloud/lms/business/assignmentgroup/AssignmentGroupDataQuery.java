package com.wdcloud.lms.business.assignmentgroup;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.AssignmentGroupDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.permission.base.StatusEnum;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP)
public class AssignmentGroupDataQuery implements IDataQueryComponent<AssignmentGroup> {

    @Autowired
    private AssignmentGroupDao assignmentGroupDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignUserService assignUserService;

    /**
     * @api {get} /assignmentGroup/list 作业组列表
     * @apiName assignmentGroupList
     * @apiGroup AssignmentGroup
     * @apiParam {Number} courseId 课程ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.name 名称
     * @apiSuccess {String} [entity.weight] 权重
     * @apiSuccess {Number} entity.seq 排序
     * @apiSuccessExample {String} json 返回值
     * {
     * "code": 200,
     * "entity": [
     * {
     * "courseId": 1,
     * "id": 1,
     * "name": "作业001",
     * "seq": 1
     * },
     * {
     * "courseId": 1,
     * "id": 3,
     * "name": "作业003",
     * "seq": 3
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends AssignmentGroup> list(Map<String, String> param) {
        Long courseId = Long.valueOf(param.get(Constants.PARAM_COURSE_ID));
        List<AssignmentGroup> assignmentGroups = Lists.newArrayList();
        Example example = new Example(AssignmentGroup.class);
        example.orderBy(Constants.PARAM_SEQ).asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.PARAM_COURSE_ID, courseId);
        if (roleService.hasStudentRole()) {
            //学生端只展示 作业组中已发布的
            List<AssignmentGroup> list = assignmentGroupDao.find(example);
            for (AssignmentGroup o : list) {
                List<AssignmentGroupItem> groupItems = assignmentGroupItemDao.find(AssignmentGroupItem.builder().assignmentGroupId(o.getId()).build());
                for (AssignmentGroupItem oo : groupItems) {
                    AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(), oo.getOriginType(), oo.getOriginId());
                    //已发布
                    if (StatusEnum.YES.getValue() == oo.getStatus() && !AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum)) {
                        assignmentGroups.add(o);//有一个分配给自己了就行
                        break;
                    }
                }
            }
            return assignmentGroups;

        }

        return assignmentGroupDao.find(example);

    }
}
