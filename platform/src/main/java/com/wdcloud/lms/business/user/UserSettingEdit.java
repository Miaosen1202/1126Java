package com.wdcloud.lms.business.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.user.dto.UserSettingDTO;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserEmailDao;
import com.wdcloud.lms.core.base.dao.UserLinkDao;
import com.wdcloud.lms.core.base.model.UserLink;
import com.wdcloud.lms.core.base.vo.UserSettingVo;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：用户信息修改接口
 *
 * @author 黄建林
 */

@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_SETTING)
public class UserSettingEdit implements IDataEditComponent {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLinkDao userLinkDao;


    /**
     * @api {post} /userSetting/modify 用户信息修改
     * @apiDescription 用户首页设置
     * @apiName userSettingModify
     * @apiGroup User
     *
     * @apiParam {Number} id 用户ID
     * @apiParam {String} [firstName]   第一名字
     * @apiParam {String} [lastName]   姓氏
     * @apiParam {String} [fullName]   用户全称，用于评分展示
     * @apiParam {String} username 登录名
     * @apiParam {String} nickname 昵称，用于讨论、消息、评论等
     * @apiParam {String} [description] 自我介绍
     * @apiParam {String} [phone] 联系电话
     * @apiParam {String} [title] 头衔
     * @apiParam {String} [oldPassword] 旧密码；
     * @apiParam {String} [password] 新密码；
     * @apiParam {String} [language] 使用语言
     * @apiParam {String} [timeZone] 时区
     * @apiParam {String} [email] 用户关联邮件地址
     * @apiParam {Object[]} [links] 用户介绍链接
     * @apiParam {String} [links.id] 连接ID (如果是添加ID为null)
     * @apiParam {String} [links.name] 名称
     * @apiParam {String} [links.url] 地址
     * @apiExample {json} 请求示例:
     * {
     * "id": 1,
     * "name": "Thinking In Java"
     * }
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {String} vo 修改测验问题ID
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "vo": "1"
     * }
     */
    @Override
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        final UserSettingDTO dto = JSON.parseObject(dataEditInfo.beanJson, UserSettingDTO.class);
        dto.setUpdateUserId(WebContext.getUserId());
        //如果旧密码输入正确，可以设置新密码
        if (dto.getOldPassword() != null || dto.getPassword() != null) {
            UserSettingVo userSettingVo = userDao.getUserSetting(dto.getId());
            if (!PasswordUtil.haxPassword(dto.getOldPassword()).equals(userSettingVo.getPassword())) {
                throw new BaseException("userSetting.modify.oldPassword.error");
            }
            dto.setPassword(PasswordUtil.haxPassword(dto.getPassword()));
        }

        /*用户用户介绍链接保存*/
        int totalLinks = 0;
        if (dto.getLinks() != null) {
            totalLinks = dto.getLinks().size();
        }
        if (totalLinks > 0) {
            List<UserLink> linksAdd = new ArrayList<>();
            List<UserLink> linksUpdate = new ArrayList<>();

            for (int i = 0; i < totalLinks; i++) {
                UserLink userLink = new UserLink();
                if (dto.getLinks().get(i).getId() != null && !"".equals(dto.getLinks().get(i).getId())) {
                    if (dto.getLinks().get(i).getName() == null||"".equals(dto.getLinks().get(i).getName())) {
                        throw new BaseException("userSetting.modify.userLink.title.required");
                    }
                    if (dto.getLinks().get(i).getUrl() == null) {
                        throw new BaseException("userSetting.modify.userLink.url.required");
                    }
                    userLink.setId(dto.getLinks().get(i).getId());
                    userLink.setName(dto.getLinks().get(i).getName());
                    userLink.setUrl(dto.getLinks().get(i).getUrl());
                    userLink.setUpdateUserId(WebContext.getUserId());
                    userLink.setUserId(WebContext.getUserId());
                    linksUpdate.add(userLink);
                } else {
                    if (dto.getLinks().get(i).getName() == null||"".equals(dto.getLinks().get(i).getName())) {
                        throw new BaseException("userSetting.modify.userLink.title.required");
                    }
                    if (dto.getLinks().get(i).getUrl() == null) {
                        throw new BaseException("userSetting.modify.userLink.url.required");
                    }
                    userLink.setName(dto.getLinks().get(i).getName());
                    userLink.setUrl(dto.getLinks().get(i).getUrl());
                    userLink.setUserId(WebContext.getUserId());
                    userLink.setUpdateUserId(WebContext.getUserId());
                    userLink.setCreateUserId(WebContext.getUserId());
                    linksAdd.add(userLink);
                }
                if (linksAdd.size() > 0) {
                    userLinkDao.batchInsert(linksAdd);
                }
                if (linksUpdate.size() > 0) {
                    userLinkDao.batchUpdate(linksUpdate);
                }
            }

        }
        //不让更改登录名
        if (dto.getUsername() != null) {
            dto.setUsername(null);
        }
        userDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }
}
