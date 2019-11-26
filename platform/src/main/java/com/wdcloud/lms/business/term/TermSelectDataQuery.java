package com.wdcloud.lms.business.term;

import com.google.common.collect.Lists;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_TERM_SELECT)
public class TermSelectDataQuery implements IDataQueryComponent<Term> {

    @Autowired
    private TermDao termDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrgDao orgDao;

    /**
     * @api {get} /termSelect/list 学期下拉列表
     * @apiDescription 系统中只有学校类型机构有学期：用户在机构A（机构A为学校）、或在机构B（机构B为机构A的下级机构），返回学期列表为机构A的学期列表
     *
     *
     * @apiName termSelectList
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

        Org schoolOrg = orgDao.get(WebContext.getOrgId());
        while (!Objects.equals(schoolOrg.getType(), OrgTypeEnum.SCHOOL.getType())
                && !Objects.equals(schoolOrg.getParentId(), Constants.TREE_ROOT_PARENT_ID)) {
            schoolOrg = orgDao.get(schoolOrg.getParentId());
        }
        if (Objects.equals(schoolOrg.getType(), OrgTypeEnum.SCHOOL.getType())) {
            return termDao.find(Term.builder().orgTreeId(schoolOrg.getTreeId()).build());
        } else {
            return new ArrayList<>();
        }
    }
}
