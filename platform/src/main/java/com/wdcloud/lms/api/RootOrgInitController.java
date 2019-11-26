package com.wdcloud.lms.api;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.*;
import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.api.utils.response.Response;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.utils.PasswordUtil;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.TreeIdUtils;
import com.wdcloud.utils.idgenerate.IdGenerateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
public class RootOrgInitController {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private SisImportOrgDao sisImportOrgDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private UserFileDao userFileDao;
    @PostMapping("rootOrg/init")
    public String rootOrgInit(@RequestBody Map<String,String> map) {
        if (userDao.count(User.builder().username(map.get("userName")).build())>0) {
            throw new ParamErrorException();
        }
        //1学校机构 sys_org
        Org org = new Org();
        org.setType(OrgTypeEnum.SCHOOL.getType());
        org.setParentId(-1L);
        org.setName(map.get("orgName"));
        String maxTreeId = orgDao.getMaxTreeIdByParentId(-1L);
        if (StringUtil.isEmpty(maxTreeId)) {
            org.setTreeId("00010001");
        } else {
            org.setTreeId(TreeIdUtils.produceTreeId(maxTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH));
        }
        String orgSisId = IdGenerateUtils.getId("SIS_O", Short.MAX_VALUE * 2);
        while (orgDao.count(Org.builder().sisId(orgSisId).build()) > 0) {
            orgSisId = IdGenerateUtils.getId("SIS_O", Short.MAX_VALUE * 2);
        }
        org.setSisId(orgSisId);
        orgDao.save(org);
        initSchoolOrg(org);
        //用户
        User user = User.builder()
                .username(map.get("userName"))
                .password(PasswordUtil.haxPassword(map.get("pwd")))
                .orgId(org.getId())
                .orgTreeId(org.getTreeId())
                .sisId("")
                .isRegistering(1)
                .status(1)
                .build();
        userDao.save(user);
        //用户机构
        OrgUser orgUser = OrgUser.builder()
                .orgId(org.getId())
                .orgTreeId(org.getTreeId())
                .userId(user.getId())
                .roleId(RoleEnum.ADMIN.getType())
                .build();
        orgUserDao.save(orgUser);

        //初始化 sys_user_file 插入4条记录
        //My Files ,unfiled,profile pictures,Submissions
        String maxTreeId2 = userFileDao.getLastTreeId(-1L);
        String treeId = null;
        if (StringUtil.isEmpty(maxTreeId2)) {
            treeId = "00010001";
        } else {
            treeId = TreeIdUtils.produceTreeId(maxTreeId2, Constants.TREE_ID_PER_LEVEL_LENGTH);
        }
        UserFile userFile1 = UserFile.builder()
                .spaceType(3).dirUsage(0).accessStrategy(1).status(1).isDirectory(1).isSystemLevel(1)
                .fileName("My Files").parentId(-1L).allowUpload(1)
                .userId(user.getId())
                .treeId(treeId)
                .build();
        userFileDao.save(userFile1);

        UserFile userFile2 = UserFile.builder()
                .spaceType(3).dirUsage(1).accessStrategy(1).status(1).isDirectory(1).isSystemLevel(1)
                .fileName("unfiled").parentId(userFile1.getId()).allowUpload(1)
                .userId(user.getId())
                .treeId(treeId+"0001")
                .build();
        userFileDao.save(userFile2);

        UserFile userFile3 = UserFile.builder()
                .spaceType(3).dirUsage(2).accessStrategy(1).status(1).isDirectory(1).isSystemLevel(1)
                .fileName("profile pictures").parentId(userFile1.getId()).allowUpload(1)
                .userId(user.getId())
                .treeId(treeId+"0002")
                .build();
        userFileDao.save(userFile3);

        UserFile userFile4 = UserFile.builder()
                .spaceType(3).dirUsage(3).accessStrategy(1).status(1).isDirectory(1).isSystemLevel(1)
                .fileName("Submissions").parentId(userFile1.getId()).allowUpload(0)
                .userId(user.getId())
                .treeId(treeId+"0003")
                .build();
        userFileDao.save(userFile4);

        return Response.returnResponse(Code.OK, "success");
    }
    //sys_term sisImportOrg
    private void initSchoolOrg(Org org) {
        // 学校机构，初始化默认学期，并同步机构到SIS中间库
        if (Objects.equals(org.getType(), OrgTypeEnum.SCHOOL.getType())) {

            Term defaultTerm = Term.builder()
                    .isDefault(Status.YES.getStatus())
                    .name("Default")
                    .orgId(org.getId())
                    .orgTreeId(org.getTreeId())
                    .code("")
                    .build();
            termDao.save(defaultTerm);

            Example importOrgExample = sisImportOrgDao.getExample();
            importOrgExample.createCriteria()
                    .andEqualTo(SisImportOrg.ACCOUNT_ID, org.getSisId())
                    .andEqualTo(SisImportOrg.PARENT_ACCOUNT_ID, "");
            if (sisImportOrgDao.count(importOrgExample) == 0) {
                SisImportOrg sisImportOrg = SisImportOrg.builder()
                        .batchCode("")
                        .accountId(org.getSisId())
                        .parentAccountId("")
                        .name(org.getName())
                        .targetId(org.getId())
                        .orgTreeId(org.getTreeId())
                        .operation(OperationTypeEnum.ACTIVE.getType())
                        .opUserId(0L)
                        .opUserOrgTreeId("")
                        .build();
                sisImportOrgDao.save(sisImportOrg);
            }
        }
    }
}
