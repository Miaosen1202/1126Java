package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.CourseNav;
import com.wdcloud.lms.core.base.vo.CourseNavVO;

import java.util.List;
import java.util.Map;

public interface CourseNavExtMapper {
    /**
     *
     * @param param
     * @return
     */
    List<CourseNavVO> getNavs(Map<String, Object> param);

    /**
     *
     * @param courseNavs
     */
    void batchInsert(List<CourseNav> courseNavs);

    /**
     *
     * @param courseNavs
     */
    void batchInsertOrUpdate(List<CourseNav> courseNavs);
}