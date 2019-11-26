package com.wdcloud.lms.business.org;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.business.org.dto.OrgDTO;
import com.wdcloud.lms.business.sis.enums.OperationTypeEnum;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.OrgDao;
import com.wdcloud.lms.core.base.dao.SisImportOrgDao;
import com.wdcloud.lms.core.base.dao.TermDao;
import com.wdcloud.lms.core.base.enums.OrgTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.Org;
import com.wdcloud.lms.core.base.model.SisImportOrg;
import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.exceptions.OperateRejectedException;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.TreeIdUtils;
import com.wdcloud.utils.idgenerate.IdGenerateUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@ResourceInfo(name = Constants.RESOURCE_TYPE_ORG)
public class OrgDataEdit implements IDataEditComponent {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TermDao termDao;
    @Autowired
    private SisImportOrgDao sisImportOrgDao;

    /**
     * @api {post} /org/add 机构添加
     * @apiDescription 机构添加
     * @apiName orgAdd
     * @apiGroup Org
     * @apiParam {String{..100}} name 学期名称
     * @apiParam {Number} [parentId] 上级ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "message": "success"
     * }
     */
    @SuppressWarnings("Duplicates")
    @Override
    @ValidationParam(clazz = OrgDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        if (!roleService.isAdmin()) {
            return new LinkedInfo(Code.ERROR.name);
        }
        OrgDTO dto = JSON.parseObject(dataEditInfo.beanJson, OrgDTO.class);
        Org org = BeanUtil.beanCopyProperties(dto, Org.class);

        String maxTreeId = orgDao.getMaxTreeIdByParentId(org.getParentId());
        if (StringUtil.isEmpty(maxTreeId)) {
            Org parent = orgDao.get(org.getParentId());
            org.setTreeId(TreeIdUtils.produceTreeIdByParentId(parent.getTreeId(), Constants.TREE_ID_PER_LEVEL_LENGTH));
        } else {
            org.setTreeId(TreeIdUtils.produceTreeId(maxTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH));
        }

        orgDao.save(org);

        return new LinkedInfo(Code.OK.name);
    }


    /**
     * @api {post} /org/modify 机构修改
     * @apiDescription 机构修改
     * @apiName orgModify
     * @apiGroup Org
     * @apiParam {Number} id 机构id
     * @apiParam {String{..100}} name 学期名称
     * @apiParam {Number} parentId 上级ID
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * "message": "success"
     * }
     */
    @Override
    @ValidationParam(clazz = OrgDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        if (!roleService.isAdmin()) {
            return null;
        }
        OrgDTO dto = JSON.parseObject(dataEditInfo.beanJson, OrgDTO.class);
        Org org = BeanUtil.beanCopyProperties(dto, Org.class);

        Org oldOrg = orgDao.get(org.getId());
        if (oldOrg == null) {
            throw new ParamErrorException();
        }

        if (Objects.equals(oldOrg.getType(), OrgTypeEnum.SCHOOL.getType())
                && !Objects.equals(org.getType(), OrgTypeEnum.SCHOOL.getType())) {
            throw new BaseException("org-forbid-change-school-to-other");
        }

        orgDao.update(org);

        return new LinkedInfo(Code.OK.name);
    }

    /**
     * @api {post} /org/deletes 机构删除
     * @apiDescription 机构删除
     * @apiName orgDeletes
     * @apiGroup org
     * @apiParam {Number} id 机构id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 200,
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        if (!roleService.isAdmin()) {
            return new LinkedInfo(Code.ERROR.name);
        }
        Long id = JSON.parseObject(dataEditInfo.beanJson, Long.class);
        //根节点不允许删除//是否root节点
        //没有下级的可以删除
        //没有课程的可以删除
        if (orgDao.count(Org.builder().parentId(id).build()) > 0 || courseDao.count(Course.builder().orgId(id).isDeleted(0).build()) > 0) {
            throw new OperateRejectedException();
        }
        orgDao.delete(id);
        return new LinkedInfo(Code.OK.name);
    }

    private void initSchoolOrg(Org org) {
        // 学校机构，初始化默认学期，并同步机构到SIS中间库
        if (Objects.equals(org.getType(), OrgTypeEnum.SCHOOL.getType())) {
            checkOrgType(org);

            // 为学习机构初始化一个 SIS ID
            if (StringUtil.isEmpty(org.getSisId())) {
                String orgSisId = IdGenerateUtils.getId("SIS_O", Short.MAX_VALUE * 2);
                while (orgDao.count(Org.builder().sisId(orgSisId).build()) > 0) {
                    orgSisId = IdGenerateUtils.getId("SIS_O", Short.MAX_VALUE * 2);
                }
                org.setSisId(orgSisId);
                orgDao.update(org);
            } else {
                Example example = orgDao.getExample();
                example.createCriteria()
                        .andEqualTo(Org.SIS_ID, org.getSisId())
                        .andNotEqualTo(Org.ID, org.getId());
                if (orgDao.count(example) > 0) {
                    throw new BaseException("org-sis-id-exists");
                }
            }

            Term defaultTerm = Term.builder()
                    .isDefault(Status.YES.getStatus())
                    .name("Default")
                    .orgId(org.getId())
                    .orgTreeId(org.getTreeId())
                    .build();
            termDao.save(defaultTerm);

            Example importOrgExample = sisImportOrgDao.getExample();
            importOrgExample.createCriteria()
                    .andEqualTo(SisImportOrg.ACCOUNT_ID, org.getSisId())
                    .andEqualTo(SisImportOrg.PARENT_ACCOUNT_ID, "");
            if (sisImportOrgDao.count(importOrgExample) == 0) {
                SisImportOrg sisImportOrg = SisImportOrg.builder()
                        .accountId(org.getSisId())
                        .parentAccountId("")
                        .name(org.getName())
                        .targetId(org.getId())
                        .orgTreeId(org.getTreeId())
                        .operation(OperationTypeEnum.ACTIVE.getType())
                        .opUserId(WebContext.getUserId())
                        .opUserOrgTreeId(WebContext.getOrgTreeId())
                        .build();
                sisImportOrgDao.save(sisImportOrg);
            }
        }
    }

    // 检查机构类型：如果机构存在上级机构是学校类型，这机构不能是学校
    private void checkOrgType(Org org) {
        if (!Objects.equals(org.getType(), OrgTypeEnum.SCHOOL)) {
            return;
        }
        List<String> treeIds = new ArrayList<>();
        String treeId = org.getTreeId();

        int index = Constants.TREE_ROOT_ID.length() + Constants.TREE_ID_PER_LEVEL_LENGTH;
        while (index < treeId.length()) {
            String tmp = treeId.substring(0, index);
            treeIds.add(tmp);

            index += Constants.TREE_ID_PER_LEVEL_LENGTH;
        }

        if (ListUtils.isNotEmpty(treeIds)) {
            Example example = orgDao.getExample();
            example.createCriteria()
                    .andIn(Org.TREE_ID, treeIds)
                    .andEqualTo(Org.TYPE, OrgTypeEnum.SCHOOL.getType());
            if (orgDao.count(example) > 0) {
                throw new BaseException("org-school-type-forbid-has-sub-school-type");
            }
        }
    }
}
