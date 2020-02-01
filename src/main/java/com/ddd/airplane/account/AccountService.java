package com.ddd.airplane.account;

import com.ddd.airplane.util.RandomNicknameGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RandomNicknameGenerator randomNicknameGenerator;

    public Account createAccount(AccountCreateRequest accountCreateRequest) {
        String email = accountCreateRequest.getEmail();

        if (accountRepository.findByEmail(email) != null) {
            throw new AccountAlreadyRegisteredException(email);
        }

        Account account = modelMapper.map(accountCreateRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setNickname(randomNicknameGenerator.generate());

        return accountRepository.save(account);
    }

    Account getAccount(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            account = new Account();
        }

        return account;
    }

    public void deleteAll() {
        accountRepository.truncate();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new AccountAdapter(account);
    }
}
