package com.wdcloud.lms.base.service;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.base.vo.*;
import com.wdcloud.lms.business.live.dto.LiveDTO;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.LiveHistoryVO;
import com.wdcloud.lms.core.base.vo.LiveListVO;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.utils.httpClient.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"JavadocReference", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@Service
public class GenseeLiveService implements LiveService {

    private static final String loginName = "admin@wdcloud.com";
    private static final String password = "21218cca77804d2ba1922c33e0151105";
    private static final String sec = "true";
    private static final String userPassword = "12345678";

    // 临时组织者加入口令、嘉宾加入口令
    public static final String ZSHD_TEMP_ORGANIZER_TOKEN = "wddemosOrg";
    public static final String ZSHD_TEMP_PANELISTTOKEN_TOKEN = "wddemosPan";

    public static final String DEFAULT_GENSEE_URL = "http://wdcloud.gensee.com/integration/site";
    //创建用户
    private static final String CREATE_USER_URL = "/webcast/user/created";
    //创建直播
    private static final String CREATE_Live_URL = "/webcast/created";
    //修改直播配置
    private static final String UPDATE_LIVE_URL = "/webcast/update";
    //同步点播
    private static final String SYNC_VOD = "/webcast/vod/sync";
    //同步直播访问历史记录
    private static final String SYNC_LIVE_HISTORY ="/webcast/export/history";
    //同步点播访问历史记录
    private static final String SYNC_VOD_HISTORY ="/webcast/export/vod/history";

    @Autowired
    private UserDao userDao;
    @Autowired
    private GenseeLiveDao genseeLiveDao;
    @Autowired
    private GenseeVodDao genseeVodDao;
    @Autowired
    private GenseeUserLiveHistoryDao genseeUserLiveHistoryDao;
    @Autowired
    private GenseeUserVodHistoryDao genseeUserVodHistoryDao;

    @Override
    public LiveTypeEnum support() {
        return LiveTypeEnum.GENSEE;
    }

    /**
     * 创建gensee直播
     */
    @Override
    public Long createLive(LiveDTO dto) {
        Long instructor = dto.getInstructor();
        Example example = genseeLiveDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(GenseeLive.COURSE_ID, dto.getCourseId());
        criteria.andEqualTo(GenseeLive.TITLE, dto.getTitle());
        criteria.andEqualTo(GenseeLive.IS_DELETED, 0);
        List<GenseeLive> genseeLives = genseeLiveDao.find(example);
        if(genseeLives.size() > 0) {
            throw new BaseException("live.add.live.exists", dto.getTitle());
        }
        GenseeLiveVO live = createLiveInner(dto);
        GenseeLive genseeLive = GenseeLive.builder()
                .courseId(dto.getCourseId())
                .description(dto.getDescription())
                .genseeAttendeeAShortJoinUrl(live.getAttendeeAShortJoinUrl())
                .genseeAttendeeJoinUrl(live.getAttendeeJoinUrl())
                .genseeAttendeeToken(live.getAttendeeToken())
                .genseeLiveId(live.getId())
                .genseeNumber(live.getNumber())
                .genseeOrganizerJoinUrl(live.getOrganizerJoinUrl())
                .genseeOrganizerToken(live.getOrganizerToken())
                .genseePanelistJoinUrl(live.getPanelistJoinUrl())
                .genseePanelistToken(live.getPanelistToken())
                .instructor(instructor)
                .startTime(new Date(dto.getStartTime()))
                .endTime(DateUtil.minuteOperation(new Date(dto.getStartTime()), 120))
                .title(dto.getTitle())
                .isDeleted(0)
                .build();
        genseeLiveDao.save(genseeLive);
        return genseeLive.getId();
    }

