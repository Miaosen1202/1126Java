package com.wdcloud.lms.business.org;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ORG2,
        functionName = Constants.FUNCTION_TYPE_SELECT
)
public class OrgSelectData2Query implements ISelfDefinedSearch<List<Org>> {

    @Autowired
    private OrgDao orgDao;

    /**
     * @api {get} /org2/select/query 注册机构下拉列表(无需登录)
     * @apiName Org2SelectQuery
     * @apiGroup Org
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 机构列表
     * @apiSuccess {Number} entity.id 机构id
     * @apiSuccess {Number} entity.name 机构名称
     * @apiSuccess {Number} entity.treeId 机构树Id
     * @apiSuccess {Number} entity.type 机构类型 1学校 2非学校
     * {
     * "code": 200,
     * "entity": [
     * {
     * "createTime": 1551894708000,
     * "createUserId": 1,
     * "id": 10,
     * "name": "Test_Account-4_1",
     * "parentId": 8,
     * "sisId": "sa_006",
     * "treeId": "0001000100070002",
     * "type": 1,
     * "updateTime": 1551894708000,
     * "updateUserId": 1
     * }
     * ],
     * "message": "common.success"
     * }
     */
    @Override
    public List<Org> search(Map<String, String> condition) {
        List<Org> orgList= orgDao.find(Org.builder().type(OrgTypeEnum.SCHOOL.getType()).build());
        //此处隐藏根机构  Ministry of Education, HONGKONG           id=92
        List<Org> orgs=orgList.stream().filter(o -> !o.getId().equals(92L)).collect(Collectors.toList());
        return orgs;
    }
}
