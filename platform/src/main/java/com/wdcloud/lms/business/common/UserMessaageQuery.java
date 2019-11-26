package com.wdcloud.lms.business.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.AnnounceDao;
import com.wdcloud.lms.core.base.vo.DialogVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_COURSE_MESSAGE)
public class UserMessaageQuery implements IDataQueryComponent<DialogVo> {
    @Autowired
    private AnnounceDao announceDao;

    /**
     * @api {get} /courseMessage/list 用户在课程收到的消息查询
     * @apiName CourseMessageList
     * @apiGroup Course
     *
     * @apiParam {Number} courseId 课程ID
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object} entity 结果
     * @apiSuccess {String} entity.content 回复内容
     * @apiSuccess {Number} entity.createTime 回复时间
     * @apiSuccess {Number} entity.userId 回复用户ID
     * @apiSuccess {String} entity.username 回复用户名
     * @apiSuccess {Number} entity.announceId 公告ID
     * @apiSuccess {String} entity.announceTitle 公告标题
     */
    @Override
    public List<? extends DialogVo> list(Map<String, String> param) {
        String cid = param.get("courseId");
        if (StringUtil.isEmpty(cid)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(cid);

        return announceDao.findMessages(courseId, WebContext.getUserId());
    }

    /**
     * @api {get} /courseMessage/pageList 用户在课程收到的消息查询分页
     * @apiName CourseMessagePageList
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
     * @apiSuccess {String} entity.list.content 回复内容
     * @apiSuccess {Number} entity.list.createTime 回复时间
     * @apiSuccess {Number} entity.list.userId 回复用户ID
     * @apiSuccess {String} entity.list.username 回复用户名
     * @apiSuccess {Number} entity.list.announceId 公告ID
     * @apiSuccess {String} entity.list.announceTitle 公告标题
     */
    @Override
    public PageQueryResult<? extends DialogVo> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        String cid = param.get("courseId");
        if (StringUtil.isEmpty(cid)) {
            throw new ParamErrorException();
        }

        long courseId = Long.parseLong(cid);

        PageHelper.startPage(pageIndex, pageSize);
        Page<DialogVo> messages = ((Page<DialogVo>) announceDao.findMessages(courseId, WebContext.getUserId()));
        return new PageQueryResult<>(messages.getTotal(), messages.getResult(), pageSize, pageIndex);
    }
}
