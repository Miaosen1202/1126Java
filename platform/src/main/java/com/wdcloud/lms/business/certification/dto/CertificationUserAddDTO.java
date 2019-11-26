package com.wdcloud.lms.business.certification.dto;

import com.wdcloud.lms.core.base.model.CertificationUser;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CertificationUserAddDTO extends CertificationUser {
    /**
     * 用户IDS
     */
    private ArrayList<Long> userIds;
}
