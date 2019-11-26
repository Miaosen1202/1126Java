package com.wdcloud.lms.business.grade.vo;

import com.wdcloud.lms.core.base.vo.QuizObjectiveDataListVo;
import com.wdcloud.lms.core.base.vo.QuizSubjectivityDataListVo;
import lombok.Data;

import java.util.List;

/**
 * @author  zhangxutao
 */
@Data
public class QuizQuestionVo {
    private List<QuizObjectiveDataListVo> quizObjectiveListVo;
    private List<QuizSubjectivityDataListVo> quizSubjectivityListVo;
    private Integer score;
    private Integer gradeScore;
    private Long userId;
    private Long graderId;

}
