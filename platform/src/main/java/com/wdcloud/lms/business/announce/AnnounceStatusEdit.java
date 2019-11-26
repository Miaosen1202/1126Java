package com.wdcloud.lms.business.announce;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.announce.dto.AnnounceDTO;
import com.wdcloud.lms.core.base.dao.AnnounceDao;
import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 讨论基本信息设置 评论开关|发布开关|置顶开关
 */
@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ANNOUNCE,
        functionName = Constants.FUNCTION_TYPE_COURSE_STATUS_TOGGLE
)
public class AnnounceStatusEdit implements ISelfDefinedEdit {
    @Autowired
    private AnnounceDao announceDao;

    /**
     * @api {post} /announce/statusToggle/edit 公告的评论开关
     * @apiName announce公告的评论开关
     * @apiGroup Announce
     * @apiParam {Number[]} ids 公告ID数组
     * @apiParam {Number=0,1} allowComment 评论开关
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} entity 讨论ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        AnnounceDTO dto = JSON.parseObject(dataEditInfo.beanJson, AnnounceDTO.class);
        Announce announce = Announce.builder().allowComment(dto.getAllowComment()).build();
        int total = announceDao.updateByExample(announce, dto.getIds());
        return new LinkedInfo(String.valueOf(total));
    }
}
