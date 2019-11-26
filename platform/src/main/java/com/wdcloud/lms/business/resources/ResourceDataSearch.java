package com.wdcloud.lms.business.resources;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.PageDefault;
import com.wdcloud.lms.base.service.PageDefaultConvertService;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.RoleValidateService;
import com.wdcloud.lms.business.resources.enums.ResourceOriginFromTypeEnum;
import com.wdcloud.lms.business.resources.vo.PageInfoVO;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceUpdate;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import org.apache.ibatis.builder.ParameterExpression;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_RES_SEARCH,
        functionName = Constants.FUNCTION_TYPE_PAGE_LIST
)
public class ResourceDataSearch implements ISelfDefinedSearch<PageInfoVO<ResourceVO>> {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResourceVersionMessageDao resourceVersionMessageDao;
    @Autowired
    private ResourceAdminOperationLogDao resourceAdminOperationLogDao;

    @Autowired
    private RoleValidateService roleValidateService;
    @Autowired
    private ResourceCommonService resourceCommonService;

    public final static Integer GRADE_MAX = 15;

    /**
     * @api {get} /resSearch/pageList/query 资源查询分页
     * @apiDescription 资源查询分页
     * @apiName resSearchPageListQuery
     * @apiGroup resource
     *
     * @apiParam {String} [types] 类型 1：作业，2：讨论，3：测验，15：课程，之间逗号隔开
     * @apiParam {Number=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14} [gradeStart] 年级开始，0：Kindergarten，1：1st Grade，
     * 2：2nd Grade，3：3rd Grade，4：4th Grade，5：5th Grade，6：6th Grade，7：7th Grade，8：8th Grade，9：9th Grade，
     * 10：10th Grade，11：11th Grade，12：12th Grade，13：Undergraduate，14：Graduate
     * @apiParam {Number=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14} [gradeEnd] 年级结束，0：Kindergarten，1：1st Grade，
     * 2：2nd Grade，3：3rd Grade，4：4th Grade，5：5th Grade，6：6th Grade，7：7th Grade，8：8th Grade，9：9th Grade，
     * 10：10th Grade，11：11th Grade，12：12th Grade，13：Undergraduate，14：Graduate
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
     * @apiSuccess {Number=0,1} entity.hasCheck 是否有用户未查看的管理员改动日志 0：没有，1：有
     * @apiSuccess {Number=0,1} entity.hasNewVersion 是否有新的版本 0：没有，1：有
     * @apiSuccess {String} entity.pageIndex 当前页
     * @apiSuccess {String} entity.pageSize 每页数据
     * @apiSuccess {String} entity.total 总数
     * @apiSuccessExample {json} 响应示例:
     * {
     *     "code": 200,
     *     "entity": {
     *         "hasCheck": 0,
     *         "hasNewVersion": 0,
     *         "list": [
     *             {
     *                 "author": "admin admin",
     *                 "favoriteCount": 0,
     *                 "gradeStart"：1，
     *                 "gradeEnd"：4，
     *                 "id": 6,
     *                 "importCount": 0,
     *                 "institution": "Root",
     *                 "isFavorite": 1,
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
    public PageInfoVO<ResourceVO> search(Map<String, String> condition) {
        roleValidateService.onlyTeacherValid();

        Long userId = WebContext.getUserId();
        String rootOrgTreeId = WebContext.getOrgTreeId().substring(0, 8);
        PageDefault page = PageDefaultConvertService.sizeSixteenConvert(condition.get(Constants.PARAM_PAGE_INDEX),
                condition.get(Constants.PARAM_PAGE_SIZE));
        List<String> types = resourceCommonService.convertList(condition.get(Constants.PARAM_TYPES));

        Integer grade = dealGrade(condition.get("gradeStart"), condition.get("gradeEnd"));
        String name = StringUtil.isEmpty(condition.get("content")) ? null : condition.get("content");

        List<String> sortParam = resourceCommonService.convertList(condition.get("sortBy"));
        String updateTime = null;
        String importCount = null;
        if(ListUtils.isNotEmpty(sortParam)){
            for(String temp : sortParam){
                if(Constants.UPDATE_TIME_SORT_PARAM.equals(temp)){
                    updateTime = ResourceUpdate.UPDATE_TIME;
                }
                if(Constants.IMPORT_COUNT_SORT_PARAM.equals(temp)){
                    importCount = Constants.IMPORT_COUNT_SORT_BY;
                }
            }
        }

        PageHelper.startPage(page.getPageIndex(),page.getPageSize());
        Page<ResourceVO> resourceVOS = new Page<>();
        if(ListUtils.isNotEmpty(types)){
            resourceVOS = (Page<ResourceVO>) resourceDao.getByShareRangeAndCondition(userId, types, grade,
                    name, rootOrgTreeId, updateTime, importCount);
            resourceVOS = resourceCommonService.convertGrade(resourceVOS);
        }

        int hasCheck = resourceAdminOperationLogDao.countByIsSeeAndResourceAuthorId(Status.NO.getStatus(), userId) > 0 ? 1 : 0;
        int hasNewVersion = resourceVersionMessageDao.countByCondition(userId, Status.YES.getStatus()) > 0 ? 1 : 0;
        return new PageInfoVO<>(resourceVOS.getTotal(), resourceVOS, page.getPageIndex(), page.getPageSize(), hasCheck, hasNewVersion);
    }

    /**
     * 处理年级数据
     * @param gradeStart
     * @param gradeEnd
     * @return
     */
    private Integer dealGrade(String gradeStart, String gradeEnd){
        Integer start = StringUtil.isEmpty(gradeStart) ? null : Integer.valueOf(gradeStart);
        Integer end = StringUtil.isEmpty(gradeEnd) ? null : Integer.valueOf(gradeEnd);
        Double temp;
        Integer grade = null;

        if(Objects.nonNull(start) || Objects.nonNull(end)){
            grade = 0;
            if(Objects.nonNull(start) && Objects.nonNull(end)){
                for(int i = start; i <= end; i++){
                    temp = Math.pow(2, i);
                    grade |= Integer.valueOf(temp.intValue());
                }
            }
            if(Objects.nonNull(start) && Objects.isNull(end)){
                for(int i = start; i <= GRADE_MAX; i++){
                    temp = Math.pow(2, i);
                    grade |= Integer.valueOf(temp.intValue());
                }
            }
            if(Objects.isNull(start) && Objects.nonNull(end)){
                for(int i = 0; i <= end; i++){
                    temp = Math.pow(2, i);
                    grade |= Integer.valueOf(temp.intValue());
                }
            }
        }
        return grade;
    }
}
