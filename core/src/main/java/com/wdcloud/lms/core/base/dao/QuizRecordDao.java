package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.mapper.ext.QuizRecordExtMapper;
import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.lms.core.base.vo.QuizRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
@Repository
public class QuizRecordDao extends CommonDao<QuizRecord, Long> {

    @Autowired
    private QuizRecordExtMapper quizRecordExtMapper;

    @Override
    protected Class<QuizRecord> getBeanClass() {
        return QuizRecord.class;
    }

    /**
     * 功能：做题次数统计
     * @param quizId
     * @param createUserId
     * @return
     */
    public int getTotalRecord(long quizId, long createUserId) {
        return quizRecordExtMapper.getTotalRecord(quizId, createUserId);
    }

    /**
     * 功能：依据测试ID及用户信息查询答题记录
     * @param quizRecord
     * @return
     */
    public List<QuizRecordVO> getQuizRecords(QuizRecord quizRecord) {
        return quizRecordExtMapper.getQuizRecords(quizRecord);
    }

    public List<Map<String,Object>> getQuizQuestionRecordList(Long quizId, Long userId){
        return quizRecordExtMapper.getQuizQuestionRecordList(quizId,userId);
    }

    /**
     * 功能：依据测验id查询该测验的所有答题记录
     * @param quizId
     * @return
     */
    public List<QuizRecord> getAllQuizRecord(Long quizId) {
        Example example = getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(QuizRecord.QUIZ_ID, quizId);
        criteria.andEqualTo(QuizRecord.TESTER_ROLE_TYPE, (RoleEnum.STUDENT.getType()).intValue());
       return find(example);
    }

}