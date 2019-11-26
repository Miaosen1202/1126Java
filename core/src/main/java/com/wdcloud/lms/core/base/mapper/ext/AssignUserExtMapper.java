package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.AssignUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 功能：实现用户分配批量添加、更新、删除功能
 *
 * @author 黄建林
 */
public interface AssignUserExtMapper {
    /**
     * 功能：用户分配批量添加
     * @param assignUserList  需要添加的分配用户列表
     * @return
     */
    int batchInsert(List<AssignUser> assignUserList);
    /**
     * 功能：用户分配批量更新
     * @param assignUserList  需要更新的分配用户列表
     * @return
     */
    int batchUpdate(List<AssignUser> assignUserList);
    /**
     * 功能：用户分配批量删除
     * @param assignUserList  需要删除的分配用户列表
     * @return
     */
    int batchDelete(List<AssignUser> assignUserList);

    List<Map<String, Object>> userIdByGrade(@Param("userId") Long userId,@Param("courseId") Long courseId);
}
