package com.ddd.airplane.account;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data @Builder
public class AccountCreateRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String nickname;
}
