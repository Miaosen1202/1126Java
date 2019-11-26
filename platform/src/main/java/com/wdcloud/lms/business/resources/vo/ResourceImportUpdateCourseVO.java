package com.wdcloud.lms.business.resources.vo;

import com.wdcloud.lms.core.base.vo.resource.ResourceBaseVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceCourseVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceVersionVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResourceImportUpdateCourseVO {

    private ResourceBaseVO resource;

    private ResourceVersionVO latestVersion;

    private List<ResourceCourseVO> importedCourses;

    private List<ResourceCourseVO> notImportedCourses;
}
