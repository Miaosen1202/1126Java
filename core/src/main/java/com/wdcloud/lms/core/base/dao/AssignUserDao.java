package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.AssignUserExtMapper;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class AssignUserDao extends CommonDao<AssignUser, Long> {
    @Autowired
    private AssignUserExtMapper assignUserExtMapper;

    @Override
    protected Class<AssignUser> getBeanClass() {
        return AssignUser.class;
    }

    public void batchInsert(List<AssignUser> assignUserList) {
        if (ListUtils.isNotEmpty(assignUserList)) {
            assignUserExtMapper.batchInsert(assignUserList);
        }
    }

    public void batchUpdate(List<AssignUser> assignUserList) {
        if (ListUtils.isNotEmpty(assignUserList)) {
            assignUserExtMapper.batchUpdate(assignUserList);
        }
    }

    public void batchDelete(List<AssignUser> assignUserList) {
        if (ListUtils.isNotEmpty(assignUserList)) {
            assignUserExtMapper.batchDelete(assignUserList);
        }
    }

    public List<AssignUser> find(int originType, long originId) {
        return find(AssignUser.builder().originType(originType).originId(originId).build());
    }

    //依据用户Id、类型、原始id查询相应信息
    public List<AssignUser> getByUseIdAndTypeAndOriginId(long userId, int originType, long originId) {
        return find(AssignUser.builder().userId(userId).originType(originType).originId(originId).build());
    }

    public void deleteByUserIdAndCourseId(Long courseId, Long userId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo("courseId", courseId)
                .andEqualTo("userId", userId);
        delete(example);
    }

    /**
     * 查询学生在当前课程下分配的所有任务
     * @param userId
     * @param courseId
     * @return
     */
    public List<Map<String, Object>> userIdByGrade(Long userId,Long courseId){
       return assignUserExtMapper.userIdByGrade(userId,courseId);
    }
}