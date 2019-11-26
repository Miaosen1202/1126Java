package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.module.dto.ModuleMoveDTO;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class ModuleMoveEdit implements ISelfDefinedEdit {

    @Autowired
    private ModuleDao moduleDao;

    /**
     * @api {post} /module/move/edit 单元移动
     * @apiDescription 单元移动
     * @apiName moduleMove
     * @apiGroup Module
     * @apiParam {Number[]} moduleIds 排序列表
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动单元ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        final ModuleMoveDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleMoveDTO.class);
        final long[] ids = dto.getModuleIds();
        final Module module = new Module();
        for (int i = 0; i < ids.length; i++) {
            module.setId(ids[i]);
            module.setSeq(i);
            moduleDao.update(module);
        }
        return new LinkedInfo(Code.OK.name);
    }
}
