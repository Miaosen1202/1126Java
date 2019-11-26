

package com.wdcloud.lms.business.grade.vo;

import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.DiscussionReplyVO;
import lombok.Data;
import com.wdcloud.lms.core.base.model.UserFile;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务内容信息
 * @author zhangxutao
 */
@Data
public class AssignmentReplyVo {
    /**
     * //内容{包括文本内容、URL内容}
     */
    private String content;
    /**
     * //1: 文本　2: 文件 3: URL 4: 媒体',
     */
    private Integer replyType;
    /**
     * //任务类型
     */
    private Integer originType;
    /**
     * //附件
     */
    private List<UserFile> attachments = new ArrayList<>();
    /**
     * //讨论
     */
    private List<DiscussionReplyVO> discussionReplyVOList = new ArrayList<>();
    /**
     * //讨论标题
     */
    private Discussion discussion = new Discussion();
    /**
     * //回复人信息
     */
    private User repUser = new User();
    /**
     * //讨论标题用户信息
     */
    private User user = new User();
    /**
     * //测验
     */
    private QuizQuestionVo quizQuestionVo = new QuizQuestionVo();
    /**
     * // '总分',
     */
    private Integer score;
    /**
     * //'得分',
     */
    private Integer gradeScore;
    /**
     * //评分规则:  1:最高分 2：最近提交  3：平均分
     */
    private Integer gradeStrategy;


}
