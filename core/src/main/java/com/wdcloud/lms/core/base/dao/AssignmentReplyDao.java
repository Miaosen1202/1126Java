package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.AssignmentReplyExtMapper;
import com.wdcloud.lms.core.base.model.AssignmentReply;
import com.wdcloud.lms.core.base.model.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class AssignmentReplyDao extends CommonDao<AssignmentReply, Long> {

    @Autowired
    private AssignmentReplyExtMapper assignmentReplyExtMapper;

    /**
     * 获取学生提交的附件、媒体数据
     * @param assignmentId
     * @param assignmentReplyId
     * @return List<UserFile>
     */
    public List<UserFile> assignmentReplyFile(Long assignmentId, Long assignmentReplyId) {
        return assignmentReplyExtMapper
                .assignmentReplyFile(Map.of("assignmentId", assignmentId, "assignmentReplyId", assignmentReplyId));
    }

    /**
     * 获取学生作业回复信息
     * @param assignmentId 作业任务ID
     * @param userId 用户ID
     * @param studyGroupId 小组ID
     * @param submitTime 提交时间
     * @return List<Map<String,Object>> sql中有固定数值
     */
    public List<Map<String,Object>> getAssignmentReply(Long assignmentId, Long userId,
                                              Long studyGroupId, Date submitTime){
        return assignmentReplyExtMapper.getAssignmentReply(assignmentId,userId
                                                            ,studyGroupId,submitTime);
    }


    @Override
    public Class<AssignmentReply> getBeanClass() {
        return AssignmentReply.class;
    }
}