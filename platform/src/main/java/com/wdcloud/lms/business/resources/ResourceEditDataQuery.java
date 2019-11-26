package com.wdcloud.lms.business.resources;

import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.ResourceOperationTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceUpdate;
import com.wdcloud.lms.core.base.model.ResourceUpdateLog;
import com.wdcloud.lms.core.base.model.ResourceVersion;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_EDIT_SEARCH, description = "资源编辑查询")
public class ResourceEditDataQuery implements IDataQueryComponent<ResourceVO> {

    @Autowired
    private ResourceUpdateDao resourceUpdateDao;
    @Autowired
    private ResourceVersionDao resourceVersionDao;

    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceCommonService resourceCommonService;


    /**
     * @api {get} /resEditSearch/get 资源编辑详情
     * @apiDescription 资源详情
     * @apiName resEditSearchGet
     * @apiGroup resource
     *
     * @apiParam {String} data 资源ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {Object} [entity] 资源信息
     * @apiSuccess {Number} entity.id 资源ID
     * @apiSuccess {String} entity.name 资源名称
     * @apiSuccess {Number=1,2,3,15} entity.originType 类型，1：作业，2：讨论，3：测验，15：课程
     * @apiSuccess {String} entity.licence 版权1：Public Domain，2：CC-Attribution，3：CC-Attribution ShareAlike，
     * 4：CC-Attribution NoDerivs，5：CC-Attribution NonCommercial，6：CC-Attribution NonCommercial ShareAlike，
     * 7：CC-Attribution NonCommercial NoDerivs
     * @apiSuccess {String} entity.thumbnailUrl 缩略图url
     * @apiSuccess {Number} entity.gradeStart 年级开始
     * @apiSuccess {Number} entity.gradeEnd 年级结束
     * @apiSuccess {String} entity.description 简介
     * @apiSuccess {String} entity.versionNotes 版本信息
     * @apiSuccess {String[]} entity.tags 标签
     * @apiSuccess {String} entity.shareRange 分享范围
     * @apiSuccess {String} entity.operationType 操作类型，5：第一次分享，4：更新
     * @apiSuccessExample {json} 响应示例:
     * {
     *     "code": 200,
     *     "entity": {
     *         "description": "第一次分享+进行了修改",
     *         "grade": 2560,
     *         "gradeEnd": 11,
     *         "gradeStart": 9,
     *         "id": 89,
     *         "licence": 1,
     *         "name": "111111",
     *         "operationType": 4,
     *         "originType": 1,
     *         "shareRange": 1,
     *         "tags": [],
     *         "thumbnailUrl": "group1/M00/00/3F/wKgFFF1NCHiAPc-NAAA_IHLV9_0617.png",
     *         "versionNotes": "hasNewNote"
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public ResourceVO find(String id) {
        roleValidateService.teacherAndAdminValid();

        Long resourceId = Long.valueOf(id);
        ResourceVO resourceVO = resourceUpdateDao.getEditDataByResourceId(resourceId);

        if(Objects.isNull(resourceVO)){
            throw new BaseException();
        }

        if(Objects.nonNull(resourceVO.getGrade())){
            resourceVO.setGradeStart(resourceCommonService.getGradeStart(resourceVO.getGrade()));
            resourceVO.setGradeEnd(resourceCommonService.getGradeEnd(resourceVO.getGrade()));
        }

        ResourceVersion resourceVersion = ResourceVersion.builder().resourceId(resourceId).build();
        int count = resourceVersionDao.count(resourceVersion);
        resourceVO.setOperationType(count > 1 ? ResourceOperationTypeEnum.UPDATE.getType() : ResourceOperationTypeEnum.FIRST_SHARE.getType());
        return resourceVO;
    }
}
