package com.example.controllers;

import com.example.dao.AccountDao;
import com.example.dao.IAccessDao;
import com.example.pojo.Access;
import com.example.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

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
    public boolean createAccess(Access access) {
        List<Access> currentAccesses = accessDao.retrieveAccessByUserID(access.getUserID());

        boolean accessExists = currentAccesses.stream().anyMatch(current -> current.equals(access));
        if (accessExists) {
            System.out.println("Access already exists: " + access);
            return false;
        }

        return accessDao.createAccess(access);
    }

    @Override
    public Account retrieve(String username) {
        Account account;
        try {
            account = accountDao.getAccountByUsername(username);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return account;
    }

    @Override
    public Account retrieve(int userID) {
        Account account;
        try {
            account = accountDao.getAccountByID(userID);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return account;
    }

    @Override
    public List<Access> retrieveAllAccesses(int id) {
        return accessDao.retrieveAccessByUserID(id);
    }
}
