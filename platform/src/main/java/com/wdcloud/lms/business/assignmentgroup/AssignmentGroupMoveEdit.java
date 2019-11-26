package com.wdcloud.lms.business.assignmentgroup;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.assignmentgroup.dto.AssignmentGroupMoveDTO;
import com.wdcloud.lms.core.base.dao.AssignmentGroupDao;
import com.wdcloud.lms.core.base.model.AssignmentGroup;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ASSIGNMENT_GROUP,
        functionName = Constants.FUNCTION_TYPE_MOVE
)
public class AssignmentGroupMoveEdit implements ISelfDefinedEdit {

    @Autowired
    private AssignmentGroupDao assignmentGroupDao;
    /**
     * @api {post} /assignmentGroup/move 作业组移动
     * @apiName assignmentGroupMove
     * @apiGroup Assignment
     *
     * @apiParam {Number[]} assignmentGroupIds 排序列表
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} entity 移动项ID
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        final AssignmentGroupMoveDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentGroupMoveDTO.class);
        final long[] ids = dto.getAssignmentGroupIds();
        final AssignmentGroup module = new AssignmentGroup();
        for (int i = 0,len = ids.length; i < len; i++) {
            module.setId(ids[i]);
            module.setSeq(i);
            assignmentGroupDao.update(module);
        }
        return new LinkedInfo(Code.OK.name);
    }
}
