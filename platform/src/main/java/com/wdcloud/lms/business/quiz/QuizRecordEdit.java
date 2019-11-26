package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.enums.UserParticipateOpEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.quiz.dto.QuizQuestionRecordDTO;
import com.wdcloud.lms.business.quiz.dto.QuizRecordDTO;
import com.wdcloud.lms.business.quiz.enums.QuestionTypeEnum;
import com.wdcloud.lms.business.quiz.enums.QuizTypeEnum;
import com.wdcloud.lms.business.quiz.enums.ShuffleAnswersEnum;
import com.wdcloud.lms.business.quiz.question.QuestionOperation;
import com.wdcloud.lms.business.quiz.question.QuizService;
import com.wdcloud.lms.business.quiz.vo.QuizRecordVo;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.lms.core.base.vo.QuizQuestionRecordVO;
import com.wdcloud.lms.core.base.vo.QuizRecordVO;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 功能：测验答题记录基本信息接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ_RECORD)
public class QuizRecordEdit implements IDataEditComponent {

    @Autowired
    AssignUserService assignUserService;
    @Autowired
    private QuizRecordDao quizRecordDao;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizItemDao quizItemDao;
    @Autowired
    private QuizQuestionRecordDao quizQuestionRecordDao;
    @Autowired
    private QuestionOperation questionOperation;
    @Autowired
    private UserParticipateDao userParticipateDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private HistoryGradeDao historyGradeDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private ModuleCompleteService moduleCompleteService;
    private Thread thread;
    private QuizRecordVo quizRecordVo;

