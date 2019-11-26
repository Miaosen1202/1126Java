package com.wdcloud.lms.business.assignmentgroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.assignment.dto.AssignmentItemMoveDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.assignmentgroupcontentmove.AssignmentGroupContentMoveContext;
import com.wdcloud.lms.business.strategy.assignmentgroupitemmove.AssignmentGroupItemMoveContext;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP,
        functionName = Constants.FUNCTION_TYPE_MOVE_CONTENT
)
public class AssignmentGroupItemMoveEdit implements ISelfDefinedEdit {

    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private StrategyFactory strategyFactory;
    /**
     * @api {post} /assignmentGroup/moveContent/edit 作业组小项移动
     * @apiName assignmentGroupContentMove
     * @apiGroup AssignmentGroup
     *
     * @apiParam {Number} sourceAssignmentGroupId 来源组ID
     * @apiParam {Number} [sourceAssignmentGroupItemId] 来源组内容项ID，不指定则移动来源组下所有内容到目标组
     * @apiParam {Number} targetAssignmentGroupId 目标组ID
     * @apiParam {Number} [targetAssignmentGroupItemId] 来源组内容项ID，不指定则移动来源组下所有内容到目标组
     * @apiParam {Number=DEFAULT:0,TOP:1,BOTTOM:2,BEFORE:3,AFTER:4,} strategy 移动策略,DEFAULT:目标组无内容时指定，TOP：置顶，BOTTOM：置底，BEFORE：指定之前，AFTER：指定之后
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动项ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        final AssignmentItemMoveDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentItemMoveDTO.class);
        if (Objects.isNull(dto.getSourceAssignmentGroupItemId())) {
            AssignmentGroupContentMoveContext context = new AssignmentGroupContentMoveContext(
                    strategyFactory.getAssignmentGroupContentMoveStrategy(MoveStrategyEnum.strategyOf(dto.getStrategy())));
            context.execute(dto.getSourceAssignmentGroupId(), dto.getTargetAssignmentGroupId(), dto.getTargetAssignmentGroupItemId());
        } else {
            final AssignmentGroupItem item = assignmentGroupItemDao.get(dto.getSourceAssignmentGroupItemId());
            AssignmentGroupItemMoveContext context = new AssignmentGroupItemMoveContext(
                    strategyFactory.getAssignmentGroupItemMoveStrategy(MoveStrategyEnum.strategyOf(dto.getStrategy())));
            context.execute(item.getId(), dto.getTargetAssignmentGroupId(), dto.getTargetAssignmentGroupItemId());
        }
        return new LinkedInfo(Code.OK.name);
    }
}
