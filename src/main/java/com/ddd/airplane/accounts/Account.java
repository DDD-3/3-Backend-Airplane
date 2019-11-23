package com.ddd.airplane.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = "email")
@Builder @NoArgsConstructor @AllArgsConstructor
class Account {
    private String email;
    @JsonIgnore
    private String password;
    private String nickname;
}
