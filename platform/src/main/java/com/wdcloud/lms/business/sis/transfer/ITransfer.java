package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.core.base.model.Org;

import java.util.List;

public interface ITransfer<SisType, SysType> {

    /**
     * 转换对象
     * @param data 业务数据
     * @param opUserId 操作人id
     * @param rootOrg 根机构
     * @return List<SysType>
     */
    List<SysType> transfer(List<SisType> data, Long opUserId, Org rootOrg);
}
