package com.exercise.doj.controller;

import com.exercise.doj.model.Account;
import com.exercise.doj.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("/account-project/rest/account/json")
public class AccountsController {

    @Value("${account.created.message}")
    private String accountCreatedMessage;

    @Value("${account.deleted.message}")
    private String accountDeletedMessage;

    @Autowired
    private AccountsRepository repository;

    @GetMapping(produces = "application/json")
    public Iterable<Account> getAllAccounts() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, String> createAccount(@RequestBody Account account) {
        repository.save(account);
        return singletonMap("message",accountCreatedMessage);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, String> deleteAccount(@PathVariable Integer id){
        repository.delete(id);
        return singletonMap("message",accountDeletedMessage);
    }
}
