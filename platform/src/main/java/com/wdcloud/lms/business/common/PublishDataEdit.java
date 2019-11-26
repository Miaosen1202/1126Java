package com.wdcloud.lms.business.common;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.common.dto.UpdatePublishStatusDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_PUBLISH,
        functionName = Constants.FUNCTION_TYPE_PUBLISH_STATUS
)
public class PublishDataEdit implements ISelfDefinedEdit {
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /publish/status/edit 内容发布/取消发布公用接口
     * @apiName publishStatusEdit
     * @apiGroup Common
     * @apiParam {Number} originId 资源Id
     * @apiParam {Number} status 状态 1:发布 0:未发布
     * @apiParam {Number} originType 资源类型 1: 作业, 2：讨论 3: 测验，4: 文件 5: 页面 13:标题 14：网址
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity success
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        UpdatePublishStatusDTO dto = JSON.parseObject(dataEditInfo.beanJson, UpdatePublishStatusDTO.class);
        UpdateContext context = new UpdateContext(strategyFactory.getUpdateStrategy(OriginTypeEnum.typeOf(dto.getOriginType())));
        context.updatePublishStatus(dto.getOriginId(), dto.getStatus());
        //1、等待0.5秒，单元小项触发器执行
        try {
            Thread.sleep(500); //1000 毫秒，也就是1秒.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        //2、获取单元小项
        Example example = moduleItemDao.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ModuleItem.ORIGIN_TYPE, dto.getOriginType());
        criteria.andEqualTo(ModuleItem.ORIGIN_ID, dto.getOriginId());
        List<ModuleItem> moduleItems = moduleItemDao.find(example);
        //3、更新单元进度状态
        if (moduleItems.size() > 0) {
            moduleCompleteService.updateModuleUserStatusByModule(moduleItems.get(0).getModuleId());
        }
        return new LinkedInfo(Code.OK.name);
    }
}
