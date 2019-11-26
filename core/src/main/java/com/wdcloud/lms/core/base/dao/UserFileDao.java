package com.wdcloud.lms.core.base.dao;

import com.github.pagehelper.PageHelper;
import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserFileExtMapper;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.FileDetailVo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserFileDao extends CommonDao<UserFile, Long> {
    @Autowired
    private UserFileExtMapper userFileExtMapper;

    @Override
    protected Class<UserFile> getBeanClass() {
        return UserFile.class;
    }

    public void batchInsert(List<UserFile> userFiles) {
        if (ListUtils.isNotEmpty(userFiles)) {
            userFileExtMapper.batchInsert(userFiles);
        }
    }

    public String getLastTreeId(Long parentId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(UserFile.PARENT_ID, parentId);
        example.orderBy(UserFile.TREE_ID).desc();

        PageHelper.startPage(1, 1);
        UserFile userFile = findOne(example);
        return userFile == null ? null : userFile.getTreeId();
    }

    /**
     * 查询treeId为前缀的所有文件及目录
     * @param treeId
     * @return
     */
    public List<UserFile> findByTreeId(String treeId, Integer publishStatus) {
        Example example = getExample();
        Example.Criteria criteria = example.createCriteria()
                .andLike(UserFile.TREE_ID, treeId + "%");
        if (publishStatus != null) {
            criteria.andEqualTo(UserFile.STATUS, publishStatus);
        }
        return find(example);
    }

    /**
     * 删除文件、文件夹及其下级文件或文件夹
     * @param treeId
     * @return
     */
    public int deleteByTreeId(String treeId) {
        if (StringUtil.isEmpty(treeId)) {
            return 0;
        }
        Example example = getExample();
        example.createCriteria()
                .andLike(UserFile.TREE_ID, treeId + "%");
        return delete(example);
    }

    public void updateSubTreeId(String oldRootTreeId, String newRootTreeId, Long updateUserId) {
        if (StringUtil.isEmpty(oldRootTreeId) || StringUtil.isEmpty(newRootTreeId)) {
            return;
        }

        userFileExtMapper.updateSubTreeId(oldRootTreeId, newRootTreeId, updateUserId);
    }

    public void reviseSubParentId(String rootTreeId, Integer perLevelTreeIdLength) {
        if (StringUtil.isEmpty(rootTreeId) || perLevelTreeIdLength <= 0) {
            return;
        }
        userFileExtMapper.reviseSubParentId(rootTreeId, perLevelTreeIdLength);
    }

    public UserFile findByParentIdAndName(Long parentId, String name) {
        Example example = getExample();
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo(UserFile.PARENT_ID, parentId)
                .andEqualTo(UserFile.FILE_NAME, name);
        return find(example).get(0);
    }

    /**
     *  判断文件（夹）名是否存在
     * @param parentId
     * @param name
     * @param isDirectory
     * @return
     */
    public boolean isNameExists(Long parentId, String name, Integer isDirectory) {
        return count(UserFile.builder().isDirectory(isDirectory).parentId(parentId).fileName(name).build()) > 0;
    }

    /**
     * 判断同级目录下，除自己外是否存在重名的文件名或文件夹名
     * @param id 文件（夹）id
     * @param parentId 文件夹id
     * @param name 文件（夹）名
     * @param isDirectory 是否是文件夹
     * @return
     */
    public boolean isNameExists(Long id, Long parentId, String name, Integer isDirectory) {
        Example example = getExample();
        example.createCriteria()
                .andNotEqualTo(UserFile.ID, id)
                .andEqualTo(UserFile.PARENT_ID, parentId)
                .andEqualTo(UserFile.FILE_NAME, name)
                .andEqualTo(UserFile.IS_DIRECTORY, isDirectory);

        return count(example) > 0;
    }

    public List<FileDetailVo> findFileDetailByModuleItem(Long moduleItemId) {
        return userFileExtMapper.findFileDetailByModuleItem(moduleItemId);
    }

    public List<UserFile> getByFileUrlIn(List<String> fileUrls) {
        Example example = getExample();
        example.createCriteria()
                .andIn(UserFile.FILE_URL, fileUrls);
        return find(example);
    }

    public void batchDelete(List<Long> ids) {
        userFileExtMapper.batchDelete(ids);
    }
}