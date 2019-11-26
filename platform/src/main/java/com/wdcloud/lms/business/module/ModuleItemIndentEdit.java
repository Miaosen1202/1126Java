package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.module.dto.ModuleItemIndentDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.update.UpdateContext;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_MODULE_ITEM,
        functionName = Constants.FUNCTION_TYPE_INDENT
)
public class ModuleItemIndentEdit implements ISelfDefinedEdit {
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private StrategyFactory strategyFactory;
    /**
     * @api {post} /moduleItem/indent/edit 单元项缩进/编辑
     * @apiDescription 单元项缩进/编辑
     * @apiName moduleItemIndent
     * @apiGroup Module
     *
     * @apiParam {Number} id 单元项id
     * @apiParam {String} [name] 单元项名称
     * @apiParam {String} [url] 外部链接参数--web链接
     * @apiParam {Number} indent 缩进量
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动项ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        final ModuleItemIndentDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemIndentDTO.class);
        final ModuleItem item = moduleItemDao.get(dto.getId());
        item.setIndentLevel(dto.getIndent());
        moduleItemDao.update(item);
        if (StringUtil.isNotEmpty(dto.getName())) {
            final Integer originType = item.getOriginType();
            UpdateContext context = new UpdateContext(strategyFactory.getUpdateStrategy(OriginTypeEnum.typeOf(originType)));
            if (originType.equals(OriginTypeEnum.EXTERNAL_URL.getType()) && null != dto.getUrl() && !"".equals(dto.getUrl())) {
                context.execute(item.getOriginId(), dto.getName(), dto.getUrl());
            } else {
                context.execute(item.getOriginId(), dto.getName());
            }
        }
        return new LinkedInfo(Code.OK.name);
    }
}
