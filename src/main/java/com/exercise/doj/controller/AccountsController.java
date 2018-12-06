package com.exercise.doj.controller;

import com.exercise.doj.model.User;
import com.exercise.doj.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-project/rest/account/json")
public class AccountsController {

    @Autowired
    private AccountsRepository repository;

    @GetMapping(produces = "application/json")
    public Iterable<User> getAll() {
        return repository.findAll();
    }
}
