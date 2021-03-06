package com.exercise.doj;

import com.exercise.doj.config.AccountsApiApplication;
import com.exercise.doj.controller.AccountsController;
import com.exercise.doj.model.Account;
import com.exercise.doj.repository.AccountsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountsApiApplication.class)
@WebAppConfiguration
public class AccountsApiApplicationTests {

    private static final String API_ROOT = "http://localhost:8080/account-project/rest/account/json";
    private static final String USER_JSON = "{" +
            "\"firstName\":\"kedar\"," +
            "\"lastName\":\"mohapatra\"," +
            "\"accountNumber\":\"1232\"" +
            "}";

    private static final String USER_JSON_INVALID = "{" +
            "\"firstName\":\"kedar\"," +
            "\"lastName\":\"mohapatra\"" +
            "}";

    @Value("${account.created.message}")
    private String accountCreatedMessage;

    @Value("${account.deleted.message}")
    private String accountDeletedMessage;

    @Value("${account.invalid.request}")
    private String jsonRequestInvalid;

    @Value("${account.invalid.id}")
    private String accountIdInvalid;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private AccountsRepository repository;

    private Account createAccount() {
        Account account = new Account();
        account.setAccountNumber("11");
        account.setFirstName("John");
        account.setLastName("Doe");

        return account;
    }

    @Before
    public void setUp() {
        AccountsController accountsController = new AccountsController();
        ReflectionTestUtils.setField(accountsController, "repository", repository);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnOkWhenGetAllAccount() throws Exception {
        Account persistedAccount = repository.save(createAccount());
        mockMvc.perform(get(API_ROOT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber", is(persistedAccount.getAccountNumber())))
                .andExpect(jsonPath("$[0].firstName", is(persistedAccount.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(persistedAccount.getLastName())));

    }

    @Test
    public void shouldSaveAccount() throws Exception {
        mockMvc.perform(post(API_ROOT).contentType(MediaType.APPLICATION_JSON).content(USER_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(accountCreatedMessage)));
    }

    @Test
    public void createShouldReturnErrorMessageForInvalidFormat() throws Exception {
        mockMvc.perform(post(API_ROOT).contentType(MediaType.APPLICATION_JSON).content(USER_JSON_INVALID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(jsonRequestInvalid)));
    }

    @Test
    public void shouldDeleteAccountForCorrectId() throws Exception {
        Account persistedAccount = repository.save(createAccount());
        mockMvc.perform(delete(API_ROOT+"/"+persistedAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(accountDeletedMessage)));
    }

    @Test
    public void deleteShouldReturnErrorMessgeForInvalidAccountId() throws Exception {
        mockMvc.perform(delete(API_ROOT+"/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(accountIdInvalid)));
    }
}
