package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.vo.CourseUserJoinPendingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseUserJoinPendingExtMapper {
    /**
     * 功能：邀请显示查询
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return List<CourseUserJoinPendingVO>
     */
    List<CourseUserJoinPendingVO> getInvitation(@Param("userId") long userId,@Param("roleId") Long roleId);

}
