package com.ddd.airplane.account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

class AccountAdapter extends User {
    private Account account;

    AccountAdapter(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Collection<AccountRole> roles) {
        return roles.stream().map(AccountRole::toAuthority).collect(Collectors.toList());
    }

    public Account getAccount() {
        return account;
    }
}
