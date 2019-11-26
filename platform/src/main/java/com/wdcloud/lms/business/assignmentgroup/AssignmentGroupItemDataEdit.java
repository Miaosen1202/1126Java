package com.wdcloud.lms.business.assignmentgroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.assignmentgroup.dto.AssignmentItemEditDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.delete.DeleteContext;
import com.wdcloud.lms.business.strategy.update.UpdateContext;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP_ITEM)
public class AssignmentGroupItemDataEdit implements IDataEditComponent {
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private StrategyFactory strategyFactory;

    /**
     * @api {post} /assignmentGroupItem/modify 作业组小项编辑
     * @apiName assignmentGroupItemModify
     * @apiGroup AssignmentGroup
     * @apiParam {Number} id ID
     * @apiParam {String} name 名称
     * @apiParam {Number} score 分值
     * @apiParam {Number} status 是否发布
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 修改ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final AssignmentItemEditDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentItemEditDTO.class);
        final AssignmentGroupItem item = assignmentGroupItemDao.get(dto.getId());
        final Integer originType = item.getOriginType();
        UpdateContext context = new UpdateContext(strategyFactory.getUpdateStrategy(OriginTypeEnum.typeOf(originType)));
        context.execute(item.getOriginId(), dto.getName(), dto.getScore(), dto.getStatus() != 0);
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /assignmentGroupItem/deletes 作业组小项删除
     * @apiName assignmentGroupItemDelete
     * @apiGroup AssignmentGroup
     * @apiParam {Number} id ID
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccessExample 响应示例:
     * {
     * "code": 200,
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long aLong = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        final AssignmentGroupItem item = assignmentGroupItemDao.get(aLong);
        assignmentGroupItemDao.delete(aLong);
        DeleteContext context = new DeleteContext(strategyFactory.getDeleteStrategy(OriginTypeEnum.typeOf(item.getOriginType())));
        context.delete(item.getOriginId());
        return new LinkedInfo(Code.OK.name);
    }
}
