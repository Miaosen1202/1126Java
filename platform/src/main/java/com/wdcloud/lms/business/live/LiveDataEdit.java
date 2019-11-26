package com.wdcloud.lms.business.live;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.LiveService;
import com.wdcloud.lms.business.live.dto.LiveDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ResourceInfo(name = Constants.RESOURCE_TYPE_LIVE, description = "直播编辑")
public class LiveDataEdit implements IDataEditComponent {
    @Value("${liveType}")
    private String liveType;
    @Autowired
    private LiveFactory liveFactory;
    @Autowired
    private AssignService assignService;

    /**
     * @api {post} /live/add 直播添加
     * @apiName liveAdd
     * @apiGroup Live
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title 标题
     * @apiParam {String} [description] 描述
     * @apiParam {Number} instructor 主讲人ID
     * @apiParam {Number} startTime 直播开始时间
     * @apiParam {Object[]} assign 分配
     * @apiParam {Number} [assign.assignId] 分配目标
     * @apiParam {Number=1,2} assign.assignType  分配类型，1: 所有， 2：section(班级)
     * @apiParam {Number} assign.startTime  直播开始日期
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = LiveDTO.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        //1、参数解析
        LiveDTO dto = JSON.parseObject(dataEditInfo.beanJson, LiveDTO.class);
        //2、创建直播
        Long liveId = liveService.createLive(dto);
        //3、创建分配
        List<Assign> newAssigns = dto.getAssign();
        assignService.batchSave(newAssigns, dto.getCourseId(), OriginTypeEnum.LIVE, liveId);
        return new LinkedInfo(liveId.toString());
    }

    /**
     * @api {post} /live/modify 直播修改
     * @apiName liveModify
     * @apiGroup Live
     * @apiParam {Number} id ID
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title 标题
     * @apiParam {String} [description] 描述
     * @apiParam {Number} instructor 主讲人ID
     * @apiParam {Long} startTime 直播开始时间
     * @apiParam {Object[]} assign 分配
     * @apiParam {Number} [assign.assignId] 分配目标
     * @apiParam {Number=1,2} assign.assignType  分配类型，1: 所有， 2：section(班级)
     * @apiParam {Number} assign.startTime  直播开始日期
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 编辑ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = LiveDTO.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        //1、参数解析
        LiveDTO dto = JSON.parseObject(dataEditInfo.beanJson, LiveDTO.class);
        //2、编辑直播
        liveService.updateLive(dto);
        //3、分配
        List<Assign> newAssigns = dto.getAssign();
        assignService.batchSave(newAssigns, dto.getCourseId(), OriginTypeEnum.LIVE, dto.getId());
        return new LinkedInfo(dto.getId().toString());
    }

    /**
     * @api {post} /live/deletes 直播删除
     * @apiName liveDeletes
     * @apiGroup Live
     * @apiParam {Number} id ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 删除ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = LiveDTO.class)
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        LiveService liveService = liveFactory.getLiveService(LiveTypeEnum.liveTypeOf(liveType));
        //1、参数解析
        LiveDTO dto = JSON.parseObject(dataEditInfo.beanJson, LiveDTO.class);
        //2、删除直播
        liveService.deleteLive(dto.getId());
        return new LinkedInfo(Code.OK.name);
    }
}
