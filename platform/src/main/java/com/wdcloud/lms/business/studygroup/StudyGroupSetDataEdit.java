package com.wdcloud.lms.business.studygroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.studygroup.vo.StudyGroupSetEditVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.lms.exceptions.ResourceNotFoundException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_STUDY_GROUP_SET)
public class StudyGroupSetDataEdit implements IDataEditComponent {
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private StudyGroupDao studyGroupDao;

    /**
     * @api {post} /studyGroupSet/add 学习小组集添加
     * @apiName studyGroupSetAdd
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} name 小组集名称
     * @apiParam {Number=0,1} [allowSelfSignup] 允许学生自行注册
     * @apiParam {Number=0,1} [isSectionGroup] 限制小组成员在相同班级
     * @apiParam {Number=1,2,3} [leaderSetStrategy] 组长设置策略： 1: 手动指定； 2: 第一个加入学生； 3: 随机设置
     * @apiParam {Number} [groupMemberNumber] 小组成员数量
     * @apiParam {Number} [createNGroup] 立即创建小组数量
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 小组集ID
     */
    @ValidationParam(clazz = StudyGroupSetEditVo.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        StudyGroupSetEditVo studyGroupSetEditVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupSetEditVo.class);
        Course course = courseDao.get(studyGroupSetEditVo.getCourseId());
        if (course == null) {
            throw new ParamErrorException();
        }

        if (studyGroupSetDao.isStudyGroupSetNameExists(course.getId(), studyGroupSetEditVo.getName())) {
            throw new BaseException("study-group-set.name.exists", studyGroupSetEditVo.getName());
        }

        if (!roleService.hasTeacherOrTutorRole()) {
            throw new PermissionException();
        }
        studyGroupSetEditVo.setIsStudentGroup(Status.NO.getStatus());
        studyGroupSetDao.save(studyGroupSetEditVo);
        return new LinkedInfo(String.valueOf(studyGroupSetEditVo.getId()), JSON.toJSONString(studyGroupSetEditVo));
    }

    /**
     * @api {post} /studyGroupSet/modify 学习小组集编辑
     * @apiName studyGroupSetModify
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} id 小组集ID
     * @apiParam {String} name 小组集名称
     * @apiParam {Number=0,1} [allowSelfSignup] 允许学生自行注册
     * @apiParam {Number=0,1} [isSectionGroup] 限制小组成员在相同班级
     * @apiParam {Number=1,2,3} [leaderSetStrategy] 组长设置策略： 1: 手动指定； 2: 第一个加入学生； 3: 随机设置
     * @apiParam {Number} [groupMemberNumber] 小组成员数量
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 小组集ID
     */
    @ValidationParam(clazz = StudyGroupSetEditVo.class, groups = GroupModify.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        StudyGroupSetEditVo groupSetEditVo = JSON.parseObject(dataEditInfo.beanJson, StudyGroupSetEditVo.class);
        StudyGroupSet oldStudyGroupSet = studyGroupSetDao.get(groupSetEditVo.getId());
        if (oldStudyGroupSet == null) {
            throw new ResourceNotFoundException();
        }

        groupSetEditVo.setCourseId(oldStudyGroupSet.getCourseId());
        if (!Objects.equals(oldStudyGroupSet.getName(), groupSetEditVo.getName())) {
            if (studyGroupSetDao.isStudyGroupSetNameExists(groupSetEditVo.getCourseId(), groupSetEditVo.getName())) {
                throw new BaseException("study-group-set.name.exists", groupSetEditVo.getName());
            }
        }

        if (!Objects.equals(oldStudyGroupSet.getGroupMemberNumber(), groupSetEditVo.getGroupMemberNumber())) {
            int maxGroupUserNumber = studyGroupUserDao.getMaxGroupUserNumber(groupSetEditVo.getId());
            if (groupSetEditVo.getGroupMemberNumber() != null && groupSetEditVo.getGroupMemberNumber() < maxGroupUserNumber) {
                throw new BaseException("group-member-limit.not.less.than", String.valueOf(maxGroupUserNumber));
            }

            Integer groupMemberNumber = groupSetEditVo.getGroupMemberNumber();
            if(groupMemberNumber == null){
                groupMemberNumber = Integer.MAX_VALUE;
            }

            studyGroupDao.updateMaxMemberNumber(oldStudyGroupSet.getId(), groupMemberNumber);
        }
        groupSetEditVo.setIsStudentGroup(Status.NO.getStatus());
        studyGroupSetDao.update(groupSetEditVo);
        return new LinkedInfo(String.valueOf(groupSetEditVo.getId()));
    }

    /**
     * @api {post} /studyGroupSet/deletes 学习小组集删除
     * @apiName studyGroupSetDelete
     * @apiGroup StudyGroup
     *
     * @apiParam {Number[]} ids 组集ID列表
     * @apiParamExample 请求示例:
     *  [1,2,3]
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity ID列表
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        studyGroupSetDao.deletes(ids);

        Example example = studyGroupDao.getExample();
        example.createCriteria()
                .andIn(Constants.PARAM_STUDY_GROUP_SET_ID, ids);
        studyGroupDao.delete(example);

        studyGroupUserDao.clearUserByGroupSets(ids);
        return new LinkedInfo(dataEditInfo.beanJson);
    }
}
