package com.ddd.airplane.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter @Setter @EqualsAndHashCode(of = "email")
@Builder @NoArgsConstructor @AllArgsConstructor
class Account implements Serializable {
    private String email;
    @JsonIgnore
    private String password;
    private String nickname;
    @JsonIgnore
    private Set<AccountRole> roles;
}
