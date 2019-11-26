package com.wdcloud.lms.business.module;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.module.vo.ModuleItemDetailVO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("JavaDoc")
@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE_ITEM_CONTENT)
public class ModuleItemContentQuery implements IDataQueryComponent<ModuleItemDetailVO> {
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private ModuleItemDataQuery moduleItemDataQuery;

    /**
     * @api {get} /moduleItemContent/get 单元项内容详情
     * @apiName moduleItemContentGet
     * @apiDescription 单元项内容详情
     * @apiGroup Module
     * @apiParam {Number} data 单元项ID
     * @apiSuccess {Number} code 响应码
     * @apiSuccess {String} message 信息
     * @apiSuccess {Object} [entity]
     * @apiSuccess {Object} entity.moduleItem 当前单元项信息
     * @apiSuccess {Number} entity.moduleItem.id 当前单元项ID
     * @apiSuccess {Number} entity.moduleItem.originType 当前单元项类型, 1: 作业 2：讨论 3: 测验 4: 文件 13：文本标题 14：url
     * @apiSuccess {Number} entity.moduleItem.originId 当前单元项内容ID
     *
     * @apiSuccess {String} entity.moduleItem.fileId 文件类型单元项--sys_user_file表ID
     * @apiSuccess {String} entity.moduleItem.fileName 文件类型单元项--文件名称
     * @apiSuccess {String} entity.moduleItem.fileType 文件类型单元项--文件类型
     * @apiSuccess {Number} entity.moduleItem.fileSize 文件类型单元项--文件大小，单位byte
     * @apiSuccess {String} entity.moduleItem.fileUrl 文件类型单元项--文件URL
     *
     * @apiSuccess {Number} entity.moduleItem.externalUrlId web链接类型单元项--cos_module_external_url表id
     * @apiSuccess {String} entity.moduleItem.pageName web链接类型单元项--页面名称
     * @apiSuccess {String} entity.moduleItem.url web链接类型单元项--web链接
     *
     * @apiSuccess {Object} [entity.previous] 上一个单元项信息
     * @apiSuccess {Number} entity.previous.originType 上一个单元项类型，1: 作业 2：讨论 3: 测验 4: 文件 13：文本标题 14：url
     * @apiSuccess {Number} entity.previous.id 上一个单元项ID
     *
     * @apiSuccess {Object} [entity.next] 下一个单元项信息
     * @apiSuccess {Number} entity.next.originType 下一个单元项类型，1: 作业 2：讨论 3: 测验 4: 文件 13：文本标题 14：url
     * @apiSuccess {Number} entity.next.id 下一个单元项ID
     */
    @Override
    public ModuleItemDetailVO find(String id){
        //0、参数解析
        Long moduleItemId = Long.valueOf(id);
        ModuleItemDetailVO moduleItemDetailVO = new ModuleItemDetailVO();
        //1、获取当前项内容
        ModuleItem currentModuleItem = moduleItemDao.get(moduleItemId);
        QueryStrategy queryStrategy = strategyFactory.getQueryStrategy(OriginTypeEnum.typeOf(currentModuleItem.getOriginType()));
        Object currentItemDetail = queryStrategy.queryDetail(moduleItemId);
        moduleItemDetailVO.setModuleItem(currentItemDetail);
        //2、获取上一个单元项内容、获取下一个单元项内容
        Map<String, String> map = new HashMap<>();
        Long moduleId = currentModuleItem.getModuleId();
        map.put("moduleId", moduleId.toString());
        List<? extends ModuleItem> oldList = moduleItemDataQuery.list(map);
        List<? extends ModuleItem> list = oldList.stream().filter(o -> !((ModuleItem) o).getOriginType().equals(OriginTypeEnum.TEXT_HEADER.getType())).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i ++) {
            if (moduleItemId.equals(list.get(i).getId())){
                if (i < list.size() - 1) {
                    moduleItemDetailVO.setNext(list.get(i + 1));
                }
                if (i > 0) {
                    moduleItemDetailVO.setPrevious(list.get(i - 1));
                }
            }
        }
        return moduleItemDetailVO;
    }

    /**
     * 查找前一个单元项
     * @author 王亚荣
     * @param currentModuleId 当前单元项所在模块id
     * @param existModuleItems 存在的单元项
     * @return
     */
    private ModuleItem queryPre(Long currentModuleId, List<ModuleItem> existModuleItems) {
        ModuleItem previousItem = null;

        if (ListUtils.isNotEmpty(existModuleItems)) {
            for (int i = existModuleItems.size() - 1; i >= 0; i--) {
                ModuleItem o = existModuleItems.get(i);
                if (roleService.hasStudentRole()) {
                    AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(),
                            o.getOriginType(), o.getOriginId());
                    if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum) || o.getOriginType() == 4) {
                        return o;
                    }
                }else{
                    return o;
                }
            }
        }

        if (ListUtils.isEmpty(existModuleItems) || Objects.isNull(previousItem)) {
            Module currentModule = moduleDao.get(currentModuleId);
            int moduleSeq = currentModule.getSeq();
            if (moduleSeq == 1) {
                return previousItem;
            } else {
                Module module = Module.builder().seq(currentModule.getSeq() - 1)
                        .courseId(currentModule.getCourseId()).build();
                currentModule = moduleDao.findOne(module);
                currentModuleId = currentModule.getId();
                existModuleItems = moduleItemDataQuery.getModuleItems(currentModule.getId());
                previousItem = queryPre(currentModuleId, existModuleItems);
            }
        }

        return previousItem;
    }

    /**
     * 后一个单元项
     * @author 王亚荣
     * @param currentModuleId 当前单元项所在模块id
     * @param existModuleItems 存在的单元项
     * @return
     */
    private ModuleItem queryNext(Long currentModuleId, List<ModuleItem> existModuleItems){
        ModuleItem nextItem = null;

        if (ListUtils.isNotEmpty(existModuleItems)) {
            for (int i = 0; i < existModuleItems.size(); i++) {
                ModuleItem o = existModuleItems.get(i);
                if (roleService.hasStudentRole()) {
                    AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(),
                            o.getOriginType(), o.getOriginId());
                    if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum) || o.getOriginType() == 4) {
                        return o;
                    }
                }else{
                    return o;
                }
            }
        }

        if (ListUtils.isEmpty(existModuleItems) || Objects.isNull(nextItem)) {
            Module currentModule = moduleDao.get(currentModuleId);
            int moduleSeq = currentModule.getSeq();
            if (moduleSeq == 1) {
                return nextItem;
            } else {
                Module module = Module.builder().seq(currentModule.getSeq() + 1)
                        .courseId(currentModule.getCourseId()).build();
                currentModule = moduleDao.findOne(module);
                if(Objects.isNull(currentModule)){
                    return nextItem;
                }else{
                    currentModuleId = currentModule.getId();
                    existModuleItems = moduleItemDataQuery.getModuleItems(currentModule.getId());
                    nextItem = queryNext(currentModuleId, existModuleItems);
                }
            }
        }

        return nextItem;
    }
}
