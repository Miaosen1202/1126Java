package com.wdcloud.lms.business.quiz;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.dao.QuizRecordDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Grade;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：测验答题记录分数相关查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_SCORE_RECORD, description = "测验记录分数信息")
public class QuizScoreRecordQuery implements IDataQueryComponent<Grade> {
    @Autowired
    private QuizRecordDao quizRecordDao;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private GradeDao gradeDao;


    @Override
    public List<? extends Grade> list(Map<String, String> param) {
        return null;

    }

    @Override
    public PageQueryResult<? extends Grade> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @api {get} /quizScoreRecord/get 测验答题记录分数展示列表
     * @apiDescription 依据测验id查询当前登录用户的测验分数信息
     * @apiName quizScoreRecord
     * @apiGroup Quiz
     * @apiParam {String} id 测验ID
     * @apiExample {json} 请求示例:
     * http://localhost:8080/quizScoreRecord/get?data=2
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * {
     * "code": 200,
     * "entity": {
     * "attemps": 2,
     * "attempsAvailable": 6,
     * "currentScore": 8,
     * "isGradedAll": false,
     * "keptScore": 8,
     * "quizId": 1106,
     * "testerId": 1,
     * "timeLimit": 8
     * },
     * "message": "成功"
     * }
     */
    @Override
    public Grade find(String id) {
        Long quizId = Long.valueOf(id);
        Quiz quiz = quizDao.get(quizId);
        if (quiz == null) {
            throw new BaseException("prop.value.not-exists","quizId",quizId+"");
        }
        List<Grade> grdlist = gradeDao.find(Grade.builder()
                .originId(quiz.getId())
                .originType(OriginTypeEnum.QUIZ.getType())
                .userId(WebContext.getUserId())
                .build());
        Grade grade = null;
        if (grdlist.size() > 0) {
            grade = grdlist.get(0);
            if(grade.getAttemps()==null)
            {
                grade.setAttemps(0);
            }
        } else {
            grade = new Grade();
            grade.setAttemps(0);
            grade.setCurrentScore(0);

        }
        return grade;

    }
}
