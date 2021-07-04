package com.github.dearrudam.service;

import com.github.dearrudam.entity.Account;
import com.github.dearrudam.repository.AccountRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account add(Account account) {
        return this.accountRepository.save(account);
    }
}
