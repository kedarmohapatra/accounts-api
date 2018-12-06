package com.exercise.doj.repository;

import com.exercise.doj.model.User;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<User, Integer> {
}