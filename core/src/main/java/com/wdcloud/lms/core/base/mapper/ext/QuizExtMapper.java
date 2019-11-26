package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.vo.CalendarQuizVo;
import com.wdcloud.lms.core.base.vo.QuizObjectiveDataListVo;
import com.wdcloud.lms.core.base.vo.QuizSubjectivityDataListVo;
import com.wdcloud.lms.core.base.vo.QuizVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface QuizExtMapper {
    List<QuizVO> getQuizWithinItemsCount(Map<String, Object> param);


    /**
     * 功能：依据课程ID和创建用户ID查询测验信息
     * @param courseId
     * @param createUserId
     * @return
     */
    List<Quiz> getByCourseIdAndCreateUserId(long courseId, long createUserId);

    List<QuizVO> search(Map<String, Object> param);



    /**
     * 功能：更新问题总数和总分
     * @param dto
     * @return
     */
    int questionsAndScoresUpdate(Quiz dto);

    /**
     * 功能：学生端查询（只要状态是发布的并且有查询权限的就能查询）
     * @param param
     * @return
     */
    List<QuizVO> studentSearch(Map<String, Object> param);

    /**
     * 功能：测验发布状态更新
     * @param dto
     * @return
     */
    int publisStatusUpdate(Quiz dto);

    /**
     * 功能：教师依据测验id获取测验信息
     *
     * @param id 测验id
     * @return
     */
    QuizVO getById(@Param("id") long id);
    /**
     * 功能：学生依据测验id获取测验信息
     *
     * @param id 测验id
     * @return
     */
    QuizVO getStudentById(@Param("id") long id,@Param("userId") Long userId);

    List<CalendarQuizVo> findCalendarQuizList(Map<String, Object> map);

    List<CalendarQuizVo> findCalendarQuizListStudent(Map<String, Object> map);

    /**
     * 获取答题分钟
     * @param testerId 提交人ID
     * @param quizId 测验ID
     * @return Integer
     */
    Integer getMinutes(@Param("testerId") Long testerId,@Param("quizId")  Long quizId);

    /**
     * 获取任务提交信息
     * @param originId 测验ID
     * @param userId 用户ID
     * @param submitTime 提交时间
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getQuizRecordInfo(@Param("originId") Long originId,
                                                @Param("userId") Long userId,
                                                @Param("submitTime") Date submitTime);

    /**
     * 获取提交后简答题的信息
     * @param type 类型
     * @param quizRecordId 测验记录ID
     * @return List<QuizSubjectivityDataListVo>
     */
    List<QuizSubjectivityDataListVo> getQuizSubjectivityList(@Param("type") Integer type,
                                                             @Param("quizRecordId") Long quizRecordId);


    /**
     * 获取数据list
     * @param quizRecordId
     * @return List<QuizObjectiveDataListVo>
     */
    List<QuizObjectiveDataListVo> getObjectiveDataList(Long quizRecordId);

}
