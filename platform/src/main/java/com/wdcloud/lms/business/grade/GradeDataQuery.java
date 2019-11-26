package com.wdcloud.lms.business.grade;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.dao.GradeDao;
import com.wdcloud.lms.core.base.dao.UserSubmitRecordDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.vo.*;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_GREDE,
        functionName = Constants.FUNCTION_TYPE_DATA
)
public class GradeDataQuery implements ISelfDefinedSearch<List<GradeTaskVO>> {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private UserSubmitRecordDao userSubmitRecordDao;
    /**
     *
     * @api {get} /grade/data/query 评分任务列表 GradeTask
     * @apiDescription 评分任务列表GradeTask查询
     * @apiName gradeDataQuery
     * @apiGroup Grade
     *
     * @apiParam {String} originType 任务类型： 1: 作业 2: 讨论 3: 测验'
     * @apiParam {Number} courseId 课程id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity
     * @apiSuccess {String} entity.title 目标任务类型标题
     * @apiSuccess {Number} entity.score 总分
     * @apiSuccess {Number} entity.graderIdcount 评分用户个数
     * @apiSuccess {Number} entity.unGraderIdcount 未评分用户个数
     * @apiSuccess {Number} entity.plgs 评论个数
     * @apiSuccess {Number} entity.taskAvg 平均分
     * @apiSuccess {String} entity.isGradeAssignment 属于组或者个人
     * @apiSuccess {Object[]} entity.fplb 分配列表
     * @apiSuccess {Long} entity.fplb.originId 分配id
     * @apiSuccess {Date} entity.fplb.endTime 可以参加测验结束日期
     * @apiSuccess {date} entity.fplb.limitTime 规定截止日期
     * @apiSuccess {Date} entity.fplb.startTime 可以参加测验开始日期
     */
    @Override
    public List<GradeTaskVO> search(Map<String, String> condition) {
        String originType =condition.get("originType");
        Long courseId =Long.parseLong(condition.get("courseId"));
        //查询在当前课程下需要评分的任务并且已经过了发布时间
        List<GradeTaskVO> gradeTestQuery = gradeDao.gradeTestQuery(originType,courseId);
        List<Long> assignmentIdList = gradeTestQuery.stream()
                .filter(o -> o.getOriginType().equals(OriginTypeEnum.ASSIGNMENT.getType()))
                .map(GradeTaskVO::getOriginId).collect(Collectors.toList());
        Map<Long, GradeTestListQueryVo> assignmentMap = new HashMap<>();
        if (ListUtils.isNotEmpty(assignmentIdList)) {
            //作业类型：判断是否是小组作业
            List<GradeTestListQueryVo> isgGroupAssignmentBatch=gradeDao.ext().isgGroupAssignmentBatch(assignmentIdList);
            assignmentMap = isgGroupAssignmentBatch.stream()
                    .collect(Collectors.toMap(GradeTestListQueryVo::getOriginId, o ->o));
        }

        List<Long> discusstionIdList=gradeTestQuery.stream()
                .filter(o->o.getOriginType().equals(OriginTypeEnum.DISCUSSION.getType()))
                .map(GradeTaskVO::getOriginId).collect(Collectors.toList());
        Map<Long, GradeTestListQueryVo> discussionMap = new HashMap<>();
        if (ListUtils.isNotEmpty(discusstionIdList)) {
            //讨论类型：判断是否是小组作业
            List<GradeTestListQueryVo> isGroupDiscussionBatch=gradeDao.ext().isgGroupDiscussionBatch(discusstionIdList);
            discussionMap = isGroupDiscussionBatch.stream()
                    .collect(Collectors.toMap(GradeTestListQueryVo::getOriginId, o ->o));
        }

        Set<Long> missionIdList=gradeTestQuery.stream()
                .map(GradeTaskVO::getOriginId).collect(Collectors.toSet());
        Map<String, Integer> taskAvgListMap = new HashMap<>();
        Map<String, UserSubmitRecordVo> userSubmitRecordVoHashMap = new HashMap<>();
        Map<String, Integer> commentTotalVOMap = new HashMap<>();
        if (CollectionUtil.isNotNullAndEmpty(missionIdList)) {
            //批量查询任务平均分
            List<TaskAvgVO> assignmentAvgList=gradeDao.taskAvgBatch(new ArrayList(missionIdList),null);
            taskAvgListMap=assignmentAvgList.stream()
                    .collect(Collectors.toMap(k->(k.getOriginType()+"_"+k.getOriginId()) , o ->o.getTaskAvg()));
            //批量统计每个任务已评分和未评分的个数
            List<UserSubmitRecordVo> userSubmitRecordVoList= userSubmitRecordDao.countIsGradeBatch(new ArrayList(missionIdList));
            userSubmitRecordVoHashMap=userSubmitRecordVoList.stream()
                    .collect(Collectors.toMap(k->(k.getOriginType()+"_"+k.getOriginId()) , o ->o));
            //批量获取评论数
            List<CommentTotalVO> commentTotalVOList=gradeCommentDao.getCommentTotalBatch(new ArrayList(missionIdList));
            commentTotalVOMap=commentTotalVOList.stream()
                    .collect(Collectors.toMap(k->(k.getOriginType()+"_"+k.getOriginId()) , o ->o.getCommentTotal()));
        }

        for (GradeTaskVO gradeTaskVO : gradeTestQuery) {
            Long originId = gradeTaskVO.getOriginId();
            Integer originIdType = gradeTaskVO.getOriginType();
            //设置评论个数
            Integer plgs = commentTotalVOMap.get(originIdType + "_" + originId);
            gradeTaskVO.setPlgs(plgs==null?0:plgs);
            //设置平均分
            gradeTaskVO.setTaskAvg(taskAvgListMap.get(originIdType+"_"+originId));
            //任务属于小组还是个人
            if(OriginTypeEnum.ASSIGNMENT.getType().equals(originIdType)){
                gradeTaskVO.setIsGradeAssignment(assignmentMap.get(originId).getIsGradeAssignment());
            }else if(OriginTypeEnum.DISCUSSION.getType().equals(originIdType)){
                gradeTaskVO.setIsGradeAssignment(discussionMap.get(originId).getIsGradeAssignment());
            }
            //设置每个任务已评分和未评分的个数
            UserSubmitRecordVo userSubmitRecordVo = userSubmitRecordVoHashMap.get(originIdType + "_" + originId);
            if (userSubmitRecordVo != null) {
                gradeTaskVO.setGraderIdcount(userSubmitRecordVo.getGraderIdcount());
                gradeTaskVO.setUnGraderIdcount(userSubmitRecordVo.getUnGraderIdcount());
            }
        }
        //任务列表排序规则：未评分个数从高到底排序，如果未评分个数都为0时，按照任务的title排序
        return gradeTestQuery.stream()
                .sorted((a,b) ->{
            Integer aunGraderIdcount= a.getUnGraderIdcount();
            Integer bunGraderIdcount= b.getUnGraderIdcount();

            if (aunGraderIdcount != null && bunGraderIdcount == null) {
                return -1;
            }
            if (aunGraderIdcount == null && bunGraderIdcount != null) {
                return 1;
            }
            if (aunGraderIdcount != null && bunGraderIdcount != null) {
                int res = bunGraderIdcount.compareTo(aunGraderIdcount);
                if (res != 0) {
                    return res;
                }
            }
            String atitle = a.getTitle();
            String btitle = b.getTitle();
            return atitle.compareTo(btitle);
        }).collect(Collectors.toList());
    }

}
