package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SysDictionaryExtMapper;
import com.wdcloud.lms.core.base.model.SysDictionary;
import com.wdcloud.lms.core.base.vo.SysDictionaryAndLocaleValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysDictionaryDao extends CommonDao<SysDictionary, Long> {
    @Autowired
    private SysDictionaryExtMapper sysDictionaryExtMapper;

    /**
     * 查询指定code的下级字典属性，返回默认字典名及指定区域配置名称
     * @param parentCode 目标code
     * @param locale 区域
     * @return 字典及指定区域配置名称
     */
    public List<SysDictionaryAndLocaleValueVo> findSubDictionaryAndLocaleValue(String parentCode, String locale) {
        return sysDictionaryExtMapper.findSubDictionaryAndLocaleValue(parentCode, locale);
    }

    @Override
    protected Class<SysDictionary> getBeanClass() {
        return SysDictionary.class;
    }
}