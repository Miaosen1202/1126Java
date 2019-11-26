package com.wdcloud.lms.business.assignment;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.AssignUserService;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.assignment.vo.AssignmentVO;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Assignment;
import com.wdcloud.lms.core.base.model.AssignmentReply;
import com.wdcloud.lms.core.base.model.StudyGroupSet;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ASSIGNMENT)
public class AssignmentDataQuery implements IDataQueryComponent<Assignment> {

    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private AssignmentReplyDao assignmentReplyDao;
    @Autowired
    private AssignDao assignDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AssignUserService assignUserService;
    @Autowired
    private StudyGroupSetDao studyGroupSetDao;

    /**
     * @api {get} /assignment/get 作业详情
     * @apiName assignmentGet
     * @apiGroup Assignment
     * @apiParam {Number} data ID
     * @apiSuccess {Number} code  响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 作业信息
     * @apiSuccess {Number} entity.id
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {String} entity.title 标题
     * @apiSuccess {String} entity.submitCount 提交次数
     * @apiSuccess {String} [entity.description] 描述
     * @apiSuccess {Number} [entity.score] 分值
     * @apiSuccess {Number} [entity.gradeSchemeId] 评分方案
     * @apiSuccess {Number=0,1} [entity.isIncludeGrade] 包含到总成绩统计里
     * @apiSuccess {Number=1,2,3,4} [entity.submissionType] 提交类型, 1. 在线、2. 书面、3. 外部工具、 4. 不提交
     * @apiSuccess {String} [entity.toolUrl] 外部工具URL，submission_type=3
     * @apiSuccess {Number=0,1} [entity.isEmbedTool] 内嵌工具（不在新窗口打开工具）
     * @apiSuccess {Number=0,1} [entity.allowText] 在线提交submission_type=1：文本输入
     * @apiSuccess {Number=0,1} [entity.allowUrl] 在线提交submission_type=1：网站地址
     * @apiSuccess {Number=0,1} [entity.allowMedia] 在线提交submission_type=1：媒体录音
     * @apiSuccess {Number=0,1} [entity.allowFile] 在线提交submission_type=1：文件上传
     * @apiSuccess {String} [entity.fileLimit] 在线提交submission_type=1&allow_file=1: 上传文件类型限制
     * @apiSuccess {Number} [entity.studyGroupSetId] 小组作业，小组集ID
     * @apiSuccess {Boolean} [entity.studyGroupEdited] 小组可否编辑
     * @apiSuccess {Number=0,1} [entity.isGradeEachOne] 分别为每位学生指定评分
     * @apiSuccess {Number} entity.status 发布状态
     * @apiSuccess {Object[]} [entity.assigns] 分配
     * @apiSuccess {Number} entity.assigns.limitTime 截至日期
     * @apiSuccess {Number} entity.assigns.startTime 可以参加测验开始日期
     * @apiSuccess {Number} entity.assigns.endTime 可以参加测验结束日期
     * @apiSuccess {Object[]} entity.assigns.assignTo 分配到对象
     * @apiSuccess {Number} entity.assigns.assignTo.assignType 分配类型，1: 所有， 2：section(班级), 3: 用户
     * @apiSuccess {Number} entity.assigns.assignTo.target 分配目标ID
     * @apiSuccess {Number} entity.showScoreType 显示分数方式，1：百分比；2： 数字分数；3：完成未完成；4：字母；5：不计分
     * @apiSuccessExample {String} json
     * {
     * "code": 200,
     * "entity": {
     * "allowFile": 0,
     * "allowMedia": 0,
     * "allowText": 0,
     * "allowUrl": 0,
     * "assigns": [
     * {
     * "assignType": 1,
     * "courseId": 1,
     * "endTime": 1548215072000,
     * "id": 1,
     * "limitTime": 1548214407000,
     * "originId": 1,
     * "originType": 1,
     * "startTime": 1547869454000,
     * }
     * ],
     * "courseId": 1,
     * "fileLimit": "",
     * "id": 1,
     * "isEmbedTool": 1,
     * "isIncludeGrade": 1,
     * "score": 0,
     * "status": 0,
     * "studyGroupEdited": true,
     * "title": "第一章作业"
     * },
     * "message": "common.success"
     * }
     */
    @Override
    public Assignment find(String id) {
        Long aid = JSON.parseObject(id, Long.class);
        Assignment assignment = assignmentDao.get(aid);
        if (Objects.isNull(assignment)) {
            return null;
        }
        AssignmentVO vo = BeanUtil.beanCopyProperties(assignment, AssignmentVO.class, Constants.IGNORE_PROPERTIES);
        if (roleService.hasTeacherOrTutorRole()) {
            //设置分配关系
            //assignment.getCourseId(), OriginTypeEnum.ASSIGNMENT.getType(), vo.getId()
            List<Assign> assigns = assignDao.find(Assign.builder().courseId(assignment.getCourseId())
                    .originType(OriginTypeEnum.ASSIGNMENT.getType())
                    .originId(vo.getId())
                    .build());
            vo.setAssigns(assigns);
            //是否提交过/提交过就不能修改 作业studyGroupSetId
            AssignmentReply one = assignmentReplyDao.findOne(AssignmentReply.builder().assignmentId(aid).build());
            vo.setStudyGroupEdited(!Objects.nonNull(one));
        } else {//学生
            vo.setAssignUser(assignUserService.getAssignUserByUseIdAndTypeAndOriginId(WebContext.getUserId(), OriginTypeEnum.ASSIGNMENT.getType(), aid));
            if (assignment.getStudyGroupSetId() != null) {
                StudyGroupSet studyGroupSet = studyGroupSetDao.get(assignment.getStudyGroupSetId());
                if (studyGroupSet != null) {
                    vo.setStudyGroupSetName(studyGroupSet.getName());
                }
            }
            int submitCount = assignmentReplyDao.count(AssignmentReply.builder().assignmentId(aid).userId(WebContext.getUserId()).build());
            vo.setSubmitCount(submitCount);
        }
        return vo;
    }
}
