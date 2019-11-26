package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.add.AddStrategy;
import com.wdcloud.lms.business.strategy.query.OriginData;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.business.strategy.update.UpdateStrategy;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Deprecated
@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE_ITEM2, description = "单元项")
public class ModuleItemEdit implements IDataEditComponent {

    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private StrategyFactory strategyFactory;

    @Override
    @Deprecated
    @ValidationParam(clazz = ModuleItemDTO.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        ModuleItemDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemDTO.class);
        Integer seq = moduleItemDao.ext().getMaxSeq(dto.getModuleId());

        BaseModuleItemDTO baseItem = dto.getBaseItemDTO();
        AddStrategy addStrategy = strategyFactory.getAddStrategy(OriginTypeEnum.typeOf(dto.getOriginType()));
        QueryStrategy queryStrategy = strategyFactory.getQueryStrategy(OriginTypeEnum.typeOf(dto.getOriginType()));

        OriginData originData;
        if(Objects.isNull(baseItem)){
            throw new ParamErrorException();
        }else{

            ModuleItem item;
            Long originId  = baseItem.getId();

            //id为null说明需要新增，否则是添加
            if(Objects.isNull(originId)){
                Module module = moduleDao.get(dto.getModuleId());
                //originId = addStrategy.add(baseItem, module.getCourseId());
                item = BeanUtil.beanCopyProperties(dto, ModuleItem.class);
                item.setOriginId(originId);
                item.setTitle(baseItem.getName());
                item.setStatus(Status.NO.getStatus());
            }else{
                originData = queryStrategy.query(originId);

                item = BeanUtil.beanCopyProperties(originData, ModuleItem.class);
                item.setIndentLevel(dto.getIndentLevel());
                item.setModuleId(dto.getModuleId());
            }

            seq = Objects.isNull(seq) ? 1 : ++seq;
            item.setSeq(seq);
            moduleItemDao.save(item);
        }

        return new LinkedInfo(Code.OK.name);
    }

    @Override
    @Deprecated
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long id = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        //删除module
        moduleItemDao.delete(id);
        return new LinkedInfo(Code.OK.name);
    }


    @Override
    @Deprecated
    @ValidationParam(clazz = ModuleItemDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        ModuleItemDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleItemDTO.class);

        ModuleItem moduleItem = moduleItemDao.get(dto.getId());
        moduleItem.setIndentLevel(dto.getIndentLevel());
        moduleItem.setTitle(dto.getBaseItemDTO().getName());
        moduleItemDao.updateIncludeNull(moduleItem);

        dto.getBaseItemDTO().setId(moduleItem.getOriginId());
        UpdateStrategy addStrategy = strategyFactory.getUpdateStrategy(OriginTypeEnum.typeOf(moduleItem.getOriginType()));
        //只更新名称、url
        addStrategy.update(dto.getBaseItemDTO());

        return new LinkedInfo(Code.OK.name);
    }
}

