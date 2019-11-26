package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.Discussion;
import lombok.Data;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DiscussionListVo extends Discussion {
    private List<Assign> assign;
    private Long userCount;
    private List<SectionVo> sectionList = new ArrayList<>();
    //评论数
    private ReadCountDTO readCountDTO;
    private Integer isRead;
    private Integer groupId;

    private WarningVO warningVO;

    //分配列表最小开始时间.最大关闭时间,最大截止时间
    private Date minStartTime;
    private Date maxEndTime;
    private Date maxLimitTime;
}
