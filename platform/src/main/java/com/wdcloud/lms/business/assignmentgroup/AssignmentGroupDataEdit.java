package com.wdcloud.lms.business.assignmentgroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.delete.DeleteContext;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.AssignmentGroupDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.exceptions.AtLeastOneGroupingException;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP, description = "作业组编辑")
public class AssignmentGroupDataEdit implements IDataEditComponent {

    @Autowired
    private AssignmentGroupDao assignmentGroupDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private StrategyFactory strategyFactory;

    /**
     * @api {post} /assignmentGroup/add 作业组添加
     * @apiName assignmentGroupAdd
     * @apiGroup AssignmentGroup
     * @apiParam {Number} courseId 课程
     * @apiParam {String} name 名称
     * @apiParam {Number} [weight] 权重
     * @apiParamExample 请求示例:
     * {
     * "courseId":1,
     * "name": "assignments",
     * "weight":100
     * }
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final AssignmentGroup assignmentGroup = JSON.parseObject(dataEditInfo.beanJson, AssignmentGroup.class);
        Integer seq = assignmentGroupDao.ext().getMaxSeq();
        assignmentGroup.setSeq(seq == null ? 1 : seq + 1);
        assignmentGroupDao.save(assignmentGroup);
        return new LinkedInfo(assignmentGroup.getId().toString());
    }

    /**
     * @api {post} /assignmentGroup/modify 作业组修改
     * @apiName assignmentGroupModify
     * @apiGroup AssignmentGroup
     * @apiParam {Number} id ID
     * @apiParam {String} name 名称
     * @apiParam {Number} [weight] 权重
     * @apiParamExample 请求示例:
     * {
     * "name": "assignments",
     * "id": 1,
     * "weight":100
     * }
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 修改ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final AssignmentGroup assignmentGroup = JSON.parseObject(dataEditInfo.beanJson, AssignmentGroup.class);
        if (null == assignmentGroup.getId() || null == assignmentGroup.getName() || "".equals(assignmentGroup.getName())) {
            throw new ParamErrorException();
        }
        assignmentGroupDao.update(assignmentGroup);
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /assignmentGroup/deletes 作业组删除
     * @apiName assignmentGroupDelete
     * @apiGroup AssignmentGroup
     * @apiParam {Number} ids ID列表
     * @apiParamExample 请求示例:
     * 1
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 删除ID列表
     * @apiSuccessExample 响应示例:
     * {
     * "code": 200,
     * "entity": "[1, 2, 3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        //删除AssignmentGroup
        Long id = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        final AssignmentGroup group = assignmentGroupDao.findOne(AssignmentGroup.builder().id(id).build());
        if (Objects.isNull(group)) {
            return new LinkedInfo(Code.OK.name);
        }
        //最少保留一个分组
        final int count = assignmentGroupDao.count(AssignmentGroup.builder().courseId(group.getCourseId()).build());
        if (count == 1) {
            throw new AtLeastOneGroupingException();
        }
        //删除组下小项
        List<AssignmentGroupItem> items = assignmentGroupItemDao.find(AssignmentGroupItem.builder().assignmentGroupId(id).build());
        if (CollectionUtil.isNotNullAndEmpty(items)) {
            items.forEach(o -> {
                DeleteContext context = new DeleteContext(strategyFactory.getDeleteStrategy(OriginTypeEnum.typeOf(o.getOriginType())));
                context.delete(o.getId());
            });
        }
        assignmentGroupDao.delete(id);
        return new LinkedInfo(Code.OK.name);
    }
}
