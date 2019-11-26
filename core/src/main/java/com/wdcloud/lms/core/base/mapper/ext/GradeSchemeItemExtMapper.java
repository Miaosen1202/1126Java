package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.GradeSchemeItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangff
 * @date 2019/9/5 16:27
 */
public interface GradeSchemeItemExtMapper {

    void batchInsert(@Param("schemeItems")List<GradeSchemeItem> schemeItems);
}
