package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.quiz.dto.QuizScoreRecordDTO;
import com.wdcloud.lms.business.quiz.enums.GradeStrategyEnum;
import com.wdcloud.lms.business.quiz.enums.QuizTypeEnum;
import com.wdcloud.lms.business.quiz.enums.QuizeAnswerStatusEnum;
import com.wdcloud.lms.business.quiz.enums.QuizeOpenStatusEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.QuizRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 功能：答题，答案相关逻辑封装
 *
 * @author 黄建林
 */

@Slf4j
@Service
public class QuizService {

    @Autowired
    AssignUserService assignUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizRecordDao quizRecordDao;
    @Autowired
    private QuizItemDao quizItemDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;

    /**
     * 功能：判断一个IP地址是否在一个网段内
     *
     * @param network 给定的IP地址
     * @param mask    设定的网段
     * @return
     */
    public static boolean isInRange(String network, String mask) {
        String[] networkips = network.split("\\.");
        int ipAddr = (Integer.parseInt(networkips[0]) << 24) | (Integer.parseInt(networkips[1]) << 16) | (Integer.parseInt(networkips[2]) << 8) | Integer.parseInt(networkips[3]);
        int type = Integer.parseInt(mask.replaceAll(".*/", ""));
        int mask1 = 0xFFFFFFFF << (32 - type);
        String maskIp = mask.replaceAll("/.*", "");
        String[] maskIps = maskIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(maskIps[0]) << 24) | (Integer.parseInt(maskIps[1]) << 16) | (Integer.parseInt(maskIps[2]) << 8) | Integer.parseInt(maskIps[3]);
        return (ipAddr & mask1) == (cidrIpAddr & mask1);
    }

    /**
     * 功能：判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static int isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        //在给定时间范围内
        int isIn = 1;
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return isIn;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return isIn;
        }
        if (date.before(begin)) {
            //时间在开始时间之前
            return isIn = 2;
        } else {
            //时间在结束时间之后
            return isIn = 3;
        }


    }

    public static void main(String[] args) {
        System.out.println(isInRange("10.153.48.127", "10.153.48.0/26"));
        System.out.println(isInRange("10.168.1.2", "10.168.0.224/23"));
        System.out.println(isInRange("192.168.0.1", "192.168.0.0/24"));
        System.out.println(isInRange("10.168.0.0", "10.168.0.0/32"));
    }

    /**
     * @Description: 获取客户端IP地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }

    /**
     * 功能：是否能够开始做题
     *
     * @param quizId     测验ID
     * @param ip         访问客户端IP地址
     * @param accessCode 访问码
     * @return
     */
    public QuizeOpenStatusEnum getAuthorization(Long quizId, String ip, String accessCode) {
        //教师本人没有限制，能查看他本人所出的所有题
        if (roleService.hasTeacherOrTutorRole()) {
            //表示允许答题
            return QuizeOpenStatusEnum.PERMIT;

        }
        Quiz quiz = quizDao.get(quizId);
        if (quiz == null) {
            throw new BaseException("prop.value.not-exists","quizId",quizId+"");
        }
        //测试处于被教师锁定状态，不能查看，目前没有这个字段
//        if (quiz.getIsLockRepliedQuestion() == 1) {
//            return QuizeOpenStatusEnum.LOCKED;//2表示处于锁定状态
//
//        }
        //需要输入访问码后才能做题
        if (quiz.getIsNeedAccessCode() == 1) {
            if (accessCode == null) {
                if (quiz.getAccessCode() != null) {
                    //3表示访问码错误
                    return QuizeOpenStatusEnum.ACCESS_CODE_ERROR;
                }
            } else if (!accessCode.equals(quiz.getAccessCode())) {
                //3表示访问码错误
                return QuizeOpenStatusEnum.ACCESS_CODE_ERROR;
            }
        }
        /*答题次数限制逻辑开始*/
        QuizRecord quizRecord = new QuizRecord();
        quizRecord.setQuizId(quiz.getId());
        quizRecord.setCreateUserId(WebContext.getUserId());
        List<QuizRecordVO> quizRecordVOList = quizRecordDao.getQuizRecords(quizRecord);
        int currentRecord = 0;
        if (quizRecordVOList != null) {
            currentRecord = quizRecordVOList.size();
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
                    return QuizeOpenStatusEnum.QUIZ_RECOVERY_RECORD;
                }
                if (quiz.getTimeLimit() == null && quizRecordVOList.get(0).getIsSubmit() == 0) {
                    return QuizeOpenStatusEnum.QUIZ_RECOVERY_RECORD;
                }
            }
        }
        if (recordIsLast(quiz, currentRecord)) {
            //4答题次数已经达到限制
            return QuizeOpenStatusEnum.MAXIMUM_NUMBER_ATTAINED;
        }
        /*答题次数限制逻辑结束*/
        if (quiz.getIsFilterIp() == 1) {
            if (StringUtils.isNotBlank(quiz.getFilterIpAddress()) && !isInRange(ip, quiz.getFilterIpAddress())) {
                //5 IP地址不在设定范围内
                return QuizeOpenStatusEnum.IP_ERROR;
            }
        }
        /*模块是否解锁逻辑等待调用秀飞接口return ispermission=6;*/
        /*时间限制看是否封装成公共接口调用return ispermission=7;*/
        AssignStatusEnum status = assignUserService.getAssignUserStatus(WebContext.getUserId(), OriginTypeEnum.QUIZ.getType(), quizId);
        if (status == AssignStatusEnum.NOTBEGIN) {
            //当前时间还没达到规定的开始时间
            return QuizeOpenStatusEnum.QUIZ_NOTBEGIN;
        } else if (status == AssignStatusEnum.EXCEEDEDENDTIME) {
            return QuizeOpenStatusEnum.QUIZ_EXCEEDEDENDTIME;
        }

        return QuizeOpenStatusEnum.PERMIT;


    }

    /**
     * 功能：是否能够看正误和答案
     *
     * @param quizId 测验ID
     * @return
     */
    public QuizeAnswerStatusEnum getRecordAuthorization(Long quizId) {

        //教师本人没有限制，能查看他本人所出的所有题
        if (roleService.hasTeacherOrTutorRole()) {
            //表示允许看正误和看答案
            return QuizeAnswerStatusEnum.PERMIT_ALL;

        }
        Quiz quiz = quizDao.get(quizId);
        int currentRecord = quizRecordDao.getTotalRecord(quizId, WebContext.getUserId());
        //不显示正确答案
        if (quiz.getShowReplyStrategy() == 0) {
            //教师设置不允许看正误,就不能看答案
            return QuizeAnswerStatusEnum.TEACHER_SETTING_NOT_PERMIT;
        }
        //每次提交答案后
        if (quiz.getShowReplyStrategy() == 1) {
            if (currentRecord == 0) {
                return QuizeAnswerStatusEnum.NO_ANSWER_SUBMIT;
            }
            if (quiz.getShowAnswerStrategy() == 0) {
                //表示允许看正误
                return QuizeAnswerStatusEnum.PERMIT_RESULT_TEACHER_SETTING_NOT_ANSWER;
            }
            if (quiz.getShowAnswerStrategy() == 1) {
                if (quiz.getShowAnswerStartTime() != null && quiz.getShowAnswerEndTime() != null) {
                    Date nowTime = new Date();
                    int isefec = isEffectiveDate(nowTime, quiz.getShowAnswerStartTime(), quiz.getShowAnswerEndTime());
                    if (2 == isefec) {
                        //允许看正误，但还未达到看答案的时间
                        return QuizeAnswerStatusEnum.PERMIT_RESULT_BEFORE_ANSWER_SETTING_TIME;
                    }
                    if (3 == isefec) {
                        //允许看正误，但当前时间超出了看答案的时间
                        return QuizeAnswerStatusEnum.PERMIT_RESULT_AFTER_ANSWER_SETTING_TIME;
                    }

                }
                //表示允许看正误和看答案
                return QuizeAnswerStatusEnum.PERMIT_ALL;
            }
            if (quiz.getShowAnswerStrategy() == 2) {
                if (recordIsLast(quiz, currentRecord)) {
                    if (quiz.getShowAnswerStartTime() != null && quiz.getShowAnswerEndTime() != null) {
                        Date nowTime = new Date();
                        int isefec = isEffectiveDate(nowTime, quiz.getShowAnswerStartTime(), quiz.getShowAnswerEndTime());
                        if (2 == isefec) {
                            //允许看正误，但还未达到看答案的时间
                            return QuizeAnswerStatusEnum.PERMIT_RESULT_BEFORE_ANSWER_SETTING_TIME;
                        }
                        if (3 == isefec) {
                            //允许看正误，但当前时间超出了看答案的时间
                            return QuizeAnswerStatusEnum.PERMIT_RESULT_AFTER_ANSWER_SETTING_TIME;
                        }
                    }
                    //表示允许看正误和看答案
                    return QuizeAnswerStatusEnum.PERMIT_ALL;
                }
                //表示允许看正误
                return QuizeAnswerStatusEnum.PERMIT_RESULT_NOT_LAST_SUBMIT;
            }
        }
        //最后一次提交后
        if (quiz.getShowReplyStrategy() == 2) {
            if (recordIsLast(quiz, currentRecord)) {

                if (quiz.getShowAnswerStrategy() == 0) {
                    //允许看正误，但教师设置不能看答案
                    return QuizeAnswerStatusEnum.PERMIT_RESULT_TEACHER_SETTING_NOT_ANSWER;
                }
                if (quiz.getShowAnswerStrategy() == 2) {
                    //不允许看正误，设置了最后一次提交才能看正误，但当前不是最后一次提交,答案教师设置允许看,但当前不是最后一次提交
                    return QuizeAnswerStatusEnum.REPLY_NOT_LAST_SUBMIT_ANSWER_NOT_LAST_SUBMIT;
                }

                //最后一种逻辑是只要在看正确答案的时间范围内
                if (quiz.getShowAnswerStartTime() != null && quiz.getShowAnswerEndTime() != null) {
                    Date nowTime = new Date();
                    int isefec = isEffectiveDate(nowTime, quiz.getShowAnswerStartTime(), quiz.getShowAnswerEndTime());
                    if (2 == isefec) {
                        //允许看正误，但还未达到看答案的时间
                        return QuizeAnswerStatusEnum.REPLY_NOT_LAST_SUBMIT_BEFORE_ANSWER_SETTING_TIME;
                    }
                    if (3 == isefec) {
                        //允许看正误，但当前时间超出了看答案的时间
                        return QuizeAnswerStatusEnum.REPLY_NOT_LAST_SUBMIT_AFTER_ANSWER_SETTING_TIME;
                    }
                }
            } else {
                //不允许看正误，设置了最后一次提交才能看正误，但当前不是最后一次提交,答案教师设置不允许看
                return QuizeAnswerStatusEnum.REPLY_NOT_LAST_SUBMIT_ANSWER_TEACHE_NOT_SETTING;
            }
            if (quiz.getShowAnswerStrategy() == 0) {
                //表示允许看正误，但教师设置不能看答案
                return QuizeAnswerStatusEnum.PERMIT_RESULT_TEACHER_SETTING_NOT_ANSWER;
            }
            //最后一种逻辑是只要在看正确答案的时间范围内
            if (quiz.getShowAnswerStartTime() != null && quiz.getShowAnswerEndTime() != null) {
                Date nowTime = new Date();
                int isefec = isEffectiveDate(nowTime, quiz.getShowAnswerStartTime(), quiz.getShowAnswerEndTime());
                if (2 == isefec) {
                    //允许看正误，但还未达到看答案的时间
                    return QuizeAnswerStatusEnum.PERMIT_RESULT_BEFORE_ANSWER_SETTING_TIME;
                }
                if (3 == isefec) {
                    //允许看正误，但当前时间超出了看答案的时间
                    return QuizeAnswerStatusEnum.PERMIT_RESULT_AFTER_ANSWER_SETTING_TIME;
                }
            }
            //表示允许看正误和看答案
            return QuizeAnswerStatusEnum.PERMIT_ALL;

        }

        return QuizeAnswerStatusEnum.TEACHER_SETTING_NOT_PERMIT;
    }

    /**
     * 功能：判断答题次数是否是最后一次
     *
     * @param quiz
     * @param currentRecord
     * @return
     */
    private boolean recordIsLast(Quiz quiz, int currentRecord) {
        //允许多次尝试
        if (quiz.getAllowMultiAttempt() == 1) {
            //允许多次尝试次数
            if (quiz.getAttemptNumber() != null) {
                //达到允许次数
                if (currentRecord >= quiz.getAttemptNumber() && currentRecord != 0) {
                    //答题次数已经达到限制
                    return true;
                }

            }
            //尝试次数为空时，默认最多允许尝试50次
            else if (currentRecord == 50) {
                //答题次数已经达到限制
                return true;
            }

        }
        //不允许多次尝试，默认允许尝试1次
        else {
            if (currentRecord >= 1) {
                //答题次数已经达到限制
                return true;
            }
        }
        //还有一种情况是时间达到考试关闭日期
        //答题次数未达到限制
        return false;
    }

    private QuizScoreRecordDTO getQuizScoreRecordDTO(Quiz quiz, List<QuizRecordVO> quizRecordVOList) {

       /*
        1、只有客观题情况直接写1；
        2、主观题和客观题的情况，学生答完题给0；教师评完部分给2，全部评完给1；
      */
        /**
         * 已答题次数
         */
        int attemps = 0;
        /**
         * 剩余答题次数
         */
        int attempsAvailable = 0;
        /**
         * 是否全部评分
         */
        Integer isGradedAll = 0;
        /**
         * 当前分数
         */
        int currentScore = 0;
        /**
         * 最终为准的分数
         */
        int keptScore = 0;
        /**
         * 计算平均分
         */
        int staticAllGraded = 0;
        float averageScore = 0;
        Date createTime = null;

        int totalRecord = quizRecordVOList.size();
        QuizScoreRecordDTO quizScoreRecordDTO = new QuizScoreRecordDTO();
        int totalSubjectiveQuestion = quizItemDao.getTotalSubjectiveQuestion(quiz.getId());

        quizScoreRecordDTO.setAttemps(totalRecord);
        quizScoreRecordDTO.setQuizId(quiz.getId());
        quizScoreRecordDTO.setTesterId(WebContext.getUserId());
        if (quiz.getAllowMultiAttempt() == 1) {
            if (quiz.getAttemptNumber() != null) {
                if (quiz.getAttemptNumber() == 0) {
                    quiz.setAttemptNumber(50);
                }
                //剩余次数
                attempsAvailable = quiz.getAttemptNumber() - totalRecord;
                if (attempsAvailable < 0) {
                    attempsAvailable = 0;
                }
                quizScoreRecordDTO.setAttempsAvailable(attempsAvailable);
            }
            //没有设置答题次数，则默认设置50次
            else
            {
                quiz.setAttemptNumber(50);
                //剩余次数
                attempsAvailable = quiz.getAttemptNumber() - totalRecord;
                if (attempsAvailable < 0) {
                    attempsAvailable = 0;
                }
                quizScoreRecordDTO.setAttempsAvailable(attempsAvailable);

            }
        }
        //只允许一次答题，剩余次数为0
        else
        {
            quizScoreRecordDTO.setAttempsAvailable(0);
        }

        //计算最后一次的分数，也就是当前分数
        if (totalRecord > 0) {
            createTime = quizRecordVOList.get(0).getCreateTime();
            if (quizRecordVOList.get(0).getGrade() != null) {
                currentScore = quizRecordVOList.get(0).getGrade().getGradeScore();
            } else {
                currentScore = 0;
            }
            quizScoreRecordDTO.setQuizRecordId(quizRecordVOList.get(0).getId());

            //获得这两个时间的毫秒值后进行处理(因为我的需求不需要处理时间大小，所以此处没有处理，可以判断一下哪个大就用哪个作为减数。)
            long diff = quizRecordVOList.get(0).getUpdateTime().getTime() - quizRecordVOList.get(0).getCreateTime().getTime();
            //此处用毫秒值除以分钟再除以毫秒既得两个时间相差的分钟数
            int minute = (int) diff / 60 / 1000;
            quizScoreRecordDTO.setTimeLimit(minute);
            quizScoreRecordDTO.setKeptScore(currentScore);
        } else {

            quizScoreRecordDTO.setQuizRecordId(0L);
            quizScoreRecordDTO.setCurrentScore(0);
            quizScoreRecordDTO.setTimeLimit(0);
            quizScoreRecordDTO.setKeptScore(0);
        }

        for (int i = 0; i < totalRecord; i++) {

            if (quizRecordVOList.get(i).getGrade() == null) {
                isGradedAll = 0;
                continue;
            }
            Long dftime = quizRecordVOList.get(i).getGrade().getCreateTime().getTime() - quizRecordVOList.get(i).getGrade().getUpdateTime().getTime();
            if (dftime < 0) {
                dftime = -dftime;
            }
            if (dftime < 3 && totalSubjectiveQuestion > 0) {
                isGradedAll = 0;
            } else {
                staticAllGraded++;
            }


            if (quiz.getScoreType() != null) {
                //显示最高分
                if (quiz.getScoreType() == 1) {
                    if (quizRecordVOList.get(i).getGrade().getGradeScore() != null) {
                        if (quizRecordVOList.get(i).getGrade().getGradeScore() > keptScore) {
                            keptScore = quizRecordVOList.get(i).getGrade().getGradeScore();
                            quizScoreRecordDTO.setQuizRecordId(quizRecordVOList.get(i).getId());
                        }
                    }
                }
                //最近一次得分
                else if (quiz.getScoreType() == 2) {
                    if (quizRecordVOList.get(0).getGrade() != null) {
                        keptScore = quizRecordVOList.get(0).getGrade().getGradeScore();
                    } else {
                        keptScore = 0;
                    }

                }

                //平均分
                else {
                    if (quizRecordVOList.get(i).getGrade() != null) {
                        averageScore += quizRecordVOList.get(i).getGrade().getGradeScore();
                    }
                }


            }
            //如果没有设置分数显示规则，则按最近一次的规则显示
            else
            {
                if (quizRecordVOList.get(0).getGrade() != null) {
                    keptScore = quizRecordVOList.get(0).getGrade().getGradeScore();
                } else {
                    keptScore = 0;
                }
                quiz.setScoreType(GradeStrategyEnum.LATEST.getStrategy());
            }

        }
        if (totalRecord == 0) {
            isGradedAll = 0;
        }
        if (quiz.getScoreType() != null) {
            if (quiz.getScoreType() == 3) {
                if (totalRecord == 0) {
                    averageScore = 0;
                    keptScore = 0;

                } else {
                    averageScore = averageScore / totalRecord;
                    //设置位数
                    int scale = 0;
                    //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
                    int roundingMode = 4;
                    BigDecimal bd = new BigDecimal((double) averageScore);
                    bd = bd.setScale(scale, RoundingMode.HALF_UP);
                    averageScore = bd.floatValue();
                    keptScore = (int) averageScore;

                }
                //平均分没有KeptScore记录的id
                quizScoreRecordDTO.setQuizRecordId(0L);
            }
        }
        if (totalRecord == 1) {
            keptScore = currentScore;
        }
        if (totalSubjectiveQuestion == 0) {
            quizScoreRecordDTO.setIsGradedAll(1);
        } else {
            if (staticAllGraded > 0 && staticAllGraded < totalRecord) {
                isGradedAll = 2;
            } else if (staticAllGraded == totalRecord) {
                isGradedAll = 1;
            }
            quizScoreRecordDTO.setIsGradedAll(isGradedAll);
        }
        quizScoreRecordDTO.setKeptScore(keptScore);
        quizScoreRecordDTO.setCurrentScore(currentScore);

        quizScoreRecordDTO.setGradeStrategy(quiz.getScoreType());
        return quizScoreRecordDTO;
    }

    /**
     * 功能：对学生测验答题信息做基本统计
     *
     * @param quiz
     * @param userId
     * @param submitOrGrade 1：为学生答题；0为教师评分
     */
    public int quizRecordStatic(Quiz quiz, long userId, int submitOrGrade) {
        QuizRecord quizRecord = new QuizRecord();
        quizRecord.setQuizId(quiz.getId());
        quizRecord.setCreateUserId(userId);
        List<QuizRecordVO> quizRecordVOList = quizRecordDao.getQuizRecords(quizRecord);

        QuizScoreRecordDTO quizScoreRecordDTO = getQuizScoreRecordDTO(quiz, quizRecordVOList);
        Grade grade = new Grade();
        grade.setCourseId(quiz.getCourseId());
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findByOriginIdAndType(quiz.getId(), OriginTypeEnum.QUIZ);
        if (assignmentGroupItem != null) {
            if (assignmentGroupItem.getId() == null) {
                grade.setAssignmentGroupItemId(0L);
            } else {
                grade.setAssignmentGroupItemId(assignmentGroupItem.getId());
            }
        } else {
            grade.setAssignmentGroupItemId(0L);
        }
        grade.setOriginType(OriginTypeEnum.QUIZ.getType());
        grade.setOriginId(quiz.getId());
        grade.setScoreType(quiz.getScoreType());
        grade.setScore(quiz.getTotalScore());
        grade.setGradeScore(quizScoreRecordDTO.getKeptScore());
        grade.setCurrentScore(quizScoreRecordDTO.getCurrentScore());
        grade.setIsGraded(quizScoreRecordDTO.getIsGradedAll());
        grade.setTimeLimit(quizScoreRecordDTO.getTimeLimit());
        grade.setRecordId(quizScoreRecordDTO.getQuizRecordId());
        grade.setAttemps(quizScoreRecordDTO.getAttemps());
        grade.setAttempsAvailable(quizScoreRecordDTO.getAttempsAvailable());

        if (quiz.getType().equals(QuizTypeEnum.GRADED_QUIZ.getType()) ||
                quiz.getType().equals(QuizTypeEnum.GRADED_SURVEY.getType()) ||
                quiz.getType().equals(QuizTypeEnum.PRACTICE_QUIZ.getType())) {
            //需要评分
            grade.setNeedGrade(1);
        } else {
            //不需要评分
            grade.setNeedGrade(0);
        }
        //学生答题提交
        if (submitOrGrade == 1)
        {
            grade.setLastSubmitTime(new Date());
        }
        //是否逾期提交未处理，在UserSubmitRecord表中做了处理，后期看是否需要合并

        grade.setUserId(userId);
        grade.setGraderId(WebContext.getUserId());

        List<Grade> grdlist = gradeDao.find(Grade.builder()
                .originId(quiz.getId())
                .originType(OriginTypeEnum.QUIZ.getType())
                .userId(userId)
                .build());
        if (grdlist == null) {
            gradeDao.save(grade);
        } else {
            if (grdlist.size() == 0) {
                gradeDao.save(grade);
            } else {
                grade.setId(grdlist.get(0).getId());
                gradeDao.update(grade);
            }
        }
        List<UserSubmitRecord> userSubmitRecordList = userSubmitRecordDao.find(
                UserSubmitRecord.builder()
                        .originType(OriginTypeEnum.QUIZ.getType())
                        .originId(quiz.getId())
                        .userId(userId)
                        .build());
        if (userSubmitRecordList != null) {
            if (userSubmitRecordList.size() > 0) {
                userSubmitRecordList.get(0).setIsGraded(quizScoreRecordDTO.getIsGradedAll());

                userSubmitRecordDao.update(userSubmitRecordList.get(0));
            }

        }
        return quizScoreRecordDTO.getKeptScore();
    }

}
