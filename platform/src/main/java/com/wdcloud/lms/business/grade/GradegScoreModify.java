package com.wdcloud.lms.business.grade;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.grade.vo.ScoreModifyVo;
import com.wdcloud.lms.business.quiz.question.QuizService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.HistoryGrade;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.model.QuizQuestionRecord;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhangxutao
 * 测验——修改评分
 **/
@ResourceInfo(name = Constants.GRADE_SCORE_MODIFY)
public class GradegScoreModify implements IDataEditComponent {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizQuestionRecordDao quizQuestionRecordDao;
    @Autowired
    private HistoryGradeDao historyGradeDao;
    @Autowired
    private QuizService quizService;
    /**
     * @api {post} /scoreModify/modify 修改测验评分
     * @apiDescription 修改测验评分
     * @apiName scoreModify
     * @apiGroup GradeGroup
     * @apiParam {Long} originId　来源ID
     * @apiParam {Long} userId　评论用户
     * @apiParam {Long} studyGroupId 学习小组
     * @apiParam {String} score 分值
     * @apiParam {String} topicScore 题号等分  “112_3,113_4”
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 评分
     * @apiSuccess {Long} entity.id 评分ID
     */

    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        Integer gradeScore = 0;
        ScoreModifyVo gradeEditVo = JSON.parseObject(dataEditInfo.beanJson, ScoreModifyVo.class);
        if (StringUtil.isNotEmpty(gradeEditVo.getScore())) {
            gradeEditVo.setScore(gradeEditVo.getScore().split("\\.")[0]);
        }
        int total=0;
        /**
         * 更新测验记录表中的分值 -测验用
         */
        if (gradeEditVo.getTopicScore() != null && gradeEditVo.getTopicScore() != "") {
            String[] topicScoreList = gradeEditVo.getTopicScore().split(",");
            if (topicScoreList != null && topicScoreList.length > 0) {
                for (String str : topicScoreList) {
                    String[] item = str.split("_");
                    if (item != null && item.length > 0) {
                        Long topicId = Long.parseLong(item[0]);
                        Integer score = Integer.parseInt(item[1]);
                        QuizQuestionRecord quizQuestionRecord = quizQuestionRecordDao.get(topicId);
                        if (quizQuestionRecord != null) {
                            quizQuestionRecord.setGradeScore(score);
                            quizQuestionRecordDao.update(quizQuestionRecord);
                        }
                    }
                }
            }
            /**
             * 修改评分历史数据表中的分值-测验用
             */
            List<HistoryGrade> historyGrades = historyGradeDao.find(HistoryGrade.builder()
                    .originId(gradeEditVo.getRecordOriginId())
                    .originType(OriginTypeEnum.QUIZ.getType())
                    .userId(gradeEditVo.getUserId())
                    .build());

            if (ListUtils.isNotEmpty(historyGrades)) {
                for (HistoryGrade hisItem : historyGrades) {
                    HistoryGrade historyGrade = historyGradeDao.get(hisItem.getId());
                    //通过 recordOriginId 获取历史得分总和
                    total=quizQuestionRecordDao.getTotalRecordScores(gradeEditVo.getRecordOriginId());
                    historyGrade.setGradeScore(total);
                    historyGradeDao.update(historyGrade);
                }
            }
        }
        //分数相关统计处理并把统计结果写入分数统计表
        Quiz quiz = quizDao.get(gradeEditVo.getOriginId());
        gradeScore = quizService.quizRecordStatic(quiz, gradeEditVo.getUserId(), 0);
        return new LinkedInfo(String.valueOf(total), dataEditInfo.beanJson);
    }


}
