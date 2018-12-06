package com.exercise.doj.controller;

import com.exercise.doj.model.Account;
import com.exercise.doj.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-project/rest/account/json")
public class AccountsController {

    public static final String ACCOUNT_SUCCESSFULLY_ADDED = "Account has been successfully added";

    @Autowired
    private AccountsRepository repository;

    @GetMapping(produces = "application/json")
    public Iterable<Account> getAllAccounts() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String createAccount(@RequestBody Account account) {
        repository.save(account);
        return ACCOUNT_SUCCESSFULLY_ADDED;
    }
}
