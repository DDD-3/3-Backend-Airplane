package com.ddd.airplane.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum AccountRole {
    ROLE_USER;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(name());
    }
}
