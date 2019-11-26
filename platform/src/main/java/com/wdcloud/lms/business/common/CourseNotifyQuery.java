package com.wdcloud.lms.business.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemChangeRecordDao;
import com.wdcloud.lms.core.base.model.AssignmentGroupItemChangeRecord;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_COURSE_NOTIFY)
public class CourseNotifyQuery implements IDataQueryComponent<AssignmentGroupItemChangeRecord> {
    @Autowired
    private AssignmentGroupItemChangeRecordDao assignmentGroupItemChangeRecordDao;


    /**
     * @api {get} /courseNotify/pageList 通知分页
     * @apiName CourseNotifyPageList
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
     * @apiSuccess {Number} entity.list.courseId 课程ID
     * @apiSuccess {String} entity.list.title 任务项标题
     * @apiSuccess {String} entity.list.originId 任务项标题
     * @apiSuccess {String} entity.list.originType 任务项类型， 1: 作业 2: 讨论 3: 测验
     * @apiSuccess {String} entity.list.content 任务项内容
     * @apiSuccess {Number} entity.list.opType 操作类型， 1： 创建 2: 更新内容
     * @apiSuccess {Number} entity.list.createTime 操作时间
     *
     */
    @Override
    public PageQueryResult<? extends AssignmentGroupItemChangeRecord> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        String cid = param.get(Constants.PARAM_COURSE_ID);
        if (StringUtil.isEmpty(cid)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(cid);

        PageHelper.startPage(pageIndex, pageSize);
        Page<AssignmentGroupItemChangeRecord> result = (Page<AssignmentGroupItemChangeRecord>) assignmentGroupItemChangeRecordDao.findNotifies(courseId);
        return new PageQueryResult<>(result.getTotal(), result.getResult(), pageSize, pageIndex);
    }
}
