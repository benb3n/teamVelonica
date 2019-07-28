package com.example.controllers;

import com.example.dao.AccountDao;
import com.example.dao.IAccessDao;
import com.example.helpers.AccessType;
import com.example.pojo.Access;
import com.example.pojo.Account;
import com.example.security.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RegistrationService implements IRegistrationService {
    private static final Set<AccessType> ADMIN_TYPES = Stream.of(AccessType.ADMIN, AccessType.SUPER_ADMIN).collect(Collectors.toSet());

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private IAccessDao accessDao;

    @Override
    public boolean create(Account resource) {
        if (accountDao.createAccount(resource)) {
            String password = resource.getPassword();
            SecurityHelper sh = new SecurityHelper();
            String hashedPass = sh.encrypt(password).toString();
            resource.setPassword(hashedPass);
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
    public Account retrieve(String email) {
        return accountDao.getAccountByEmail(email);
    }

    @Override
    public Account retrieve(int userID) {
        return accountDao.getAccountByID(userID);
    }

    @Override
    public List<Access> retrieveAllAccesses(int id) {
        return accessDao.retrieveAccessByUserID(id);
    }

    @Override
    public boolean deleteAccess(int currentUserID, Access access) {
        List<Access> accesses = retrieveAllAccesses(access.getUserID());

        boolean deleteAccessExists = accesses.stream().anyMatch(current -> current.equals(access));
        boolean isCurrentUserAdmin = retrieveAllAccesses(currentUserID).stream().anyMatch(this::isAdmin);

        if (isCurrentUserAdmin && deleteAccessExists)
            return accessDao.deleteAccess(access);

        return false;
    }

    private boolean isAdmin(Access user) {
        return ADMIN_TYPES.contains(user.getAccess());
    }

}
