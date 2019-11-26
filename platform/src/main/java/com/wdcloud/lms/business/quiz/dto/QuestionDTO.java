package com.wdcloud.lms.business.quiz.dto;

import com.wdcloud.lms.core.base.model.Question;
import com.wdcloud.lms.core.base.model.QuestionMatchOption;
import com.wdcloud.lms.core.base.model.QuestionOption;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**功能：测验问题dto
 *  * @author 黄建林
 */
@Data
public class QuestionDTO extends Question {
    @Null(groups = GroupAdd.class)
    @NotNull(groups = GroupModify.class)
     private Long id;

    /**
     * 测验ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
     private Long quizId;
    /**
     * 课程ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long courseId;

    /**
     * 问题组ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long groupId;

    /**
     * 问题类型，1：单选， 2:多选， 3：判断题、4、多项下拉题、5、匹配题、6、简答题、7:文件上传题、8:文本题
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer type;

    /**
     * 标题
     */
    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    @Length(max = 512,groups = {GroupAdd.class,GroupModify.class})
    private String title;

    /**
     * 是否为问题模板
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer isTemplate;

    /**
     * 为问题模板时表示题库ID
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long questionBankId;

    /**
     * 问题来源模板
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Long questionTemplateId;

    /**
     * 分值
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer score;

    /**
     * 排序
     */
    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer seq;


    /**
     * 内容
     */
    private String content;

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
    private List<QuestionOption> options;
    /**
     * 匹配题
     */
    private List<QuestionMatchOption> matchoptions;
}
