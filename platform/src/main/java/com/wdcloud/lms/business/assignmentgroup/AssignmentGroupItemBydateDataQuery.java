package com.wdcloud.lms.business.assignmentgroup;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.business.assignmentgroup.vo.AssignmentGroupItemByDateVO;
import com.wdcloud.lms.business.assignmentgroup.vo.AssignmentGroupItemVO;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP_ITEM_BY_DATE)
public class AssignmentGroupItemBydateDataQuery implements IDataQueryComponent<AssignmentGroupItemByDateVO> {
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private DiscussionReplyDao discussionReplyDao;
    @Autowired
    private QuizRecordDao quizRecordDao;

    /**
     * @api {get} /assignmentGroupItemByDate/list 按日期查看作业组小项列表
     * @apiName assignmentGroupItemListByDate
     * @apiGroup AssignmentGroup
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} [title] 作业标题模糊查
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Object[]} [entity.overdue] 结果
     * @apiSuccess {Number} entity.overdue.id ID
     * @apiSuccess {String} entity.overdue.title 名称
     * @apiSuccess {Number} entity.overdue.originId 源ID
     * @apiSuccess {Number} entity.overdue.originType 源类型
     * @apiSuccess {Number} entity.overdue.score 分数
     * @apiSuccess {Number} [entity.overdue.modules] 被依赖module
     * @apiSuccess {Object[]} [entity.upcoming] 结果
     * @apiSuccess {Number} entity.upcoming.id ID
     * @apiSuccess {String} entity.upcoming.title 名称
     * @apiSuccess {Number} entity.upcoming.originId 源ID
     * @apiSuccess {Number} entity.upcoming.originType 源类型
     * @apiSuccess {Number} entity.upcoming.score 分数
     * @apiSuccess {Number} [entity.upcoming.modules] 被依赖module
     * @apiSuccess {Object[]} [entity.undated] 结果
     * @apiSuccess {Number} entity.undated.id ID
     * @apiSuccess {String} entity.undated.title 名称
     * @apiSuccess {Number} entity.undated.originId 源ID
     * @apiSuccess {Number} entity.undated.originType 源类型
     * @apiSuccess {Number} entity.undated.score 分数
     * @apiSuccess {Number} [entity.undated.modules] 被依赖module
     * @apiSuccess {Object[]} [entity.past] 结果
     * @apiSuccess {Number} entity.past.id ID
     * @apiSuccess {String} entity.past.title 名称
     * @apiSuccess {Number} entity.past.originId 源ID
     * @apiSuccess {Number} entity.past.originType 源类型
     * @apiSuccess {Number} entity.past.score 分数
     * @apiSuccess {Number} [entity.past.modules] 被依赖module
     * @apiSuccessExample {String} json 返回值
     * {
     *     "code": 200,
     *     "entity": [
     *         {
     *             "overdue": [],
     *             "past": [],
     *             "undated": [
     *                 {
     *                     "createTime": 1557397101000,
     *                     "id": 2374,
     *                     "modules": [
     *                         {
     *                             "courseId": 304,
     *                             "createTime": 1559282768000,
     *                             "createUserId": 76,
     *                             "id": 184,
     *                             "name": "module1",
     *                             "seq": 1,
     *                             "status": 0,
     *                             "updateTime": 1559282768000,
     *                             "updateUserId": 76
     *                         }
     *                     ],
     *                     "originId": 856,
     *                     "originType": 1,
     *                     "score": 0,
     *                     "seq": 1,
     *                     "status": 1,
     *                     "title": "111"
     *                 }
     *             ],
     *             "upcoming": [
     *                 {
     *                     "createTime": 1559283660000,
     *                     "id": 2782,
     *                     "originId": 1001,
     *                     "originType": 1,
     *                     "score": 500,
     *                     "seq": 13,
     *                     "status": 1,
     *                     "title": "555"
     *                 }
     *             ]
     *         }
     *     ],
     *     "message": "success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    public List<AssignmentGroupItemByDateVO> list(Map<String, String> param) {
        //初始化数据
        AssignmentGroupItemByDateVO result = new AssignmentGroupItemByDateVO();
        List<AssignmentGroupItemByDateVO> list = Lists.newArrayList();
        //获取本课程下所有已发布的任务
        Map<String, Object> map = new HashMap<>();
        map.put("courseId", param.get(AssignmentGroup.COURSE_ID));
        map.put("title", param.get(AssignmentGroupItem.TITLE));
        List<AssignmentGroupItem> items = assignmentGroupItemDao.findAssignmentGroupItemList(map);

        if (CollectionUtil.isNullOrEmpty(items)) {
            return list;
        }
        //遍历每个任务
        List<AssignmentGroupItemVO> overdue = new ArrayList<>(items.size());
        List<AssignmentGroupItemVO> upcoming = new ArrayList<>(items.size());
        List<AssignmentGroupItemVO> undated = new ArrayList<>(items.size());
        List<AssignmentGroupItemVO> past = new ArrayList<>(items.size());
        Date now = DateUtil.now();
        //获取任务提交状态
        Map<Long, Boolean> submitStatusMap = getSubmitStatus(items);
        //1、除去未分配给登陆人的任务
        //2、加单元
        //3、把任务分组
        for (AssignmentGroupItem o : items) {
            //除去未分配给登陆人的任务
            AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(), o.getOriginType(), o.getOriginId());
            if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum)) {
                //加单元
                AssignmentGroupItemVO vo = BeanUtil.beanCopyProperties(o, AssignmentGroupItemVO.class, Constants.IGNORE_PROPERTIES);
                List<Module> modules = moduleDao.getModuleByOriginTypeAndOriginId(o.getOriginId(), o.getOriginType());
                vo.setModules(modules);
                //把任务分组
                AssignUser assignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), o.getOriginType(), o.getOriginId());
                Date limitTime = assignUser.getLimitTime();
                Date endTime = assignUser.getEndTime();
                if (limitTime == null) {
                    undated.add(vo);
                } else if (limitTime.after(now)) {
                    upcoming.add(vo);
                } else if (null != endTime && endTime.before(now)) {
                    past.add(vo);
                } else if (submitStatusMap.get(vo.getId())) {
                    past.add(vo);
                } else {
                    overdue.add(vo);
                }
            }
        }
        //排序
        if (overdue.size() > 0) {
            List<AssignmentGroupItemVO> overdue2 = sortAssignmentItem(overdue);
            result.setOverdue(overdue2);}
        if (upcoming.size() > 0) {
            List<AssignmentGroupItemVO> upcoming2 = sortAssignmentItem(upcoming);
            result.setUpcoming(upcoming2);}
        if (undated.size() > 0) {
            List<AssignmentGroupItemVO> undated2 = sortAssignmentItem(undated);
            result.setUndated(undated2);}
        if (past.size() > 0) {
            List<AssignmentGroupItemVO> past2 = sortAssignmentItem(past);
            result.setPast(past2);}
        list.add(result);
        return list;
    }

    private Map<Long, Boolean> getSubmitStatus(List<AssignmentGroupItem> items) {
        Map<Long, Boolean> map = new HashMap<>();
        for (AssignmentGroupItem o : items) {
            Long id = o.getOriginId();
            if (o.getOriginType() == 1) {
                Assignment assignment = assignmentDao.get(id);
                Example example = assignmentReplyDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(Constants.PARAM_ASSIGNMENT_ID, id);
                if (StringUtils.isEmpty(assignment.getStudyGroupSetId())) {
                    criteria.andEqualTo(AssignmentReply.USER_ID, WebContext.getUserId());//不是组作业
                } else {
                    //获取小组Ids
                    List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                            .studyGroupSetId(assignment.getStudyGroupSetId())
                            .userId(WebContext.getUserId())
                            .courseId(assignment.getCourseId()).build());
                    List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
                    if (ss.size() > 0) {
                        criteria.andIn(AssignmentReply.STUDY_GROUP_ID, ss);
                    }else{
                        criteria.andEqualTo(AssignmentReply.USER_ID, WebContext.getUserId());//没有在小组中
                    }
                }
                AssignmentReply reply = assignmentReplyDao.findOne(example);
                map.put(o.getId(), reply != null);
            } else if (o.getOriginType() == 2) {
                Discussion discussion = discussionDao.get(id);
                Example example = discussionReplyDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(DiscussionReply.DISCUSSION_ID, id);
                criteria.andEqualTo(DiscussionReply.IS_DELETED, 0);
                criteria.andEqualTo(DiscussionReply.ROLE_ID, 4);
                if (StringUtils.isEmpty(discussion.getStudyGroupSetId())) {
                    criteria.andEqualTo(DiscussionReply.CREATE_USER_ID, WebContext.getUserId());//不是组作业
                } else {
                    //获取小组Ids
                    List<StudyGroupUser> studyGroupUsers = studyGroupUserDao.find(StudyGroupUser.builder()
                            .studyGroupSetId(discussion.getStudyGroupSetId())
                            .userId(WebContext.getUserId())
                            .courseId(discussion.getCourseId()).build());
                    List<Long> ss = studyGroupUsers.stream().map(StudyGroupUser::getStudyGroupId).collect(Collectors.toList());
                    if (ss.size() > 0) {
                        criteria.andIn(DiscussionReply.CREATE_USER_ID, ss);
                    }else{
                        criteria.andEqualTo(DiscussionReply.CREATE_USER_ID, WebContext.getUserId());//没有在小组中
                    }
                }
                DiscussionReply reply = discussionReplyDao.findOne(example);
                map.put(o.getId(), reply != null);
            } else if (o.getOriginType() == 3) {
                Example example = quizRecordDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(QuizRecord.QUIZ_ID, id);
                criteria.andEqualTo(QuizRecord.CREATE_USER_ID, WebContext.getUserId());
                QuizRecord reply = quizRecordDao.findOne(example);
                map.put(o.getId(), reply != null);
            }
        }
       return map;
    }

    private List<AssignmentGroupItemVO> sortAssignmentItem(List<AssignmentGroupItemVO> originVo) {
        return originVo.stream()
                .sorted((a, b) -> {
                    AssignUser aAssignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), a.getOriginType(), a.getOriginId());
                    AssignUser bAssignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), b.getOriginType(), b.getOriginId());
                    Date acreateTime = a.getCreateTime();
                    Date bcreateTime = b.getCreateTime();
                    Date alimitTime = aAssignUser.getLimitTime();
                    Date blimitTime = bAssignUser.getLimitTime();
                    int res1=0;
                    int res2=0;
                    if(alimitTime!=null && blimitTime==null){
                        res1 = -1;
                    }
                    if(alimitTime==null && blimitTime!=null){
                        res1 = 1;
                    }
                    if(alimitTime!=null && blimitTime!=null){
                        res1 = blimitTime.compareTo(alimitTime);
                    }
                    if (res1 != 0) {
                        res2 = res1;
                    } else {
                        if(acreateTime!=null && bcreateTime==null){
                            res2 = -1;
                        }
                        if(acreateTime==null && bcreateTime!=null){
                            res2 = 1;
                        }
                        if(acreateTime!=null && bcreateTime!=null){
                            res2 = bcreateTime.compareTo(acreateTime);
                        }
                    }
                    return res2;
                }).collect(Collectors.toList());
    }
}
