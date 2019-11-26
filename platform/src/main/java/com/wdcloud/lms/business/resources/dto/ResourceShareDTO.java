package com.wdcloud.lms.business.resources.dto;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ResourceShareDTO {

    @NotNull(groups = GroupModify.class)
    private Long id;

    private Long resourceId;

    @NotNull(groups = GroupAdd.class)
    private Long originId;

    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer shareRange;

    @NotNull(groups = {GroupAdd.class,GroupModify.class})
    private Integer licence;

    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    private String name;

    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    private String description;

    private List<String> tags;

    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    private String fileUrl;

    private Integer gradeStart;

    private Integer gradeEnd;

    @NotNull(groups = GroupAdd.class)
    private Integer originType;

    private Long updateId;

    private String versionNotes;
}
