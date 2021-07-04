package com.github.dearrudam.controller;

import com.github.dearrudam.entity.Account;
import com.github.dearrudam.service.AccountService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> create(
        @RequestBody @Valid Account account
    ) {
        return ResponseEntity.ok(this.accountService.add(account));
    }
}