    private GenseeLiveVO createLiveInner(LiveDTO dto) {
        String subject = dto.getTitle();
        String startTime = dto.getStartTime().toString();
        Map<String, String> map = Maps.newHashMap();
        map.put("subject", subject);
        map.put("startTime", startTime);
        map.put("loginName", loginName);
        map.put("password", password);
        map.put("sec", sec);
        map.put("organizerToken", ZSHD_TEMP_ORGANIZER_TOKEN);
        map.put("panelistToken", ZSHD_TEMP_PANELISTTOKEN_TOKEN);
        String message = "";
        try {
            String result = HttpClientUtils.sentPost(map, DEFAULT_GENSEE_URL + CREATE_Live_URL, "UTF-8");
            GenseeLiveVO genseeLiveVO = JSON.parseObject(result, GenseeLiveVO.class);
            if ("0".equals(genseeLiveVO.getCode())) {
                return genseeLiveVO;
            } else {
                message = genseeLiveVO.getMessage();
            }
        } catch (Exception e) {
            throw new BaseException("live.add.createLive.failed");
        }
        throw new BaseException(message);
    }

    /**
     * 编辑gensee直播
     */
    @Override
    public void updateLive(LiveDTO dto) {
        GenseeLive genseeLive = genseeLiveDao.get(dto.getId());
        if (genseeLive == null) {
            throw new BaseException("live.update.live.nonExists");
        }
        updateLiveInner(dto, genseeLive);
        genseeLive.setTitle(dto.getTitle());
        genseeLive.setInstructor(dto.getInstructor());
        genseeLive.setDescription(dto.getDescription());
        Date date = new Date(dto.getStartTime());
        genseeLive.setStartTime(date);
        genseeLive.setEndTime(DateUtil.minuteOperation(date, 120));
        genseeLiveDao.update(genseeLive);
    }

    private Boolean updateLiveInner(LiveDTO dto, GenseeLive genseeLive) {
        String id = genseeLive.getGenseeLiveId();
        String subject = dto.getTitle();
        String startTime = dto.getStartTime().toString();
        Map<String, String> map = Maps.newHashMap();
        map.put("id", id);
        map.put("subject", subject);
        map.put("startTime", startTime);
        map.put("loginName", loginName);
        map.put("password", password);
        map.put("sec", sec);
        map.put("organizerToken", ZSHD_TEMP_ORGANIZER_TOKEN);
        map.put("panelistToken", ZSHD_TEMP_PANELISTTOKEN_TOKEN);
        String message = "";
        try {
            String result = HttpClientUtils.sentPost(map, DEFAULT_GENSEE_URL + UPDATE_LIVE_URL, "UTF-8");
            GenseeLiveVO genseeLiveVO = JSON.parseObject(result, GenseeLiveVO.class);
            if ("0".equals(genseeLiveVO.getCode())) {
                return true;
            } else {
                message = genseeLiveVO.getMessage();
            }
        } catch (Exception e) {
            throw new BaseException("live.update.updateLive.failed");
        }
        throw new BaseException(message);
    }

    /**
     * 删除gensee直播
     */
    @Override
    public void deleteLive(Long id) {
        GenseeLive genseeLive = genseeLiveDao.get(id);
        if (genseeLive == null) {
            throw new BaseException("live.delete.live.nonExists");
        }
        if (genseeLive.getStartTime().before(new Date())) {
            throw new BaseException("live.delete.startTime.out");
        }
        genseeLive.setIsDeleted(1);
        genseeLiveDao.update(genseeLive);
    }

    /**
     * 结束gensee直播
     */
    @Override
    public void endLive(Long id) {
        GenseeLive genseeLive = genseeLiveDao.get(id);
        if (genseeLive == null) {
            throw new BaseException("live.end.live.nonExists");
        }
        genseeLive.setEndTime(new Date());
        genseeLiveDao.update(genseeLive);
    }

