package com.wdcloud.lms.business.assignmentgroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.assignmentgroup.vo.AssignmentGroupItemVO;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.permission.base.StatusEnum;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP_ITEM)
public class AssignmentGroupItemDataQuery implements IDataQueryComponent<AssignmentGroupItem> {
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private AssignUserService assignUserService;

    /**
     * @api {get} /assignmentGroupItem/list 作业组小项列表
     * @apiName assignmentGroupItemList
     * @apiGroup AssignmentGroup
     * @apiParam {Number} assignmentGroupId 作业组ID
     * @apiParam {String} [title] 作业标题模糊查
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.title 名称
     * @apiSuccess {Number} entity.originId 源ID
     * @apiSuccess {Number} entity.originType 源类型
     * @apiSuccess {Number} entity.status 状态
     * @apiSuccess {Number} entity.seq 排序
     * @apiSuccess {Number} [entity.modules] 被依赖module
     * @apiSuccess {Number} [entity.assigns] 分配列表
     * @apiSuccessExample {String} json 返回值
     * {
     * "code": 200,
     * "entity": [
     * {
     * "assignmentGroupId": 1,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "createTime": 1548243401000,
     * "createUserId": 2,
     * "endTime": 1548215072000,
     * "id": 1,
     * "limitTime": 1548214407000,
     * "originId": 1,
     * "originType": 1,
     * "startTime": 1547869454000,
     * "updateTime": 1548243876000,
     * "updateUserId": 2
     * }
     * ],
     * "id": 1,
     * "modules": [
     * {
     * "courseId": 1,
     * "createTime": 1548174406000,
     * "createUserId": 2,
     * "id": 2,
     * "name": "开学第2课",
     * "seq": 2,
     * "startTime": 1544760039000,
     * "status": 0,
     * "updateTime": 1548174406000,
     * "updateUserId": 2
     * }
     * ],
     * "originId": 1,
     * "originType": 1,
     * "score": 10,
     * "seq": 0,
     * "status": 1,
     * "title": "第一章作业"
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    public List<? extends AssignmentGroupItem> list(Map<String, String> param) {
        Example example = assignmentGroupItemDao.getExample();
        example.orderBy(AssignmentGroupItem.SEQ);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(AssignmentGroupItem.ASSIGNMENT_GROUP_ID, param.get(Constants.PARAM_ASSIGNMENT_GROUP_ID));
        if (roleService.hasStudentRole()) {
            criteria.andEqualTo(AssignmentGroupItem.STATUS, StatusEnum.YES.getValue());
        }
        String title = param.get(AssignmentGroupItem.TITLE);
        if (title != null && !"".equals(title)) {
            criteria.andLike(AssignmentGroupItem.TITLE, "%"+title+"%");
        }
        List<AssignmentGroupItem> items = assignmentGroupItemDao.find(example);
        if (CollectionUtil.isNullOrEmpty(items)) {
            return Collections.emptyList();
        }
        //items 包含 作业 讨论 测验三种类型
        List<AssignmentGroupItemVO> vos = new ArrayList<>(items.size());
        for (AssignmentGroupItem o : items) {
            AssignmentGroupItemVO vo = BeanUtil.beanCopyProperties(o, AssignmentGroupItemVO.class, Constants.IGNORE_PROPERTIES);
            List<Module> modules = moduleDao.getModuleByOriginTypeAndOriginId(o.getOriginId(), o.getOriginType());
            vo.setModules(modules);
            //设置assign
            if (roleService.hasStudentRole()) {
                AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(), o.getOriginType(), o.getOriginId());
                if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum)) {
                    vos.add(vo);
                }
            } else {
                vos.add(vo);
            }
        }
        return vos;
    }

    /**
     * @api {get} /assignmentGroupItem/get 作业组小项详情
     * @apiName assignmentGroupItemGet
     * @apiGroup AssignmentGroup
     * @apiParam {Number} id 作业ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id 作业组小项ID
     * @apiSuccess {Number} entity.assignmentGroupId 作业组ID
     * @apiSuccess {String} entity.title 名称
     * @apiSuccess {Number} entity.score 得分
     * @apiSuccess {Number} entity.status 发布状态
     * @apiSuccess {Number} entity.originType 任务类型： 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {Number} entity.originId 来源ID
     * @apiSuccess {Number} entity.seq 排序
     */
    @Override
    public AssignmentGroupItem find(String id) {
        return assignmentGroupItemDao
                .findOne(AssignmentGroupItem.builder()
                        .originType(OriginTypeEnum.ASSIGNMENT.getType())
                        .originId(Long.valueOf(id))
                        .build());
    }
}
