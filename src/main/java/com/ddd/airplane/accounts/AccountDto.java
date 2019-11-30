package com.ddd.airplane.accounts;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data @Builder
public class AccountDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String nickname;
}
