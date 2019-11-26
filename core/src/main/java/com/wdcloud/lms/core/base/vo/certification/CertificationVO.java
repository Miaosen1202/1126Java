package com.wdcloud.lms.core.base.vo.certification;

import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.model.UserFile;
import lombok.Data;

@Data
public class CertificationVO extends Certification {

    private UserFile userFile;
}
