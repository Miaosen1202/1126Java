package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.resources.dto.ResourceImportDTO;
import com.wdcloud.lms.business.resources.dto.ResourceShareDTO;
import com.wdcloud.lms.business.resources.enums.ResourceLogOperationTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.ResourceLogStatusEnum;
import com.wdcloud.lms.core.base.enums.ResourceOperationTypeEnum;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.StringUtil;
import org.apache.ibatis.builder.ParameterExpression;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author WangYaRong
 */
@Component
@Aspect
public class ResourceLogAspect {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResourceUpdateDao resourceUpdateDao;
    @Autowired
    private ResourceUpdateLogDao resourceUpdateLogDao;
    @Autowired
    private ResourceImportLogDao resourceImportLogDao;
    @Autowired
    private ResourceAdminOperationLogDao resourceAdminOperationLogDao;

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceLogService logService;

    /**
     * 正在进行时的日志
     * @param joinPoint
     */
    @Before("@annotation(com.wdcloud.lms.business.resources.ResourceLog)")
    public void processingLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ResourceLog resourceLog = signature.getMethod().getAnnotation(ResourceLog.class);
        DataEditInfo dataEditInfo = (DataEditInfo) joinPoint.getArgs()[0];
        Integer operationType = ResourceOperationTypeEnum.UPDATE.getType();
        Long operatorId = WebContext.getUserId();

