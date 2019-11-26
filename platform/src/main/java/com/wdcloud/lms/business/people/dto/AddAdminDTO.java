package com.wdcloud.lms.business.people.dto;

import com.wdcloud.lms.core.base.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddAdminDTO {
    @NotNull
    private List<User> users;
    @NotNull
    private Long orgId;
    @NotNull
    @Length(max = 128)
    private String orgTreeId;
}
