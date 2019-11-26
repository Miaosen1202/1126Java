package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.FileDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserFileExtMapper {
    /**
     * 查询课程文件数量
     * @param courseIds
     * @return
     */
    List<Map<String, Long>> findCourseFileNumber(List<Long> courseIds);

    /**
     * treeId前缀为oldRootTreeId时，更新前缀为newRootTreeId
     * @param oldRootTreeId 原始前缀
     * @param newRootTreeId 新的前缀
     * @param updateUserId 操作人员id
     */
    void updateSubTreeId(@Param("oldRootTreeId") String oldRootTreeId, @Param("newRootTreeId") String newRootTreeId, @Param("updateUserId") Long updateUserId);

    /**
     *  批量插入用户文件
     * @param userFiles 用户文件
     */
    void batchInsert(List<UserFile> userFiles);

    /**
     * 更新rootTreeId下级所有节点的parentId为正确的上级节点Id
     * @param rootTreeId
     * @param perLevelTreeIdLength
     */
    void reviseSubParentId(@Param("rootTreeId") String rootTreeId, @Param("perLevelTreeIdLength") int perLevelTreeIdLength);

    List<FileDetailVo> findFileDetailByModuleItem(Long moduleItemId);

    void batchDelete(List<Long> ids);
}
