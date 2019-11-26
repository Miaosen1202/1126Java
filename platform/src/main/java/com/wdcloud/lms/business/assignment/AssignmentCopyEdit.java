package com.wdcloud.lms.business.assignment;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.business.discussion.enums.AssignmentGroupItemOriginTypeEnum;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_ASSIGNMENT,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class AssignmentCopyEdit implements ISelfDefinedEdit {
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private AssignmentGroupItemChangeRecordDao groupItemChangeRecordDao;
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private AssignService assignService;

    /**
     * @api {post} /assignment/copy/edit 作业复制
     * @apiDescription 讨论复制
     * @apiName assignmentCopy
     * @apiGroup Assignment
     * @apiParam {Number} id 作业Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        //参数解析
        Assignment assignmentParam = JSON.parseObject(dataEditInfo.beanJson, Assignment.class);
        //get Assignment
        Assignment source = assignmentDao.findOne(assignmentParam);
        if (source == null) {
            throw new BaseException("prop.value.not-exists", "id", String.valueOf(assignmentParam.getId()));
        }
        //copy Assignment
        Assignment target = BeanUtil.beanCopyProperties(source, Assignment.class,
                "id,createTime,updateTime,createUserId,updateUserId");
        target.setTitle(target.getTitle()+" copy");
        target.setStatus(Status.NO.getStatus());
        assignmentDao.insert(target);
        //copy assignmentGroupItem
        Long groupItemId = copyAssignmentGroupItem(source, target);
        //copy changeRecord
        if (groupItemId != null) {
            groupItemChangeRecordDao.assignmentAdded(target, groupItemId);
        }
        //copy assign
        copyAssign(source, target);
        return new LinkedInfo(target.getId().toString());
    }

    private Long copyAssignmentGroupItem(Assignment source, Assignment target) {
        AssignmentGroupItem param = AssignmentGroupItem.builder()
                .originType(AssignmentGroupItemOriginTypeEnum.ASSIGNMENT.getType())
                .originId(source.getId())
                .build();
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findOne(param);
        if (assignmentGroupItem != null) {
            AssignmentGroupItem item = AssignmentGroupItem.builder()
                    .originType(AssignmentGroupItemOriginTypeEnum.ASSIGNMENT.getType())
                    .originId(target.getId())
                    .assignmentGroupId(assignmentGroupItem.getAssignmentGroupId())
                    .title(target.getTitle())
                    .score(target.getScore())
                    .status(target.getStatus())
                    .build();
            assignmentGroupItemDao.save(item);
            return item.getId();
        }

        return null;
    }

    private void copyAssign(Assignment source, Assignment target) {
        List<Assign> assignListSource = assignDao.find(Assign.builder().originType(OriginTypeEnum.ASSIGNMENT.getType()).originId(source.getId()).build());
        if (ListUtils.isNotEmpty(assignListSource)) {
            assignService.batchSave(assignListSource, target.getCourseId(), OriginTypeEnum.ASSIGNMENT, target.getId());
        }
    }
}
