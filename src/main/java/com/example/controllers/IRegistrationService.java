package com.example.controllers;

import com.example.pojo.Access;
import com.example.pojo.Account;
import com.example.pojo.Organisation;

import java.util.List;

public interface IRegistrationService {
    boolean create(Account resource);

    boolean createAccess(Access access);

    Account retrieveAccount(String email);

    Account retrieveAccount(int id);

    Organisation retrieveOrganisation(String orgID);

    List<Access> retrieveAllAccesses(int id);

    boolean deleteAccess(int currentUserID, Access access);
}
