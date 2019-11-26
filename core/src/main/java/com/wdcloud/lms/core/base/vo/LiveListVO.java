package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.AssignUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class LiveListVO {
    private Long id;
    private String title;
    private Long instructorId;
    private String instructor;
    private String instructorUrl;
    private Date startTime;
    private Date endTime;
    private String description;
    private Long courseId;
    private String status;
    private String liveId;
    private String panelistJoinUrl;
    private String organizerToken;
    private String attendeeJoinUrl;
    private String nickname;
    private String attendeeJoinUrlOfVod;
    private String vodId;
    private String password;
    private List<Assign> assigns;
    private Long userCount;
    private List<SectionVo> sectionList = new ArrayList<>();
}
