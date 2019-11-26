package com.wdcloud.lms.business.user;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.idgenerate.IdGenerateUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_USER)
public class UserDataQuery implements IDataQueryComponent<User> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    /**
     * @api {get} /user/list 用户列表
     * @apiDescription 用户列表查询，更新邮箱、登录名、SIS ID精确查找用户
     * @apiName userList
     * @apiGroup People
     *
     * @apiParam {String} [emails] 逗号分隔邮箱列表
     * @apiParam {String} [sisIds] 逗号分隔sisId列表
     * @apiParam {String} [usernames] 逗号分隔用户登录名列表
     *
     * @apiSuccess {String} code
     * @apiSuccess {String} [message]
     * @apiSuccess {Object[]} [entity] 用户列表
     * @apiSuccess {Number} entity.id 用户ID
     * @apiSuccess {String} entity.nickname 昵称
     * @apiSuccess {String} entity.username 登录名（登录ID）
     * @apiSuccess {String} entity.email 邮箱
     * @apiSuccess {String} entity.sisId SIS ID
     * @apiSuccess {Object} entity.org 机构
     * @apiSuccess {Number} entity.org.id 机构ID
     * @apiSuccess {String} entity.org.name 机构名称
     */
    @Override
    public List<? extends User> list(Map<String, String> param) {
        String emails = param.get(Constants.PARAM_EMAILS);
        String sisIds = param.get(Constants.PARAM_SISIDS);
        String usernames = param.get(Constants.PARAM_USER_NAMES);

        List<String> emailList = null;
        List<String> sisIdList = null;
        List<String> usernameList = null;
        if (StringUtil.isNotEmpty(emails)) {
            String[] split = emails.split(",");
            if (split != null && split.length > 0) {
                emailList = Arrays.asList(split);
            }
        }
        if (StringUtil.isNotEmpty(sisIds)) {
            String[] split = sisIds.split(",");
            if (split != null && split.length > 0) {
                sisIdList = Arrays.asList(split);
            }
        }
        if (StringUtil.isNotEmpty(usernames)) {
            String[] split = usernames.split(",");
            if (split != null && split.length > 0) {
                usernameList = Arrays.asList(split);
            }
        }

        String rootOrgTreeId = null;
        if (ListUtils.isNotEmpty(sisIdList)) {
            Org rootOrg = orgDao.get(WebContext.getOrgId());
            while (!Objects.equals(rootOrg.getType(), OrgTypeEnum.SCHOOL.getType())
                    && !Objects.equals(rootOrg.getParentId(), Constants.TREE_ROOT_PARENT_ID)) {
                rootOrg = orgDao.get(rootOrg.getParentId());
            }

            rootOrgTreeId = rootOrg.getTreeId();
        }

        List<User> users = userDao.findUsers(usernameList, emailList, sisIdList, rootOrgTreeId);
        List<UserVo> result = BeanUtil.beanCopyPropertiesForList(users, UserVo.class);
        if (ListUtils.isNotEmpty(result)) {
            List<Long> orgIds = result.stream().map(UserVo::getOrgId).sorted().distinct().collect(Collectors.toList());
            List<Org> orgs = orgDao.gets(orgIds);
            Map<Long, Org> orgIdMap = orgs.stream().collect(Collectors.toMap(Org::getId, a -> a));
            for (UserVo userVo : result) {
                userVo.setPassword(null);
                userVo.setOrg(orgIdMap.get(userVo.getOrgId()));
            }
        }
        return result;
    }

    @Data
    public static class UserVo extends User {
        private Org org;
    }
}