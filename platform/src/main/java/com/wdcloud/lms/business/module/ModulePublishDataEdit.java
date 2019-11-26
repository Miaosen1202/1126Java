package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE,
        functionName = Constants.FUNCTION_TYPE_PUBLISH_STATUS
)
public class ModulePublishDataEdit implements ISelfDefinedEdit {
    @Autowired
    private ModuleDao moduleDao;

    /**
     * @api {post} /module/status/edit 单元发布/取消
     * @apiName moduleStatusEdit
     * @apiGroup Module
     * @apiParam {Number} id id
     * @apiParam {Number} status 状态 1:发布 0:未发布
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity success
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        Module module = JSON.parseObject(dataEditInfo.beanJson, Module.class);
        moduleDao.update(module);
        return new LinkedInfo(Code.OK.name);
    }
}