    private void batchImportQuestion(long quizId, long quizRecordId, Integer isShuffleAnswer) {
        //学生开始答题前，先把问题导入到测验记录表中
        List<QuizItemVO> quizItemVOs = quizItemDao.getQuerstionAllInfors(quizId);
        if (quizItemVOs != null) {
            int totalquizItem = quizItemVOs.size();
            for (int i = 0; i < totalquizItem; i++) {
                QuizQuestionRecordDTO questionDto = new QuizQuestionRecordDTO();
                questionDto.setQuizRecordId(quizRecordId);
                questionDto.setQuestionId(quizItemVOs.get(i).getQuestion().getId());
                questionDto.setGroupId(quizItemVOs.get(i).getQuestion().getGroupId());
                questionDto.setType(quizItemVOs.get(i).getQuestion().getType());
                questionDto.setTitle(quizItemVOs.get(i).getQuestion().getTitle());
                questionDto.setContent(quizItemVOs.get(i).getQuestion().getContent());
                questionDto.setCorrectComment(quizItemVOs.get(i).getQuestion().getCorrectComment());
                questionDto.setWrongComment(quizItemVOs.get(i).getQuestion().getWrongComment());
                questionDto.setGeneralComment(quizItemVOs.get(i).getQuestion().getGeneralComment());
                questionDto.setScore(quizItemVOs.get(i).getQuestion().getScore());
                questionDto.setSeq(quizItemVOs.get(i).getSeq());
                questionDto.setGradeScore(0);
                quizQuestionRecordDao.save(questionDto);
                /**
                 * 组装选择题选项
                 */
                List<QuizQuestionOptionRecord> optionsRecord = new ArrayList<>();
                List<QuestionOption> options = quizItemVOs.get(i).getQuestion().getOptions();
                if (options != null) {
                    int totaloptions = options.size();
                    List<Integer> seqList = new ArrayList<>();
                    //重组答案
                    if (ShuffleAnswersEnum.IS_SHUFFLE_ANSWER.getType().equals(isShuffleAnswer)) {
                        for (int j = 0; j < totaloptions; j++) {
                            seqList.add(options.get(j).getSeq());
                        }
                        Collections.shuffle(seqList);
                    }
                    for (int j = 0; j < totaloptions; j++) {
                        QuizQuestionOptionRecord quizQuestionOptionRecord = new QuizQuestionOptionRecord();
                        quizQuestionOptionRecord.setQuizQuestionRecordId(questionDto.getId());
                        quizQuestionOptionRecord.setQuestionOptionId(options.get(j).getId());
                        quizQuestionOptionRecord.setCode(options.get(j).getCode());
                        quizQuestionOptionRecord.setContent(options.get(j).getContent());
                        quizQuestionOptionRecord.setExplanation(options.get(j).getExplanation());
                        quizQuestionOptionRecord.setIsCorrect(options.get(j).getIsCorrect());
                        //如果需要重组答案
                        if (ShuffleAnswersEnum.IS_SHUFFLE_ANSWER.getType().equals(isShuffleAnswer)) {
                            quizQuestionOptionRecord.setSeq(seqList.get(j));
                        } else {
                            quizQuestionOptionRecord.setSeq(options.get(j).getSeq());
                        }
                        //初始时都没选中
                        quizQuestionOptionRecord.setIsChoice(0);
                        optionsRecord.add(quizQuestionOptionRecord);

                    }
                    questionDto.setOptions(optionsRecord);
                }
                /**
                 * 组装匹配题
                 */
                List<QuestionMatchOption> matchoptions = quizItemVOs.get(i).getQuestion().getMatchoptions();
                List<QuizQuestionMatchOptionRecord> matchoptionsRecord = new ArrayList<>();
                if (matchoptions != null) {
                    int totalMatchOptions = matchoptions.size();
                    for (int j = 0; j < totalMatchOptions; j++) {
                        QuizQuestionMatchOptionRecord quizQuestionMatchOptionRecord = new QuizQuestionMatchOptionRecord();
                        quizQuestionMatchOptionRecord.setQuestionMatchOptionId(matchoptions.get(j).getId());
                        quizQuestionMatchOptionRecord.setContent(matchoptions.get(j).getContent());
                        quizQuestionMatchOptionRecord.setQuizQuestionRecordId(questionDto.getId());
                        //初始时没选择为0
                        quizQuestionMatchOptionRecord.setQuizQuestionOptionRecordId(0L);
                        matchoptionsRecord.add(quizQuestionMatchOptionRecord);
                    }
                    questionDto.setMatchoptions(matchoptionsRecord);
                }
                //如果问题类型为简答题
                if (quizItemVOs.get(i).getQuestion().getType().equals(QuestionTypeEnum.ESSAY_QUESTION.getType())) {
                    QuizQuestionReplyRecord quizQuestionReplyRecord = new QuizQuestionReplyRecord();
                    quizQuestionReplyRecord.setQuizQuestionRecordId(questionDto.getId());
                    quizQuestionReplyRecord.setReply("");
                    questionDto.setReply(quizQuestionReplyRecord);
                }
                /*保存问题附加项*/
                questionOperation.saveRecord(questionDto);


            }
        }
    }

    /**
     * 功能：删除测验记录
     *
     * @param id 测验记录Id
     */
    private void deleteQuizRecord(Long id) {
        QuizQuestionRecord quizQuestionRecord = new QuizQuestionRecord();
        quizQuestionRecord.setQuizRecordId(id);
        List<QuizQuestionRecordVO> quizQuestionRecordVOList = quizQuestionRecordDao.getQuerstionRecords(quizQuestionRecord);
        //删除所有的问题
        int totaQuizQuestionRecord = quizQuestionRecordVOList.size();
        //删除问题所有选项记录
        for (int i = 0; i < totaQuizQuestionRecord; i++) {
            questionOperation.deleteRecord(quizQuestionRecordVOList.get(i));
            //删除问题本身
            quizQuestionRecordDao.delete(quizQuestionRecordVOList.get(i).getId());
        }
        //删除评分记录表中的记录
        Example example = historyGradeDao.getExample();
        example.createCriteria()
                .andEqualTo(Grade.ORIGIN_ID, id)
                .andEqualTo(Grade.ORIGIN_TYPE, OriginTypeEnum.QUIZ.getType()
                );
        historyGradeDao.delete(example);
        quizRecordDao.delete(id);
    }

