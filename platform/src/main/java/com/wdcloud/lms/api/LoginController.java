package com.wdcloud.lms.api;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.api.dto.ChangeDTO;
import com.wdcloud.lms.api.dto.ChangeLanguageDTO;
import com.wdcloud.lms.api.dto.LoginDTO;
import com.wdcloud.lms.api.vo.LoginVO;
import com.wdcloud.lms.core.base.dao.LoginRecordDao;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.RoleEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.LoginRecord;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.OrgUser;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.api.utils.response.Response;
import com.wdcloud.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private LoginRecordDao loginRecordDao;

    /**
     * @api {post} /login 登录
     * @apiDescription 登录
     * @apiName Login
     * @apiGroup Common
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParam {String=1:管理员,2:教师,3:助教,4:学生} roleType 角色类型
     * @apiExample {json} 请求示例:
     * {
     * username: "admin",
     * password: "abcdefg"
     * roleType: 1
     * }
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} vo 成功登录用户信息
     * @apiSuccess {Number} vo.id 用户ID
     * @apiSuccess {String} vo.username 登录名
     * @apiSuccess {String} vo.nickname 昵称
     * @apiSuccess {String} vo.fullName 全称
     * @apiSuccess {Number=1,2,3,4} vo.type 用户类型, 1.管理员 2.教师 3.助教 4.学生
     * @apiSuccess {String} vo.email 邮箱
     * @apiSuccess {String} vo.phone 联系电话
     * @apiSuccess {Url} vo.avatarUrl 头像
     * @apiSuccess {Object[]} vo.orgs 只有管理员角色返回 用于切换系统使用
     * @apiSuccess {Number} vo.orgs.id 用户所属机构ID
     * @apiSuccess {Number} vo.orgs.name 用户所属机构名称
     * @apiSuccess {Number} vo.orgs.treeId 用户所属机构树id
     * @apiSuccessExample json data
     * {
     * "code": 200,
     * "entity": {
     * "fullName": "admin",
     * "id": 1,
     * "nickname": "admin",
     * "orgs": [
     * {
     * "id": 1,
     * "name": "sys_root",
     * "treeId": "0001"
     * }
     * ],
     * "sex": 0,
     * "status": 1,
     * "username": "admin"
     * },
     * "message": "success"
     * }
     */
    @PostMapping("login")
    public String login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {
        User user = userDao.findOne(User.builder()
                .username(dto.getUsername())
                .password(PasswordUtil.haxPassword(dto.getPassword()))
                .build());
        if (user == null) {
            throw new BaseException("login.error");
        }
        if (Objects.equals(Status.NO.getStatus(), user.getStatus())) {
            throw new BaseException("user.disable.error");
        }
        Long uid = user.getId();
        LoginVO userInfo = BeanUtil.beanCopyProperties(user, LoginVO.class);
        userInfo.setType(dto.getRoleType());
        List<Org> orgs = orgDao.getByUserIdAndRoleId(Map.of(OrgUser.USER_ID, uid, OrgUser.ROLE_ID, RoleEnum.ADMIN.getType()));
//            管理员就返回所属的机构(设计上可以管理多个机构)
        userInfo.setOrgs(orgs);
        if (RoleEnum.ADMIN.getType().equals(dto.getRoleType())) {
            if (CollectionUtil.isNullOrEmpty(orgs)) {
                throw new BaseException("login.role.error");
            }
            //管理员设置最近登录的所属机构 初次登录选第一个 默认保存30 30天之后自动删除
            Org lastLoginOrg = getLastLoginOrg(uid);
            if (lastLoginOrg != null) {//动态设置所属机构
                Optional<Org> first = orgs.stream().filter(org -> org.getId().equals(lastLoginOrg.getId())).findFirst();
                if (first.isPresent()) {
                    userInfo.setOrgId(lastLoginOrg.getId());
                    userInfo.setOrgTreeId(lastLoginOrg.getTreeId());
                } else {
                    userInfo.setOrgId(orgs.get(0).getId());
                    userInfo.setOrgTreeId(orgs.get(0).getTreeId());
                    saveLastLoginOrg(uid, orgs.get(0));//任意一个
                }
            } else if (ListUtils.isNotEmpty(orgs)) {
                userInfo.setOrgId(orgs.get(0).getId());
                userInfo.setOrgTreeId(orgs.get(0).getTreeId());
                saveLastLoginOrg(uid, orgs.get(0));//任意一个
            }

        }
        //设置session
        setSession(userInfo, dto.getRoleType());
        //更新最后登录时间
        userDao.update(User.builder().id(uid).lastLoginTime(new Date()).build());

        loginRecordDao.save(LoginRecord.builder()
                .userId(uid)
                .username(user.getUsername())
                .ip(IpUtils.getIpAdrress(request))
                .build());

        return Response.returnResponse(Code.OK, userInfo, "common.success");
    }

    private void saveLastLoginOrg(Long userId, Org org) {
        redisService.setEx(Constants.REDIS_ADMIN_LAST_LOGIN_ORG + userId, JSON.toJSONString(org), 30, TimeUnit.DAYS);
    }

    private Org getLastLoginOrg(Long userId) {
        String org = redisService.get(Constants.REDIS_ADMIN_LAST_LOGIN_ORG + userId);
        if (StringUtil.isNotEmpty(org)) {
            return JSON.parseObject(org, Org.class);
        }
        return null;

    }

    /**
     * 设置session信息
     *
     * @param user     user
     * @param roleType 角色类型
     */
    private void setSession(User user, Long roleType) {
        httpSession.setAttribute(Constants.SESSION_USER, user);
        httpSession.setAttribute(Constants.SESSION_USER_ID, user.getId());
        httpSession.setAttribute(Constants.SESSION_USER_ORG_ID, user.getOrgId());
        httpSession.setAttribute(Constants.SESSION_USER_ROLE_ID, roleType);
        httpSession.setAttribute(Constants.SESSION_USER_ORG_TREE_ID, user.getOrgTreeId());
        String language = StringUtils.isBlank(user.getLanguage())?Locale.ENGLISH.getLanguage():user.getLanguage();
        httpSession.setAttribute(Constants.SESSION_USER_LANGUAGE, language);
    }

    /**
     * @api {get} /logout 登出
     * @apiDescription 登出
     * @apiName Logout
     * @apiGroup Common
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} vo 响应体
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "",
     * "message": "success"
     * }
     * <p>
     */
    @GetMapping("logout")
    public String logout() {
        httpSession.invalidate();
        return Response.returnResponse(Code.OK, "common.success");
    }

    /**
     * @api {POST} /change 切换角色/管理员切换机构
     * @apiDescription 切换角色/管理员切换机构
     * @apiName change
     * @apiGroup Common
     * @apiParam {Number} [orgId] 机构ID 机构切换使用
     * @apiParam {Number=1:管理员,2:教师,3:助教,4:学生} [typeId] 角色切换使用
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} vo 响应体
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "",
     * "message": "success"
     * }
     * <p>
     */
    @PostMapping("change")
    public String change(@Valid @RequestBody ChangeDTO dto) {
        if (RoleEnum.TEACHER.getType().equals(dto.getTypeId()) || RoleEnum.TA.getType().equals(dto.getTypeId())
                || RoleEnum.STUDENT.getType().equals(dto.getTypeId())) {//角色切换
            User user = userDao.get(WebContext.getUserId());
            httpSession.setAttribute(Constants.SESSION_USER_ORG_ID, user.getOrgId());
            httpSession.setAttribute(Constants.SESSION_USER_ROLE_ID, dto.getTypeId());
            httpSession.setAttribute(Constants.SESSION_USER_ORG_TREE_ID, user.getOrgTreeId());
        } else {
            //切换机构管理员
            List<Org> orgs = orgDao.getByUserIdAndRoleId(Map.of("userId", WebContext.getUserId(), "roleId", RoleEnum.ADMIN.getType()));
            if (CollectionUtil.isNullOrEmpty(orgs)) {
                throw new BaseException("login.role.error");
            }
            //校验是否非法操作
            Optional<Org> org = orgs.stream().filter(o -> o.getId().equals(dto.getOrgId())).findFirst();
            //管理员设置最近登录的所属机构 初次登录选第一个 默认保存30 30天之后自动删除
            org.ifPresent(o -> {
                //动态切换管理员机构
                httpSession.setAttribute(Constants.SESSION_USER_ORG_ID, o.getId());
                httpSession.setAttribute(Constants.SESSION_USER_ROLE_ID, dto.getTypeId());
                httpSession.setAttribute(Constants.SESSION_USER_ORG_TREE_ID, o.getTreeId());
                saveLastLoginOrg(WebContext.getUserId(), o);
            });
        }
        return Response.returnResponse(Code.OK, "common.success");
    }

    /**
     * @api {get} /getUserInfo 获取用户信息
     * @apiDescription 获取用户信息
     * @apiName getUserInfo
     * @apiGroup Common
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} vo 成功登录用户信息
     * @apiSuccess {Number} vo.id 用户ID
     * @apiSuccess {String} vo.username 登录名
     * @apiSuccess {String} vo.nickname 昵称
     * @apiSuccess {String} vo.fullName 全称
     * @apiSuccess {Number=1,2,3,4} vo.type 用户类型, 1.管理员 2.教师 3.助教 4.学生
     * @apiSuccess {String} vo.email 邮箱
     * @apiSuccess {String} vo.phone 联系电话
     * @apiSuccess {Url} vo.avatarUrl 头像
     * @apiSuccess {Object[]} vo.orgs 只有管理员角色返回 用于切换系统使用
     * @apiSuccess {Number} vo.orgs.id 用户所属机构ID
     * @apiSuccess {Number} vo.orgs.name 用户所属机构名称
     * @apiSuccess {Number} vo.orgs.treeId 用户所属机构树id
     * @apiSuccessExample json data
     * {
     * "code": 200,
     * "entity": {
     * "fullName": "admin",
     * "id": 1,
     * "nickname": "admin",
     * "orgs": [
     * {
     * "id": 1,
     * "name": "sys_root",
     * "treeId": "0001"
     * }
     * ],
     * "sex": 0,
     * "status": 1,
     * "username": "admin"
     * },
     * "message": "success"
     * }
     */
    @GetMapping("getUserInfo")
    public String getUserInfo() {
        if (WebContext.isLogin()) {
            User user = userDao.get(WebContext.getUser().getId());
            Long uid = user.getId();
            LoginVO userInfo = BeanUtil.beanCopyProperties(user, LoginVO.class);
            userInfo.setType(WebContext.getRoleId());
            List<Org> orgs = orgDao.getByUserIdAndRoleId(Map.of(OrgUser.USER_ID, uid, OrgUser.ROLE_ID, RoleEnum.ADMIN.getType()));
//            管理员就返回所属的机构(设计上可以管理多个机构)
            userInfo.setOrgs(orgs);
            if (RoleEnum.ADMIN.getType().equals(WebContext.getRoleId())) {
                if (CollectionUtil.isNullOrEmpty(orgs)) {
                    throw new BaseException("login.role.error");
                }
                //管理员设置最近登录的所属机构 初次登录选第一个 默认保存30 30天之后自动删除
                Org lastLoginOrg = getLastLoginOrg(uid);
                if (lastLoginOrg != null) {//动态设置所属机构
                    Optional<Org> first = orgs.stream().filter(org -> org.getId().equals(lastLoginOrg.getId())).findFirst();
                    if (first.isPresent()) {
                        userInfo.setOrgId(lastLoginOrg.getId());
                        userInfo.setOrgTreeId(lastLoginOrg.getTreeId());
                    } else {
                        saveLastLoginOrg(uid, orgs.get(0));//任意一个
                    }
                } else if (ListUtils.isNotEmpty(orgs)) {
                    saveLastLoginOrg(uid, orgs.get(0));//任意一个
                }
            }
            return Response.returnResponse(Code.OK, userInfo, "common.success");
        }
        return Response.returnResponse(Code.OK, new LoginVO(), "common.success");
    }

    /**
     * @api {POST} /changeLanguage 切换语言
     * @apiDescription 切换语言
     * @apiName changeLanguage
     * @apiGroup Common
     * @apiParam {String} language 语言 可选范围: zh,en,ru,fr（中英俄法）
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} vo 响应体
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "vo": "",
     * "message": "success"
     * }
     * <p>
     */
    @PostMapping("changeLanguage")
    public String changeLanguage(@Valid @RequestBody ChangeLanguageDTO dto) {
        httpSession.setAttribute(Constants.SESSION_USER_LANGUAGE, dto.getLanguage());
        //如果登陆了，则入库，登录以后自动记住以前设置的语言
        if (WebContext.isLogin()) {
          User user=  userDao.get(WebContext.getUserId());
          user.setLanguage(dto.getLanguage());
          userDao.update(user);
        }
        return Response.returnResponse(Code.OK, "common.success");
    }

    /**
     * @api {GET} /getLanguage 获取语言
     * @apiDescription 获取语言
     * @apiName getLanguage
     * @apiGroup Common
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} vo 响应体
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "entity": "zh",
     * "message": "success"
     * }
     * <p>
     */
    @GetMapping("getLanguage")
    public String getLanguage() {
        String language=(String) httpSession.getAttribute(Constants.SESSION_USER_LANGUAGE);
        if (language == null) {
            language = Locale.ENGLISH.getLanguage();
        }
        return Response.returnResponse(Code.OK, language,"common.success");
    }
}
