package com.exercise.doj.repository;

import com.exercise.doj.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Account, Integer> {
}