package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ModuleItemExtMapper;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.lms.core.base.vo.ModuleItemCompleteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class ModuleItemDao extends CommonDao<ModuleItem, Long> {

    @Autowired
    private ModuleItemExtMapper moduleItemExtMapper;

    public ModuleItemExtMapper ext() {
        return moduleItemExtMapper;
    }

    public int updateSeq(long id, long moduleId, int seq) {
        ModuleItem moduleItem = new ModuleItem();
        moduleItem.setId(id);
        moduleItem.setSeq(seq);
        moduleItem.setModuleId(moduleId);
        return update(moduleItem);
    }

    public int updateBatchSeqAndModuleId(List<ModuleItem> moduleItems) {
        return moduleItemExtMapper.updateBatchSeqAndModuleId(moduleItems);
    }

    public List<ModuleItem> getModuleItemOrderBySeq(Long moduleId, Boolean status) {
        final Example example = getExample();
        example.orderBy("seq");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("moduleId", moduleId);
        if (status != null) {
            criteria.andEqualTo("status", status ? 1 : 0);
        }
        return find(example);
    }

    public List<ModuleItem> getModuleItemOrderBySeq(Long moduleId) {
        return getModuleItemOrderBySeq(moduleId, null);
    }

    public List<ModuleItem> getModuleItemByCourse(Long courseId) {
        return moduleItemExtMapper.getModuleItemByCourse(courseId);
    }

    public List<ModuleItemCompleteVO> getModuleItemCompleteStatusByModuleAndUser(Map<String, Object> params){
        return moduleItemExtMapper.getModuleItemCompleteStatusByModuleAndUser(params);
    }

    @Override
    protected Class<ModuleItem> getBeanClass() {
        return ModuleItem.class;
    }
}