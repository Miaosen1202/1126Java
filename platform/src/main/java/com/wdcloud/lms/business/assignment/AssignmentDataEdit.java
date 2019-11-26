package com.wdcloud.lms.business.assignment;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.AssignmentGroupItemDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.AssignmentGroupItemService;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.business.assignment.dto.AssignmentDTO;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.delete.DeleteStrategy;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.AssignmentDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemChangeRecordDao;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.enums.AssignmentGroupItemChangeOpTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.CollectionUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT, description = "作业编辑")
public class AssignmentDataEdit implements IDataEditComponent {
    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private AssignmentGroupItemService assignmentGroupItemService;
    @Autowired
    private AssignService assignService;
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private AssignmentGroupItemChangeRecordDao groupItemChangeRecordDao;
    @Autowired
    private ModuleCompleteService moduleCompleteService;

    /**
     * @api {post} /assignment/add 作业添加
     * @apiName assignmentAdd
     * @apiGroup Assignment
     * @apiParam {Number} courseId 课程ID
     * @apiParam {String} title 标题
     * @apiParam {String} [description] 描述
     * @apiParam {Number} [score] 分值
     * @apiParam {Number} assignmentGroupId 作业组ID
     * @apiParam {Number} [assignmentGroupItemId] 修改传过来
     * @apiParam {Number} [gradeSchemeId] 评分方案（gradeType=3,4 时设置）
     * @apiParam {Number=0,1} [isIncludeGrade] 是否包含到最终成绩统计
     * @apiParam {Number=1,2,3,4} [submissionType] 提交类型, 1: 在线 2: 书面 3: 外部工具 4: 不提交
     * @apiParam {String} [toolUrl] 外部工具URL，submissionType=3
     * @apiParam {Number=0,1} [studyGroupSetId]  小组集
     * @apiParam {Number=0,1} [isEmbedTool]  是否内嵌工具
     * @apiParam {Number=0,1} [allowText] 在线提交submission_type=1：文本输入
     * @apiParam {Number=0,1} [allowUrl] 在线提交submission_type=1：网站地址
     * @apiParam {Number=0,1} [allowMedia] 在线提交submission_type=1：媒体录音
     * @apiParam {Number=0,1} [allowFile]  在线提交submission_type=1：文件上传
     * @apiParam {String} [fileLimit] 在线提交submission_type=1 & allow_file=1: 上传文件类型限制
     * @apiParam {Number=0,1} [status] 发布状态
     * @apiParam {Number=0,1} [notifyUserChange] 通知用户内容已更改
     * @apiParam {Object[]} [assign] 分配
     * @apiParam {Object[]} [assign.assignId] 分配目标
     * @apiParam {Number=1,2,3} assign.assignType  分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiParam {Number} [assign.limitTime]  截至时间
     * @apiParam {Number} [assign.startTime]  可以参加测验开始日期
     * @apiParam {Number} [assign.endTime]  可以参加测验结束日期
     * @apiParam {Number} showScoreType 显示分数方式，1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = AssignmentDTO.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        AssignmentDTO dto = JSON.parseObject(dataEditInfo.beanJson, AssignmentDTO.class);
        Assignment assignment = BeanUtil.beanCopyProperties(dto, Assignment.class);

        if (assignment.getSubmissionType() != null && assignment.getSubmissionType() == 1) {
            if ((assignment.getAllowMedia() == null || assignment.getAllowMedia() == 0)
                    && (assignment.getAllowText() == null || assignment.getAllowText() == 0)
                    && (assignment.getAllowFile() == null || assignment.getAllowFile() == 0)
                    && (assignment.getAllowUrl() == null || assignment.getAllowUrl() == 0)) {
                throw new BaseException("assignment.add.submissionType.null");
            }
        }

        boolean isAdd = false;
        Assignment oldAssignment = null;
        if (dto.getId() == null) {//新增
            assignmentDao.save(assignment);
            dto.setId(assignment.getId());

            isAdd = true;
        } else {
            oldAssignment = assignmentDao.get(assignment.getId());
            assignment.setCreateUserId(oldAssignment.getCreateUserId());
            assignment.setCreateTime(oldAssignment.getCreateTime());
            assignment.setUpdateTime(new Date());
            assignment.setUpdateUserId(WebContext.getUserId());
            if (assignment.getScore() == null || "".equals(assignment.getScore())) {
                assignment.setScore(0);
            }
            if (assignment.getIsIncludeGrade() == null || "".equals(assignment.getIsIncludeGrade())) {
                assignment.setIsIncludeGrade(1);
            }
            if (assignment.getIsEmbedTool() == null || "".equals(assignment.getIsEmbedTool())) {
                assignment.setIsEmbedTool(1);
            }
            if (assignment.getAllowText() == null || "".equals(assignment.getAllowText())) {
                assignment.setAllowText(0);
            }
            if (assignment.getAllowMedia() == null || "".equals(assignment.getAllowMedia())) {
                assignment.setAllowMedia(0);
            }
            if (assignment.getAllowFile() == null || "".equals(assignment.getAllowFile())) {
                assignment.setAllowFile(0);
            }
            if (assignment.getAllowUrl() == null || "".equals(assignment.getAllowUrl())) {
                assignment.setAllowUrl(0);
            }
            if (assignment.getFileLimit() == null) {
                assignment.setFileLimit("");
            }
            if (assignment.getStatus() == null || "".equals(assignment.getStatus())) {
                assignment.setStatus(0);
            }
            assignmentDao.updateIncludeNull(assignment);
        }

        //保存assign记录
        List<Assign> newAssigns = dto.getAssign();
        if (CollectionUtil.isNotNullAndEmpty(newAssigns)) {
            assignService.batchSave(newAssigns, dto.getCourseId(), OriginTypeEnum.ASSIGNMENT, dto.getId());
        }

        Long assignmentGroupItemId = dto.getAssignmentGroupItemId();
        if (assignmentGroupItemId != null) {
            //先删除
            assignmentGroupItemDao.delete(assignmentGroupItemId);
        }
        //关联到作业组
        assignmentGroupItemId = assignmentGroupItemService.save(AssignmentGroupItemDTO.builder()
                .assignmentGroupId(dto.getAssignmentGroupId())
                .originId(dto.getId())
                .originType(OriginTypeEnum.ASSIGNMENT)
                .score(dto.getScore())
                .status(dto.getStatus())
                .title(dto.getTitle())
                .build());
        if (isAdd) {
            groupItemChangeRecordDao.assignmentAdded(assignment, assignmentGroupItemId);
        } else {
            if (oldAssignment != null && !Objects.equals(assignment.getDescription(), oldAssignment.getDescription())) {
                groupItemChangeRecordDao.changed(assignment, assignmentGroupItemId, AssignmentGroupItemChangeOpTypeEnum.UPDATE_CONTENT);
            }
        }

        //更新进度
        if (!isAdd) {
            moduleCompleteService.updateAssign(dto.getCourseId(), dto.getId(), OriginTypeEnum.ASSIGNMENT.getType());
        }
        return new LinkedInfo(dto.getId() + "_" + assignmentGroupItemId);
    }

    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        return add(dataEditInfo);
    }

    /**
     * @api {post} /assignment/deletes 作业删除
     * @apiName assignmentDeletes
     * @apiGroup Assignment
     * @apiParam {Number} data ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 删除ID
     * @apiSuccessExample 响应示例:
     * {
     * "code": 200,
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long id = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        moduleCompleteService.deleteModuleItemByOriginId(id, OriginTypeEnum.ASSIGNMENT.getType());
        DeleteStrategy strategy = strategyFactory.getDeleteStrategy(OriginTypeEnum.ASSIGNMENT);
        strategy.delete(id);
        return new LinkedInfo(Code.OK.name);
    }
}
