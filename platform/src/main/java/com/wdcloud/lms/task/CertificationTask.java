package com.wdcloud.lms.task;

import com.wdcloud.lms.business.certification.enums.CertificationStatusEnum;
import com.wdcloud.lms.business.certification.enums.CertificationTypeEnum;
import com.wdcloud.lms.core.base.dao.CertificationUserDao;
import com.wdcloud.lms.core.base.model.CertificationUser;
import com.wdcloud.lms.core.base.vo.certification.LearnerCertificationVO;
import com.wdcloud.utils.DateUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class CertificationTask {

    @Autowired
    private CertificationUserDao certificationUserDao;

    /**
     * 每天凌晨1点执行一次
     * @throws Exception
     */
//    @Scheduled(cron = "0 0/10 * * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() throws Exception {
      log.info("【CertificationTask任务】开始时间:{}",System.currentTimeMillis());
        doWork();
      log.info("【CertificationTask任务】结束时间:{}",System.currentTimeMillis());
    }

    /**
     * 用户证书状态 定时跑
     */
    private void doWork() {
        doWorkStatus(null, CertificationStatusEnum.ASSIGNED.getValue());
        doWorkStatus(CertificationTypeEnum.CYCLE.getValue(),CertificationStatusEnum.CERTIFIED.getValue());
        doWorkStatus(CertificationTypeEnum.CYCLE.getValue(), CertificationStatusEnum.EXPIRED.getValue());
    }


    private void doWorkStatus(Integer type, Integer status) {
        List<LearnerCertificationVO> learnerCertificationVOList= certificationUserDao.findCertListForSync(type,status);
        if (ListUtils.isNotEmpty(learnerCertificationVOList)){
            buildStatus(learnerCertificationVOList,status);
        }
    }

    /**
     *  A->E:当前时间 > 注册日期（凌晨） + opDay 则标记E
     *  C->A:当前时间 > 完成日期（凌晨） + 周期 - opDay 则标记A
     *  E->A:当前时间 > 失效日期（凌晨） + 周期 - opDay  则标记A
     * @param learnerCertificationVOList
     * @param status
     */
    private void buildStatus(List<LearnerCertificationVO> learnerCertificationVOList, Integer status) {
        Date now = DateUtil.now();
        List<Long> idList = new ArrayList<>();

        if (CertificationStatusEnum.ASSIGNED.getValue().equals(status)) {
            learnerCertificationVOList.forEach(vo->{
                if (now.after(DateUtil.daysOperation(DateUtil.getDayStartOfDate(vo.getEnrollTime()), vo.getOpDay()))) {
                    idList.add(vo.getId());
                }
            });
        }else if(CertificationStatusEnum.CERTIFIED.getValue().equals(status)) {
            learnerCertificationVOList.forEach(vo->{
                Date nextEnd=DateUtil.monthOperation(DateUtil.getDayStartOfDate(vo.getCompleteTime()),vo.getValidity());
                Date nextA=DateUtil.daysOperation(nextEnd,-vo.getOpDay());
                if (now.after(nextA)) {
                    idList.add(vo.getId());
                }
            });
        }else if(CertificationStatusEnum.EXPIRED.getValue().equals(status)) {
            learnerCertificationVOList.forEach(vo->{
                Date nextEnd=DateUtil.monthOperation(DateUtil.getDayStartOfDate(vo.getExpireTime()),vo.getValidity());
                Date nextA=DateUtil.daysOperation(nextEnd,-vo.getOpDay());
                if (now.after(nextA)) {
                    idList.add(vo.getId());
                }
            });
        }
        if (ListUtils.isNotEmpty(idList)) {
            CertificationUser.CertificationUserBuilder builder = CertificationUser.builder();

            if(CertificationStatusEnum.ASSIGNED.getValue().equals(status)) {
                builder.status(CertificationStatusEnum.EXPIRED.getValue())
                        .expireTime(DateUtil.getDayStartOfDate(now));
            }else{
                builder.status(CertificationStatusEnum.ASSIGNED.getValue())
                        .enrollTime(now).completeTime(null).expireTime(null);
            }
            certificationUserDao.updateByExample(builder.build(),idList);
        }
    }


}