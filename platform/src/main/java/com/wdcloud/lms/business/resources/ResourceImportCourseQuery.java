package com.wdcloud.lms.business.resources;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.course.vo.CourseVo;
import com.wdcloud.lms.business.resources.vo.ResourceImportUpdateCourseVO;
import com.wdcloud.lms.core.base.dao.ResourceDao;
import com.wdcloud.lms.core.base.dao.ResourceImportDao;
import com.wdcloud.lms.core.base.dao.ResourceUpdateDao;
import com.wdcloud.lms.core.base.dao.ResourceVersionDao;
import com.wdcloud.lms.core.base.model.Resource;
import com.wdcloud.lms.core.base.model.ResourceUpdate;
import com.wdcloud.lms.core.base.model.ResourceVersion;
import com.wdcloud.lms.core.base.vo.resource.ResourceBaseVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceCourseVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceVersionVO;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_IMPORT_COURSE, description = "资源分享")
public class ResourceImportCourseQuery implements IDataQueryComponent<ResourceImportUpdateCourseVO> {

    @Autowired
    private ResourceUpdateDao resourceUpdateDao;
    @Autowired
    private ResourceImportDao resourceImportDao;
    @Autowired
    private ResourceVersionDao resourceVersionDao;

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleValidateService roleValidateService;

    /**
     * @api {get} /resImportCourse/get 查询可导入或更新的课程
     * @apiDescription 查询导入信息，若之前导入过，返回已打入的课程和可以导入的课程；若之前没导入过，
     *                 返回可以导入的课程
     * @apiName resImportCourseGet
     * @apiGroup resource
     *
     * @apiParam {Number} data 资源ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} entity 响应体
     * @apiSuccess {Object} entity.resource 资源
     * @apiSuccess {Number} entity.resource.id 资源ID
     * @apiSuccess {String} entity.resource.name 资源名字
     * @apiSuccess {Object} entity.version 资源最新版本
     * @apiSuccess {Number} entity.latestVersion.id 版本ID
     * @apiSuccess {Data} entity.latestVersion.version 资源最新版本
     * @apiSuccess {String} entity.latestVersion.description 资源描述
     * @apiSuccess {Object[]} entity.importedCourses 已导入的课程
     * @apiSuccess {Object[]} entity.notImportedCourses 可以导入的课程
     * @apiSuccess {Number} entity.importedCourses.id  已导入的课程的id
     * @apiSuccess {String} entity.importedCourses.name  已导入的课程的名称
     * @apiSuccess {String} entity.importedCourses.version  已导入的课程的资源版本
     * @apiSuccess {Number} entity.notImportedCourses.id  可导入的课程的id
     * @apiSuccess {String} entity.notImportedCourses.name  可导入的课程的名称
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "entity": {
     *         "importedCourses": [
     *             {
     *                 "id": 106,
     *                 "name": "AA",
     *                 "version":1563825887000
     *              },
     *         ],
     *         "latestVersion": {
     *             "id": 42,
     *             "version": 1563825887000
     *         },
     *         "notImportedCourses": [
     *             {
     *                 "id": 105,
     *                 "name": "SS"
     *             },
     *             {
     *                 "id": 106,
     *                 "name": "Pyhon"
     *             }
     *         ],
     *         "resource": {
     *             "id": 25,
     *             "name": "是分享作业呀"
     *         }
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public ResourceImportUpdateCourseVO find(String id) {
        roleValidateService.onlyTeacherValid();

        Long resourceId = Long.valueOf(id);
        Long userId = WebContext.getUserId();

        ResourceUpdate resourceUpdate = resourceUpdateDao.getByResourceId(resourceId);
        ResourceBaseVO resourceBaseVO = BeanUtil.copyProperties(resourceUpdate, ResourceBaseVO.class);
        ResourceVersion resourceVersion = resourceVersionDao.getLatestByResourceId(resourceId);
        ResourceVersionVO resourceVersionVO = BeanUtil.copyProperties(resourceVersion, ResourceVersionVO.class);
        List<ResourceCourseVO> importedCourses = resourceImportDao.getCourseByResourceIdAndUserId(resourceId, userId);
        List<ResourceCourseVO> notImportedCourses = resourceImportDao.getCourseByNotInResourceAndUserId(resourceId, userId);

        ResourceImportUpdateCourseVO result = ResourceImportUpdateCourseVO.builder().resource(resourceBaseVO)
                .latestVersion(resourceVersionVO).importedCourses(importedCourses)
                .notImportedCourses(notImportedCourses).build();
        return result;
    }
}
