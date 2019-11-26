package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.ModuleUser;

import java.util.List;
/**
 * 功能：实现单元分配用户批量添加、更新、删除功能
 *
 * @author 黄建林
 */
public interface ModuleUserExtMapper {
    /**
     * 功能：单元分配用户批量添加
     * @param moduleUserList  需要添加的单元分配用户列表
     * @return
     */
    int batchInsert(List<ModuleUser> moduleUserList);
    /**
     * 功能：单元分配用户批量更新
     * @param moduleUserList  需要更新的单元分配用户列表
     * @return
     */
    int batchUpdate(List<ModuleUser> moduleUserList);
    /**
     * 功能：单元分配用户批量删除
     * @param moduleUserList  需要删除的分配用户列表
     * @return
     */
    int batchDelete(List<ModuleUser> moduleUserList);


}