    /**
     * 同步gensee点播
     */
    @Override
    public void syncVod() {
        //获取点播数据
        Date date = DateUtil.daysOperation(new Date(), -1);
//        Date date = new Date();
        String dateStr = DateUtil.format(date, "yyyy-MM-dd");
        String startTime = dateStr+" 00:00:00";
        String endTime = dateStr+" 23:59:59";
        List<GenseeVodInnerVO> genseeVodList = syncVodInner(startTime, endTime);
        //同步到db
        if (null != genseeVodList && genseeVodList.size() > 0){
            for (GenseeVodInnerVO genseeVod : genseeVodList) {
                String webcastId = genseeVod.getWebcastId();
                String vodId = genseeVod.getId();
                String password = genseeVod.getPassword();
                Example example = genseeVodDao.getExample();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo(GenseeVod.GENSEE_WEBCAST_ID, webcastId);
                criteria.andEqualTo(GenseeVod.GENSEE_PASSWORD, password);
                List<GenseeVod> genseeVods = genseeVodDao.find(example);
                if(null != vodId && !"".equals(vodId) && genseeVods.size()==0){
                    GenseeVod vod = new GenseeVod();
                    vod.setGenseeId(vodId);
                    vod.setGenseeSubject(genseeVod.getSubject());
                    vod.setGenseePassword(genseeVod.getPassword());
                    vod.setGenseeDescription(genseeVod.getDescription());
                    vod.setGenseeCreatedTime(new Date(genseeVod.getCreatedTime()));
                    vod.setGenseeAttendeeJoinUrl(genseeVod.getAttendeeJoinUrl());
                    vod.setGenseeWebcastId(genseeVod.getWebcastId());
                    vod.setGenseeScreenshot(genseeVod.getScreenshot());
                    vod.setGenseeCreator(genseeVod.getCreator());
                    vod.setGenseeNumber(genseeVod.getNumber());
                    vod.setGenseeRecordId(genseeVod.getRecordId());
                    vod.setGenseeRecordStartTime(new Date(genseeVod.getRecordStartTime()));
                    vod.setGenseeRecordEndTime(new Date(genseeVod.getRecordEndTime()));
                    vod.setGenseeGrType(genseeVod.getGrType());
                    vod.setGenseeDuration(genseeVod.getDuration());
                    vod.setGenseeConvertResult(genseeVod.getConvertResult());
                    vod.setGenseeSpeakerInfo(genseeVod.getSpeakerInfo());
                    genseeVodDao.insert(vod);
                }

            }

        }
    }

    private List<GenseeVodInnerVO> syncVodInner(String startTime, String endTime) {
        Map<String, String> map = Maps.newHashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageNo", "1");
        map.put("loginName", loginName);
        map.put("password", password);
        map.put("sec", sec);
        String message = "";
        try {
            String result = HttpClientUtils.sentPost(map, DEFAULT_GENSEE_URL + SYNC_VOD, "UTF-8");
            GenseeVodVO genseeVodVO = JSON.parseObject(result, GenseeVodVO.class);
            if ("0".equals(genseeVodVO.getCode())) {
                return genseeVodVO.getList();
            } else {
                message = genseeVodVO.getMessage();
            }
        } catch (Exception e) {
            throw new BaseException("live.syncVod.syncVod.failed");
        }
        throw new BaseException(message);
    }

    /**
     * 同步gensee直播访问记录
     */
    @Override
    public void syncLiveHistory() {
       //1、查出需要同步直播访问记录的直播id列表
        Map<String, String> map = new HashMap<>();
        String start = DateUtil.getFormatDate(DateUtil.daysOperation(new Date(), -1), "yyyy-MM-dd") + " 00:00:00";
        String end = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd") + " 00:00:00";
//        String start = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd") + " 00:00:00";
//        String end = DateUtil.getFormatDate(DateUtil.daysOperation(new Date(), 1), "yyyy-MM-dd") + " 00:00:00";
        map.put("start", start);
        map.put("end", end);
        log.debug("start:" + start);
        log.debug("end:" + end);
        List<GenseeLive> genseeLives = genseeLiveDao.findGenseeLive(map);
        List<String> liveIds = genseeLives.stream().map(GenseeLive::getGenseeLiveId).collect(Collectors.toList());
        //2、获取直播访问记录
        for (String liveId: liveIds) {
            List<GenseeLiveHistoryInnerVO> historys = syncLiveHistoryInner(liveId);
            if (null == historys || historys.size() == 0) {
                continue;
            }
            // 3、同步到db
            for (GenseeLiveHistoryInnerVO history: historys) {
                GenseeUserLiveHistory liveHistory = new GenseeUserLiveHistory();
                liveHistory.setGenseeWebcastId(liveId);
                liveHistory.setGenseeNickname(history.getNickname());
                liveHistory.setGenseeJoinTime(new Date(history.getJoinTime()));
                liveHistory.setGenseeLeaveTime(new Date(history.getLeaveTime()));
                liveHistory.setGenseeIp(history.getIp());
                liveHistory.setGenseeUid(history.getUid());
                liveHistory.setGenseeArea(history.getArea());
                liveHistory.setGenseeMobile(history.getMobile());
                liveHistory.setGenseeCompany(history.getCompany());
                liveHistory.setGenseeJoinType(history.getJoinType());
                genseeUserLiveHistoryDao.insert(liveHistory);
            }
        }
    }

