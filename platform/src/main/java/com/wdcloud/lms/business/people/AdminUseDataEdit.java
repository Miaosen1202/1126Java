package com.wdcloud.lms.business.people;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.people.dto.UserDTO;
import com.wdcloud.lms.core.base.dao.UserConfigDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserConfig;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ResourceInfo(name = Constants.RESOURCE_TYPE_ADMIN_USER)
public class AdminUseDataEdit implements IDataEditComponent {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserConfigDao userConfigDao;


    /**
     * @api {get} /adminUser/add 用户添加
     * @apiDescription 管理员用户添加
     * @apiName 管理员用户添加
     * @apiGroup admin
     * @apiParam {String} firstName firstName
     * @apiParam {String} lastName lastName
     * @apiParam {String} loginId 登录名
     * @apiParam {String} orgId 机构ID
     * @apiParam {String} orgTreeId 机构TreeId
     * @apiParam {String} [email] 邮箱
     * @apiParam {String} [sisId] SIS ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     */
    @Override
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        UserDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserDTO.class);
        User user = BeanUtil.beanCopyProperties(dto, User.class);
        if (userDao.existsUsername(dto.getLoginId().trim())) {
            throw new BaseException("prop.value.exists2", "Login ID", dto.getLoginId());
        }
        user.setUsername(dto.getLoginId());
        user.setFullName(dto.getFirstName() + " " + dto.getLastName());
        user.setNickname(user.getFullName());
        user.setPassword(PasswordUtil.haxPassword(Constants.USER_DEFAULT_PASSWORD));
        userDao.save(user);
        userConfigDao.save(UserConfig.builder()
                .userId(user.getId())
                .build());
        userFileService.initPersonalSpaceDir(user.getId());
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /adminUser/modify 用户编辑
     * @apiName adminUserModify
     * @apiGroup admin
     * @apiParam {Number} id 用户
     * @apiParam {String} firstName  firstName
     * @apiParam {String} lastName lastName
     * @apiParam {String} nickname 显示名称
     * @apiParam {String} email 用户email
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {String} entity 用户ID
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        UserDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserDTO.class);
        User user = BeanUtil.beanCopyProperties(dto, User.class);
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        userDao.update(user);
        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /adminUser/deletes 用户删除
     * @apiName adminUserDelete
     * @apiGroup admin
     * @apiParam {Number} id 用户
     * @apiSuccess {String} code 返回码
     * @apiSuccess {String} message 消息
     * @apiSuccess {String} entity 用户ID
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        Long uid = JSON.parseObject(dataEditInfo.beanJson).getLong(User.ID);
        userDao.delete(uid);
        userConfigDao.delete(uid);
        userFileService.removePersonalSpaceDirs(List.of(uid));
        return new LinkedInfo(Code.OK.name);
    }

}
