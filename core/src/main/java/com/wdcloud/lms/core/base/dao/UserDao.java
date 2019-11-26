package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.UserExtMapper;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.AdminUserDetailVO;
import com.wdcloud.lms.core.base.vo.AdminUserVo;
import com.wdcloud.lms.core.base.vo.UserSettingVo;
import com.wdcloud.lms.core.base.vo.UserVo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends CommonDao<User, Long> {
    @Autowired
    private UserExtMapper userExtMapper;

    @Override
    protected Class<User> getBeanClass() {
        return User.class;
    }

    public List<User> findUsers(List<String> usernames, List<String> emails, List<String> sisIds, String orgTreeId) {
        return userExtMapper.findUsers(usernames, emails, sisIds, orgTreeId);
    }


    public List<User> findUsersByIdAndUsername(List<Long> userIds, String likeUsername) {
        if (ListUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }

        return userExtMapper.findUsersByIdAndUsername(userIds, likeUsername);
    }
    public List<User> findUsersByCondition(Map<String,Object> map) {
        return userExtMapper.findUsersByCondition(map);
    }

    public UserVo findUserById(Long userId) {
        return userExtMapper.findUserById(userId);
    }

    public UserSettingVo getUserSetting(Long userId) {
        return userExtMapper.getUserSetting(userId);
    }

    public User findByUsername(String username) {
        return findOne(User.builder().username(username).build());
    }

    public List<User> findBySisIds(Collection<String> sisIds, String rootTreeId) {
        if (sisIds == null || sisIds.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = getExample();
        example.createCriteria()
                .andIn(User.SIS_ID, sisIds)
                .andLike(User.ORG_TREE_ID, rootTreeId + "%");
        return find(example);
    }

    public List<AdminUserDetailVO> getUserCourseAndRoles(Long uid) {
        return userExtMapper.getUserCourseAndRoles(uid);
    }

    public boolean existsUsername(String username) {
        return count(User.builder().username(username).build()) > 0;
    }

    public List<User> getUserBySectionTable(Long courseId, Long roleId) {
        return userExtMapper.getUserBySectionTable(courseId, roleId);
    }

    public List<AdminUserVo> getOrgAdminIncludeChild(String orgTreeId) {
        return userExtMapper.getOrgAdminIncludeChild(orgTreeId);
    }

    public void updateAvatar(UserFile avatarFile, Long userId) {
        mapper.updateByPrimaryKeySelective(User.builder().id(userId).avatarUserFileId(avatarFile.getId()).avatarFileId(avatarFile.getFileUrl()).build());
    }

    public List<User> findUserListByRole(Long courseId, Long roleId) {
        return userExtMapper.findUserListByRole(courseId, roleId);
    }
}