    private List<GenseeLiveHistoryInnerVO> syncLiveHistoryInner(String liveId) {
        Map<String, String> map = Maps.newHashMap();
        map.put("webcastId", liveId);
        map.put("loginName", loginName);
        map.put("password", password);
        map.put("sec", sec);
        String message = "";
        try {
            String result = HttpClientUtils.sentPost(map, DEFAULT_GENSEE_URL + SYNC_LIVE_HISTORY, "UTF-8");
            GenseeLiveHistoryVO genseeLiveHistoryVO = JSON.parseObject(result, GenseeLiveHistoryVO.class);
            if ("0".equals(genseeLiveHistoryVO.getCode())) {
                return genseeLiveHistoryVO.getList();
            } else {
                message = genseeLiveHistoryVO.getMessage();
            }
        } catch (Exception e) {
            throw new BaseException("live.syncLiveHistory.syncLiveHistory.failed");
        }
        throw new BaseException(message);
    }

    /**
     * 同步gensee点播访问记录
     */
    @Override
    public void syncVodHistory() {
        //1、获取点播访问记录
        List<GenseeVodHistoryInnerVO> historys = syncVodHistoryInner();
        //2、同步db
        if (null == historys || historys.size() == 0) {
            return;
        }
        for (GenseeVodHistoryInnerVO history: historys) {
            GenseeUserVodHistory vodHistory = new GenseeUserVodHistory();
            vodHistory.setGenseeVodid(history.getVodId());
            vodHistory.setGenseeUid(history.getUid());
            vodHistory.setGenseeStartTime(new Date(history.getStartTime()));
            vodHistory.setGenseeLeaveTime(new Date(history.getLeaveTime()));
            vodHistory.setGenseeName(history.getName());
            vodHistory.setGenseeDuration(history.getDuration());
            vodHistory.setGenseeIp(history.getIp());
            vodHistory.setGenseeArea(history.getArea());
            vodHistory.setGenseeDevice(history.getDevice());
            genseeUserVodHistoryDao.insert(vodHistory);
        }
    }

    private List<GenseeVodHistoryInnerVO> syncVodHistoryInner() {
        Map<String, String> map = Maps.newHashMap();
        Date date = DateUtil.daysOperation(new Date(), -1);
//        Date date = new Date();
        String dateStr = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        map.put("date", dateStr);
        map.put("loginName", loginName);
        map.put("password", password);
        map.put("sec", sec);
        String message = "";
        try {
            String result = HttpClientUtils.sentPost(map, DEFAULT_GENSEE_URL + SYNC_VOD_HISTORY, "UTF-8");
            GenseeVodHistoryVO genseeVodHistoryVO = JSON.parseObject(result, GenseeVodHistoryVO.class);
            if ("0".equals(genseeVodHistoryVO.getCode())) {
                return genseeVodHistoryVO.getList();
            } else {
                message = genseeVodHistoryVO.getMessage();
            }
        } catch (Exception e) {
            throw new BaseException("live.syncVodHistory.syncVodHistory.failed");
        }
        throw new BaseException(message);
    }