    /**
     * @api {post} /quizRecord/add 测验答题记录添加
     * @apiDescription 测验答题记录添加
     * @apiName quizRecordAdd
     * @apiGroup Quiz
     * @apiParam {Number} quizId  测验ID
     * @apiParam {Number} quizVersion  提交时与测验版本一致
     * @apiParam {Number} isSubmit   是否确认提交  1：是；0：否
     * @apiParam {Number} isLastTime 是否最后一次提交  1：是；0：否  (后端处理，前端每次传0）
     * @apiParam {datetime} startTime 开始时间
     * @apiParam {datetime} submitTime 提交时间
     * @apiParam {datetime} dueTime 截至时间
     * @apiParam {Number} testerId  测验人ID
     * @apiExample {json} 请求示例:
     * {
     * "quizId": "13",
     * "quizVersion": "1",
     * "isSubmit": "0",
     * "isLastTime": "0",
     * "startTime": "2019-03-01 16:44:57",
     * "submitTime": "2019-03-01 16:44:57",
     * "dueTime": "2019-03-01 16:44:57",
     * "testerId": "2"
     * <p>
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 新增问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "1"
     * }
     */
    @Override
    @ValidationParam(clazz = QuizRecordDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizRecordDTO.class);
        Quiz quiz = quizDao.get(dto.getQuizId());
        if (quiz == null) {
            throw new BaseException("prop.value.not-exists", "quizId", String.valueOf(dto.getQuizId()));
        }
        QuizRecord quizRecord = new QuizRecord();
        quizRecord.setQuizId(quiz.getId());
        quizRecord.setCreateUserId(WebContext.getUserId());
        List<QuizRecordVO> quizRecordVOList = quizRecordDao.getQuizRecords(quizRecord);
        int currentRecord = 0;
        if (quizRecordVOList != null) {
            currentRecord = quizRecordVOList.size();
        }
        //如果是教师答题，删除上次答题记录，只保留一次答题记录
        if (roleService.hasTeacherOrTutorRole()) {
            if (currentRecord > 0) {
                deleteQuizRecord(quizRecordVOList.get(0).getId());
            }
            dto.setTesterRoleType((RoleEnum.TEACHER.getType()).intValue());
        } else {
            dto.setTesterRoleType((RoleEnum.STUDENT.getType()).intValue());
        }
        //判断答题记录是否在最后一次答题时间范围内，如果在，
        // 并且创建时间和更新时间基本一致，则直接恢复答题
        if (currentRecord > 0) {
            Long dftime = quizRecordVOList.get(0).getCreateTime().getTime() - quizRecordVOList.get(0).getUpdateTime().getTime();
            if (dftime < 0) {
                dftime = -dftime;
            }

            if (dftime < 5) {
                if (new Date().before(quizRecordVOList.get(0).getDueTime()) && quizRecordVOList.get(0).getIsSubmit() == 0) {
                    return new LinkedInfo(String.valueOf(quizRecordVOList.get(0).getId()), dataEditInfo.beanJson);
                }
                if (quiz.getTimeLimit() == null && quizRecordVOList.get(0).getIsSubmit() == 0) {
                    return new LinkedInfo(String.valueOf(quizRecordVOList.get(0).getId()), dataEditInfo.beanJson);
                }

            }


        }
        //允许多次尝试
        if (quiz.getAllowMultiAttempt() == 1) {
            //允许多次尝试次数
            if (quiz.getAttemptNumber() != null) {
                if (roleService.hasStudentRole()) {
                    if (currentRecord + 1 > quiz.getAttemptNumber()) {
                        throw new BaseException("the.required.number.of.answers.has.been.reached!");
                    }
                }
                //达到允许次数
                if (currentRecord + 1 == quiz.getAttemptNumber()) {
                    dto.setIsLastTime(1);
                } else {
                    dto.setIsLastTime(0);
                }
            }

            //尝试次数为空时，默认最多允许尝试50次
            else if (currentRecord + 1 == 50) {
                dto.setIsLastTime(1);
            } else {
                dto.setIsLastTime(0);
            }
        }
        //不允许多次尝试，默认允许尝试1次
        else {
            if (roleService.hasStudentRole()) {
                if (currentRecord + 1 > 1) {
                    throw new BaseException("no.more.attempts.available");
                }
            }
            dto.setIsLastTime(1);
        }
        dto.setStartTime(new Date());
        dto.setTesterId(WebContext.getUserId());

