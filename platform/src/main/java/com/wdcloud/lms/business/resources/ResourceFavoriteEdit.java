package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.dto.ResourceFavoriteDTO;
import com.wdcloud.lms.business.resources.dto.ResourceShareDTO;
import com.wdcloud.lms.core.base.dao.ResourceFavoriteDao;
import com.wdcloud.lms.core.base.model.ResourceFavorite;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupDelete;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;

import java.util.List;
import java.util.Objects;

/**
 * @author  WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_FAVORITE, description = "资源收藏或取消收藏")
public class ResourceFavoriteEdit implements IDataEditComponent {

    @Autowired
    private ResourceFavoriteDao resourceFavoriteDao;


    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceCommonService resourceCommonService;


    /**
     * @api {post} /resFavorite/add 将资源收藏
     * @apiDescription 将资源收藏
     * @apiName resFavoriteAdd
     * @apiGroup resource
     *
     * @apiParam {Number} resourceId 资源id
     * @apiExample {json} 请求示例:
     * {
     * 	    "resourceId":4
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {String} [entity] 新增ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "1"
     * }
     */
    @ValidationParam(clazz = ResourceFavoriteDTO.class, groups = GroupAdd.class)
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        roleValidateService.onlyTeacherValid();

        final ResourceFavoriteDTO dto = JSON.parseObject(dataEditInfo.beanJson, ResourceFavoriteDTO.class);
        Long resourceId = dto.getResourceId();

        ResourceFavorite resourceFavorite = ResourceFavorite.builder()
                .resourceId(resourceId)
                .userId(WebContext.getUserId()).build();
        List<ResourceFavorite> favorites = resourceFavoriteDao.find(resourceFavorite);
        if(ListUtils.isNotEmpty(favorites)){
            throw new BaseException();
        }
        resourceFavoriteDao.save(resourceFavorite);

        return new LinkedInfo(String.valueOf(resourceFavorite.getId()));
    }

    /**
     * @api {post} /resFavorite/deletes 将收藏的资源移除
     * @apiDescription 将收藏的资源移除
     * @apiName resFavoriteDelete
     * @apiGroup resource
     *
     * @apiParam {Number[]} ids 资源ID
     * @apiExample {json} 请求示例:
     * [1]
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {String} [entity] 新增ID
     * @apiSuccessExample {json} 响应示例：
     * {
     *      "code": 200,
     *      "message": "4"
     *  }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        roleValidateService.onlyTeacherValid();
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        Long resourceId = resourceCommonService.singleDeleteValid(ids);

        ResourceFavorite resourceFavorite = ResourceFavorite.builder()
                .resourceId(resourceId)
                .userId(WebContext.getUserId()).build();

        resourceFavoriteDao.deleteByField(resourceFavorite);

        return new LinkedInfo(String.valueOf(ids.size()));
    }
}