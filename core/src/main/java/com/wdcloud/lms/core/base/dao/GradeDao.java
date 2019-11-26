package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.mapper.ext.GradeCommentExtMapper;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Grade;
import com.wdcloud.lms.core.base.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GradeDao extends CommonDao<Grade, Long> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GradeCommentExtMapper gradeCommentExtMapper;
    @Override
    public Class<Grade> getBeanClass() {
        return Grade.class;
    }

    public GradeCommentExtMapper ext() {
        return gradeCommentExtMapper;
    }

    /**
     * 查询在当前课程下需要评分的任务并且已经过了发布时间
     * @param originType,type
     * @return
     */
    public List<GradeTaskVO> gradeTestQuery(String originType, Long courseId) {
        List<GradeTaskVO> gradeTaskVOList=gradeCommentExtMapper.gradeTestQuery(originType,courseId);
        return gradeTaskVOList.stream().filter(vo->vo.getShowScoreType()!=5).collect(Collectors.toList());
    }

    /**
     * 根据班级id和课程id查询所属班级下的用户的信息，任务得分情况
     *
     * @param sectionId，courseId
     */
    public List<Map<String, Object>> userQuery(Long sectionId, Long courseId) {
        return gradeCommentExtMapper.userQuery(sectionId, courseId);
    }

    /**
     * 查询用户下所有的任务
     */
    public List<GradeTestListQueryVo> gradeTestListQuery(Long sectionId, Long courseId, Status status) {
        List<GradeTestListQueryVo> listQueryVoList= gradeCommentExtMapper.gradeTestListQuery(sectionId, courseId, status == null ? null : status.getStatus());
        //权重开关处理
        Course course=courseDao.get(courseId);
        if (course.getIsWeightGrade()==null||course.getIsWeightGrade()==0) {
            listQueryVoList.forEach(vo->vo.setWeight(BigDecimal.valueOf(100)));
        }
        return listQueryVoList.stream().filter(vo->vo.getShowScoreType()!=5).collect(Collectors.toList());
    }

    /**
     * 查询已评分的人数
     * @param originId 任务ID
     * @param originType 任务类型
     * @param courseId 课程ID
     * @return Integer
     */
    public Integer getGradeList(Long originId, Integer originType, Long courseId) {
        return gradeCommentExtMapper.getGradeList(originId, originType, courseId);
    }

    /**
     * 获取评分后的数据
     * @param originType 任务类型
     * @param originId 任务ID
     * @param userId 用户ID
     * @param studyGroupId 小组ID
     * @param courseId 课程ID
     * @return CosGradeVo
     */
    public CosGradeVo getCosGradeInfo(Integer originType,
                                      Long originId,
                                      Long userId,
                                      Long studyGroupId,
                                      Long courseId) {
        return gradeCommentExtMapper.getCosGradeInfo(originType, originId, userId, studyGroupId, courseId);
    }

    /**
     * 班级下用户的得分情况比例
     *
     * @param courseId
     * @param sectionId
     * @return
     */
    public List<Map<String, Object>> sectionGrade(Long courseId, Long sectionId) {
        return gradeCommentExtMapper.sectionGrade(courseId, sectionId);
    }

    /**
     * 任务分配给每个用户，用户的提交及得分情况
     *
     * @param originId
     * @param originType
     * @return
     */
    public Map<String, Object> submitGradeQuery(Long originId, Integer originType, Long userId, Long courseId) {
        return gradeCommentExtMapper.submitGradeQuery(originId, originType, userId, courseId);
    }

    /**
     * 用户每个任务的得分情况比例
     * @param courseId
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findUserGrade(Long courseId, Long userId) {
        return gradeCommentExtMapper.findUserGrade(courseId, userId);
    }

    /**
     *  批量查询任务平均分
     * @param originIdList
     * @param originType
     * @return
     */
    public List<TaskAvgVO> taskAvgBatch(List<Long> originIdList, Integer originType) {
        return gradeCommentExtMapper.taskAvgBatch(originIdList, originType);
    }
}