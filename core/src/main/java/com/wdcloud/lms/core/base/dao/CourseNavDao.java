package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.CourseNavExtMapper;
import com.wdcloud.lms.core.base.model.CourseNav;
import com.wdcloud.lms.core.base.vo.CourseNavVO;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class CourseNavDao extends CommonDao<CourseNav, Long> {

    @Autowired
    private CourseNavExtMapper courseNavExtMapper;

    public List<CourseNavVO> getNavs(Map<String,Object> param){
        return courseNavExtMapper.getNavs(param);
    }

    public void batchInsert(List<CourseNav> courseNavs) {
        if (ListUtils.isNotEmpty(courseNavs)) {
            courseNavExtMapper.batchInsert(courseNavs);
        }
    }

    public void batchInsertOrUpdate(List<CourseNav> courseNavs) {
        if (ListUtils.isNotEmpty(courseNavs)) {
            courseNavExtMapper.batchInsertOrUpdate(courseNavs);
        }
    }

    @Override
    protected Class<CourseNav> getBeanClass() {
        return CourseNav.class;
    }
}