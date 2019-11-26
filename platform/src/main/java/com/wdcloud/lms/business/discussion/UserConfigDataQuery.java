package com.wdcloud.lms.business.discussion;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.UserConfigDao;
import com.wdcloud.lms.core.base.model.UserConfig;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_CONFIG)
public class UserConfigDataQuery implements IDataQueryComponent<UserConfig> {
    @Autowired
    private UserConfigDao userConfigDao;


    @Override
    public List<? extends UserConfig> list(Map<String, String> param) {
        return null;
    }


    @Override
    public PageQueryResult<UserConfig> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        return null;
    }


    /**
     * @api {get} /userConfig/get 讨论设置-我的设置详情
     * @apiName userConfigGet
     * @apiGroup Discussion
     * @apiParam {Number} data 课程ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.userId 用户Id
     * @apiSuccess {Number} entity.allowMarkPostStatus 手动将帖子标记为已读
     * @apiSuccess {String} entity.coverColor 前景色，格式: #666666(日历使用)
     */
    @Override
    public UserConfig find(String id) {
        return userConfigDao.findByUserId(WebContext.getUserId());
    }

}
