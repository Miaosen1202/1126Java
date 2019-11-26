package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.model.AssignmentReply;
import org.apache.ibatis.annotations.Param;


import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AssignmentReplyExtMapper {

    /**
     *
     * 获取学生提交的附件、媒体数据
     * @param assignmentId 作业任务ID
     * @param assignmentReplyId 作业任务回复ID
     * @return List<UserFile>
     */
    List<UserFile> assignmentReplyFile(Map<String, Object> param);

    /**
     * 获取学生作业回复信息
     * @param assignmentId 作业任务ID
     * @param userId 用户ID
     * @param studyGroupId 小组ID
     * @param submitTime 提交时间
     * @return List<Map<String,Object>> sql中有固定数值
     */
    List<Map<String,Object>> getAssignmentReply(@Param("assignmentId") Long assignmentId,
                                       @Param("userId") Long userId,
                                       @Param("studyGroupId") Long studyGroupId,
                                       @Param("submitTime") Date submitTime);
}
