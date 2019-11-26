package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.module.dto.ModuleItemMoveDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.modulecontentmove.ModuleContentMoveContext;
import com.wdcloud.lms.business.strategy.moduleitemmove.ModuleItemMoveContext;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE_ITEM,
        functionName = Constants.FUNCTION_TYPE_MOVE_CONTENT
)
public class ModuleItemMoveEdit implements ISelfDefinedEdit {
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /moduleItem/moveContent/edit 单元项移动
     * @apiDescription 单元项移动
     * @apiName moduleContentMove
     * @apiGroup Module
     * @apiParam {Number} sourceModuleId 来源单元ID
     * @apiParam {Number} [sourceModuleItemId] 来源单元内容项ID，不指定则移动来源单元下所有内容到目标单元
     * @apiParam {Number} targetModuleId 目标单元ID
     * @apiParam {Number} [targetModuleItemId] 配合strategy使用，当strategy为 BEFORE/AFTER 时用于指定单元内容ID
     * @apiParam {Number=DEFAULT:0,TOP:1,BOTTOM:2,BEFORE:3,AFTER:4,} strategy 移动策略,DEFAULT:目标组无内容时指定，TOP：置顶，BOTTOM：置底，BEFORE：指定之前，AFTER：指定之后
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动项ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        final ModuleItemMoveDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemMoveDTO.class);
        if (dto.getSourceModuleItemId() == null) {
            moduleCompleteService.moveModuleItems(dto.getSourceModuleId(), dto.getTargetModuleId());
            ModuleContentMoveContext context = new ModuleContentMoveContext(strategyFactory.getModuleContentMoveStrategy(MoveStrategyEnum.strategyOf(dto.getStrategy())));
            context.execute(dto.getSourceModuleId(), dto.getTargetModuleId(), dto.getTargetModuleItemId());
        } else {
            moduleCompleteService.moveModuleItem(dto.getSourceModuleItemId(), dto.getTargetModuleId());
            final ModuleItem item = moduleItemDao.get(dto.getSourceModuleItemId());
            ModuleItemMoveContext context = new ModuleItemMoveContext(strategyFactory.getModuleItemMoveStrategy(MoveStrategyEnum.strategyOf(dto.getStrategy())));
            context.execute(item.getId(), dto.getTargetModuleId(), dto.getTargetModuleItemId());
        }
        return new LinkedInfo(Code.OK.name);
    }
}
