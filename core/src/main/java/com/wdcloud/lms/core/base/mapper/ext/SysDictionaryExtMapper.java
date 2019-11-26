package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.SysDictionaryAndLocaleValueVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictionaryExtMapper {

    List<SysDictionaryAndLocaleValueVo> findSubDictionaryAndLocaleValue(@Param("parentCode") String parentCode, @Param("locale") String locale);
}
