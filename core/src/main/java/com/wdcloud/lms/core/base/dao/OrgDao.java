package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.OrgExtMapper;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.vo.OrgVO;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class OrgDao extends CommonDao<Org, Long> {

    @Autowired
    private OrgExtMapper orgExtMapper;

    public List<OrgVO> getOrgWithSubCountAndCoursesCount(Map<String, Object> map) {
        return orgExtMapper.getOrgWithSubCountAndCoursesCount(map);
    }

    public Org getByTreeId(String treeId) {
        if (StringUtil.isEmpty(treeId)) {
            return null;
        }
        return findOne(Org.builder().treeId(treeId).build());
    }

    public String getMaxTreeIdByParentId(Long parentId) {
        return orgExtMapper.getMaxTreeIdByParentId(parentId);
    }


    /**
     * 查找用户所在机构
     *
     * @param userId
     * @return
     */
    public Org findUserOrg(Long userId) {
        return orgExtMapper.getUserOrg(userId);
    }


    /**
     * 查找用户角色为管理员的机构
     *
     * @param userId
     * @return
     */
    public List<Org> findUserAdminOrg(Long userId) {
        return orgExtMapper.getUserAdminOrg(userId);
    }

    public List<Org> findBySisIds(Collection<String> sisIds, String treeId) {
        if (sisIds == null || sisIds.isEmpty()) {
            return null;
        }

        Example example = getExample();
        example.createCriteria()
                .andIn(Org.SIS_ID, sisIds)
                .andLike(Org.TREE_ID, treeId + "%");
        return find(example);
    }

    public List<Org> findChildIncludeSelf(@Nullable String treeId) {
        Example example = getExample();
        example.orderBy(Org.TREE_ID);
        example.createCriteria()
                .andLike(Org.TREE_ID, treeId + "%");
        return find(example);
    }

    @Override
    protected Class<Org> getBeanClass() {
        return Org.class;
    }

    public List<Org> getByUserIdAndRoleId(Map<String, Object> map) {
        return orgExtMapper.getByUserIdAndRoleId(map);
    }
}