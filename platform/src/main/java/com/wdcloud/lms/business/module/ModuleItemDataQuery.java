package com.wdcloud.lms.business.module;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.enums.AssignStatusEnum;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.module.vo.ModuleItemVO;
import com.wdcloud.lms.core.base.dao.ExternalUrlDao;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.lms.core.base.model.ExternalUrl;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("JavaDoc")
@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE_ITEM)
public class ModuleItemDataQuery implements IDataQueryComponent<ModuleItem> {
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
    @Autowired
    private ExternalUrlDao externalUrlDao;


    /**
     * @api {get} /moduleItem/list 单元项列表
     * @apiName moduleItemList
     * @apiGroup Module
     * @apiParam {Number} moduleId 单元ID
     * @apiSuccess {Number} code 响应码
     * @apiSuccess {String} message 信息
     * @apiSuccess {Object} [entity] 单元项内容
     * @apiSuccess {Number} entity.id itemId
     * @apiSuccess {Number} entity.moduleId 所属单元
     * @apiSuccess {Number} entity.originType 类型，（1: 作业 2：讨论 3: 测验 4: 文件 13：文本标题 14：url）
     * @apiSuccess {Number} entity.originId 来源ID
     * @apiSuccess {Number} entity.seq 排序
     * @apiSuccess {Number} entity.status 状态
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} [entity.url] 外部链接返回--url
     * @apiSuccess {Number} [entity.score] 分值
     * @apiSuccess {Number} [entity.indentLevel] 缩进级别
     * @apiSuccess {Number=0,1} [entity.completeStatus] 完成状态，0：未完成；1：完成
     * @apiSuccess {Number} [entity.limitTime] 截至时间
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 200,
     * "entity": [
     * {
     * "id": 3,
     * "moduleId": 2,
     * "originId": 1,
     * "originType": 1,
     * "indentLevel": 1,
     * "score": 0,
     * "seq": 1,
     * "status": 0,
     * "title": "第一章作业",
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends ModuleItem> list(Map<String, String> param) {
        Long moduleId = Long.valueOf(param.get(Constants.PARAM_MODULE_ID));
        Module module = moduleDao.get(moduleId);
        if (Objects.isNull(module)) {
            return Lists.newArrayList();
        }
        List<ModuleItem> items = getModuleItems(moduleId);
        List<ModuleItemVO> vos = BeanUtil.beanCopyPropertiesForList(items, ModuleItemVO.class);
        List<ModuleItemVO> nvos = new ArrayList<>(vos.size());
        if (roleService.hasStudentRole()) {
            //获取单元项完成状态
            Map<Long, Integer> statusMap = moduleCompleteService.getModuleItemCompleteStatusByModuleAndUser(moduleId);
            for (ModuleItemVO o : vos) {//如果是学生需要查询记录是否分配给自己 或者item对所有人开放 或者对班级开放
                //过滤有效数据
                AssignStatusEnum assignStatusEnum = assignUserService.getAssignUserStatus(WebContext.getUserId(), o.getOriginType(), o.getOriginId());
                if (!AssignStatusEnum.NOAUTHORITY.equals(assignStatusEnum) || o.getOriginType() == 4 || o.getOriginType() == 13 || o.getOriginType() == 14) {
                    //获取完成状态
                    if (null == statusMap.get(o.getId())) {
                        o.setCompleteStatus(0);
                    } else {
                        o.setCompleteStatus(statusMap.get(o.getId()));
                    }
                    //获取limitTime
                    AssignUser assignUser = assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), o.getOriginType(), o.getOriginId());
                    if (null != assignUser) {
                        o.setLimitTime(assignUser.getLimitTime());
                    }
                    nvos.add(o);
                }
            }
            vos = nvos;
        } else {
            for (ModuleItemVO o : vos) {
                //获取url
                if (o.getOriginType() == 14) {
                    ExternalUrl externalUrl = externalUrlDao.get(o.getOriginId());
                    o.setUrl(externalUrl.getUrl());
                }
            }
        }
        return vos;
    }

    public List<ModuleItem> getModuleItems(Long moduleId) {
        if (roleService.hasTeacherOrTutorRole()) {
            return moduleItemDao.getModuleItemOrderBySeq(moduleId);
        }
        return moduleItemDao.getModuleItemOrderBySeq(moduleId, Boolean.TRUE);
    }

}
