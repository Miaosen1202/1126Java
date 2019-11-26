package com.wdcloud.lms.business.announce;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.vo.ContentLinkInfoVo;
import com.wdcloud.lms.core.base.dao.AnnounceDao;
import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ANNOUNCE,
        functionName = Constants.FUNCTION_TYPE_LINK_INFO
)
public class AnnounceLinkInfoQuery implements ISelfDefinedSearch<List<ContentLinkInfoVo>> {
    @Autowired
    private AnnounceDao announceDao;

    /**
     * @api {get} /announce/linkInfo/query 公告链接信息查询
     * @apiName announceLinkInfo
     * @apiGroup Common
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} [message] 消息描述
     * @apiSuccess {Object[]} entity 链接信息
     * @apiSuccess {Long} entity.courseId 课程
     * @apiSuccess {Long} entity.id 公告ID
     * @apiSuccess {Long} entity.name 名称
     */
    @Override
    public List<ContentLinkInfoVo> search(Map<String, String> condition) {
        if (!condition.containsKey("courseId")) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(condition.get("courseId"));
        List<ContentLinkInfoVo> result = new ArrayList<>();

        List<Announce> announces = announceDao.find(Announce.builder().courseId(courseId).build());
        for (Announce announce : announces) {
            ContentLinkInfoVo linkInfoVo = ContentLinkInfoVo.builder()
                    .courseId(courseId)
                    .id(announce.getId())
                    .name(announce.getTopic())
                    .build();
            result.add(linkInfoVo);
        }

        return result;
    }
}
