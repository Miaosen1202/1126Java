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
import com.wdcloud.lms.core.base.dao.ResourceAdminOperationLogDao;
import com.wdcloud.lms.core.base.dao.ResourceImportLogDao;
import com.wdcloud.lms.core.base.dao.ResourceVersionMessageDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.vo.resource.ResourceImportLogVO;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_RES_IMPORT_STATUS,
        functionName = Constants.FUNCTION_TYPE_PAGE_LIST
)
public class ResourceImportedLogQuery implements ISelfDefinedSearch<PageInfoVO<ResourceImportLogVO>> {

    @Autowired
    private ResourceImportLogDao resourceImportLogDao;
    @Autowired
    private ResourceVersionMessageDao resourceVersionMessageDao;
    @Autowired
    private ResourceAdminOperationLogDao resourceAdminOperationLogDao;

    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resImportStatus/pageList/query 资源导入日志分页
     * @apiDescription 资源导入日志分页
     * @apiName resImportStatusPageList
     * @apiGroup resource
     *
     * @apiParam {Number} [pageIndex] 页码，null时默认值为1
     * @apiParam {Number} [pageSize] 页大小，null时默认值为20
     * @apiExample {json} 请求示例:
     * {
     *      "pageIndex":1,
     *      "pageSize":20
     * }
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 响应体
     * @apiSuccess {Object[]} entity.list 资源列表
     * @apiSuccess {Number} entity.list.id
     * @apiSuccess {String} entity.list.resourceName 资源名称
     * @apiSuccess {String} entity.list.category 资源类别
     * @apiSuccess {String} entity.list.courseName 课程名称
     * @apiSuccess {Number=3,4} entity.list.importType 导入类型，3：导入，4：更新
     * @apiSuccess {Date} entity.list.createTime 创建时间
     * @apiSuccess {Number=1,2,3} entity.list.status 状态，1：正在进行，2：完成，3：失败
     * @apiSuccess {Number=0,1} entity.isCheck 是否有管理员给动过 0：没有，1：有
     * @apiSuccess {Number=0,1} entity.hasNewVersion 是否有新的版本 0：没有，1：有
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
     *         "pageIndex": 1,
     *         "pageSize": 20,
     *         "total": 0
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public PageInfoVO search(Map<String, String> param) {
        roleValidateService.onlyTeacherValid();

        PageDefault page = PageDefaultConvertService.defaultConvert(param.get(Constants.PARAM_PAGE_INDEX), param.get("pageSize"));
        Long userId = WebContext.getUserId();

        PageHelper.startPage(page.getPageIndex(),page.getPageSize());
        Page<ResourceImportLogVO> resourceImportLogVOS = (Page<ResourceImportLogVO>)
                resourceImportLogDao.getByImportUserId(userId);
        int isCheck = resourceAdminOperationLogDao.countByIsSeeAndResourceAuthorId(0, userId) > 0 ? 1 : 0;
        int hasNewVersion = resourceVersionMessageDao.countByCondition(userId, Status.YES.getStatus()) > 0 ? 1 : 0;
        PageInfoVO pageInfoVO = new PageInfoVO<>(resourceImportLogVOS.getTotal(), resourceImportLogVOS, page.getPageSize(),
                page.getPageIndex(), isCheck, hasNewVersion);
        return pageInfoVO;
    }

}