        switch (resourceLog.module()){
            case UPDATE:
                if(resourceLog.operation().equals(ResourceLogOperationTypeEnum.Add)){
                    final ResourceShareDTO shareDTO = JSON.parseObject(dataEditInfo.beanJson, ResourceShareDTO.class);
                    Long resourceId = shareDTO.getUpdateId();

                    if(Objects.equals(null, resourceId)){
                        operationType = ResourceOperationTypeEnum.FIRST_SHARE.getType();
                    }
                    ResourceUpdateLog resourceUpdateLog = ResourceUpdateLog.builder()
                            .status(ResourceLogStatusEnum.PROCESSING.getStatus()).operationType(operationType)
                            .resourceName(shareDTO.getName()).shareRange(shareDTO.getShareRange())
                            .originType(shareDTO.getOriginType()).userId(operatorId).build();
                    logService.save(resourceUpdateLog);
                }else if(resourceLog.operation().equals(ResourceLogOperationTypeEnum.DELETE)){
                    if(roleService.isAdmin()){
                        Long resourceId = JSON.parseArray(dataEditInfo.beanJson, Long.class).get(0);
                        ResourceUpdate resourceUpdate = resourceUpdateDao.getByResourceId(resourceId);
                        Resource resource = resourceDao.get(resourceUpdate.getResourceId());
                        ResourceAdminOperationLog adminOperationLog = ResourceAdminOperationLog.builder()
                                .authorId(resource.getAuthorId()).operationType(ResourceOperationTypeEnum.REMOVE.getType())
                                .resourceName(resourceUpdate.getName()).shareRange(resourceUpdate.getShareRange())
                                .originType(resource.getOriginType()).adminUserName(WebContext.getUser().getFullName()).build();
                        resourceAdminOperationLogDao.save(adminOperationLog);
                    }
                }
                break;
            case IMPORT:
                final ResourceImportDTO importDTO = JSON.parseObject(dataEditInfo.beanJson, ResourceImportDTO.class);
                ResourceUpdate resourceUpdate = resourceUpdateDao.getByResourceId(importDTO.getResourceId());
                Resource resource = resourceDao.get(importDTO.getResourceId());

                ResourceLogOperationTypeEnum operation = resourceLog.operation();
                if(operation.equals(ResourceLogOperationTypeEnum.Add)){
                    operationType = ResourceOperationTypeEnum.IMPORT.getType();
                }else if(operation.equals(ResourceLogOperationTypeEnum.UPDATE)){
                    operationType = ResourceOperationTypeEnum.UPDATE.getType();
                }else{
                    throw new BaseException();
                }

                for(Long courseId : importDTO.getCourseIds()){
                    ResourceImportedLog resourceImportedLog = ResourceImportedLog.builder().resourceName(resourceUpdate.getName())
                            .courseName(courseDao.get(courseId).getName()).operationType(operationType).userId(operatorId)
                            .status(ResourceLogStatusEnum.PROCESSING.getStatus()).originType(resource.getOriginType()).build();
                    logService.save(resourceImportedLog);
                }
                break;
             default:
                 break;
        }
    }

    /**
     *  成功后的日志
     * @param joinPoint
     */
    @AfterReturning("@annotation(com.wdcloud.lms.business.resources.ResourceLog)")
    public void successfulLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ResourceLog resourceLog = signature.getMethod().getAnnotation(ResourceLog.class);
        DataEditInfo dataEditInfo = (DataEditInfo) joinPoint.getArgs()[0];
        Integer status = ResourceLogStatusEnum.PROCESSING.getStatus();
        Long userId = WebContext.getUserId();

        switch (resourceLog.module()){
            case UPDATE:
                ResourceLogOperationTypeEnum operation = resourceLog.operation();

                if(operation.equals(ResourceLogOperationTypeEnum.Add)){
                    ResourceUpdateLog resourceUpdateLog = resourceUpdateLogDao.getLatestByStatusAndUserId(status, userId);
                    resourceUpdateLog.setStatus(ResourceLogStatusEnum.DONE.getStatus());
                    resourceUpdateLogDao.update(resourceUpdateLog);
                }else if(operation.equals(ResourceLogOperationTypeEnum.EDIT)){
                    if(roleService.isAdmin()){
                        final ResourceShareDTO shareDTO = JSON.parseObject(dataEditInfo.beanJson, ResourceShareDTO.class);
                        Resource resource = resourceDao.get(shareDTO.getId());
                        ResourceAdminOperationLog adminOperationLog = ResourceAdminOperationLog.builder()
                                .authorId(resource.getAuthorId()).operationType(ResourceOperationTypeEnum.EDIT.getType())
                                .resourceName(shareDTO.getName()).shareRange(shareDTO.getShareRange())
                                .originType(resource.getOriginType()).adminUserName(WebContext.getUser().getFullName()).build();
                        resourceAdminOperationLogDao.save(adminOperationLog);
                    }

                }
                break;
            case IMPORT:
                ResourceImportedLog resourceImportedLog = ResourceImportedLog.builder()
                        .status(ResourceLogStatusEnum.PROCESSING.getStatus()).build();
                List<ResourceImportedLog> resourceImportedLogs = resourceImportLogDao.find(resourceImportedLog);

                for(ResourceImportedLog temp : resourceImportedLogs){
                    temp.setStatus(ResourceLogStatusEnum.DONE.getStatus());
                    resourceImportLogDao.update(temp);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 失败后的日志
     * @param joinPoint
     */
    @AfterThrowing("@annotation(com.wdcloud.lms.business.resources.ResourceLog)")
    public void failedLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ResourceLog resourceLog = signature.getMethod().getAnnotation(ResourceLog.class);
        Integer status = ResourceLogStatusEnum.PROCESSING.getStatus();
        Long userId = WebContext.getUserId();

        switch (resourceLog.module()){
            case UPDATE:
                if(resourceLog.operation().equals(ResourceLogOperationTypeEnum.Add)){
                    ResourceUpdateLog resourceUpdateLog = resourceUpdateLogDao.getLatestByStatusAndUserId(status, userId);
                    resourceUpdateLog.setStatus(ResourceLogStatusEnum.FAILED.getStatus());
                    logService.update(resourceUpdateLog);
                }
                break;
            case IMPORT:
                List<ResourceImportedLog> resourceImportedLogs = resourceImportLogDao.getLatestByStatusAndUserId(
                        status, userId);

                for(ResourceImportedLog temp : resourceImportedLogs){
                    temp.setStatus(ResourceLogStatusEnum.FAILED.getStatus());
                    logService.update(temp);
                }
                break;
            default:
                break;
        }
    }
}
