package com.wdcloud.lms.business.certification;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CertificationHistoryUserDao;
import com.wdcloud.lms.core.base.model.CertificationHistoryUser;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION_LEARNER_HIS)
public class LearnerCertificationHisDataQuery implements IDataQueryComponent<CertificationHistoryUser> {

    @Autowired
    private CertificationHistoryUserDao certificationHistoryUserDao;


    /**
     * @api {get} /certificationLearnerHis/pageList 学习者历史证书列表
     * @apiName certificationLearnerHisPageList
     * @apiGroup certificationLearner
     *
     * @apiParam {Number} certificationId 认证ID
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
     * @apiSuccess {Number} entity.list.certificationId 认证ID
     * @apiSuccess {Number} entity.list.userId 用户ID
     * @apiSuccess {Number} entity.list.createTime 证书获得时间
     */
    @Override
    public PageQueryResult<? extends CertificationHistoryUser> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        CertificationHistoryUser certificationHistoryUser = CertificationHistoryUser.builder()
                .certificationId(Long.valueOf(param.get("certificationId")))
                .userId(WebContext.getUserId())
                .build();
        PageHelper.startPage(pageIndex,pageSize);
        Page<CertificationHistoryUser> page = (Page<CertificationHistoryUser>) certificationHistoryUserDao.find(certificationHistoryUser);

        return new PageQueryResult(page.getTotal(),page.getResult(),pageSize,pageIndex);
    }

}
