package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.module.dto.ModuleItemDTO2;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.query.OriginData;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE_ITEM, description = "单元项")
public class ModuleItemEdit2 implements IDataEditComponent {

    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private ModuleCompleteService moduleCompleteService;
    @Autowired
    private ModuleDao moduleDao;

    /**
     * @api {post} /moduleItem/add 单元项添加
     * @apiDescription 单元项添加
     * @apiName moduleItemAdd
     * @apiGroup Module
     * @apiParam {Number} moduleId 单元ID
     * @apiParam {Number} originType 来源类型（1: 作业, 2: 测验，4: 文件 4: 页码 5：讨论）
     * @apiParam {Number[]} originIds 来源ID
     * @apiParam {Number} [indentLevel] 缩进级别
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增单元项ID
     */
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        ModuleItemDTO2 dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemDTO2.class);
        Integer seq = moduleItemDao.ext().getMaxSeq(dto.getModuleId());
        final Long[] originIds = dto.getOriginIds();//目前只有一个 不能同时添加多个
        QueryStrategy queryStrategy = strategyFactory.getQueryStrategy(OriginTypeEnum.typeOf(dto.getOriginType()));
        seq = Objects.isNull(seq) ? 1 : ++seq;
        OriginData originData = queryStrategy.query(originIds[0]);
        ModuleItem item = BeanUtil.beanCopyProperties(originData, ModuleItem.class);
        item.setIndentLevel(dto.getIndentLevel());
        item.setModuleId(dto.getModuleId());
        item.setSeq(seq);
        moduleItemDao.save(item);
        //添加单元项进度
        Module module = moduleDao.get(item.getModuleId());
        moduleCompleteService.addAssignmentToModule(item.getId(), module.getCourseId());
        return new LinkedInfo(item.getId().toString());
    }

    /**
     * @api {post} /moduleItem/deletes 单元项删除
     * @apiDescription 单元项删除
     * @apiName moduleItemDeletes
     * @apiGroup Module
     * @apiParam {Number} id 内容ID
     * @apiParamExample 请求示例：
     * 1
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long id = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        //删除进度
        moduleCompleteService.deleteModuleItem(id);
        //删除module
        moduleItemDao.delete(id);
        return new LinkedInfo(Code.OK.name);
    }
}