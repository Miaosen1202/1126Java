package com.wdcloud.lms.business.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.DiscussionDao;
import com.wdcloud.lms.core.base.dao.GradeCommentDao;
import com.wdcloud.lms.core.base.model.GradeComment;
import com.wdcloud.lms.core.base.vo.DialogVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_COURSE_DIALOG)
public class UserDialogQuery implements IDataQueryComponent<DialogVo> {

    @Autowired
    private DiscussionDao discussionDao;
    @Autowired
    private GradeCommentDao gradeCommentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private RoleService roleService;

    /**
     * @api {get} /courseDialog/pageList 用户在课程下发生的对话查询分页
     * @apiName CourseDialogPageList
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     * @apiParam {Number} [pageIndex] 页码
     * @apiParam {Number} [pageSize] 页大小
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} entity 结果
     * @apiSuccess {Number} entity.total 总数
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 结果列表
     * @apiSuccess {Number=1,2} entity.list.type 类型, 1: 讨论 2: 评分任务评论
     * @apiSuccess {String} entity.list.content 回复内容
     * @apiSuccess {Number} entity.list.createTime 回复时间
     * @apiSuccess {Number} entity.list.userId 回复用户ID
     * @apiSuccess {String} entity.list.username 回复用户名
     * @apiSuccess {Number} [entity.list.discussionId] 讨论ID(type=1: 讨论）
     * @apiSuccess {String} [entity.list.discussionTitle] 讨论标题(type=1: 讨论）
     * @apiSuccess {Number} [entity.list.assignmentGroupItemId] 任务项ID(type=2, 评分任务评论）
     * @apiSuccess {Number} [entity.list.assignmentGroupItemTitle] 任务项标题(type=2, 评分任务评论）
     * @apiSuccess {Number} [entity.list.assignmentGroupItemTotalScore] 任务项总分(type=2, 评分任务评论）
     * @apiSuccess {Number} [entity.list.assignmentGroupItemGradeScore] 任务项得分(type=2, 评分任务评论）
     */
    @Override
    public PageQueryResult<? extends DialogVo> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        String cid = param.get("courseId");
        if (StringUtil.isEmpty(cid)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(cid);

        PageHelper.startPage(pageIndex, pageSize);
        Page<DialogVo> dialogs = (Page) discussionDao.findDialogs(courseId, WebContext.getUserId());
        for (DialogVo dialog : dialogs) {
            dialog.setType(DialogVo.DialogTypeEnum.DISCUSSION.getType());
        }

        if (roleService.hasStudentRole()) {

        } else if (roleService.hasTeacherOrTutorRole()) {
            // 教师
        }

        return new PageQueryResult<>(dialogs.getTotal(), dialogs.getResult(), pageSize, pageIndex);
    }
}
