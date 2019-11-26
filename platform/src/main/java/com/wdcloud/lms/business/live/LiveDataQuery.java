package com.wdcloud.lms.business.live;

import com.google.common.collect.Lists;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.LiveService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.vo.LiveListVO;
import com.wdcloud.lms.core.base.vo.SectionVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_LIVE)
public class LiveDataQuery implements IDataQueryComponent<LiveListVO> {

    @Value("${liveType}")
    private String liveType;
    @Autowired
    private LiveFactory liveFactory;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private SectionDao sectionDao;

    /**
     * @api {get} /live/get 直播详情
     * @apiName liveGet
     * @apiGroup Live
     * @apiParam {Number} id 直播ID
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 结果
     * @apiSuccess {String} entity.id 直播id
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} entity.status 直播状态：Not Started,Finished,In Progress
     * @apiSuccess {String} entity.instructorId 主讲人ID
     * @apiSuccess {String} entity.instructor 主讲人
     * @apiSuccess {String} entity.instructorUrl 主讲人头像
     * @apiSuccess {Number} entity.startTime 开始时间
     * @apiSuccess {Number} entity.endTime 结束时间
     * @apiSuccess {String} entity.description 描述
     * @apiSuccess {String} entity.liveId 第三方直播id
     * @apiSuccess {String} entity.panelistJoinUrl 嘉宾加入URL(教师进入链接)
     * @apiSuccess {String} entity.organizerToken 组织者口令
     * @apiSuccess {String} entity.attendeeJoinUrl 普通参加者加入URL(带token)(学生进入链接)
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} entity.attendeeJoinUrlOfVod 点播URL
     * @apiSuccess {String} entity.vodId 第三方直播点播id
     * @apiSuccess {String} entity.password  点播观看口令
     * @apiSuccess {Object[]} entity.assigns 分配
     * @apiSuccess {Number} entity.assigns.assignType 分配类型，1: 所有， 2：section(班级)
     * @apiSuccess {Number} [entity.assigns.assignId] 分配目标ID
     * @apiSuccess {Object[]} [entity.sectionList] 会话数组 [{班级名称：用户个数}]
     * @apiSuccess {Number} [entity.sectionList.sectionId] 会话班级ID
     * @apiSuccess {String} [entity.sectionList.sectionName] 会话班级名称
     * @apiSuccess {Number} [entity.sectionList.userCount]  会话班级用户个数
     * @apiSuccess {Number} [entity.userCount] 用户总数，所有班级时才有此字段
     */
    @Override
    public LiveListVO find(String id) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PARAM_ID, id);
        List<LiveListVO> liveDetails = liveService.findLiveDetail(map);
        if (liveDetails.size() < 1) {
            throw new BaseException("live.find.liveDetail.nonExists");
        }
        LiveListVO res = liveDetails.get(0);
        res.setNickname(WebContext.getUser().getUsername());
        return buildSections(liveDetails.get(0).getCourseId(), getAssign(getHaveStatusLive(res)));
    }

    private LiveListVO getHaveStatusLive(LiveListVO o){
            Date startTime = o.getStartTime();
        Date endTime = o.getEndTime();
        if (startTime.after(new Date())) {
            o.setStatus("Not Started");
        } else if (startTime.before(new Date()) && endTime.after(new Date())) {
            o.setStatus("In Progress");
        } else {
            o.setStatus("Finished");
        }
        return o;
    }

    private LiveListVO buildSections(Long courseId, LiveListVO liveDetail) {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.PARAM_COURSE_ID, courseId.toString());
        List<SectionVo> sectionList = sectionDao.findSectionListByCourseId(map);
        Map<Long, SectionVo> sectionMap = sectionList.stream().collect(Collectors.toMap(SectionVo::getSectionId, a -> a));
        Long userCount = sectionList.stream().map(SectionVo::getUserCount).reduce(Long::sum).get();
        liveDetail.getAssigns().forEach(assign -> {
            if (assign.getAssignType().equals(AssignTypeEnum.ALL.getType())) {
                liveDetail.setUserCount(userCount);
            } else if (assign.getAssignType().equals(AssignTypeEnum.SECTION.getType())) {
                liveDetail.getSectionList().add(sectionMap.get(assign.getAssignId()));
            }
        });
        return liveDetail;
    }

    private LiveListVO getAssign(LiveListVO o) {
        List<Assign> assigns = assignDao.find(Assign.builder().courseId(o.getCourseId())
                .originType(OriginTypeEnum.LIVE.getType())
                .originId(o.getId())
                .build());
        o.setAssigns(assigns);
        return o;
    }

    /**
     * @api {get} /live/list 直播列表
     * @apiName liveList
     * @apiGroup Live
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} [instructor] 主讲人ID
     * @apiParam {String} [title] 标题模糊
     * @apiParam {String} entity.status 直播状态：All,Not Started,Finished,In Progress
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 结果
     * @apiSuccess {String} entity.id 直播id
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} entity.status 直播状态：Not Started,Finished,In Progress
     * @apiSuccess {String} entity.instructor 主讲人
     * @apiSuccess {String} entity.instructorUrl 主讲人头像
     * @apiSuccess {Number} entity.startTime 开始时间
     * @apiSuccess {Number} entity.endTime 结束时间
     * @apiSuccess {String} entity.liveId 第三方直播id
     * @apiSuccess {String} entity.panelistJoinUrl 嘉宾加入URL(教师进入链接)
     * @apiSuccess {String} entity.organizerToken 组织者口令
     * @apiSuccess {String} entity.attendeeJoinUrl 普通参加者加入URL(带token)(学生进入链接)
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} entity.attendeeJoinUrlOfVod 点播URL
     * @apiSuccess {String} entity.vodId 第三方直播点播id
     * @apiSuccess {String} entity.password  点播观看口令
     */
    @Override
    public List<LiveListVO> list(Map<String, String> param) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        //1、参数解析
        Long courseId = Long.parseLong(param.get(Constants.PARAM_COURSE_ID));
        String instructor = param.get("instructor");
        String title = param.get("title");
        String status = param.get("status");
        //2、获取nickname
        String nickname = WebContext.getUser().getUsername();
        //3、分类
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PARAM_COURSE_ID, courseId);
        if (instructor != null && !"".equals(instructor)) {
            map.put("instructor", Long.parseLong(instructor));
        }
        if (title != null && !"".equals(title)) {
            map.put("title", title);
        }
        //3.1、all
        if ("All".equals(status)) {
            List<LiveListVO> liveListVOs = Lists.newArrayList();
            List<LiveListVO> liveList = liveService.findLiveByCon(map);
            //2.1.1、求status
            for (LiveListVO o: liveList) {
                o = getHaveStatusLive(o);
                liveListVOs.add(o);
            }
            return getRes(liveListVOs, nickname);
        }
        //3.2、not started
        else if ("Not Started".equals(status)) {
            return getRes(liveService.findNotStartedLiveByCon(map), nickname);
        }
        //3.3、finished
        else if ("Finished".equals(status)) {
            return getRes(liveService.findFinishedLiveByCon(map), nickname);
        }
        //3.4、in progress
        else {
            return getRes(liveService.findInProgressLiveByCon(map), nickname);
        }
    }

    private List<LiveListVO> getRes(List<LiveListVO> items, String nickname) {
        List<LiveListVO> vos = new ArrayList<>(items.size());
        for (LiveListVO o : items) {
            LiveListVO vo = BeanUtil.beanCopyProperties(o, LiveListVO.class);
            vo.setNickname(nickname);
            if (roleService.hasStudentRole()) {
                AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(), 12, o.getId());
                if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum)) {
                    vos.add(vo);
                }
            } else {
                vos.add(vo);
            }
        }
        return vos;
    }

}
