package com.ddd.airplane.account;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/v1/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequest) {
        Account account = accountService.createAccount(accountCreateRequest);
        return new AccountDto(account);
    }

    @GetMapping("/v1/accounts/{email}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(
            @PathVariable String email,
            @CurrentAccount Account self
    ) {
        Account account = accountService.getAccount(email);
        return new AccountDto(account);
    }
}
