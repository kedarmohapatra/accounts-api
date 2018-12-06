package com.exercise.doj;

import com.exercise.doj.config.AccountsApiApplication;
import com.exercise.doj.controller.AccountsController;
import com.exercise.doj.model.User;
import com.exercise.doj.repository.AccountsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountsApiApplication.class)
public class AccountsApiApplicationTests {

    private static final String API_ROOT = "http://localhost:8080/account-project/rest/account/json";

    MockMvc mockMvc;

    @Autowired
    AccountsRepository repository;

    @Before
    public void setUp() {
        AccountsController accountsController = new AccountsController();
        ReflectionTestUtils.setField(accountsController, "repository", repository);
        mockMvc = MockMvcBuilders.standaloneSetup(accountsController).build();
    }

    private User createUser() {
        User user = new User();
        user.setAccountNumber("11");
        user.setFirstName("John");
        user.setLastName("Doe");

        return user;
    }

    @Test
    public void shouldReturnOkWhenGetAllUsers() throws Exception {
        User persistedUser = repository.save(createUser());
        mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber", is(persistedUser.getAccountNumber())))
                .andExpect(jsonPath("$[0].firstName", is(persistedUser.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(persistedUser.getLastName())));

    }

}
