package com.ddd.airplane.accounts;

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
    public Account createAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/v1/accounts/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(
            @PathVariable String email,
            @CurrentAccount Account account
    ) {
        return accountService.getAccount(email);
    }
}
