package com.wdcloud.lms.business.announce.dto;

import com.wdcloud.lms.core.base.model.Announce;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class AnnounceAddDTO extends Announce {

    private List<Assign> assign;

    @NotNull(groups = GroupModify.class)
    private Long id;
    @NotNull(groups = {GroupAdd.class})
    private Long courseId;
    @NotBlank(groups = GroupAdd.class)
    private String topic;
    @NotBlank(groups = GroupAdd.class)
    private String content;
    //文件ID
    private String fileId;
}
