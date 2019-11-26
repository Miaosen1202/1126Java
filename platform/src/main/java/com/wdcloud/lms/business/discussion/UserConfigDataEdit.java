package com.wdcloud.lms.business.discussion;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.UserConfigDao;
import com.wdcloud.lms.core.base.model.UserConfig;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("JavaDoc")
@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_CONFIG)
public class UserConfigDataEdit implements IDataEditComponent {
    @Autowired
    private UserConfigDao userConfigDao;


    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        return null;
    }


    /**
     * @api {post} /userConfig/modify 讨论设置-我的设置-将帖子手动标记为已读
     * @apiName userConfigModify 将帖子手动标记为已读 修改
     * @apiGroup Discussion
     * @apiParam {Number=0,1} [allowMarkPostStatus] 手动将帖子标记为已读
     * @apiParam {String} [coverColor] 前景色，格式: #666666(日历使用)
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 新增ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        UserConfig dto = JSON.parseObject(dataEditInfo.beanJson, UserConfig.class);
        dto.setUserId(WebContext.getUserId());
        UserConfig userConfig = userConfigDao.findByUserId(WebContext.getUserId());
        if (userConfig == null) {
            userConfigDao.save(dto);
        } else {
            userConfigDao.update(dto);
        }
        return new LinkedInfo(String.valueOf(dto.getId()));
    }


    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        return null;
    }
}
