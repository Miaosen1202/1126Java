package com.wdcloud.lms.business.live;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.base.service.LiveService;
import com.wdcloud.lms.business.live.dto.LiveEndDTO;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_LIVE,
        functionName = Constants.RESOURCE_TYPE_END
)
public class LiveEndEdit implements ISelfDefinedEdit {
    @Value("${liveType}")
    private String liveType;
    @Autowired
    private LiveFactory liveFactory;

    /**
     * @api {post} /live/end/edit 直播结束
     * @apiName liveEnd
     * @apiGroup Live
     * @apiParam {Number} id ID
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 结束ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        //1、参数解析
        LiveEndDTO dto = JSON.parseObject(dataEditInfo.beanJson, LiveEndDTO.class);
        //2、结束直播
        liveService.endLive(dto.getId());
        return new LinkedInfo(Code.OK.name);
    }
}
