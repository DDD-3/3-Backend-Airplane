package com.ddd.airplane.accounts;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    Account createAccount(AccountDto accountDto) {
        String email = accountDto.getEmail();

        if (accountRepository.findByEmail(email) != null) {
            throw new AccountAlreadyRegisteredException(email);
        }

        Account account = modelMapper.map(accountDto, Account.class);

        return accountRepository.save(account);
    }

    Account getAccount(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new AccountNotFoundException(email);
        }

        return account;
    }

    public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }
}
