package com.wdcloud.lms.business.certification;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.certification.dto.CertificationAddDTO;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.CertificationDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION)
public class CertificationDataEdit implements IDataEditComponent {

    @Autowired
    private CertificationDao certificationDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserFileDao userFileDao;
    /**
     * @api {post} /certification/add 认证创建
     * @apiName certificationAdd 认证创建
     * @apiGroup certification
     * @apiParam {String} name 认证名称
     * @apiParam {String} [memo] 认证描述
     * @apiParam {Number=0,1} type 认证类型（是否周期）0：永久 1: 周期
     * @apiParam {Number=0,1} issuer 发行机构（内部，外部） 0：Internal 1：External
     * @apiParam {Number} opDay 可操作天数 （Days to complet)
     * @apiParam {Number} [validity] 证件有效月份（单位月) 周期认证时 必填
     * @apiParam {String} [status=0,1,2] 认证状态 0:草稿 1：已发布 2：已注销
     * @apiParam {String} [fileUrl] 文件URL
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = CertificationAddDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        CertificationAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, CertificationAddDTO.class);
        buildFile(dto);
        certificationDao.save(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * @api {post} /certification/modify 认证修改
     * @apiName certificationModify
     * @apiGroup certification
     *
     * @apiParam {Number} id  认证ID
     * @apiParam {String} name 认证名称
     * @apiParam {String} [memo] 认证描述
     * @apiParam {Number=0,1} type 认证类型（是否周期）0：永久 1: 周期
     * @apiParam {Number=0,1} issuer 发行机构（内部，外部） 0：Internal 1：External
     * @apiParam {Number} opDay 可操作天数 （Days to complet)
     * @apiParam {Number} [validity] 证件有效月份（单位月) 周期认证时 必填
     * @apiParam {String} [fileUrl] 文件URL
     * @apiParam {String} [status=0,1,2] 认证状态 0:草稿 1：已发布 2：已注销
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 认证ID
     */
    @Override
    @ValidationParam(clazz = CertificationAddDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        CertificationAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, CertificationAddDTO.class);

        buildFile(dto);

        if (dto.getStatus().equals(Status.YES.getStatus())) {
            //状态变更为已发布时，发布时间为当前时间
            dto.setPublishTime(new Date());
        }

        certificationDao.update(dto);
        return new LinkedInfo(String.valueOf(dto.getId()));
    }

    /**
     * 封面图构建
     * @param dto
     */
    private void buildFile(CertificationAddDTO dto) {
        if (StringUtils.isNotBlank(dto.getFileUrl())) {
            //判断以前有没有上传过，如果没有则上传
            UserFile userFile=userFileDao.findOne(UserFile.builder().fileUrl(dto.getFileUrl()).build());
            if (userFile == null) {
                userFile=userFileService.saveAvatar(userFileService.getFileInfo(dto.getFileUrl()));
            }
            dto.setCoverImgId(userFile.getId());
        }
    }

    /**
     * @api {post} /certification/deletes 认证删除
     * @apiName certificationDeletes
     * @apiGroup certification
     * @apiParam {Number[]} ids 认证ID集合
     * @apiParamExample {json} 请求示例:
     * [1,2,3]
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} [entity] 删除个数
     * @apiSuccessExample {json} 响应示例:
     * {
     * "code": 200,
     * "entity": "2"
     * }
     */
    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        int count = 0;
        if (ListUtils.isNotEmpty(idList)) {
            //逻辑删除
            count= certificationDao.updateByExample(Certification.builder().isDelete(1).build(),idList);
        }
        return new LinkedInfo(count+"");
    }
}
