package com.ddd.airplane.accounts;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "계정")
@RestController
@RequestMapping("/api")
public class AccountApiController {
    private final AccountService accountService;

    @ApiOperation("회원 가입")
    @PostMapping("/v1/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public Account signUp(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @ApiOperation("계정 조회")
    @GetMapping("/v1/accounts/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable String email) {
        return accountService.getAccount(email);
    }

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }
}
