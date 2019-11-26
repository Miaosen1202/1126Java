package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**功能：测验dto
 *  * @author 黄建林
 */

@Data
public class QuizDTO  extends Quiz {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 课程ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long courseId;

    /**
     * 标题
     */
    @NotBlank(groups = {GroupAdd.class, GroupModify.class})
    @Length(max = 500,groups = {GroupAdd.class, GroupModify.class})
    private String title;

    /**
     * 测验类型, 1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer type;

    /**
     * 作业小组ID（type=2,3）
     */
    private Long assignmentGroupId;

    /**
     * 计分值(type=3)
     */
    private Integer score;

    /**
     * 允许匿名提交(type=3,4)
     */
     private Integer allowAnonymous;

    /**
     * 是否重组答案
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
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
     * 多次尝试时的计分规则，1：最高得分，2：最近一次得分，3：平均分
     */
    private Integer scoreType;

    /**
     * 显示学生的回答是否正确策略，0: 不显示 1：每次提交答案后 2：最后一次提交后
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer showReplyStrategy;

    /**
     * 显示正确答案策略，0: 不显示 1：每次提交后 2：最后一次提交后
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer showAnswerStrategy;

    /**
     * 显示正确答案开始时间
     */
    private Date showAnswerStartTime;

    /**
     * 显示正确答案结束时间
     */
    private Date showAnswerEndTime;

    /**
     * 显示问题策略, 0: 全部显示, 1: 每页一个
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer showQuestionStrategy;

    /**
     * 回答后锁定问题
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer isLockRepliedQuestion;

    /**
     * 是否需要访问码访问
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer isNeedAccessCode;

    /**
     * 访问码
     */
    @Length(max = 64,groups = {GroupAdd.class, GroupModify.class})
    private String accessCode;

    /**
     * 是否过滤访问IP
     */
     private Integer isFilterIp;

    /**
     * 过滤IP地址
     */
    private String filterIpAddress;
    /**
     * 版本号（预留）,每次更新后版本号增加
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer version;

    /**
     * 发布状态
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer status;

    /**
     * 问题总数
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer totalQuestions;

    /**
     * 总分
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer totalScore;

    /**
     * 描述
     */
    private String description;
    /**
     * 测验分配到班级的记录
     */

    private List<Assign> assign;

}