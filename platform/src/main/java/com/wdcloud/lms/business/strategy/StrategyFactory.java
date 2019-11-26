package com.wdcloud.lms.business.strategy;

import com.wdcloud.lms.base.enums.MoveStrategyEnum;
import com.wdcloud.lms.business.strategy.add.AddStrategy;
import com.wdcloud.lms.business.strategy.assignmentgroupcontentmove.AssignmentGroupContentMoveStrategy;
import com.wdcloud.lms.business.strategy.assignmentgroupitemmove.AssignmentGroupItemMoveStrategy;
import com.wdcloud.lms.business.strategy.delete.DeleteStrategy;
import com.wdcloud.lms.business.strategy.modulecontentmove.ModuleContentMoveStrategy;
import com.wdcloud.lms.business.strategy.moduleitemmove.ModuleItemMoveStrategy;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.business.strategy.update.UpdateStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class StrategyFactory {

    private Map<OriginTypeEnum, AddStrategy> addStrategyMap = new EnumMap<>(OriginTypeEnum.class);
    private Map<OriginTypeEnum, QueryStrategy> queryStrategyMap = new EnumMap<>(OriginTypeEnum.class);
    private Map<OriginTypeEnum, DeleteStrategy> deleteStrategyMap = new EnumMap<>(OriginTypeEnum.class);
    private Map<OriginTypeEnum, UpdateStrategy> updateStrategyMap = new EnumMap<>(OriginTypeEnum.class);
    private Map<MoveStrategyEnum, ModuleItemMoveStrategy> moduleItemMoveStrategyMap = new EnumMap<>(MoveStrategyEnum.class);
    private Map<MoveStrategyEnum, ModuleContentMoveStrategy> moduleContentMoveStrategyMap = new EnumMap<>(MoveStrategyEnum.class);
    private Map<MoveStrategyEnum, AssignmentGroupItemMoveStrategy> assignmentGroupItemMoveStrategyMap = new EnumMap<>(MoveStrategyEnum.class);
    private Map<MoveStrategyEnum, AssignmentGroupContentMoveStrategy> assignmentGroupContentMoveStrategyMap = new EnumMap<>(MoveStrategyEnum.class);

    @Autowired
    private void init(AddStrategy[] strategies) {
        for (AddStrategy strategy : strategies) {
            addStrategyMap.put(strategy.support(), strategy);
        }
    }

    public AddStrategy getAddStrategy(OriginTypeEnum type) {
        return addStrategyMap.get(type);
    }

    @Autowired
    private void init(DeleteStrategy[] strategies) {
        for (DeleteStrategy strategy : strategies) {
            deleteStrategyMap.put(strategy.support(), strategy);
        }
    }

    public DeleteStrategy getDeleteStrategy(OriginTypeEnum type) {
        return deleteStrategyMap.get(type);
    }

    @Autowired
    private void init(QueryStrategy[] strategies) {
        for (QueryStrategy strategy : strategies) {
            queryStrategyMap.put(strategy.support(), strategy);
        }
    }

    public QueryStrategy getQueryStrategy(OriginTypeEnum type) {
        return queryStrategyMap.get(type);
    }

    @Autowired
    private void init(UpdateStrategy[] strategies) {
        for (UpdateStrategy strategy : strategies) {
            updateStrategyMap.put(strategy.support(), strategy);
        }
    }

    public UpdateStrategy getUpdateStrategy(OriginTypeEnum type) {
        return updateStrategyMap.get(type);
    }

    @Autowired
    private void init(ModuleItemMoveStrategy[] strategies) {
        for (ModuleItemMoveStrategy strategy : strategies) {
            moduleItemMoveStrategyMap.put(strategy.support(), strategy);
        }
    }

    public ModuleItemMoveStrategy getModuleItemMoveStrategy(MoveStrategyEnum type) {
        return moduleItemMoveStrategyMap.get(type);
    }

    @Autowired
    private void init(ModuleContentMoveStrategy[] strategies) {
        for (ModuleContentMoveStrategy strategy : strategies) {
            moduleContentMoveStrategyMap.put(strategy.support(), strategy);
        }
    }

    public ModuleContentMoveStrategy getModuleContentMoveStrategy(MoveStrategyEnum type) {
        return moduleContentMoveStrategyMap.get(type);
    }

    @Autowired
    private void init(AssignmentGroupItemMoveStrategy[] strategies) {
        for (AssignmentGroupItemMoveStrategy strategy : strategies) {
            assignmentGroupItemMoveStrategyMap.put(strategy.support(), strategy);
        }
    }

    public AssignmentGroupItemMoveStrategy getAssignmentGroupItemMoveStrategy(MoveStrategyEnum type) {
        return assignmentGroupItemMoveStrategyMap.get(type);
    }

    @Autowired
    private void init(AssignmentGroupContentMoveStrategy[] strategies) {
        for (AssignmentGroupContentMoveStrategy strategy : strategies) {
            assignmentGroupContentMoveStrategyMap.put(strategy.support(), strategy);
        }
    }

    public AssignmentGroupContentMoveStrategy getAssignmentGroupContentMoveStrategy(MoveStrategyEnum type) {
        return assignmentGroupContentMoveStrategyMap.get(type);
    }

}
