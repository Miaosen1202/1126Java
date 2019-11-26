package com.wdcloud.lms.business.resources.dto;


import com.wdcloud.lms.core.base.vo.QuizItemVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class QuizResourceShareDTO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 测验类型, 1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)
     */
    private Integer type;

    /**
     * 是否重组答案
     */
    private Integer isShuffleAnswer;

    /**
     * 时间限制，单位分钟
     */
    private Integer timeLimit;

    /**
     * 允许多次尝试
     */
    private Integer allowMultiAttempt;

    /**
     * 尝试次数
     */
    private Integer attemptNumber;

    /**
     * 显示学生的回答是否正确策略，0: 不显示 1：每次提交答案后 2：最后一次提交后
     */
    private Integer showReplyStrategy;

    /**
     * 显示正确答案策略，0: 不显示 1：每次提交后 2：最后一次提交后
     */
    private Integer showAnswerStrategy;

    /**
     * 是否需要访问码访问
     */
    private Integer isNeedAccessCode;

    /**
     * 访问码
     */
    private String accessCode;

    /**
     * 题
     */
    private List<QuizItemVO> quizItemVOs;

    /**
     * 多次尝试时的计分规则，1：最高得分，2：最近一次得分，3：平均分
     */
    private Integer scoreType;
}
