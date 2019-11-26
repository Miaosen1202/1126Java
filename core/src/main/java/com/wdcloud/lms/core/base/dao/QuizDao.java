package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.QuizExtMapper;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.vo.CalendarQuizVo;
import com.wdcloud.lms.core.base.vo.QuizObjectiveDataListVo;
import com.wdcloud.lms.core.base.vo.QuizSubjectivityDataListVo;
import com.wdcloud.lms.core.base.vo.QuizVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuizDao extends CommonDao<Quiz, Long> {

    @Autowired
    private QuizExtMapper quizExtMapper;

    public List<QuizVO> getQuizWithinItemsCount(Map<String, Object> param) {
        List<QuizVO> quizWithinItemsCount = quizExtMapper.getQuizWithinItemsCount(param);
        return quizWithinItemsCount;
    }

    public List<QuizVO> search(Map<String, Object> param) {
        return quizExtMapper.search(param);
    }

    public int questionsAndScoresUpdate(Quiz dto) {
        return quizExtMapper.questionsAndScoresUpdate(dto);
    }

    public int publisStatusUpdate(Quiz dto) {
        return quizExtMapper.publisStatusUpdate(dto);
    }


    /**
     * 获取答题分钟
     * @param testerId 提交人ID
     * @param quizId 测验ID
     * @return Integer
     */
    public Integer getMinutes(Long testerId, Long quizId) {
        return quizExtMapper.getMinutes(testerId, quizId);
    }

    /**
     * 获取任务提交信息
     * @param originId 测验ID
     * @param userId 用户ID
     * @param submitTime 提交时间
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getQuizRecordInfo(Long originId, Long userId, Date submitTime) {
        return quizExtMapper.getQuizRecordInfo(originId, userId, submitTime);
    }

    /**
     * 获取提交后简答题的信息
     * @param type 类型
     * @param quizRecordId 测验记录ID
     * @return List<QuizSubjectivityDataListVo>
     */
    public List<QuizSubjectivityDataListVo> getQuizSubjectivityList(Integer type, Long quizRecordId) {
        return quizExtMapper.getQuizSubjectivityList(type, quizRecordId);
    }



    /**
     * 获取数据list
     * @param quizRecordId
     * @return List<QuizObjectiveDataListVo>
     */
    public List<QuizObjectiveDataListVo> getObjectiveDataList(Long quizRecordId){
        return quizExtMapper.getObjectiveDataList(quizRecordId);
    }


    public QuizVO getById(Long id) {
        return quizExtMapper.getById(id);
    }

    public QuizVO getStudentById(long id,long userId) {
        return quizExtMapper.getStudentById(id,userId);
    }

    @Override
    protected Class<Quiz> getBeanClass() {
        return Quiz.class;
    }

    public List<QuizVO> studentSearch(Map<String, Object> param) {
        return quizExtMapper.studentSearch(param);
    }

    public List<CalendarQuizVo> findCalendarQuizList(Map<String, Object> map) {
        return quizExtMapper.findCalendarQuizList(map);
    }

    public List<CalendarQuizVo> findCalendarQuizListStudent(Map<String, Object> map) {
        return quizExtMapper.findCalendarQuizListStudent(map);
    }
}