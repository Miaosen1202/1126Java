package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.studygroup.vo.StudyGroupSetCopyVo;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.lms.core.base.model.StudyGroupUser;
import com.wdcloud.lms.exceptions.ResourceNotFoundException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_STUDY_GROUP_SET,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class StudyGroupSetCopyEdit implements ISelfDefinedEdit {
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private UserFileService userFileService;

    /**
     * @api {post} /studyGroupSet/copy/edit 学习小组集克隆
     * @apiName studyGroupSetCopy
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} id 小组集ID
     * @apiParam {String} name 新小组集名称
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新小组集ID
     */
    @ValidationParam(clazz = StudyGroupSetCopyVo.class)
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        StudyGroupSetCopyVo groupSetCopyVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupSetCopyVo.class);
        StudyGroupSet copySource = studyGroupSetDao.get(groupSetCopyVo.getId());
        if (copySource == null) {
            throw new ResourceNotFoundException();
        }

        if (studyGroupSetDao.isStudyGroupSetNameExists(copySource.getCourseId(), groupSetCopyVo.getName())) {
            throw new BaseException("study-group-set.name.exists", groupSetCopyVo.getName());
        }

        StudyGroupSet targetGroupSet = BeanUtil.beanCopyProperties(copySource, StudyGroupSet.class,
                Joiner.on(",").join(StudyGroupSet.ID, StudyGroupSet.NAME, StudyGroupSet.SIS_ID, StudyGroupSet.IS_STUDENT_GROUP));
        targetGroupSet.setName(groupSetCopyVo.getName());
        studyGroupSetDao.save(targetGroupSet);

        // 复制小组及小组用户
        List<StudyGroup> copyGroups = studyGroupDao.find(StudyGroup.builder().studyGroupSetId(copySource.getId()).build());
        List<StudyGroup> targetGroups = BeanUtil.beanCopyPropertiesForList(copyGroups, StudyGroup.class);
        for (StudyGroup targetGroup : targetGroups) {
            Long oldId = targetGroup.getId();

            targetGroup.setId(null);
            targetGroup.setStudyGroupSetId(targetGroupSet.getId());
            // SIS ID 不能复制
            targetGroup.setSisId("");
            targetGroup.setIsStudentGroup(Status.NO.getStatus());
            studyGroupDao.save(targetGroup);

            userFileService.initStudyGroupDir(targetGroup.getCourseId(), targetGroup.getId(), targetGroup.getName());


            List<StudyGroupUser> groupUsers = studyGroupUserDao.find(StudyGroupUser.builder().studyGroupId(oldId).build());
            List<StudyGroupUser> targetGroupUsers = BeanUtil.beanCopyPropertiesForList(groupUsers, StudyGroupUser.class);
            for (StudyGroupUser targetGroupUser : targetGroupUsers) {
                targetGroupUser.setId(null);
                targetGroupUser.setStudyGroupSetId(targetGroupSet.getId());
                targetGroupUser.setStudyGroupId(targetGroup.getId());
                studyGroupUserDao.save(targetGroupUser);
            }
        }

        return new LinkedInfo(String.valueOf(targetGroupSet.getId()));
    }
}
