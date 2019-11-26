package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.AssignmentGroupItemDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.AssignmentGroupItemService;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.quiz.dto.QuizDTO;
import com.wdcloud.lms.business.quiz.enums.QuizTypeEnum;
import com.wdcloud.lms.business.quiz.question.QuestionOperation;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignmentGroupItemChangeOpTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * 功能：实现测验表增、删、改调用接口
 *
 * @author 黄建林
 */
@ResourceInfo(name = Constants.RESOURCE_TYPE_QUIZ)
public class QuizEdit implements IDataEditComponent {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private AssignService assignService;
    @Autowired
    private QuizItemDao quizItemDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionOperation questionOperation;
    @Autowired
    private QuestionGroupDao questionGroupDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private AssignmentGroupItemService assignmentGroupItemService;
    @Autowired
    private AssignmentGroupItemChangeRecordDao groupItemChangeRecordDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /quiz/add 添加测验
     * @apiDescription 添加测验
     * @apiName quizAdd
     * @apiGroup Quiz
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title名称
     * @apiParam {String} [description] 描述
     * @apiParam {Number=1,2,3,4} type 类型，1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)
     * @apiParam {Number} [assignmentGroupId] 作业小组ID（type=2,3）
     * @apiParam {Number} [score] 计分值(type=3，一旦学生参加调查，将自动获得满分)
     * @apiParam {Number} [allowAnonymous] 允许匿名提交(type=3,4)
     * @apiParam {Number=0,1} isShuffleAnswer 是否重组答案（学生测验时答案选项将随机排列）
     * @apiParam {Number} [timeLimit] 时间限制（分钟）
     * @apiParam {Number=0,1} [allowMultiAttempt] 允许多次尝试  1：是；0：否
     * @apiParam {Number} [attemptNumber] 尝试次数
     * @apiParam {Number=1,2,3} [scoreType] 如果可以多次尝试，该字段表示最终评分规则：1. 取最高分，2. 最低分， 3：平均分
     * @apiParam {Number=0,1,2} showReplyStrategy 显示学生的答题记录（正误）策略，0: 不显示 1：每次提交答案后 2：最后一次提交后
     * @apiParam {Number=0,1,2} showAnswerStrategy 显示答案策略，0: 不显示 1：每次提交后 2：最后一次提交后
     * @apiParam {datetime} [showAnswerStartTime] 显示正确答案开始时间
     * @apiParam {datetime} [showAnswerEndTime] 显示正确答案结束时间
     * @apiParam {Number=0,1} showQuestionStrategy 显示问题策略, 0: 全部显示, 1: 每页一个
     * @apiParam {Number=0,1} isLockRepliedQuestion 回答后是否锁定问题
     * @apiParam {Number=0,1} isNeedAccessCode 是否需要访问码访问
     * @apiParam {String} [accessCode] 访问码（配置后学生测验需要先输入该访问码）
     * @apiParam {Number=0,1} isFilterIP 是否过滤访问IP
     * @apiParam {String} [filterIpAddress] 过滤IP地发布状态址（只允许指定 IP 范围的计算机访问测验）
     * @apiParam {Number=1} version  版本号（预留）,每次更新后版本号增加
     * @apiParam {Number=0,1} status  发布状态： 1：已发布；0：未发布
     * @apiParam {Number} totalQuestions  问题总数 前端默认传0，后台自动计算
     * @apiParam {Number} totalScore  总分 前端默认传0，后台自动计算
     * @apiParam {Object[]} [assign] 分配
     * @apiParam {Number=1,2,3} [assign.assignType]  分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiParam {datetime} [assign.limitTime]  截至时间
     * @apiParam {datetime} [assign.startTime]  可以参加测验开始日期
     * @apiParam {datetime} [assign.endTime]  可以参加测验结束日期
     * @apiExample {json} 请求示例:
     * {
     * <p>
     * "courseId": "2",
     * "title": "名称",
     * "description": "描述",
     * "type": "3",
     * "assignmentGroupId": "4",
     * "score": "5",
     * "allowAnonymous": "6",
     * "isShuffleAnswer": "7",
     * "timeLimit": "8",
     * "allowMultiAttempt": "9",
     * "attemptNumber": "10",
     * "scoreType": "11",
     * "showReplyStrategy": "12",
     * "showAnswerStrategy": "13",
     * "showAnswerStartTime": "2018-12-18 11:31:15",
     * "showAnswerEndTime": "2018-12-18 11:31:15",
     * "showQuestionStrategy": "14",
     * "isLockRepliedQuestion": "15",
     * "isNeedAccessCode": "16",
     * "accessCode": "17",
     * "isFilterIP": "18",
     * "filterIpAddress": "19",
     * "version": "20",
     * "status": "21",
     * "totalQuestions": "22",
     * "totalScore": "22",
     * "assign":[{"assignId":"2","assignType":"1","originId":"2","limitTime":"2019-03-07 13:08:24","startTime":"2019-03-07 09:08:24","endTime":"2019-03-07 13:08:24"}]
     * <p>
     * <p>
     * }
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增测验ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "159"
     * }
     */
    @AccessLimit
    @ValidationParam(clazz = QuizDTO.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        final QuizDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizDTO.class);
        if (dto.getType().equals(QuizTypeEnum.GRADED_SURVEY.getType()) ) {
            //初始分数为0
            dto.setTotalScore(0);

        }
        dto.setTotalQuestions(0);
        quizDao.save(dto);
        //存储测验分配到班级的记录
        if (CollectionUtil.isNotNullAndEmpty(dto.getAssign())) {
            assignService.batchSave(dto.getAssign(), dto.getCourseId(), OriginTypeEnum.QUIZ, dto.getId());
        }
        Long groupItemId = insertAssignmentGroupItem(dto);
        if (groupItemId != null) {
            groupItemChangeRecordDao.quizAdded(dto, groupItemId);
        }
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    private Long insertAssignmentGroupItem(QuizDTO dto) {
        if (dto.getAssignmentGroupId() != null) {
            AssignmentGroupItemDTO item = null;
            AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(dto.getId(), OriginTypeEnum.QUIZ);
            if (assignmentGroupItem == null) {
                item = AssignmentGroupItemDTO.builder()
                        .assignmentGroupId(dto.getAssignmentGroupId())
                        .originId(dto.getId())
                        .originType(OriginTypeEnum.QUIZ)
                        .title(dto.getTitle())
                        .score(dto.getTotalScore())
                        .status(dto.getStatus())
                        .build();
                Long groupItemId = assignmentGroupItemService.save(item);
                return groupItemId;
            } else {
                AssignmentGroupItem asitem = AssignmentGroupItem.builder()
                        .assignmentGroupId(dto.getAssignmentGroupId())
                        .originId(dto.getId())
                        .originType(OriginTypeEnum.QUIZ.getType())
                        .title(dto.getTitle())
                        .score(dto.getTotalScore())
                        .status(dto.getStatus())
                        .id(assignmentGroupItem.getId())
                        .build();
                assignmentGroupItemDao.update(asitem);
                return asitem.getId();
            }
        }
        return null;
    }

    /**
     * @api {post} /quiz/modify 测验修改
     * @apiDescription 测验修改
     * @apiName quizModify
     * @apiGroup Quiz
     * @apiParam {Number} id ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title名称
     * @apiParam {String} [description] 描述
     * @apiParam {Number=1,2,3,4} type 类型，1：练习测验(practice quiz)、2：评分测验(graded quiz)、3：评分调查(graded survey)、4：非评分调查(ungraded survey)
     * @apiParam {Number} [assignmentGroupId] 作业小组ID（type=2,3）
     * @apiParam {Number} [score] 计分值(type=3，一旦学生参加调查，将自动获得满分)
     * @apiParam {Number} [allowAnonymous] 允许匿名提交(type=3,4)
     * @apiParam {Number=0,1} isShuffleAnswer 是否重组答案（学生测验时答案选项将随机排列）
     * @apiParam {Number} [timeLimit] 时间限制（分钟）
     * @apiParam {Number=0,1} [allowMultiAttempt] 允许多次尝试  1：是；0：否
     * @apiParam {Number} [attemptNumber] 尝试次数
     * @apiParam {Number=1,2,3} [scoreType] 如果可以多次尝试，该字段表示最终评分规则：1. 取最高分，2. 最低分， 3：平均分
     * @apiParam {Number=0,1,2} showReplyStrategy 显示学生的答题记录（正误）策略，0: 不显示 1：每次提交答案后 2：最后一次提交后
     * @apiParam {Number=0,1,2} showAnswerStrategy 显示答案策略，0: 不显示 1：每次提交后 2：最后一次提交后
     * @apiParam {datetime} [showAnswerStartTime] 显示正确答案开始时间
     * @apiParam {datetime} [showAnswerEndTime] 显示正确答案结束时间
     * @apiParam {Number=0,1} showQuestionStrategy 显示问题策略, 0: 全部显示, 1: 每页一个
     * @apiParam {Number=0,1} isLockRepliedQuestion 回答后是否锁定问题
     * @apiParam {Number=0,1} isNeedAccessCode 是否需要访问码访问
     * @apiParam {String} [accessCode] 访问码（配置后学生测验需要先输入该访问码）
     * @apiParam {Number=0,1} isFilterIP 是否过滤访问IP
     * @apiParam {String} [filterIpAddress] 过滤IP地发布状态址（只允许指定 IP 范围的计算机访问测验）
     * @apiParam {Number=1} version  版本号（预留）,每次更新后版本号增加
     * @apiParam {Number=0,1} status  发布状态： 1：已发布；0：未发布
     * @apiParam {Number} totalQuestions  问题总数 前端传后端返回值
     * @apiParam {Number} totalScore  总分 前端传后端返回值
     * @apiParam {Object[]} [assign] 分配
     * @apiParam {Number=1,2,3} [assign.assignType]  分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiParam {datetime} [assign.limitTime]  截至时间
     * @apiParam {datetime} [assign.startTime]  可以参加测验开始日期
     * @apiParam {datetime} [assign.endTime]  可以参加测验结束日期
     * @apiExample {json} 请求示例:
     * {
     * "id": "159",
     * "courseId": "2",
     * "title": "名称",
     * "description": "描述",
     * "type": "3",
     * "assignmentGroupId": "4",
     * "score": "5",
     * "allowAnonymous": "6",
     * "isShuffleAnswer": "7",
     * "timeLimit": "8",
     * "allowMultiAttempt": "9",
     * "attemptNumber": "10",
     * "scoreType": "11",
     * "showReplyStrategy": "12",
     * "showAnswerStrategy": "13",
     * "showAnswerStartTime": "2018-12-18 11:31:15",
     * "showAnswerEndTime": "2018-12-18 11:31:15",
     * "showQuestionStrategy": "14",
     * "isLockRepliedQuestion": "15",
     * "isNeedAccessCode": "16",
     * "accessCode": "17",
     * "isFilterIP": "18",
     * "filterIpAddress": "19",
     * "version": "20",
     * "status": "21",
     * "totalQuestions": "22",
     * "totalScore": "22",
     * "assign":[{"assignId":"2","assignType":"1","originId":"2","limitTime":"2019-03-07 13:08:24","startTime":"2019-03-07 09:08:24","endTime":"2019-03-07 13:08:24"}]
     * <p>
     * <p>
     * }
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增测验ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "159"
     * }
     */
    @Override
    @ValidationParam(clazz = QuizDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final QuizDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizDTO.class);
        Quiz oldQuiz = quizDao.get(dto.getId());
        if (oldQuiz == null) {
            throw new ParamErrorException();
        }
        if (!(oldQuiz.getType().equals(QuizTypeEnum.GRADED_SURVEY.getType()))) {
            dto.setTotalScore(oldQuiz.getTotalScore());
            dto.setTotalQuestions(null);
        } else {

            dto.setTotalQuestions(null);
        }
        if(dto.getAllowMultiAttempt()==0)
        {
            //不设置默认允许一次答题
            dto.setAttemptNumber(1);
        }
        else
        {
            //设置允许多次答题，但没设置答题次数，默认允许50次答题
            if( dto.getAttemptNumber()==null)
            {
                dto.setAttemptNumber(50);
            }
        }
        quizDao.update(dto);
        //存储测验分配到班级的记录
        if (CollectionUtil.isNotNullAndEmpty(dto.getAssign())) {
            assignService.batchSave(dto.getAssign(), dto.getCourseId(), OriginTypeEnum.QUIZ, dto.getId());
        }
        //插入cos_assignment_group_item表
        Long groupItemId = insertAssignmentGroupItem(dto);
        if (groupItemId != null && !Objects.equals(oldQuiz.getDescription(), dto.getDescription())) {
            groupItemChangeRecordDao.changed(dto, groupItemId, AssignmentGroupItemChangeOpTypeEnum.UPDATE_CONTENT);
        }
        //更新单元任务表中相应信息内容
        moduleCompleteService.updateAssign(dto.getCourseId(), dto.getId(), OriginTypeEnum.QUIZ.getType());
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }

    /**
     * @api {post} /quiz/deletes 测验删除
     * @apiDescription 测验删除
     * @apiName quizDeletes
     * @apiGroup Quiz
     * @apiParam {Number[] } ids 测验问题ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除测验问题ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "[49]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {
            //删除测验信息时删除单元任务进度相关信息
            moduleCompleteService.deleteModuleItemByOriginId(id, OriginTypeEnum.QUIZ.getType());
            //删除测验里的所有问题及问题附加信息
            List<QuizItem> quizItems = quizItemDao.getByQuizId(id);
            if (quizItems == null) {
                throw new BaseException("prop.value.not-exists","quizItem",id+"");
            }
            for (QuizItem quizItem : quizItems) {
                Question question = questionDao.get(quizItem.getTargetId());
                //删除问题对应子项记录
                questionOperation.delete(question);
                //删除问题对应id记录
                questionDao.delete(quizItem.getTargetId());
            }

            //删除问题组信息
            questionGroupDao.deleteByquizId(id);
            //删除测验问题项记录
            quizItemDao.deleteByquizId(id);
            //删除测验信息对应id记录
            quizDao.delete(id);

            //删除user_submit_recor表中插入信息

            UserSubmitRecord userSubmitRecord = userSubmitRecordDao.deleteByOriginIdAndOriginType(id, OriginTypeEnum.QUIZ.getType());
            if (userSubmitRecord != null) {
                userSubmitRecordDao.delete(userSubmitRecord.getId());
            }
        }


        return new LinkedInfo(JSON.toJSONString(ids));
    }

}
