package com.wdcloud.lms.business.live;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.base.service.LiveService;
import com.wdcloud.lms.core.base.vo.LiveHistoryVO;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_LIVE,
        functionName = Constants.RESOURCE_TYPE_HISTORY
)
public class LiveHistoryQuery implements ISelfDefinedSearch<List<LiveHistoryVO>> {
    @Value("${liveType}")
    private String liveType;
    @Autowired
    private LiveFactory liveFactory;

    /**
     * @api {get} /live/history/query 直播用户统计
     * @apiName liveHistoryQuery
     * @apiGroup Live
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} liveId 直播ID
     * @apiParam {String} innerLiveId 第三方直播ID
     * @apiParam {String} [innerVodId] 第三方点播ID
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} entity 结果
     * @apiSuccess {Number} entity.id 用户id
     * @apiSuccess {String} entity.fullName 全称
     * @apiSuccess {String} entity.username 用户名
     * @apiSuccess {Number} entity.joinTime 加入时间
     * @apiSuccess {Number} entity.length 观看时长（秒）
     * @apiSuccess {Number} entity.status 观看状态：0：not joined；1：joined
     * @apiSuccess {Number} entity.reviewTimes 回看次数
     */
    @Override
    public List<LiveHistoryVO> search(Map<String, String> param) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        //0、初始化数据
        Map<Long, Integer> vodHisMap = new HashMap<>();
        //1、获取直播访问历史纪录
        List<LiveHistoryVO> liveHistorys = liveService.searchLiveHistory(param);
        if (null != param.get("innerVodId") && !"".equals(param.get("innerVodId"))) {
            //2、获取点播访问历史纪录
            List<LiveHistoryVO> vodHistorys = liveService.searchVodHistory(param);
            //3、获取点播访问历史纪录map
            for (LiveHistoryVO vodHistory: vodHistorys) {
                vodHisMap.put(vodHistory.getId(), vodHistory.getReviewTimes());
            }
        }
        //4、合并直播和点播历史纪录
        for (LiveHistoryVO liveHistory: liveHistorys) {
            Integer reviewTimes = vodHisMap.get(liveHistory.getId());
            if (null != reviewTimes) {
                liveHistory.setReviewTimes(reviewTimes);
            } else {
                liveHistory.setReviewTimes(0);
            }
        }
        return liveHistorys;
    }

}
