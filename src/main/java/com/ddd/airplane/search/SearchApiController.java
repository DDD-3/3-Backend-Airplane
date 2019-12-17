package com.ddd.airplane.search;

import com.ddd.airplane.account.Account;
import com.ddd.airplane.account.CurrentAccount;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api")
public class SearchApiController {
    // TODO : 검색
    @GetMapping("/v1/search")
    @ResponseStatus(HttpStatus.OK)
    public void search(
            @RequestParam String query,
            Pageable pageable,
            @CurrentAccount Account account
    ) {
        //
    }
}
