package com.wdcloud.lms.business.certification;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.certification.dto.LearnerCertificationUserModifyDTO;
import com.wdcloud.lms.business.certification.enums.CertificationStatusEnum;
import com.wdcloud.lms.core.base.dao.CertificationUserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.model.CertificationUser;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION_LEARNER)
public class LearnerCertificationUserDataEdit implements IDataEditComponent {
    @Autowired
    private CertificationUserDao certificationUserDao;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private UserFileDao userFileDao;
    /**
     * @api {post} /certificationLearner/modify 外部认证附件上传
     * @apiName certificationLearnerModify 外部认证附件上传
     * @apiGroup certificationLearner
     *
     * @apiParam {Number} certificationId 认证ID
     * @apiParam {Number} fileUrl 附件Url
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 认证ID
     */
    @Override
    @ValidationParam(clazz = LearnerCertificationUserModifyDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
            LearnerCertificationUserModifyDTO dto = JSON.parseObject(dataEditInfo.beanJson, LearnerCertificationUserModifyDTO.class);
            CertificationUser certificationUser=certificationUserDao.findOne(CertificationUser.builder().userId(WebContext.getUserId()).certificationId(dto.getCertificationId()).build());
            buildFile(dto,certificationUser);
            certificationUser.setStatus(CertificationStatusEnum.PENDINGAPPROVAL.getValue());

            certificationUserDao.update(certificationUser);

        return new LinkedInfo(dto.getCertificationId()+"");
    }

    private void buildFile(LearnerCertificationUserModifyDTO dto, CertificationUser certificationUser) {
        if (StringUtils.isNotBlank(dto.getFileUrl())) {
            //判断以前有没有上传过，如果没有则上传
            UserFile userFile=userFileDao.findOne(UserFile.builder().fileUrl(dto.getFileUrl()).build());
            if (userFile == null) {
                userFile=userFileService.saveAvatar(userFileService.getFileInfo(dto.getFileUrl()));
            }
            certificationUser.setProofFileId(userFile.getId());
        }
    }


}
