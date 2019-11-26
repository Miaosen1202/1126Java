package com.wdcloud.lms.business.resources;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.PageDefault;
import com.wdcloud.lms.base.service.PageDefaultConvertService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.vo.PageInfoVO;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.ResourceAdminOperationLogDao;
import com.wdcloud.lms.core.base.dao.ResourceVersionMessageDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceAdminOperationLog;
import com.wdcloud.lms.core.base.vo.resource.ResourceAdminOperationLogVO;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_RES_ADMIN_LOG,
        functionName = Constants.FUNCTION_TYPE_PAGE_LIST
)
public class ResourceAdminOperationLogQuery implements ISelfDefinedSearch<PageInfoVO<ResourceAdminOperationLogVO>> {

    @Autowired
    private ResourceVersionMessageDao resourceVersionMessageDao;
    @Autowired
    private ResourceAdminOperationLogDao resourceAdminOperationLogDao;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resAdminLog/pageList/query 资源管理员日志分页
     * @apiDescription 资源管理员日志分页
     * @apiName resAdminLogPageList
     * @apiGroup resource
     *
     * @apiParam {Number} [pageIndex] 页码，null时默认值为1
     * @apiParam {Number} [pageSize] 页大小，null时默认值为20
     * @apiExample {json} 请求示例:
     * {
     *     "pageIndex":1,
     *     "pageSize":20
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应体
     * @apiSuccess {Object} entity 资源列表页所有信息
     * @apiSuccess {Object[]} entity.list 资源列表
     * @apiSuccess {Number} entity.list.id
     * @apiSuccess {String} entity.list.resourceName 资源名称
     * @apiSuccess {Number=1,2,3,4} entity.list.category 资源类别，1：课程，2：作业，3：测验，4：讨论
     * @apiSuccess {Number=1,2,3} entity.list.shareRange 资源分享范围，1：自己，2：机构，3：公开
     * @apiSuccess {Number=1,2} entity.list.operationType 资源操作类型，1：编辑，2：移除
     * @apiSuccess {Date} entity.list.createTime 创建时间
     * @apiSuccess {String} entity.list.adminName 管理员名字
     * @apiSuccess {Number=0,1} entity.hasCheck 是否有管理员给动过 0：没有，1：有
     * @apiSuccess Number=0,1} entity.hasNewVersion 是否有新的版本 0：没有，1：有
     * @apiSuccess {String} entity.pageIndex 当前页
     * @apiSuccess {String} entity.pageSize 每页数据
     * @apiSuccess {String} entity.total 总数
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": {
     *         "hasNewVersion": 0,
     *         "isCheck": 0,
     *         "list": [],
     *         "pageIndex": 20,
     *         "pageSize": 1,
     *         "total": 0
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public PageInfoVO search(Map<String, String> param) {
        roleValidateService.onlyTeacherValid();

        PageDefault page =PageDefaultConvertService.defaultConvert(param.get(Constants.PARAM_PAGE_INDEX),
                param.get(Constants.PARAM_PAGE_SIZE));
        Long userId = WebContext.getUserId();

        PageHelper.startPage(page.getPageIndex(),page.getPageSize());
        Page<ResourceAdminOperationLogVO> resourceAdminOperationLogVOs = (Page<ResourceAdminOperationLogVO>)
                resourceAdminOperationLogDao.getByResourceAuthorId(userId);
        int hasCheck = resourceAdminOperationLogDao.countByIsSeeAndResourceAuthorId(Status.NO.getStatus(), userId) > 0 ? 1 : 0;

        //对当前查询过的数据设为已读
        List<Long> ids = resourceAdminOperationLogVOs.stream()
                .map(ResourceAdminOperationLogVO :: getId).collect(Collectors.toList());
        ResourceAdminOperationLog resourceAdminOperationLog = ResourceAdminOperationLog.builder()
                .isSee(Status.YES.getStatus()).build();
        resourceAdminOperationLogDao.updateByExample(resourceAdminOperationLog, ids);
        int hasNewVersion = resourceVersionMessageDao.countByCondition(userId, Status.YES.getStatus()) > 0 ? 1 : 0;


        PageInfoVO pageInfoVO = new PageInfoVO<>(resourceAdminOperationLogVOs.getTotal(),
                resourceAdminOperationLogVOs, page.getPageIndex(), page.getPageSize(), hasCheck, hasNewVersion);
        return pageInfoVO;
    }

}
