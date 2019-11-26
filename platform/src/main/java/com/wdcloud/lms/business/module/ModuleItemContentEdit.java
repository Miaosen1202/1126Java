package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemDTO2;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.add.AddStrategy;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE_ITEM,
        functionName = Constants.FUNCTION_TYPE_CONTENT
)
public class ModuleItemContentEdit implements ISelfDefinedEdit {

    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private ModuleItemEdit2 moduleItemEdit;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /moduleItem/content/edit 单元项内容添加
     * @apiDescription 单元项内容添加
     * @apiName moduleItemContentEdit
     * @apiGroup Module
     * @apiParam {Number} courseId 公共参数--课程ID
     * @apiParam {Number} moduleId 公共参数--单元ID
     * @apiParam {Number} originType 公共参数--任务类型（1: 作业, 2: 讨论, 3: 测验, 4: 文件, 13: 文本标题, 14: 外部链接）
     * @apiParam {Number} [indentLevel] 公共参数--缩进级别
     * @apiParam {String} title 公共参数--标题
     * @apiParam {Number} [assignmentGroupId] 公共参数: 作业参数--作业组ID(必填);讨论参数--作业小组ID (isGrade=1时必填);测验参数--作业小组ID（type=2）
     *
     * @apiParam {Number=0,1} isGrade 讨论参数--是否评分 分配为所有班级时可选
     *
     * @apiParam {Number=1,2} type 测验参数--类型，1：练习测验(practice quiz)、2：评分测验(graded quiz)
     *
     * @apiParam {String} fileId 文件参数--文件ID
     * @apiParam {Number} parentDirectoryId 文件参数--上级文件夹ID
     *
     * @apiParam {String} url 外部链接参数--web链接
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 单元项ID
     */
    @Override
    @AccessLimit
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        //0、参数解析
        ModuleItemContentDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemContentDTO.class);
        if (null == dto.getCourseId() || null == dto.getModuleId() || null == dto.getOriginType() || null == dto.getTitle() || "".equals(dto.getTitle())) {
            throw new ParamErrorException();
        }
        //1、获取任务类型对象
        AddStrategy addStrategy = strategyFactory.getAddStrategy(OriginTypeEnum.typeOf(dto.getOriginType()));
        //2、调用添加接口
        Long id = addStrategy.add(dto);
        //3、添加单元项
        Long[] originIds = new Long[]{id};
        ModuleItemDTO2 moduleItemDTO = new ModuleItemDTO2();
        moduleItemDTO.setModuleId(dto.getModuleId());
        moduleItemDTO.setOriginType(dto.getOriginType());
        moduleItemDTO.setOriginIds(originIds);
        moduleItemDTO.setIndentLevel(dto.getIndentLevel());
        String beanJson = JSON.toJSONString(moduleItemDTO);
        DataEditInfo dataEditInfo2 = new DataEditInfo(beanJson);
        LinkedInfo add = moduleItemEdit.add(dataEditInfo2);
        //4、添加单元项进度
        Long moduleItemId = Long.parseLong(add.masterId);
        moduleCompleteService.addAssignmentToModule(moduleItemId, dto.getCourseId());
        return add;
    }
}
