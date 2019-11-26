package com.wdcloud.lms.business.certification;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.AssignmentGroupItemDTO;
import com.wdcloud.lms.base.service.AssignService;
import com.wdcloud.lms.base.service.AssignmentGroupItemService;
import com.wdcloud.lms.base.service.ContentViewRecordService;
import com.wdcloud.lms.business.discussion.enums.AssignmentGroupItemOriginTypeEnum;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.ContentViewRecordOrignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignmentGroupItem;
import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.model.Discussion;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_CERTIFICATION,
        functionName = Constants.FUNCTION_TYPE_COPY
)
public class CertificationCopyEdit implements ISelfDefinedEdit {

    @Autowired
    private CertificationDao certificationDao;

    /**
     * @api {post} /certification/copy/edit 认证复制
     * @apiDescription 认证复制
     * @apiName certificationCopy
     * @apiGroup certification
     * @apiParam {Number} id 认证Id
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增ID
     */
    @Override
    @AccessLimit
    public LinkedInfo edit(DataEditInfo dataEditInfo) {
        Certification certification = JSON.parseObject(dataEditInfo.beanJson, Certification.class);
        Certification source=certificationDao.get(certification.getId());

        Certification target = BeanUtil.beanCopyProperties(source,Certification.class,"id,status,publishTime,createTime,updateTime,createUserId,updateUserId");
        target.setName(target.getName()+" copy");

        certificationDao.save(target);

        return new LinkedInfo(String.valueOf(target.getId()));
    }


}
