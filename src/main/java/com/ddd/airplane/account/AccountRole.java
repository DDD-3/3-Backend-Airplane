package com.ddd.airplane.account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum AccountRole {
    ROLE_USER;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(name());
    }
}
