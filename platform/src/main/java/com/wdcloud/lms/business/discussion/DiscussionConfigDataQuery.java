package com.wdcloud.lms.business.discussion;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.DiscussionConfigDao;
import com.wdcloud.lms.core.base.model.DiscussionConfig;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_DISCUSSION_CONFIG)
public class DiscussionConfigDataQuery implements IDataQueryComponent<DiscussionConfig> {
    @Autowired
    private DiscussionConfigDao discussionConfigDao;


    @Override
    public List<? extends DiscussionConfig> list(Map<String, String> param) {
        return null;
    }


    @Override
    public PageQueryResult<DiscussionConfig> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        return null;
    }


    /**
     * @api {get} /discussionConfig/get 讨论设置-学生设置详情
     * @apiName discussionConfigGet
     * @apiGroup Discussion
     * @apiParam {Number} data 课程ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {Number} entity.courseId 课程ID
     * @apiSuccess {Number} entity.userId 用户Id
     * @apiSuccess {Number} entity.allowStudentCreateDiscussion 学生可创建讨论
     * @apiSuccess {Number} entity.allowStudentEditDiscussion 学生可编辑/删除自己话题和评论
     * @apiSuccess {Number} entity.allowDiscussionAttachFile 允许学生讨论上传附件
     */
    @Override
    public DiscussionConfig find(String id) {
        DiscussionConfig discussionConfig = discussionConfigDao.findOne(DiscussionConfig.builder()
                .courseId(Long.valueOf(id))
                .build());
        return discussionConfig;
    }

}
