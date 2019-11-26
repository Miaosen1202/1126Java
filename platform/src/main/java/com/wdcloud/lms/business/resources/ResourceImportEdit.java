package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.resources.dto.ResourceImportDTO;
import com.wdcloud.lms.business.resources.dto.ResourceImportUpdateDTO;
import com.wdcloud.lms.business.resources.enums.ResourceModuleTypeEnum;
import com.wdcloud.lms.business.resources.enums.ResourceLogOperationTypeEnum;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.add.AddStrategy;
import com.wdcloud.lms.business.strategy.update.UpdateStrategy;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @author  WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_IMPORT, description = "资源导入")
public class ResourceImportEdit implements IDataEditComponent {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResourceImportDao resourceImportDao;
    @Autowired
    private ResourceUpdateDao resourceUpdateDao;
    @Autowired
    private ResourceVersionMessageDao resourceVersionMessageDao;
    @Autowired
    private ResourceImportGenerationDao resourceImportGenerationDao;

    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceCommonService resourceCommonService;

    @Autowired
    private StrategyFactory strategyFactory;

    /**
     * @api {post} /resImport/add 导入资源
     * @apiDescription 导入资源
     * @apiName resImportAdd
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源id
     * @apiParam {Number[]} courseIds 课程id
     * @apiExample {json} 请求示例:
     * {
     *     "resourceId":53,
     *     "courseIds":[218]
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {String} [entity] 新增ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *      "code": 200,
     *      "message": "11"
     *  }
     */
    @AccessLimit
    @ResourceLog(module = ResourceModuleTypeEnum.IMPORT, operation = ResourceLogOperationTypeEnum.Add)
    @ValidationParam(clazz = ResourceImportUpdateDTO.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        roleValidateService.onlyTeacherValid();

        final ResourceImportDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceImportDTO.class);
        Integer originType = resourceDao.getOriginTypeById(dto.getResourceId());
        String beanJson = resourceCommonService.getResourceData(dto.getResourceId());

        ResourceUpdate resourceUpdate = resourceUpdateDao.getByResourceId(dto.getResourceId());
        resourceUpdate.setImportCount(resourceUpdate.getImportCount() + dto.getCourseIds().size());
        resourceUpdateDao.update(resourceUpdate);

        addByResource(originType, beanJson, dto.getResourceId(), dto.getCourseIds());
        handleVersionMessage(dto.getResourceId());
        return new LinkedInfo(String.valueOf(dto.getCourseIds().size()));
    }

    /**
     * @api {post} /resImport/modify 更新资源
     * @apiDescription 更新资源
     * @apiName resImportModify
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源id
     * @apiParam {Number[]} courseIds 课程id
     * @apiExample {json} 请求示例:
     * {
     * 	"resourceId":93,
     * 	"courseIds":[218]
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {String} [entity] 新增ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *      "code": 200,
     *      "message": "11"
     *  }
     */
    @AccessLimit
    @ResourceLog(module = ResourceModuleTypeEnum.IMPORT, operation = ResourceLogOperationTypeEnum.UPDATE)
    @ValidationParam(clazz = ResourceImportUpdateDTO.class)
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        roleValidateService.onlyTeacherValid();

        final ResourceImportDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceImportDTO.class);
        Long resourceId = dto.getResourceId();
        List<Long> courseIds = dto.getCourseIds();
        Integer originType = resourceDao.getOriginTypeById(resourceId);
        String beanJson = resourceCommonService.getResourceData(resourceId);

        UpdateStrategy updateStrategy = strategyFactory.getUpdateStrategy(OriginTypeEnum.typeOf(originType));
        List<CourseImportGenerationDTO> courseImportAssignmentGroupDTOs = updateStrategy.updateByResource(beanJson, resourceId, courseIds);

        Long versionId = resourceUpdateDao.getVersionIdByResourceId(dto.getResourceId());
        ResourceImport resourceImport = null;

        for( Long courseId : courseIds){
            resourceImport = ResourceImport.builder().resourceId(dto.getResourceId())
                    .userId(WebContext.getUserId()).courseId(courseId).build();
            resourceImport = resourceImportDao.findOne(resourceImport);
            resourceImport.setVersionId(versionId);
            resourceImportDao.update(resourceImport);
        }
        for(CourseImportGenerationDTO temp : courseImportAssignmentGroupDTOs){
            ResourceImportGeneration resourceImportGeneration = ResourceImportGeneration.builder()
                    .resourceImportId(resourceImport.getId()).newId(temp.getNewId())
                    .originId(temp.getOriginId()).originType(temp.getOriginType()).build();
            resourceImportGenerationDao.save(resourceImportGeneration);
        }

        handleVersionMessage(dto.getResourceId());
        return new LinkedInfo(String.valueOf(dto.getCourseIds().size()));
    }

    private void addByResource(Integer originType, String beanJson, Long resourceId, List<Long> courseIds){
        Long versionId = resourceUpdateDao.getVersionIdByResourceId(resourceId);
        AddStrategy addStrategy = strategyFactory.getAddStrategy(OriginTypeEnum.typeOf(originType));
        List<CourseImportGenerationDTO> courseImportAssignmentGroupDTOs = addStrategy.addByResource(beanJson,
                resourceId, courseIds);

        ResourceImport resourceImport;
        ResourceImport originResourceImport;
        for(CourseImportGenerationDTO temp : courseImportAssignmentGroupDTOs){
            resourceImport = ResourceImport.builder().resourceId(resourceId)
                    .versionId(versionId).userId(WebContext.getUserId()).courseId(temp.getCourseId()).build();
            originResourceImport = resourceImportDao.findOne(resourceImport);
            if(Objects.isNull(originResourceImport)){
                resourceImportDao.save(resourceImport);
            }else{
                resourceImport = originResourceImport;
            }

            ResourceImportGeneration resourceImportGeneration = ResourceImportGeneration.builder()
                    .resourceImportId(resourceImport.getId()).newId(temp.getNewId())
                    .originId(temp.getOriginId()).originType(temp.getOriginType()).build();
            resourceImportGenerationDao.save(resourceImportGeneration);
        }
    }

    private void handleVersionMessage(Long resourceId){
        ResourceVersionMessage versionMessage = ResourceVersionMessage.builder()
                .resourceId(resourceId).userId(WebContext.getUserId()).build();
        ResourceVersionMessage sourceVersionMessage = resourceVersionMessageDao.findOne(versionMessage);
        if(Objects.isNull(sourceVersionMessage)){
            versionMessage.setIsRemind(Status.NO.getStatus());
            resourceVersionMessageDao.save(versionMessage);
        }else{
            sourceVersionMessage.setIsRemind(Status.NO.getStatus());
            resourceVersionMessageDao.update(sourceVersionMessage);
        }
    }
}
