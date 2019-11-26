package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ModuleItemUser;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现单元任务分配用户批量添加、更新、删除功能
 *
 * @author 黄建林
 */
public interface ModuleItemUserExtMapper {
    /**
     * 功能：单元任务分配用户批量添加
     * @param moduleItemUserList  需要添加的单元任务分配用户列表
     * @return
     */
    int batchInsert(List<ModuleItemUser> moduleItemUserList);
    /**
     * 功能：单元任务分配用户批量更新
     * @param moduleItemUserList  需要更新的单元任务分配用户列表
     * @return
     */
    int batchUpdate(List<ModuleItemUser> moduleItemUserList);
    /**
     * 功能：单元任务分配用户批量删除
     * @param moduleItemUserList  需要删除的任务分配用户列表
     * @return
     */
    int batchDelete(List<ModuleItemUser> moduleItemUserList);

    int getModuleItemUserCountByModule(Map<String, Object> map);

    int getInCompleteModuleItemUserCountByModule(Map<String, Object> map);
}
