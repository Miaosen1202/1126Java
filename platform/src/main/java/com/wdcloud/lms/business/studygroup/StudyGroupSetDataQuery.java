package com.wdcloud.lms.business.studygroup;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.StudyGroupSetDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_STUDY_GROUP_SET)
public class StudyGroupSetDataQuery implements IDataQueryComponent<StudyGroupSet> {
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;
    @Autowired
    private StudyGroupDao studyGroupDao;

    /**
     * @api {get} /studyGroupSet/list 学习小组集列表
     * @apiName studyGroupSetList
     * @apiGroup StudyGroup
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} [name] 名称
     * @apiParam {Number=0,1} [includeStudentGroupSet] 是否返回学生小组集，默认不返回
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 学习小组集列表
     * @apiSuccess {Number} [entity.id] ID
     * @apiSuccess {Number} [entity.courseId] 课程ID
     * @apiSuccess {String} [entity.name] 名称
     * @apiSuccess {Number=0,1} [entity.allowSelfSignup] 是否允许学生自行加入
     * @apiSuccess {Number=0,1} [entity.isSectionGroup] 是否是班级小组集（小组学生需要在相同班级）
     * @apiSuccess {Number=0,1} [entity.isStudentGroup] 是否是学生小组集（学生可以创建小组）
     * @apiSuccess {Number=1,2,3} [entity.leaderSetStrategy] 组长设置策略，1: 手动指定； 2: 第一个加入学生； 3: 随机设置
     * @apiSuccess {Number=0,1} [entity.groupMemberNumber] 组成员数量限制
     * @apiSuccess {Number=0,1} [entity.groupNumber] 小组数量
     * @apiSuccess {Number} [entity.createTime] 创建时间
     * @apiSuccess {Number} [entity.updateTime] 更新时间
     */
    @Override
    public List<? extends StudyGroupSet> list(Map<String, String> param) {
        if (!param.containsKey(Constants.PARAM_COURSE_ID)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(param.get(Constants.PARAM_COURSE_ID));

        boolean includeStudentGroupSet = false;
        if (param.containsKey(Constants.PARAM_INCLUDE_STUDENT_GROUP_SET)) {
            includeStudentGroupSet = Objects.equals(Integer.parseInt(param.get(Constants.PARAM_INCLUDE_STUDENT_GROUP_SET)),
                    Status.YES.getStatus());
        }

        StudyGroupSet.StudyGroupSetBuilder studyGroupSetBuilder = StudyGroupSet.builder().courseId(courseId);
        if (!includeStudentGroupSet) {
            studyGroupSetBuilder.isStudentGroup(Status.NO.getStatus());
        }

        List<StudyGroupSet> studyGroupSets = studyGroupSetDao.find(studyGroupSetBuilder.build());


        studyGroupSets.sort((a, b) -> -Integer.compare(a.getIsStudentGroup(), b.getIsStudentGroup()));

        Map<Long, List<StudyGroup>> studyGroupMap = studyGroupDao.find(StudyGroup.builder().courseId(courseId).build())
                .stream()
                .collect(Collectors.groupingBy(StudyGroup::getStudyGroupSetId));

        List<StudyGroupSetVo> studyGroupSetVos = BeanUtil.beanCopyPropertiesForList(studyGroupSets, StudyGroupSetVo.class);
        for (StudyGroupSetVo studyGroupSetVo : studyGroupSetVos) {
            if (studyGroupMap.containsKey(studyGroupSetVo.getId())) {
                studyGroupSetVo.setGroupNumber(studyGroupMap.get(studyGroupSetVo.getId()).size());
            }
        }

        return studyGroupSetVos;
    }

    @Data
    public static class StudyGroupSetVo extends StudyGroupSet {
        private int groupNumber;
    }
}
