package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.AdminUserDetailVO;
import com.wdcloud.lms.core.base.vo.AdminUserVo;
import com.wdcloud.lms.core.base.vo.UserSettingVo;
import com.wdcloud.lms.core.base.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserExtMapper {

    List<User> findUsers(@Param("usernames") List<String> usernames,
                         @Param("emails") List<String> emails,
                         @Param("sisIds") List<String> sisIds, @Param("orgTreeId") String orgTreeId);

    UserVo findUserById(Long userId);

    /*个人信息设置*/
    //int userSetting(User user);

    UserSettingVo getUserSetting(Long userId);

    List<User> findUsersByIdAndUsername(@Param("ids") List<Long> userIds, @Param("username") String username);

    List<User> findOrgUsers(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);

    List<User> findUsersByCondition(Map<String, Object> map);

    List<AdminUserDetailVO> getUserCourseAndRoles(Long userId);

    List<User> getUserBySectionTable(@Param("courseId") Long courseId, @Param("roleId") Long roleId);

    List<User> getOrgAdmin(@Param("orgId") Long orgId);

    List<AdminUserVo> getOrgAdminIncludeChild(String orgTreeId);

    List<User> findUserListByRole(@Param("courseId") Long courseId, @Param("roleId") Long roleId);
}
