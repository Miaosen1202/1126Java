package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.QuizQuestionMatchOptionRecord;
import com.wdcloud.lms.core.base.model.QuizQuestionOptionRecord;
import com.wdcloud.lms.core.base.model.QuizQuestionRecord;
import com.wdcloud.lms.core.base.model.QuizQuestionReplyRecord;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**功能：测验问题记录dto
 *  * @author 黄建林
 */
@Data
public class QuizQuestionRecordDTO extends QuizQuestionRecord {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
    private Long id;

    /**
     * 测验记录ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long quizRecordId;

    /**
     * 问题ID
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Long questionId;

    /**
     * 问题组ID
     */
    private Long groupId;

    /**
     * 问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题
     */
    private Integer type;

    /**
     * 分值
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer score;

    /**
     * 排序
     */
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private Integer seq;

    /**
     * 得分
     */
     private Integer gradeScore;

    /**
     * 标题
     */
    private String title;

    /**
     * 正确提示
     */
    private String correctComment;

    /**
     * 错误提示
     */
    private String wrongComment;

    /**
     * 通用提示
     */
    private String generalComment;

    /**
     * 选择题选项
     */
    private List<QuizQuestionOptionRecord> options;
    /**
     * 匹配题
     */
    private List<QuizQuestionMatchOptionRecord> matchoptions;
    /**
     * 填空，简单题
     */
    private QuizQuestionReplyRecord reply;
}