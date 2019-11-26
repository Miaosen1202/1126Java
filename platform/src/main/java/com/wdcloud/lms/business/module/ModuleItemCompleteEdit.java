package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.module.dto.ModuleItemCompleteDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE_ITEM,
        functionName = Constants.FUNCTION_TYPE_COMPLETE
)
public class ModuleItemCompleteEdit implements ISelfDefinedEdit {

    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /moduleItem/complete/edit 单元项完成
     * @apiDescription 单元项完成
     * @apiName moduleItemCompleteEdit
     * @apiGroup Module
     * @apiParam {Number} originId 单元项内容ID
     * @apiParam {Number} originType 单元项类型（4: 文件, 14: 外部链接）
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     */
    @Override
    @AccessLimit
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        //0、参数解析
        ModuleItemCompleteDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemCompleteDTO.class);
        //1、完成进度
        moduleCompleteService.completeAssignment(dto.getOriginId(), dto.getOriginType());
        return new LinkedInfo(Code.OK.name);
    }
}
