package com.wdcloud.lms.business.announce.dto;

import com.wdcloud.lms.core.base.model.Announce;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AnnounceDTO extends Announce {
    @NotEmpty
    private List<Long> ids;
    @NotNull
    private Integer allowComment;
}
