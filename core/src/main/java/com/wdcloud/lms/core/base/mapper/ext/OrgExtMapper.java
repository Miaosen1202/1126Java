package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.vo.OrgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrgExtMapper {
    List<OrgVO> getOrgWithSubCountAndCoursesCount(Map<String,Object> map);

    Org getUserOrg(@Param("userId") Long userId);

    List<Org> getUserAdminOrg(@Param("userId") Long userId);

    String getMaxTreeIdByParentId(Long parentId);

    List<Org> getByUserIdAndRoleId(Map<String, Object> map);
}
