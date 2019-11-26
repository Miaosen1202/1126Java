package com.wdcloud.lms.business.module;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.module.dto.ModuleDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModulePrerequisiteDao;
import com.wdcloud.lms.core.base.dao.ModuleRequirementDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModulePrerequisite;
import com.wdcloud.lms.core.base.model.ModuleRequirement;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE)
public class ModuleDataEdit implements IDataEditComponent {
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ModulePrerequisiteDao modulePrerequisiteDao;
    @Autowired
    private ModuleRequirementDao moduleRequirementDao;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /module/add 单元添加
     * @apiDescription 单元(Module)添加
     * @apiName moduleAdd
     * @apiGroup Module
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} name 名称
     * @apiParam {Number} [startTime] 开始时间(锁定到该时间开始，null表示不锁定,使用毫秒值）
     * @apiParam {Number=1,2,3} [completeStrategy] 完成策略（1：完成所有，2：按顺序完成所有，3：完成任意一个）
     * @apiParam {Object[]} prerequisites 先决条件列表
     * @apiParam {Number} prerequisites.preModuleId 前置单元ID
     * @apiParam {Object[]} requirements 要求列表
     * @apiParam {Number} requirements.moduleItemId 单元项
     * @apiParam {Number=1,2,3,4,5} requirements.strategy 完成策略，1：查看，2：标记为完成，3：提交，4：至少得分，5：参与
     * @apiParam {Number} [requirements.leastScore] 至少得分，strategy=4时填写
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "1",
     * "message": "success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    @AccessLimit
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        ModuleDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleDTO.class);
        Module module = BeanUtil.beanCopyProperties(dto, Module.class);
        //排序
        final Integer seq = moduleDao.ext().getMaxSeq(dto.getCourseId());
        module.setSeq(Objects.isNull(seq) ? 1 : seq + 1);
        moduleDao.save(module);
        savePrerequisites(dto.getPrerequisites(), module.getId());
        saveRequirements(dto.getRequirements(), module.getId());
        //添加单元进度
        moduleCompleteService.addModule(module);
        return new LinkedInfo(Code.OK.name);
    }


    /**
     * @api {post} /module/modify 单元修改
     * @apiDescription 单元(Module)修改
     * @apiName moduleModify
     * @apiGroup Module
     * @apiParam {Number} id ID
     * @apiParam {String} name 名称
     * @apiParam {Number} [startTime] 开始时间(锁定到该时间开始）
     * @apiParam {Number=1,2,3} [completeStrategy] 完成策略（1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个）
     * @apiParam {Object[]} prerequisites 先决条件列表
     * @apiParam {Number} prerequisites.preModuleId 前置单元ID
     * @apiParam {Object[]} requirements 要求列表
     * @apiParam {Number} requirements.moduleItemId 单元项
     * @apiParam {Number=1,2,3,4,5} requirements.require 完成要求，1：查看，2：标记为完成，3：提交，4：至少得分，5：参与
     * @apiParam {Number} [requirements.leastScore] 至少得分，strategy=4 时填写
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "1",
     * "message": "success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        ModuleDTO dto = JSON.parseObject(dataEditInfo.beanJson, ModuleDTO.class);
        Module module = BeanUtil.beanCopyProperties(dto, Module.class);
        moduleDao.update(module);
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /module/deletes 单元删除
     * @apiDescription 单元删除
     * @apiName moduleDeletes
     * @apiGroup Module
     * @apiParam {json} data ID列表
     * @apiParamExample {json} 请求示例：
     * 1
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 删除单元ID列表
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "1",
     * "message": "[1,2]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long id = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        //删除进度
        moduleCompleteService.deleteModule(id);
        //删除module
        moduleDao.delete(id);
        //删除关联项
        modulePrerequisiteDao.deleteByField(ModulePrerequisite.builder().moduleId(id).preModuleId(id).build());
        return new LinkedInfo(id.toString());
    }

    private void saveRequirements(List<ModuleRequirement> requirements, Long moduleId) {
        if (CollectionUtil.isNotNullAndEmpty(requirements)) {
            //完成条件
            for (ModuleRequirement requirement : requirements) {
                requirement.setModuleId(moduleId);
                moduleRequirementDao.save(requirement);
            }
        }
    }

    private void savePrerequisites(List<ModulePrerequisite> prerequisites, Long moduleId) {
        if (CollectionUtil.isNotNullAndEmpty(prerequisites)) {
            //前置条件
            for (ModulePrerequisite prerequisite : prerequisites) {
                prerequisite.setModuleId(moduleId);
                modulePrerequisiteDao.save(prerequisite);
            }
        }
    }
}
