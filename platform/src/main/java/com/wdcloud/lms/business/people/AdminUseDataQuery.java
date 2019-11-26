package com.wdcloud.lms.business.people;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.people.vo.UserDetailVO;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.vo.AdminUserDetailVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ADMIN_USER)
public class AdminUseDataQuery implements IDataQueryComponent<User> {
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /adminUser/pageList 管理员用户查询
     * @apiDescription 管理员用户查询
     * @apiName adminUserPageList
     * @apiGroup admin
     * @apiParam {Number} pageIndex 当前页码
     * @apiParam {Number} pageSize 分页条数
     * @apiParam {String} [q] 搜索条件 (full name搜索)
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} [entity.sisId] SIS ID
     * @apiSuccess {Number} entity.username 登录名
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} [entity.fullname] 用户全名
     */
    @Override
    public PageQueryResult<? extends User> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        Map<String, Object> map = Maps.newHashMap();
        map.put(User.USERNAME, param.get("q"));
        map.put(Constants.PARAM_ORG_TREE_ID, WebContext.getOrgTreeId());
        map.put(Constants.PARAM_ORDER_BY, User.USERNAME);
        Page<User> users = (Page<User>) userDao.findUsersByCondition(map);
        return new PageQueryResult<>(users.getTotal(), users, pageSize, pageIndex);
    }

    /**
     * @api {get} /adminUser/get 管理员用户详情查询
     * @apiDescription 管理员用户详情查询
     * @apiName adminUserGet
     * @apiGroup admin
     * @apiParam {Number} data id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object[]} [entity] 结果
     */
    @Override
    public User find(String id) {
        Long uid = Long.valueOf(id);
        User user = userDao.get(uid);
        UserDetailVO detailVO = BeanUtil.beanCopyProperties(user, UserDetailVO.class, Constants.IGNORE_PROPERTIES);
        List<AdminUserDetailVO> roles = userDao.getUserCourseAndRoles(uid);
        detailVO.setCourses(roles);
        return detailVO;
    }
}
