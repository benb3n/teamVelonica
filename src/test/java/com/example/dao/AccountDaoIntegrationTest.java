package com.example.dao;

import com.example.pojo.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AccountDaoIntegrationTest {
    private IAccountDao accountDao = new AccountDao();

    @Test
    public void getAllAccounts_Test() {
        List<Account> accounts = accountDao.getAllAccounts();
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void getAccountByEmail_Test() {
        String trueEmail = "benedictwyr@gmail.com";
        String falseEmail = "test@test.com";
        try {
            Account account = accountDao.getAccountByEmail(trueEmail);
            assertNotNull(account);

            account = accountDao.getAccountByEmail(falseEmail);
            assertNull(account);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAccount() {
        try {
            int maxUserID = accountDao.getMaxUserID() + 1;
            Account testAccount =
                    new Account(
                            maxUserID,
                            "Password123",
                            "Test",
                            "Test User",
                            "testing@test.com",
                            "Female",
                            "19-01-1900",
                            "Test Citizen",
                            null,
                            "West");

            boolean success = accountDao.createAccount(testAccount);
            assertTrue(success);

            Account account = accountDao.getAccountByID(maxUserID);
            assertNotNull(account);

            success = accountDao.deleteAccount(account);
            assertTrue(success);

            account = accountDao.getAccountByID(maxUserID);
            assertNull(account);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateAccountParticulars() {
        try {
            int maxUserID = accountDao.getMaxUserID() + 1;
            Account testAccount =
                    new Account(
                            maxUserID,
                            "Password123",
                            "Test",
                            "Test User",
                            "testing@test.com",
                            "Female",
                            "19-01-1900",
                            "Test Citizen",
                            null,
                            "West");

            boolean success = accountDao.createAccount(testAccount);
            assertTrue(success);

            Account account = accountDao.getAccountByID(maxUserID);
            assertNotNull(account);

            String birthDate = "30-01-1995";
            testAccount.setBirthDate(birthDate);
            success = accountDao.updateAccountParticulars(testAccount);
            assertTrue(success);

            account = accountDao.getAccountByID(maxUserID);
            assertEquals(account.getBirthDate(), birthDate);

            success = accountDao.deleteAccount(account);
            assertTrue(success);

            account = accountDao.getAccountByID(maxUserID);
            assertNull(account);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}