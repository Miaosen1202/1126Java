package com.wdcloud.lms.business.user;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.vo.UserSettingVo;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 功能：用户设置查询接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_SETTING)
public class UserSettingQuery implements IDataQueryComponent<UserSettingVo> {

    @Autowired
    private UserDao userDao;


    @Override
    public List<? extends UserSettingVo> list(Map<String, String> param) {

        return null;
    }


    @Override
    public PageQueryResult<? extends UserSettingVo> pageList(Map<String, String> param, int pageIndex, int pageSize) {

        return null;

    }

    /**
     * @param id
     * @return
     * @api {get} /userSetting/get 用户设置信息查找
     * @apiDescription 用户设置信息查找
     * @apiName userSettingFind
     * @apiGroup user
     * @apiParam {String} id 用户ID
     * @apiExample {json} 请求示例:
     * http://localhost:8080/userSetting/get?data=2
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "entity": {
     * "createTime": 1551993830000,
     * "createUserId": 1,
     * "description": "6",
     * "email": "15",
     * "emails": [
     * {
     * "createTime": 1550712545000,
     * "createUserId": 2,
     * "email": "1@126.com",
     * "id": 6,
     * "isDefault": 0,
     * "updateTime": 1550712545000,
     * "updateUserId": 2,
     * "userId": 2
     * },
     * {
     * "createTime": 1550713082000,
     * "createUserId": 2,
     * "email": "2@126.com",
     * "id": 10,
     * "isDefault": 0,
     * "updateTime": 1550713082000,
     * "updateUserId": 2,
     * "userId": 2
     * }
     * ],
     * "fullName": "4",
     * "id": 2,
     * "isRegistering": 0,
     * "language": "10",
     * "links": [
     * {
     * "createTime": 1550713090000,
     * "createUserId": 2,
     * "id": 1,
     * "name": "name",
     * "updateTime": 1550713090000,
     * "updateUserId": 2,
     * "url": "url",
     * "userId": 2
     * }
     * ],
     * "nickname": "student",
     * "orgId": 1,
     * "password": "9",
     * "phone": "7",
     * "sex": 0,
     * "status": 1,
     * "timeZone": "11",
     * "title": "8",
     * "updateTime": 1552094423000,
     * "updateUserId": 1,
     * "username": "5"
     * },
     * "message": "common.success"
     * }
     */
    @Override
    public UserSettingVo find(String id) {

        return userDao.getUserSetting(Long.valueOf(id));
    }
}
