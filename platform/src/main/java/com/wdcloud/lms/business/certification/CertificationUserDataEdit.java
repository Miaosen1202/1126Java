package com.wdcloud.lms.business.certification;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.certification.dto.CertificationUserAddDTO;
import com.wdcloud.lms.business.certification.dto.CertificationUserModifyDTO;
import com.wdcloud.lms.business.certification.enums.CertificationStatusEnum;
import com.wdcloud.lms.business.certification.enums.CertificationUserOpTypeEnum;
import com.wdcloud.lms.config.AccessLimit;
import com.wdcloud.lms.core.base.dao.CertificationDao;
import com.wdcloud.lms.core.base.dao.CertificationHistoryUserDao;
import com.wdcloud.lms.core.base.dao.CertificationUserDao;
import com.wdcloud.lms.core.base.dao.CertificationUserRejectHistoryDao;
import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.model.CertificationHistoryUser;
import com.wdcloud.lms.core.base.model.CertificationUser;
import com.wdcloud.lms.core.base.model.CertificationUserRejectHistory;
import com.wdcloud.server.frame.interfaces.IDataEditComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.validate.annotation.ValidationParam;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION_USER)
public class CertificationUserDataEdit implements IDataEditComponent {
    @Autowired
    private CertificationDao certificationDao;
    @Autowired
    private CertificationUserDao certificationUserDao;
    @Autowired
    private CertificationHistoryUserDao certificationHistoryUserDao;
    @Autowired
    private CertificationUserRejectHistoryDao certificationUserRejectHistoryDao;
    /**
     * @api {post} /certificationUser/add 认证用户添加
     * @apiName certificationUserAdd 认证用户添加
     * @apiGroup certification
     *
     * @apiParam {Number} certificationId 认证ID
     * @apiParam {Object[]} userIds 用户IDS
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增个数
     */
    @Override
    @AccessLimit
    @ValidationParam(clazz = CertificationUserAddDTO.class, groups = GroupAdd.class)
    public LinkedInfo add(DataEditInfo dataEditInfo) {
        CertificationUserAddDTO dto = JSON.parseObject(dataEditInfo.beanJson, CertificationUserAddDTO.class);
        int count = 0;
        if (ListUtils.isNotEmpty(dto.getUserIds())) {
            List<CertificationUser> certificationUserList = new ArrayList<>();
            Date now = new Date();
            //校验此人是否注册过
            Example example = certificationUserDao.getExample();
            example.createCriteria()
                    .andEqualTo(CertificationUser.CERTIFICATION_ID, dto.getCertificationId())
                    .andIn(CertificationUser.USER_ID,dto.getUserIds());
            List<CertificationUser> certificationUsers=certificationUserDao.find(example);
            if (ListUtils.isNotEmpty(certificationUsers)) {
                List<Long> userIds=certificationUsers.stream().map(CertificationUser::getUserId).collect(Collectors.toList());
                dto.getUserIds().removeAll(userIds);
            }

            dto.getUserIds().forEach(userId->{
                CertificationUser certificationUser = CertificationUser.builder()
                        .certificationId(dto.getCertificationId())
                        .userId(userId)
                        .status(0)
                        .enrollTime(now)
                        .build();
                certificationUserList.add(certificationUser);
            });
            if (ListUtils.isNotEmpty(certificationUserList)) {
                count=certificationUserDao.batchSave(certificationUserList);
            }
        }

        return new LinkedInfo(count+"");
    }

    /**
     * @api {post} /certificationUser/modify 认证用户修改
     * @apiName certificationUserModify 认证用户修改
     * @apiGroup certification
     *
     * @apiParam {Number} id 认证用户关联ID
     * @apiParam {Number} opType 操作类型 1:Mark Complete 2：Reject 3：Unenroll 4：ReEnroll
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {String} entity 新增个数
     */
    @Override
    @ValidationParam(clazz = CertificationUserModifyDTO.class, groups = GroupModify.class)
    public LinkedInfo update(DataEditInfo dataEditInfo) {
        CertificationUserModifyDTO dto = JSON.parseObject(dataEditInfo.beanJson, CertificationUserModifyDTO.class);
        CertificationUser certificationUser=certificationUserDao.get(dto.getId());
        Date now = DateUtil.now();
        if (CertificationUserOpTypeEnum.MARKCOMPLETE.getValue().equals(dto.getOpType())) {
            //标记完成
            certificationUser.setCompleteTime(now);
            certificationUser.setStatus(CertificationStatusEnum.CERTIFIED.getValue());
            certificationUserDao.update(certificationUser);
            //记录完成历史
            Certification certification=certificationDao.get(certificationUser.getCertificationId());
            CertificationHistoryUser certificationHistoryUser = CertificationHistoryUser.builder()
                    .certificationId(certificationUser.getCertificationId())
                    .userId(certificationUser.getUserId())
                    .startTime(now)
                    .endTime(DateUtil.monthOperation(now,certification.getValidity()))
                    .build();
            certificationHistoryUserDao.save(certificationHistoryUser);
        }else  if(CertificationUserOpTypeEnum.REJECT.getValue().equals(dto.getOpType())) {
            //拒绝 外部认证
            certificationUser.setRejectTime(now);
            certificationUser.setEnrollTime(now);
            certificationUser.setStatus(CertificationStatusEnum.ASSIGNED.getValue());
            certificationUserDao.update(certificationUser);
            //保存拒绝记录
            CertificationUserRejectHistory history = CertificationUserRejectHistory.builder()
                    .certificationId(certificationUser.getCertificationId())
                    .userId(certificationUser.getUserId())
                    .build();
            certificationUserRejectHistoryDao.save(history);
        }else  if(CertificationUserOpTypeEnum.UNENROLL.getValue().equals(dto.getOpType())) {
            //注销用户
            certificationUser.setUnenrolllTime(now);
            certificationUser.setStatus(CertificationStatusEnum.UNENROLLED.getValue());
            certificationUserDao.update(certificationUser);
        }else  if(CertificationUserOpTypeEnum.REENROLL.getValue().equals(dto.getOpType())) {
            //重新注册
//            certificationUser.setEnrollTime(now);
//            certificationUser.setStatus(CertificationStatusEnum.ASSIGNED.getValue());
//            certificationUser.setCompleteTime(null);
//            certificationUserDao.updateIncludeNull(certificationUser);
            certificationUserDao.updateByExample(CertificationUser.builder()
                    .status(CertificationStatusEnum.ASSIGNED.getValue())
                    .enrollTime(now).completeTime(null)
                    .build(),
                    List.of(dto.getId()));
        }

        return new LinkedInfo(dto.getId()+"");
    }


    @Override
    public LinkedInfo delete(DataEditInfo dataEditInfo) {
        List<Long> idList = JSON.parseArray(dataEditInfo.beanJson, Long.class);
        return new LinkedInfo("");
    }
}
