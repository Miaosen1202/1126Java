package com.wdcloud.lms.business.common;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.core.base.model.AssignUser;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGN_STUDENT)
public class ItemAssignQuery implements IDataQueryComponent<AssignUser> {

    @Autowired
    private AssignUserService assignUserService;

    /**
     * @api {get} /assignStudent/get 学生分配信息
     * @apiName assignStudentGet
     * @apiGroup Common
     * @apiParam {Number} data originType_originId
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 作业信息
     * @apiSuccess {Number} entity.id
     * @apiSuccess {String} entity.title 标题
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "message": "common.success"
     * }
     */
    @Override
    public AssignUser find(String id) {
        String[] s = id.split("_");
        return assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), Integer.valueOf(s[0]), Long.valueOf(s[1]));
    }
}
