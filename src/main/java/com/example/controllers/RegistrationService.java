package com.example.controllers;

import com.example.dao.AccountDao;
import com.example.dao.IAccessDao;
import com.example.dao.IAccountDao;
import com.example.dao.IOrganisationDAO;
import com.example.helpers.AccessType;
import com.example.pojo.Access;
import com.example.pojo.Account;
import com.example.pojo.Organisation;
import com.example.security.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RegistrationService implements IRegistrationService {
    private static final Set<AccessType> ADMIN_TYPES = Stream.of(AccessType.ADMIN, AccessType.SUPER_ADMIN).collect(Collectors.toSet());

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private IAccessDao accessDao;

    @Autowired
    private IOrganisationDAO organisationDAO;

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
    public Account retrieveAccount(String email) {
        try {
            return accountDao.getAccountByEmail(email);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account retrieveAccount(int userID) {
        try {
            return accountDao.getAccountByID(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Organisation retrieveOrganisation(String orgID) {
        return organisationDAO.getOrganiserById(orgID);
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

    @Override
    public boolean deleteAccount(String email) {
        Account account = retrieveAccount(email);

        if (Objects.isNull(account))
            return false;

        boolean success = accountDao.deleteAccount(account);
        if (success) {
            return accessDao.deleteAllAccesses(account.getUserId());
        }
        return false;
    }

    private boolean isAdmin(Access user) {
        return ADMIN_TYPES.contains(user.getAccess());
    }

}
