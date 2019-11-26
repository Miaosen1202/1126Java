package com.wdcloud.lms.business.quiz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.vo.QuizVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：实现测验学生查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ, description = "测验表")
public class QuizStudentQuery implements IDataQueryComponent<Quiz> {
    @Autowired
    private QuizDao quizDao;
//    private  List<QuizDTO> getQuizDTO(List<QuizVo> quizList)
//    {
//        List<QuizDTO>  quizDTOList =new ArrayList<>();
//        int len=quizList.size();
//        QuizDTO quizDTOInfor=null;
//        List<Assign> assign=null;
//        Long lastid=0L;
//        for(int i=0;i<len;i++)
//        {
//           if(quizList.get(i).getId()!=lastid)
//           {
//               lastid= quizList.get(i).getId();
//               quizDTOInfor=new QuizDTO();
//               assign=new ArrayList<>();
//               quizDTOInfor.setId(quizList.get(i).getId());
//               quizDTOInfor.setCourseId(quizList.get(i).getCourseId());
//               quizDTOInfor.setTitle(quizList.get(i).getTitle());
//               quizDTOInfor.setType(quizList.get(i).getType());
//               quizDTOInfor.setAssignmentGroupId(quizList.get(i).getAssignmentGroupId());
//               quizDTOInfor.setScore(quizList.get(i).getScore());
//               quizDTOInfor.setAllowAnonymous(quizList.get(i).getAllowAnonymous());
//               quizDTOInfor.setIsShuffleAnswer(quizList.get(i).getIsShuffleAnswer());
//               quizDTOInfor.setTimeLimit(quizList.get(i).getTimeLimit());
//               quizDTOInfor.setAllowMultiAttempt(quizList.get(i).getAllowMultiAttempt());
//               quizDTOInfor.setAttemptNumber(quizList.get(i).getAttemptNumber());
//               quizDTOInfor.setScoreType(quizList.get(i).getScoreType());
//               quizDTOInfor.setShowReplyStrategy(quizList.get(i).getShowReplyStrategy());
//               quizDTOInfor.setShowAnswerStrategy(quizList.get(i).getShowAnswerStrategy());
//               quizDTOInfor.setShowAnswerStartTime(quizList.get(i).getShowAnswerStartTime());
//               quizDTOInfor.setShowAnswerEndTime(quizList.get(i).getShowAnswerEndTime());
//               quizDTOInfor.setShowQuestionStrategy(quizList.get(i).getShowQuestionStrategy());
//               quizDTOInfor.setIsLockRepliedQuestion(quizList.get(i).getIsLockRepliedQuestion());
//               quizDTOInfor.setIsNeedAccessCode(quizList.get(i).getIsNeedAccessCode());
//               quizDTOInfor.setAccessCode(quizList.get(i).getAccessCode());
//               quizDTOInfor.setIsFilterIp(quizList.get(i).getIsFilterIp());
//               quizDTOInfor.setFilterIpAddress(quizList.get(i).getFilterIpAddress());
//               quizDTOInfor.setVersion(quizList.get(i).getVersion());
//               quizDTOInfor.setStatus(quizList.get(i).getStatus());
//               quizDTOInfor.setTotalQuestions(quizList.get(i).getTotalQuestions());
//               quizDTOInfor.setTotalScore(quizList.get(i).getTotalScore());
//
//           }
//
//        }
//    }


    @Override
    public List<? extends Quiz> list(Map<String, String> param) {
//        Quiz quiz=new Quiz();
//        quiz.setId(Long.valueOf(param.get("id")));
        return null;
    }

    /**
     * @api {get} /quiz/pageList  测验列表分页学生端查询
     * @apiName quizPageList
     * @apiGroup Quiz
     * @apiParam {String} [title] 标题（可选填）
     * @apiParam {String} [description] 描述（可选填）
     * @apiParam {Number} courseId 课程ID（必填）
     * @apiParam {Number} [pageIndex] 页码
     * @apiParam {Number} [pageSize] 页大小
     * @apiExample 请求示例:
     * http://localhost:8080//quiz/pageList?courseId=1
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * {
     * "code": 200,
     * "entity": {
     * "list": [
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [],
     * "courseId": 1,
     * "createTime": 1552593460000,
     * "createUserId": 1,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 622,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "Unnamed quiz",
     * "totalQuestions": 0,
     * "totalScore": 23,
     * "type": 1,
     * "updateTime": 1552593460000,
     * "updateUserId": 1,
     * "version": 20
     * },
     * {
     * "accessCode": "",
     * "allowAnonymous": 0,
     * "allowMultiAttempt": 0,
     * "assigns": [],
     * "courseId": 1,
     * "createTime": 1552590878000,
     * "createUserId": 1,
     * "description": "",
     * "filterIpAddress": "",
     * "id": 595,
     * "isFilterIp": 0,
     * "isLockRepliedQuestion": 0,
     * "isNeedAccessCode": 0,
     * "isShuffleAnswer": 0,
     * "showAnswerStrategy": 0,
     * "showQuestionStrategy": 0,
     * "showReplyStrategy": 0,
     * "status": 1,
     * "title": "1231322",
     * "totalQuestions": 0,
     * "totalScore": 23,
     * "type": 1,
     * "updateTime": 1552590878000,
     * "updateUserId": 1,
     * "version": 20
     * }
     * ],
     * "pageIndex": 1,
     * "pageSize": 20,
     * "total": 2
     * },
     * "message": "成功"
     * }
     */
    @Override
    public PageQueryResult<? extends QuizVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> dataParam = Maps.newHashMap();
        final String title = param.get("title");
        if (StringUtil.isNotEmpty(title)) {
            dataParam.put("title", title);
        }
        final String description = param.get("description");
        if (StringUtil.isNotEmpty(description)) {
            dataParam.put("description", description);
        }
        final String courseId = param.get("courseId");
        if (StringUtil.isNotEmpty(courseId)) {
            dataParam.put("courseId", courseId);
        }
        dataParam.put("originType", OriginTypeEnum.QUIZ.getType());
        dataParam.put("userId", WebContext.getUserId());

        PageHelper.startPage(pageIndex, pageSize);
        Page<QuizVO> resources = (Page<QuizVO>) quizDao.studentSearch(dataParam);
        return new PageQueryResult<>(resources.getTotal(), resources.getResult(), pageSize, pageIndex);


    }

    /**
     * @param id
     * @return
     * @api {get} /quiz/find 题库查找
     * @apiDescription 题库查找
     * @apiName quizFind
     * @apiGroup Quiz
     * @apiParam {String} id 题库ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1]"
     * }
     */
    @Override
    public Quiz find(String id) {

        return null;
    }
}
