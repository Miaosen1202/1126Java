package com.wdcloud.lms.business.certification;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.certification.enums.CertificationStatusEnum;
import com.wdcloud.lms.business.certification.enums.CertificationTypeEnum;
import com.wdcloud.lms.core.base.dao.CertificationDao;
import com.wdcloud.lms.core.base.dao.CertificationUserDao;
import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.vo.certification.CertificationUserVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION_USER)
public class CertificationUserDataQuery implements IDataQueryComponent<CertificationUserVO> {

    @Autowired
    private CertificationUserDao certificationUserDao;
    @Autowired
    private CertificationDao certificationDao;
    /**
     * @api {get} /certificationUser/pageList 认证用户列表分页
     * @apiName certificationUserPageList
     * @apiGroup certification
     *
     * @apiParam {Number} certificationId 认证ID
     * @apiParam {String} [searchKey] 用户名称 模糊搜索
     * @apiParam {Number} status 用户证书状态 -1:所有 0:Assigned 1:PendingApproval 2:Certified 3:Expired 4:Unenrolled 注意0,2,3无法搜索
     * @apiParam {Number} orderBy 用户名称排序 0：name升序 1:name降序
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 列表
     * @apiSuccess {Number} entity.list.id 认证用户关联ID
     * @apiSuccess {Number} entity.list.certificationId 认证ID
     * @apiSuccess {Number} entity.list.enrollTime 最近加入时间
     * @apiSuccess {Number} entity.list.completeTime 认证最近完成时间
     * @apiSuccess {Number=0,1,2,3,4} entity.list.status 认证状态 0:Assigned 1:PendingApproval 2:Certified 3:Expired 4:Unenrolled
     * @apiSuccess {Number} entity.list.username 用户名
     * @apiSuccess {Number} entity.list.userId 用户ID
     * @apiSuccess {Number} entity.list.fullName 用户全称
     * @apiSuccess {Number} entity.list.fileUrl 附件URL
     * @apiSuccess {Number} entity.list.fileName 附件名称
     */
    @Override
    public PageQueryResult<? extends CertificationUserVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = new HashMap();
        params.putAll(param);
        PageHelper.startPage(pageIndex,pageSize);
        Page<CertificationUserVO> page= (Page<CertificationUserVO>) certificationUserDao.findCertificationUserListByAdmin(params);
        //buildStatus(Long.valueOf(param.get("certificationId")),page.getResult());
        return new PageQueryResult(page.getTotal(),page.getResult(),pageSize,pageIndex);
    }

    /**
     * 构建认证状态:P和U不随时间变化
     * CAE会随时间变化:
     *     永久 A->E
     *     周期 C->A->E
     */
    private void buildStatus(Long certificationId, List<CertificationUserVO> userVOList) {
        if(ListUtils.isNotEmpty(userVOList)){
            Certification certification=certificationDao.get(certificationId);
            Date now = new Date();
            //永久认证
            if (CertificationTypeEnum.PERMANENT.getValue().equals(certification.getType())) {
                userVOList.forEach(userVO->{
                    userVO.setShowStatus(userVO.getStatus());
                    if (userVO.getStatus().equals(CertificationStatusEnum.ASSIGNED.getValue())){
                        //当前时间大于 最新加人时间+opDay 则过期
                        if (now.after(DateUtil.daysOperation(userVO.getEnrollTime(),certification.getOpDay()))){
                            userVO.setShowStatus(CertificationStatusEnum.EXPIRED.getValue());
                        }
                    }
                });
            }else{//周期认证
                userVOList.forEach(userVO->{
                    userVO.setShowStatus(userVO.getStatus());
                    if (userVO.getStatus().equals(CertificationStatusEnum.CERTIFIED.getValue())){
                        /**
                         * 截止日期=完成时间+有效月份
                         * C=当前时间<=截止日期-opday
                         * A=截止日期-opday <当前时间< =截止日期
                         * E=当前时间>截止日期
                         */
                        Date endTime = DateUtil.monthOperation(userVO.getCompleteTime(), certification.getValidity());
                        if (now.after(DateUtil.daysOperation(endTime,-certification.getOpDay()))&&now.before(endTime)) {
                            userVO.setShowStatus(CertificationStatusEnum.ASSIGNED.getValue());
                        }else if(now.after(endTime)){
                            userVO.setShowStatus(CertificationStatusEnum.EXPIRED.getValue());
                        }
                    }else if(userVO.getStatus().equals(CertificationStatusEnum.ASSIGNED.getValue())){
                        //当前时间大于 最新加人时间+opDay 则过期
                        if (now.after(DateUtil.daysOperation(userVO.getEnrollTime(),certification.getOpDay()))){
                            userVO.setShowStatus(CertificationStatusEnum.EXPIRED.getValue());
                        }
                    }
                });
            }
        }
    }


    @Override
    public CertificationUserVO find(String id) {
        return null;
    }
}
