package com.wdcloud.lms.business.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserLinkDao;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：用户介绍链接删除接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_LINK)
public class UserLinkEdit implements IDataEditComponent {

    @Autowired
    private UserLinkDao userLinkDao;

    /**
     * @api {post} /userLink/deletes 用户介绍链接删除
     * @apiDescription 用户介绍链接删除
     * @apiName userLinkDeletes
     * @apiGroup User
     * @apiParam {Number[] } ids 用户介绍链接ID
     * @apiExample {json} 请求示例:
     * [1, 2, 3]
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 删除用户介绍链接ID列表字符串
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "[49]"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<String> isnull = JSON.parseArray(dataEditInfo.beanJson, String.class);
        int isnullLen = isnull.size();
        if (isnullLen == 0) {
            throw new BaseException("delete.id.is.null");
        }
        for (int i = 0; i < isnullLen; i++) {
            if ("undefined".equals(isnull.get(0))) {
                throw new BaseException("delete.id.is.null");
            }
        }
        List<Long> ids = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        for (Long id : ids) {
            //删除用户介绍链接对应id记录
            userLinkDao.delete(id);
        }
        return new LinkedInfo(JSON.toJSONString(ids));
    }
}