    /**
     * 统计直播访问数据
     */
    @Override
    public List<LiveHistoryVO> searchLiveHistory(Map<String, String> param) {
        //0、初始化数据
        Map<Long, List<LiveHistoryVO>> liveHistoryMap = new HashMap<Long, List<LiveHistoryVO>>();
        List<LiveHistoryVO> res = Lists.newArrayList();
        //1、获取db统计数据
        Map<String, Object> map = Maps.newHashMap();
        map.put("liveId", Long.parseLong(param.get("liveId")));
        map.put("courseId", Long.parseLong(param.get(Constants.PARAM_COURSE_ID)));
        map.put("genseeLiveId", param.get("innerLiveId"));
        List<LiveHistoryVO> liveHistorys = genseeUserLiveHistoryDao.searchLiveHistory(map);
        //2、合并相同用户
        for (LiveHistoryVO liveHistory: liveHistorys) {
            Long id = liveHistory.getId();
            List<LiveHistoryVO> list;
            if (null == liveHistoryMap.get(id)) {
                list = Lists.newArrayList();
            } else {
                list = liveHistoryMap.get(id);
            }
            list.add(liveHistory);
            liveHistoryMap.put(id, list);
        }
        for (Long id: liveHistoryMap.keySet()) {
            List<LiveHistoryVO> list = liveHistoryMap.get(id);
            List<LiveHistoryVO> newList = list.stream()
                    .sorted((a, b) -> {
                        Date aJoinTime = a.getJoinTime();
                        Date bJoinTime = b.getJoinTime();
                        int result = 0;
                        if (aJoinTime != null && bJoinTime == null) {
                            return 1;
                        }
                        if (aJoinTime == null && bJoinTime != null) {
                            return -1;
                        }
                        if (aJoinTime != null && bJoinTime != null) {
                            result = aJoinTime.compareTo(bJoinTime);
                        }
                        return result;
                    }).collect(Collectors.toList());
            LiveHistoryVO history = newList.get(0);
            //3、计算length
            long length = 0L;
            for (LiveHistoryVO h: newList) {
                Date joinTime = h.getJoinTime();
                Date leaveTime = h.getLeaveTime();
                if (null != joinTime && null != leaveTime) {
                    long len = (leaveTime.getTime() - joinTime.getTime()) / 1000;
                    length += len;
                }
            }
            history.setLength(length);
            //4、计算status
            Long historyId = history.getHistoryId();
            if (null == historyId || "".equals(historyId)) {
                history.setStatus(0);
            } else {
                history.setStatus(1);
            }
            res.add(history);
        }
        return res;
    }

    @Override
    public List<LiveHistoryVO> searchVodHistory(Map<String, String> param) {
        //1、获取db统计数据
        Map<String, Object> map = Maps.newHashMap();
        map.put("liveId", Long.parseLong(param.get("liveId")));
        map.put("courseId", Long.parseLong(param.get(Constants.PARAM_COURSE_ID)));
        map.put("genseeLiveId", param.get("innerLiveId"));
        map.put("genseeVodId", param.get("innerVodId"));
        List<LiveHistoryVO> liveHistorys = genseeUserLiveHistoryDao.searchVodHistory(map);
        return liveHistorys;
    }

    @Override
    public List<LiveListVO> findLiveByCon(Map<String, Object> map) {
        return genseeLiveDao.findLiveByCon(map);
    }

    @Override
    public List<LiveListVO> findNotStartedLiveByCon(Map<String, Object> map) {
        return genseeLiveDao.findNotStartedLiveByCon(map);
    }

    @Override
    public List<LiveListVO> findInProgressLiveByCon(Map<String, Object> map) {
        return genseeLiveDao.findInProgressLiveByCon(map);
    }

    @Override
    public List<LiveListVO> findFinishedLiveByCon(Map<String, Object> map) {
        return genseeLiveDao.findFinishedLiveByCon(map);
    }

    @Override
    public List<LiveListVO> findLiveDetail(Map<String, Object> map) {
        return genseeLiveDao.findLiveDetail(map);
    }
}
