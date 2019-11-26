package com.wdcloud.lms.business.module;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.lms.core.base.vo.ModuleVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE)
public class ModuleDataQuery implements IDataQueryComponent<Module> {

    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {get} /module/list 单元列表
     * @apiName moduleList
     * @apiGroup Module
     * @apiParam {Number} courseId 课程ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 单元列表
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {String} entity.name 名称
     * @apiSuccess {Number} [entity.startTime] 开始时间(解锁时间)
     * @apiSuccess {Number=1,2,3} [entity.completeStrategy] 单元要求完成策略，1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个
     * @apiSuccess {Object[]} [entity.prerequisites] 前置单元
     * @apiSuccess {String} [entity.prerequisites.name] 前置单元名称
     * @apiSuccess {Number=0,1} entity.status 发布状态
     * @apiSuccess {Number=0,1} [entity.completeStatus] 完成状态，0：未完成；1：完成
     * @apiSuccessExample {String} json 返回值
     * {
     * "courseId": 1,
     * "id": 2,
     * "name": "开学第2课",
     * "seq": 2,
     * "startTime": 1544760039000,
     * "status": 0
     * }
     */
    @Override
    public List<? extends Module> list(Map<String, String> param) {
        //是否是学生
        List<ModuleVO> modules = Lists.newArrayList();
        Long courseId = Long.valueOf(param.get(Constants.PARAM_COURSE_ID));
        if (roleService.hasStudentRole()) {
            List<Module> oldList = moduleDao.getModuleOrderBySeq(courseId, Boolean.TRUE);
            List<ModuleVO> list = BeanUtil.beanCopyPropertiesForList(oldList, ModuleVO.class);
            //获取单元完成状态
            Map<Long, Integer> statusMap = moduleCompleteService.getModuleCompleteStatusByModuleAndUser(courseId);
            //modules 查看单元下有没有分配给当前人的资源
            for (ModuleVO o : list) {
                List<ModuleItem> items = moduleItemDao.find(ModuleItem.builder().moduleId(o.getId()).build());
                for (ModuleItem oo : items) {
                    AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(), oo.getOriginType(), oo.getOriginId());
                    if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum) || oo.getOriginType() == 4 || oo.getOriginType() == 13 || oo.getOriginType() == 14) {
                        //获取completeStatus
                        if (null == statusMap.get(o.getId())) {
                            o.setCompleteStatus(0);
                        } else {
                            o.setCompleteStatus(statusMap.get(o.getId()));
                        }
                        //有一个分配给自己了就行
                        modules.add(o);
                        break;
                    }
                }
            }
        } else {
            List<Module> oldModules = moduleDao.getModuleOrderBySeq(courseId);
            modules = BeanUtil.beanCopyPropertiesForList(oldModules, ModuleVO.class);
        }

        return modules;
    }
}