        if (quiz.getTimeLimit() != null) {
            //如果时间为0表示没有时间限制，允许一天之内恢复答题
            if (quiz.getTimeLimit() == 0) {
                quiz.setTimeLimit(24 * 60);
            }
            dto.setDueTime(DateUtil.minuteOperation(new Date(), quiz.getTimeLimit()));
        }
        quizRecordDao.save(dto);
        //批量导入问题记录
        batchImportQuestion(dto.getQuizId(), dto.getId(), quiz.getIsShuffleAnswer());
         quizRecordVo = new QuizRecordVo();
        quizRecordVo.setQuizId(dto.getQuizId());
        quizRecordVo.setQuizRecordId(dto.getId());
        quizRecordVo.setUserId(WebContext.getUserId());
        quizRecordVo.setNeedAutoRun(true);
        if (roleService.hasStudentRole()) {
            quizRecordVo.setHasStudentRole(true);
        } else {
            quizRecordVo.setHasStudentRole(false);
        }

        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(quiz.getTimeLimit() * 60 * 1000);
                    if(quizRecordVo.isNeedAutoRun()) {
                        autoUpdate(quizRecordVo);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        //创建定时器
        thread = new Thread(runnable);
        //开始执行
        thread.start();

        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quizRecord/modify 测验答题记录修改
     * @apiDescription 测验答题记录修改(非必填参数 ， 如果不修改 ， 可以不传)
     * @apiName quizRecordModify
     * @apiGroup Quiz
     * @apiParam {Number} id 测验答题记录Id
     * @apiParam {Number} quizId  测验ID
     * @apiParam {Number} quizVersion  提交时测验版本
     * @apiParam {Number} isSubmit   是否确认提交  1：是；0：否
     * @apiParam {Number} isLastTime 是否最后一次提交  1：是；0：否
     * @apiParam {datetime} startTime 开始时间
     * @apiParam {datetime} submitTime 提交时间
     * @apiParam {datetime} dueTime 截至时间
     * @apiParam {Number} testerId  测验人ID
     * @apiExample {json} 请求示例:
     * {
     * "id": "1",
     * "quizId": "13",
     * "quizVersion": "1",
     * "isSubmit": "0",
     * "isLastTime": "0",
     * "startTime": "2019-03-01 16:44:57",
     * "submitTime": "2019-03-01 16:44:57",
     * "dueTime": "2019-03-01 16:44:57",
     * "testerId": "2"
     * <p>
     * }
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改问题选项ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "[49]"
     * }
     */
    @Override
    @ValidationParam(clazz = QuizRecordDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizRecordDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizRecordDTO.class);
        quizRecordVo.setNeedAutoRun(false);
        dto.setSubmitTime(new Date());
        dto.setTesterId(WebContext.getUserId());
        quizRecordDao.update(dto);
        Quiz quiz = quizDao.get(dto.getQuizId());
        if (quiz == null) {
            throw new BaseException("prop.value.not-exists", "quizId", dto.getQuizId() + "");
        }
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(quiz.getId(), OriginTypeEnum.QUIZ);
        int totalscore = quizQuestionRecordDao.getTotalRecordScores(dto.getId());
        HistoryGrade historygrade = new HistoryGrade();
        historygrade.setCourseId(quiz.getCourseId());
        historygrade.setOriginId(dto.getId());
        historygrade.setOriginType(OriginTypeEnum.QUIZ.getType());
        historygrade.setGradeScore(totalscore);
        historygrade.setScore(quiz.getScore());
        historygrade.setUserId(WebContext.getUserId());
        historygrade.setGraderId(WebContext.getUserId());
        if (assignmentGroupItem != null) {
            if (assignmentGroupItem.getId() == null) {
                historygrade.setAssignmentGroupItemId(0L);
            } else {
                historygrade.setAssignmentGroupItemId(assignmentGroupItem.getId());
            }
        } else {
            historygrade.setAssignmentGroupItemId(0L);
        }
        List<HistoryGrade> grdlist = historyGradeDao.find(HistoryGrade.builder()
                .originId(dto.getId())
                .originType(OriginTypeEnum.QUIZ.getType())
                .userId(WebContext.getUserId())
                .build());
        if (grdlist == null) {
            historyGradeDao.save(historygrade);
        } else {
            if (grdlist.size() == 0) {
                historyGradeDao.save(historygrade);
            } else {
                historygrade.setId(grdlist.get(0).getId());
                historyGradeDao.update(historygrade);
            }
        }
        //把分数统计结果放最终统计评分表
        quizService.quizRecordStatic(quiz, WebContext.getUserId(), 1);

