package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportCourse;

import java.util.List;

public interface SisImportCourseExtMapper {
    /**
     * 批量保存或更新导入的课程
     * @param importCourses 需导入的课程
     */
    void batchSaveOrUpdate(List<SisImportCourse> importCourses);
}
