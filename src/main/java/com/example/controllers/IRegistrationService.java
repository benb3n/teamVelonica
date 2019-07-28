package com.example.controllers;

import com.example.pojo.Access;
import com.example.pojo.Account;

import java.util.List;

public interface IRegistrationService {
    boolean create(Account resource);

    boolean createAccess(Access access);

    Account retrieve(String email);

    Account retrieve(int id);

    List<Access> retrieveAllAccesses(int id);

    boolean deleteAccess(int currentUserID, Access access);
}
