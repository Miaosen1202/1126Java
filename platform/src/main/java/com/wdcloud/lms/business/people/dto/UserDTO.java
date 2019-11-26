package com.wdcloud.lms.business.people.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    private Long id;
    @NotNull
    @Length(max = 64)
    private String lastName;
    @NotNull
    @Length(max = 64)
    private String firstName;
    @Length(max = 128)
    private String sisId;
    @NotNull
    @Length(max = 64)
    private String loginId;
    @NotNull
    private Long orgId;
    @NotNull
    @Length(max = 128)
    private String orgTreeId;
    @Email
    private String email;

    private String nickname;
    private String timeZone;
}