        //向单元任务中写入记录，用于记录该学生该任务是否完成
        moduleCompleteService.completeAssignment(dto.getQuizId(), OriginTypeEnum.QUIZ.getType());

        //教师模拟答题不用向如下2个表中插入记录，只有学生需要
        if (roleService.hasStudentRole()) {
            //向学生用户活动表中提交记录
            userParticipateDao.save(UserParticipate.builder()
                    .courseId(quiz.getCourseId())
                    .originId(quiz.getId())
                    .originType(OriginTypeEnum.QUIZ.getType())
                    .operation(UserParticipateOpEnum.SUBMIT.getOp())
                    .targetName(quiz.getTitle())
                    .userId(WebContext.getUserId())
                    .build());
            //向user_submit_recor表中插入信息
            int totalSubjectiveQuestion = quizItemDao.getTotalSubjectiveQuestion(quiz.getId());
            List<UserSubmitRecord> userSubmitRecordList = userSubmitRecordDao.findByOriginIdAndOriginType(quiz.getId(), OriginTypeEnum.QUIZ.getType(), WebContext.getUserId());
            assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(quiz.getId(), OriginTypeEnum.QUIZ);
            int totalUserSubmitRecord = 0;
            if (userSubmitRecordList != null) {
                totalUserSubmitRecord = userSubmitRecordList.size();
            }
            UserSubmitRecord userSubmitRecord = new UserSubmitRecord();
            userSubmitRecord.setOriginId(quiz.getId());
            userSubmitRecord.setCourseId(quiz.getCourseId());
            userSubmitRecord.setOriginType(OriginTypeEnum.QUIZ.getType());
            if (assignmentGroupItem != null) {
                if (assignmentGroupItem.getId() == null) {
                    userSubmitRecord.setAssignmentGroupItemId(0L);
                } else {
                    userSubmitRecord.setAssignmentGroupItemId(assignmentGroupItem.getId());
                }
            } else {
                userSubmitRecord.setAssignmentGroupItemId(0L);
            }
            userSubmitRecord.setUserId(WebContext.getUserId());
            if (quiz.getType().equals(QuizTypeEnum.GRADED_QUIZ.getType()) ||
                    quiz.getType().equals(QuizTypeEnum.GRADED_SURVEY.getType()) ||
                    quiz.getType().equals(QuizTypeEnum.PRACTICE_QUIZ.getType())) {
                //需要评分
                userSubmitRecord.setNeedGrade(1);
            } else {
                //不需要评分
                userSubmitRecord.setNeedGrade(0);
            }
            userSubmitRecord.setLastSubmitTime(new Date());
            if (totalUserSubmitRecord > 0) {
                userSubmitRecord.setId(userSubmitRecordList.get(0).getId());
                //提交次数
                userSubmitRecord.setSubmitCount(userSubmitRecordList.get(0).getSubmitCount() + 1);
                userSubmitRecordDao.update(userSubmitRecord);
            } else {
                //是否逾期提交
                AssignStatusEnum status = assignUserService.getAssignUserStatus(WebContext.getUserId(), OriginTypeEnum.QUIZ.getType(), quiz.getId());
                if (status == AssignStatusEnum.EXCEEDEDDEADLINE) {
                    userSubmitRecord.setIsOverdue(1);
                }
                //提交次数
                userSubmitRecord.setSubmitCount(1);
                //为了评分模块区分是否需要教师评分用
                if (totalSubjectiveQuestion < 1) {
                    userSubmitRecord.setIsGraded(1);
                } else {
                    userSubmitRecord.setIsGraded(0);
                }
                userSubmitRecordDao.save(userSubmitRecord);
            }
        }

        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * 功能：定时自动提交答题记录
     *
     * @param quizRecordVo
     */
    public void autoUpdate(QuizRecordVo quizRecordVo) {
        QuizRecord quizRecord = new QuizRecord();
        quizRecord.setSubmitTime(new Date());
        quizRecord.setTesterId(quizRecordVo.getUserId());
        quizRecord.setId(quizRecordVo.getQuizRecordId());
        quizRecordDao.update(quizRecord);
        Quiz quiz = quizDao.get(quizRecordVo.getQuizId());
        if (quiz == null) {
            throw new BaseException("prop.value.not-exists", "quizId", quizRecordVo.getQuizId() + "");
        }
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(quiz.getId(), OriginTypeEnum.QUIZ);
        int totalscore = quizQuestionRecordDao.getTotalRecordScores(quizRecordVo.getQuizRecordId());
        HistoryGrade historygrade = new HistoryGrade();
        historygrade.setCourseId(quiz.getCourseId());
        historygrade.setOriginId(quizRecordVo.getQuizRecordId());
        historygrade.setOriginType(OriginTypeEnum.QUIZ.getType());
        historygrade.setGradeScore(totalscore);
        historygrade.setScore(quiz.getScore());
        historygrade.setUserId(quizRecordVo.getUserId());
        historygrade.setGraderId(quizRecordVo.getUserId());

        if (assignmentGroupItem != null) {
            if (assignmentGroupItem.getId() == null) {
                historygrade.setAssignmentGroupItemId(0L);
            } else {
                historygrade.setAssignmentGroupItemId(assignmentGroupItem.getId());
            }
        } else {
            historygrade.setAssignmentGroupItemId(0L);
        }
        List<HistoryGrade> grdlist = historyGradeDao.find(HistoryGrade.builder()
                .originId(quizRecordVo.getQuizRecordId())
                .originType(OriginTypeEnum.QUIZ.getType())
                .userId(quizRecordVo.getUserId())
                .build());
        if (grdlist == null) {
            historyGradeDao.save(historygrade);
        } else {
            if (grdlist.size() == 0) {
                historyGradeDao.save(historygrade);
            } else {
                historygrade.setId(grdlist.get(0).getId());
                historyGradeDao.update(historygrade);
            }
        }
        //把分数统计结果放最终统计评分表
        quizService.quizRecordStatic(quiz, quizRecordVo.getUserId(), 1);

        //向单元任务中写入记录，用于记录该学生该任务是否完成
        moduleCompleteService.completeAssignment(quizRecordVo.getQuizId(), OriginTypeEnum.QUIZ.getType());

        //教师模拟答题不用向如下2个表中插入记录，只有学生需要
        if (quizRecordVo.isHasStudentRole()) {
            //向学生用户活动表中提交记录
            userParticipateDao.save(UserParticipate.builder()
                    .courseId(quiz.getCourseId())
                    .originId(quiz.getId())
                    .originType(OriginTypeEnum.QUIZ.getType())
                    .operation(UserParticipateOpEnum.SUBMIT.getOp())
                    .targetName(quiz.getTitle())
                    .userId(quizRecordVo.getUserId())
                    .build());
            //向user_submit_recor表中插入信息
            int totalSubjectiveQuestion = quizItemDao.getTotalSubjectiveQuestion(quiz.getId());
            List<UserSubmitRecord> userSubmitRecordList = userSubmitRecordDao.findByOriginIdAndOriginType(quiz.getId(), OriginTypeEnum.QUIZ.getType(), quizRecordVo.getUserId());
            assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(quiz.getId(), OriginTypeEnum.QUIZ);
            int totalUserSubmitRecord = 0;
            if (userSubmitRecordList != null) {
                totalUserSubmitRecord = userSubmitRecordList.size();
            }
            UserSubmitRecord userSubmitRecord = new UserSubmitRecord();
            userSubmitRecord.setOriginId(quiz.getId());
            userSubmitRecord.setCourseId(quiz.getCourseId());
            userSubmitRecord.setOriginType(OriginTypeEnum.QUIZ.getType());
            if (assignmentGroupItem != null) {
                if (assignmentGroupItem.getId() == null) {
                    userSubmitRecord.setAssignmentGroupItemId(0L);
                } else {
                    userSubmitRecord.setAssignmentGroupItemId(assignmentGroupItem.getId());
                }
            } else {
                userSubmitRecord.setAssignmentGroupItemId(0L);
            }
            userSubmitRecord.setUserId(quizRecordVo.getUserId());
            if (quiz.getType().equals(QuizTypeEnum.GRADED_QUIZ.getType()) ||
                    quiz.getType().equals(QuizTypeEnum.GRADED_SURVEY.getType()) ||
                    quiz.getType().equals(QuizTypeEnum.PRACTICE_QUIZ.getType())) {
                //需要评分
                userSubmitRecord.setNeedGrade(1);
            } else {
                //不需要评分
                userSubmitRecord.setNeedGrade(0);
            }
            userSubmitRecord.setLastSubmitTime(new Date());
            if (totalUserSubmitRecord > 0) {
                userSubmitRecord.setId(userSubmitRecordList.get(0).getId());
                //提交次数
                userSubmitRecord.setSubmitCount(userSubmitRecordList.get(0).getSubmitCount() + 1);
                userSubmitRecordDao.update(userSubmitRecord);
            } else {
                //是否逾期提交
                AssignStatusEnum status = assignUserService.getAssignUserStatus(quizRecordVo.getUserId(), OriginTypeEnum.QUIZ.getType(), quiz.getId());
                if (status == AssignStatusEnum.EXCEEDEDDEADLINE) {
                    userSubmitRecord.setIsOverdue(1);
                }
                //提交次数
                userSubmitRecord.setSubmitCount(1);
                //为了评分模块区分是否需要教师评分用
                if (totalSubjectiveQuestion < 1) {
                    userSubmitRecord.setIsGraded(1);
                } else {
                    userSubmitRecord.setIsGraded(0);
                }
                userSubmitRecordDao.save(userSubmitRecord);
            }
        }

        thread.interrupt();
    }

    /**
     * @api {post} /quizRecord/deletes 测验记录id删除
     * @apiDescription 测验记录删除
     * @apiName quizRecordDelete
     * @apiGroup Quiz
     * @apiParam {Number[]} ids 测验记录ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除填空类问题回答ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "[1,2,3]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {
            //删除对应id记录
            deleteQuizRecord(id);

        }
        return new LinkedInfo(JSON.toJSONString(ids));
    }


}
