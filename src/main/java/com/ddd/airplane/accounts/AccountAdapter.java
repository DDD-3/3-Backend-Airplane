package com.ddd.airplane.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

class AccountAdapter extends User {
    AccountAdapter(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
    }

    private static Collection<? extends GrantedAuthority> authorities(Collection<AccountRole> roles) {
        return roles.stream().map(AccountRole::toAuthority).collect(Collectors.toList());
    }
}
