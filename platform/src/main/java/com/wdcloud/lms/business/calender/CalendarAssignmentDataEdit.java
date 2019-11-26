package com.wdcloud.lms.business.calender;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.calender.dto.AssignDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.delete.DeleteStrategy;
import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupDelete;
import org.springframework.beans.factory.annotation.Autowired;

@ResourceInfo(name = Constants.RESOURCE_TYPE_CALENDAR_RES)
public class CalendarAssignmentDataEdit implements IDataEditComponent {

    @Autowired
    private StrategyFactory strategyFactory;

    @Autowired
    private AssignDao assignDao;

    /**
     * @api {POST} /calendarRes/deletes 日历作业,讨论，测验删除
     * @apiName calendarResDeletes
     * @apiGroup Calendar
     * @apiParam {Number} assignTableId 分配主键ID
     * @apiParam {Number=1,2,3} originType 1: 作业 2:讨论 3:测验
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "message": "common.success"
     * }
     */
    @Override
    @ValidationParam(clazz = AssignDTO.class, groups = GroupDelete.class)
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        AssignDTO assignDTO = JSON.parseObject(dataEditInfo.beanJson, AssignDTO.class);
        Assign assign = assignDao.get(assignDTO.getAssignTableId());
        Long taskId = assign.getOriginId();//任务ID（作业|讨论|测验ID）
        int count = assignDao.count(Assign.builder().originId(taskId).originType(assignDTO.getOriginType()).build());
        if (count == 1) {
            DeleteStrategy strategy = strategyFactory.getDeleteStrategy(OriginTypeEnum.typeOf(assignDTO.getOriginType()));
            strategy.delete(taskId);
        }
        assignDao.delete(assignDTO.getAssignTableId());
        return new LinkedInfo(Code.OK.name);
    }
}
