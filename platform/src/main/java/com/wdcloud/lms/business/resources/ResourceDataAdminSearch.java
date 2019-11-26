package com.wdcloud.lms.business.resources;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.PageDefault;
import com.wdcloud.lms.base.service.PageDefaultConvertService;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.vo.PageInfoVO;
import com.wdcloud.lms.core.base.dao.ResourceAdminOperationLogDao;
import com.wdcloud.lms.core.base.dao.ResourceDao;
import com.wdcloud.lms.core.base.dao.ResourceVersionMessageDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceUpdate;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_SEARCH_ADMIN)
public class ResourceDataAdminSearch implements IDataQueryComponent<ResourceVO> {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceCommonService resourceCommonService;

    /**
     * @api {get} /resSearchAdmin/pageList 管理员资源查询分页
     * @apiDescription 管理员资源查询分页
     * @apiName resSearchAdminPageList
     * @apiGroup resource
     *
     * @apiParam {String} [types] 类型 1：作业，2：讨论，3：测验，15：课程，之间逗号隔开
     * @apiParam {String} [sortBy] 排序类型 1：更新时间，2：下载数量，之间逗号隔开
     * @apiParam {String} [content] 查询内容，即搜索框输入的内容，范围有资源名称、资源标签、资源描述
     * @apiParam {Number} [pageIndex] 页码，null时默认值为1
     * @apiParam {Number} [pageSize] 页大小，null时默认值为16
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应体
     * @apiSuccess {Object[]} entity.list 资源列表
     * @apiSuccess {Number} entity.list.id 导入的资源ID
     * @apiSuccess {String} entity.list.shareRange 资源范围
     * @apiSuccess {String} entity.list.thumbnailUrl 缩略图URL
     * @apiSuccess {String} entity.list.originType 资源类型，1：作业，2：讨论，3：测验，15：课程
     * @apiSuccess {String} entity.list.name 标题
     * @apiSuccess {String} entity.list.gradeStart 年级开始
     * @apiSuccess {String} entity.list.gradeEnd 年级结束
     * @apiSuccess {String[]} entity.list.tags 标签
     * @apiSuccess {String} entity.list.author 作者名字
     * @apiSuccess {Number} entity.list.importedCount 导入次数
     * @apiSuccess {String} entity.pageIndex 当前页
     * @apiSuccess {String} entity.pageSize 每页数据
     * @apiSuccess {String} entity.total 总数
     * @apiSuccessExample {json} 响应示例:
     * {
     *     "code": 200,
     *     "entity": {
     *         "list": [
     *             {
     *                 "author": "admin admin",
     *                 "gradeStart"：1，
     *                 "gradeEnd"：4，
     *                 "id": 6,
     *                 "importCount": 0,
     *                 "institution": "Root",
     *                 "licence": 6,
     *                 "name": "是分享作业呀",
     *                 "originType": 1,
     *                 "shareRange": 3,
     *                 "tags": [],
     *                 "thumbnailUrl": "group1/M00/00/2F/wKgFFF0evV2ARE5-AAAOCcTFS-s024.png",
     *             }
     *         ],
     *         "pageIndex": 1,
     *         "pageSize": 16,
     *         "total": 1
     *     },
     *     "message": "success"
     * }
     *
     */
    @Override
    public PageQueryResult<ResourceVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        roleValidateService.onlyAdminValid();
        String orgTreeId = WebContext.getOrgTreeId();
        List<String> types = resourceCommonService.convertList(param.get(Constants.PARAM_TYPES));
        pageIndex = Objects.isNull(pageIndex) ? Constants.PAGE_INDEX_FIRST  : pageIndex;
        pageSize = Objects.isNull(pageSize) ? Constants.PAGE_SIZE_SIXTEEN  : pageSize;

        String name = StringUtil.isEmpty(param.get(Constants.PARAM_CONTENT)) ? null : param.get(Constants.PARAM_CONTENT);

        List<String> sortParam = resourceCommonService.convertList(param.get(Constants.PARAM_SORT_BY));
        String updateTime = null;
        String importCount = null;
        if(ListUtils.isNotEmpty(sortParam)){
            for(String temp : sortParam){
                if(Constants.UPDATE_TIME_SORT_PARAM .equals(temp)){
                    updateTime = ResourceUpdate.UPDATE_TIME;
                }
                if(Constants.IMPORT_COUNT_SORT_PARAM.equals(temp)){
                    importCount = Constants.IMPORT_COUNT_SORT_BY;
                }
            }
        }

        PageHelper.startPage(pageIndex, pageSize);
        Page<ResourceVO> resourceVOS = new Page<>();
        if(ListUtils.isNotEmpty(types)){
            resourceVOS = (Page<ResourceVO>) resourceDao.getByShareRangeAndCondition(types,  name,
                    orgTreeId, updateTime, importCount);
            resourceVOS = resourceCommonService.convertGrade(resourceVOS);
        }

        return new PageQueryResult<>(resourceVOS.getTotal(), resourceVOS, pageIndex, pageSize);
    }
}
