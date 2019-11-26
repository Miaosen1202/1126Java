package com.wdcloud.lms.business.module;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_MODULE_ITEM_ASSIGN)
public class ModuleItemAssignDataQuery implements IDataQueryComponent<Assign> {
    @Autowired
    private AssignDao assignDao;

    /**
     * @api {get} /moduleItemAssign/list 小项分配列表
     * @apiName moduleItemAssignList
     * @apiGroup Common
     * @apiParam {Number} courseId courseId
     * @apiParam {Number} originId originId
     * @apiParam {Number} originType originType
     * @apiSuccess {Number} code 响应码
     * @apiSuccess {String} message 信息
     * @apiSuccess {Object} [entity] 单元项内容
     * @apiSuccess {Number} entity.id itemId
     * @apiSuccess {Object[]} [entity] 分配记录
     * @apiSuccess {Number} [assignType] 分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiSuccess {Number} [limitTime] 截止时间
     * @apiSuccess {Number} [startTime] 开始时间
     * @apiSuccess {Number} [endTime] 结束时间
     */
    @Override
    public List<? extends Assign> list(Map<String, String> param) {
        List<Assign> assigns = assignDao.find(Assign.builder()
                .courseId(Long.valueOf(param.get(Assign.COURSE_ID)))
                .originId(Long.valueOf(param.get(Assign.ORIGIN_ID)))
                .originType(Integer.valueOf(param.get(Assign.ORIGIN_TYPE)))
                .build());
        return assigns;
    }
}
