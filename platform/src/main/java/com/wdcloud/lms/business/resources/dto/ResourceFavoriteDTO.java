package com.wdcloud.lms.business.resources.dto;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupDelete;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ResourceFavoriteDTO {

    @NotNull(groups = GroupAdd.class)
    private Long resourceId;
}
