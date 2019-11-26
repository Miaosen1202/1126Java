package com.wdcloud.lms.business.people.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CourseUserAddVo {
    @NotNull
    private Long courseId;
    @NotNull
    private Long sectionId;
    @NotNull
    private Long roleId;

    @NotEmpty
    private List<InviteInfo> invites;

    @Data
    public static class InviteInfo {
        @NotNull
        private Integer inviteType;
        private String nickname;
        private String email;
        private String sisId;
        private String loginId;
    }
}
