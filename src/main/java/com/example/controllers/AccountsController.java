package com.example.controllers;

import com.example.pojo.Account;
import jdk.internal.jline.internal.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private IRegistrationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Account resource) {
        Preconditions.checkNotNull(resource);
        return service.create(resource);
    }
}
