package com.wdcloud.lms.base.service;


import com.wdcloud.lms.base.dto.AssignmentGroupItemDTO;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@SuppressWarnings({"JavadocReference", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@Service
public class AssignmentGroupItemService {
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;

    /**
     * 保存 作业/讨论/测验 等类型与作业组的映射关系
     *
     * @param dto 关联信息
     * @return itemId
     */
    public Long save(AssignmentGroupItemDTO dto) {
        Integer seq = assignmentGroupItemDao.ext().getMaxSeq(dto.getAssignmentGroupId());
        AssignmentGroupItem assignmentGroupItem = AssignmentGroupItem.builder()
                .assignmentGroupId(dto.getAssignmentGroupId())
                .originId(dto.getOriginId())
                .originType(dto.getOriginType().getType())
                .title(dto.getTitle())
                .score(dto.getScore())
                .status(dto.getStatus() == null ? 0 : dto.getStatus())
                .seq(Objects.isNull(seq) ? 1 : seq + 1)
                .build();
        assignmentGroupItemDao.save(assignmentGroupItem);
        return assignmentGroupItem.getId();
    }

    /**
     * 删除item
     *
     * @param id id
     * @return 条数
     */
    public int delete(Long id) {
        return assignmentGroupItemDao.delete(id);
    }
}
