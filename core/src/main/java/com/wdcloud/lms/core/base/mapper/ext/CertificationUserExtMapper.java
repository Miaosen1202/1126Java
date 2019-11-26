package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.CertificationUser;
import com.wdcloud.lms.core.base.vo.certification.CertificationUserVO;
import com.wdcloud.lms.core.base.vo.certification.LearnerCertificationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CertificationUserExtMapper {
    int batchSave(List<CertificationUser> certificationUserList);

    List<CertificationUserVO> findCertificationUserListByAdmin(Map<String, Object> params);

    List<LearnerCertificationVO> findCertListForSync( @Param("type")Integer type, @Param("status") Integer status);
}
