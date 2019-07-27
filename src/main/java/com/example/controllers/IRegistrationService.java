package com.example.controllers;

import com.example.pojo.Account;

public interface IRegistrationService {
    boolean create(Account resource);

    Account retrieve(String username);
}
