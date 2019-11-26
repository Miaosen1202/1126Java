package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractAssignmentStrategy;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.utils.BeanUtil;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class AssignmentQuery extends AbstractAssignmentStrategy implements QueryStrategy {
    @SuppressWarnings("Duplicates")
    @Override
    public OriginData query(Long id) {
        Assignment assignment = assignmentDao.get(id);
        if (assignment != null) {
            return OriginData.builder()
                    .status(assignment.getStatus())
                    .originId(assignment.getId())
                    .score(assignment.getScore())
                    .originType(support().getType())
                    .title(assignment.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    public Object queryDetail(Long moduleItemId) {
        ModuleItem moduleItem = moduleItemDao.get(moduleItemId);
        return moduleItem;
    }

    @Override
    public String queryResourceShareInfo(Long id, Long resourceId, String resourceName) {
        Assignment assignment = assignmentDao.get(id);
        AssignmentResourceShareDTO dto = BeanUtil.copyProperties(assignment, AssignmentResourceShareDTO.class);
        dto.setTitle(resourceName);
        return JSON.toJSONString(dto);
    }

    @Override
    public AssignmentResourceShareDTO convertResourceObject(String beanJson) {
        return JSON.parseObject(beanJson, AssignmentResourceShareDTO.class);
    }
}
