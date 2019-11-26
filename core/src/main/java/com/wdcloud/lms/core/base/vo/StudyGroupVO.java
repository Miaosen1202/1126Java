package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.StudyGroup;
import lombok.Data;

@Data
public class StudyGroupVO extends StudyGroup {
    private ReadCountDTO readCountDTO;
}
