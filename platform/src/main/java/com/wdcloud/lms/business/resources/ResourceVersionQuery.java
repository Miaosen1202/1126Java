package com.wdcloud.lms.business.resources;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.core.base.dao.ResourceVersionDao;
import com.wdcloud.lms.core.base.model.Resource;
import com.wdcloud.lms.core.base.vo.ResourceSearchVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceVersionVO;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_VERSION, description = "导入的资源状态查询")
public class ResourceVersionQuery implements IDataQueryComponent<ResourceVersionVO> {

    @Autowired
    private ResourceVersionDao resourceVersionDao;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resVersion/list 资源版本信息
     * @apiDescription 资源版本信息
     * @apiName resVersionList
     * @apiGroup resource
     *
     * @apiParam {Number} data 资源id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应体
     * @apiSuccess {Object[]} entity.list 资源列表
     * @apiSuccess {Number} entity.list.id
     * @apiSuccess {Number=4} entity.list.shareType 资源分享类型，4：更新
     * @apiSuccess {Date} entity.list.version 版本
     * @apiSuccess {String} entity.list.description 简介
     * @apiExample {json} 请求示例:
     * {
     *     "code": 200,
     *     "entity": [
     *         {
     *             "description": "wewewqe",
     *             "id": 17,
     *             "version": 1563377544000
     *         },
     *         {
     *             "description": "refgserf",
     *             "id": 23,
     *             "version": 1563551780000
     *         }
     *     ],
     *     "message": "success"
     * }
     */
    @Override
    public List<ResourceVersionVO> list(Map<String, String> param) {
        roleValidateService.teacherAndAdminValid();
        String data = param.get(Constants.PARAM_SORT_DATA);
        if(StringUtil.isEmpty(data)){
            throw new ParamErrorException();
        }
        Long resourceId = Long.valueOf(data);

        List<ResourceVersionVO> resourceVersionVOs = resourceVersionDao.getByResourceId(resourceId);
        if(ListUtils.isEmpty(resourceVersionVOs)){
            throw new ParamErrorException();
        }
        List<ResourceVersionVO> result = resourceVersionVOs.subList(0, resourceVersionVOs.size()-1);
        return result;
    }
}
