package com.wdcloud.lms.business.term;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_TERM)
public class TermDataQuery implements IDataQueryComponent<Term> {

    @Autowired
    private TermDao termDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /term/list 学期列表
     * @apiName termList
     * @apiGroup Term
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 单元列表
     * @apiSuccess {Number} entity.id 学期ID
     * @apiSuccess {Number} entity.code 编码
     * @apiSuccess {Number} entity.coursesCount 课程数量
     * @apiSuccess {Number} entity.isDefault 是否默认
     * @apiSuccess {Number} entity.name 名称
     * @apiSuccess {Number} [entity.startTime] 学期开始时间
     * @apiSuccess {Number} [entity.endTime] 学期结束时间
     * @apiSuccess {Number} [entity.termConfigs] 人员访问时间配置
     * @apiSuccess {Number} [entity.termConfigs.role_id] 人员角色 1:教师 2:助教 3:学生
     * @apiSuccess {Number} [entity.termConfigs.start_time] 人员访问生效时间
     * @apiSuccess {Number} [entity.termConfigs.end_time] 人员访问截止时间
     * @apiSuccessExample {String} json 返回值
     * {
     * "code": 200,
     * "entity": [
     * {
     * "id": 1,
     * "code": "000",
     * "coursesCount": 1,
     * "isDefault": 1,
     * "name": "Default",
     * "startTime": 1546621122000,
     * "endTime": 1546621132000,
     * "termConfigs": [
     * {
     * {
     * "start_time": 1549087685000,
     * "role_id": 1,
     * "end_time": 1550815701000,
     * },
     * {
     * "start_time": 1549087685000,
     * "role_id": 2,
     * "end_time": 1550815701000,
     * },
     * {
     * "start_time": 1549087685000,
     * "role_id": 3,
     * "end_time": 1550815701000,
     * }
     * ]
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<? extends Term> list(Map<String, String> param) {
        if (!roleService.isAdmin()) {
            return Lists.newArrayList();
        }
        return termDao.termList(WebContext.getOrgTreeId());
    }
}
