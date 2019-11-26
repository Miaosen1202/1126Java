package com.wdcloud.lms.business.file.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserFileStatusVo {
    @NotNull
    private Long id;
    private Integer status;

    private Integer accessStrategy;
    private Date startTime;
    private Date endTime;
}
