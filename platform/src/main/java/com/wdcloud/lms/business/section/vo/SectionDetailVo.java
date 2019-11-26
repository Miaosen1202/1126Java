package com.wdcloud.lms.business.section.vo;

import com.wdcloud.lms.business.user.vo.SectionUserVo;
import com.wdcloud.lms.core.base.model.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SectionDetailVo extends Section {
    private List<SectionUserVo> activeUsers;
    private List<SectionUserVo> pendingUsers;
}
