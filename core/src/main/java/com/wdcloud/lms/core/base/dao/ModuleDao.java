package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ModuleExtMapper;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.vo.ModuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Repository
public class ModuleDao extends CommonDao<Module, Long> {

    @Autowired
    private ModuleExtMapper moduleExtMapper;

    public ModuleExtMapper ext() {
        return moduleExtMapper;
    }

    public List<Module> getModuleByOriginTypeAndOriginId(Long originId, Integer originType) {
        return moduleExtMapper.getModuleByOriginTypeAndOriginId(Map.of("originId", originId, "originType", originType));
    }


    public List<Module> getModuleOrderBySeq(Long courseId, Boolean status) {
        final Example example = getExample();
        example.orderBy("seq");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseId", courseId);
        if (status != null) {
            criteria.andEqualTo("status", status ? 1 : 0);
        }
        return find(example);
    }

    public List<Module> getModuleOrderBySeq(Long courseId) {
        return getModuleOrderBySeq(courseId, null);
    }

    public List<ModuleVO> getModuleCompleteStatusByModuleAndUser(Map<String, Object> params){
        return moduleExtMapper.getModuleCompleteStatusByModuleAndUser(params);
    }
    
    @Override
    protected Class<Module> getBeanClass() {
        return Module.class;
    }
}