package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.AssignmentGroupItemDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.AssignmentGroupItemService;
import com.wdcloud.lms.base.service.ContentViewRecordService;
import com.wdcloud.lms.business.discussion.enums.AssignmentGroupItemOriginTypeEnum;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.AssignDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemChangeRecordDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_DISCUSSION,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class DiscussionCopyEdit implements ISelfDefinedEdit {

    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private ContentViewRecordService contentViewRecordService;
    @Autowired
    private AssignmentGroupItemChangeRecordDao groupItemChangeRecordDao;
    @Autowired
    private AssignmentGroupItemService assignmentGroupItemService;
    @Autowired
    private AssignService assignService;
    /**
     * @api {post} /discussion/copy/edit 讨论复制
     * @apiDescription 讨论复制
     * @apiName discussionCopy
     * @apiGroup Discussion
     * @apiParam {Number} id 讨论Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        Discussion discussionParam = JSON.parseObject(dataEditInfo.beanJson, Discussion.class);
        //查询讨论源
        Discussion source = discussionDao.findOne(discussionParam);
        if (source == null) {
            throw new BaseException("prop.value.not-exists", "id", String.valueOf(discussionParam.getId()));
        }
        //1复制discussion
        Discussion target = BeanUtil.beanCopyProperties(source, Discussion.class,
                "id,lastActiveTime,createTime,updateTime,createUserId,updateUserId");
        target.setTitle(target.getTitle()+" copy");
        if (target.getStudyGroupId()!=null) {
            target.setStatus(Status.YES.getStatus());
        }else{
            target.setStatus(Status.NO.getStatus());
        }
        target.setIsDeleted(Status.NO.getStatus());
        discussionDao.insert(target);

        //2复制 讨论作业小组关联表
        Long groupItemId = copyAssignmentGroupItem(source, target);
        if (groupItemId != null) {
            groupItemChangeRecordDao.discussionAdded(target, groupItemId);
        }

        //3复制分配
        copyAssign(source, target);
        contentViewRecordService.insert(target.getId(), ContentViewRecordOrignTypeEnum.DISCUSSION_TOPIC.getType());
        return new LinkedInfo(String.valueOf(target.getId()));
    }

    private void copyAssign(Discussion source, Discussion target) {
        List<Assign> assignListSource = assignDao.find(Assign.builder().originType(OriginTypeEnum.DISCUSSION.getType()).originId(source.getId()).build());
        if (ListUtils.isNotEmpty(assignListSource)) {
            assignService.batchSave(assignListSource, target.getCourseId(), OriginTypeEnum.DISCUSSION, target.getId());
        }
    }

    private Long copyAssignmentGroupItem(Discussion source, Discussion target) {
        AssignmentGroupItem param = AssignmentGroupItem.builder()
                .originType(AssignmentGroupItemOriginTypeEnum.DISCUSSION.getType())
                .originId(source.getId())
                .build();
        AssignmentGroupItem assignmentGroupItem = assignmentGroupItemDao.findOne(param);
        if (assignmentGroupItem != null) {
            AssignmentGroupItemDTO item = AssignmentGroupItemDTO.builder()
                    .assignmentGroupId(assignmentGroupItem.getId())
                    .originId(target.getId())
                    .originType(OriginTypeEnum.DISCUSSION)
                    .title(target.getTitle())
                    .score(target.getScore())
                    .status(target.getStatus())
                    .build();
            return  assignmentGroupItemService.save(item);
        }

        return null;
    }
}
