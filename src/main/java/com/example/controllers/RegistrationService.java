package com.example.controllers;

import com.example.dao.AccountDao;
import com.example.dao.IAccessDao;
import com.example.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class RegistrationService implements IRegistrationService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private IAccessDao accessDao;

    @Override
    public boolean create(Account resource) {
        if (accountDao.createAccount(resource)) {
            accessDao.createAccess(resource.getUserId());
        }
        return false;
    }

    @Override
    public Account retrieve(String username) {
        Account account;
        try {
            account = accountDao.getAccountByID(username);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return account;
    }

}
