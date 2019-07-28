package com.example.dao;

import com.example.pojo.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
public class AccountDaoIntegrationTest {
    private IAccountDao accountDao = new AccountDao();

    @Test
    public void getAllAccounts_Test() {
        List<Account> accounts = accountDao.getAllAccounts();
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void getAccountByUsername() {
    }

    @Test
    public void createAccount() {
    }

    @Test
    public void updateAccountParticulars() {
    }

    @Test
    public void getAccountByID() {
    }
}