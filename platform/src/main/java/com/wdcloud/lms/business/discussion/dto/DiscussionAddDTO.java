package com.wdcloud.lms.business.discussion.dto;

import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.lms.core.base.vo.ReadCountDTO;
import com.wdcloud.lms.core.base.vo.SectionVo;
import com.wdcloud.lms.core.base.vo.StudyGroupVO;
import com.wdcloud.lms.core.base.vo.UserVo;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DiscussionAddDTO extends Discussion {
    private Long isSubmited;
    //分配列表最大的dueTime
    private Long maxDueTime;

    /***********学生评论开始时间***********/
    private AssignUser assignUser;


    //分配
    private List<Assign> assign;
    //创建者
    private UserVo author;
    //回复统计，未读统计
    private ReadCountDTO readCountDTO;

    private List<StudyGroupVO> studyGroupList;

    private List<SectionVo> sectionList = new ArrayList<>();
    private Long userCount;
    private String session;

    private UserFile attachmentFile;

    private String fileId;//附件ID
    /**
     * 入参校验
     */
    //作业小组ID  cos_assignment_group.id=cos_assignment_group_item.assignment_group_id新增/更新  cos_assignment_group_item. origin_type=2  并且origin_id=讨论ID
    private Long assignmentGroupId;

    @NotNull(groups={GroupModify.class})
    private Long id;

    @NotNull(groups={ GroupAdd.class, GroupModify.class})
    private Long courseId;

    @NotBlank(groups={ GroupAdd.class, GroupModify.class})
    private String title;

    @Range(min = 1,max = 2,groups = { GroupAdd.class, GroupModify.class})
    private Integer type;

    @Range(min = 0,max = 1,groups = { GroupAdd.class, GroupModify.class})
    private Integer status;

    @Range(min = 0,max = 1,groups = { GroupAdd.class, GroupModify.class})
//    @NotNull(groups = { GroupAdd.class, GroupModify.class})
    private Integer isGrade;

    @Range(min = 0,max = 1,groups = { GroupAdd.class, GroupModify.class})
    private Integer isPin;

    @Range(min = 0,max = 1,groups = { GroupAdd.class, GroupModify.class})
    private Integer allowComment;
}
