package com.example.controllers;

import com.example.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private IRegistrationService service;

    @PostMapping(value = "/registration")
    public ResponseEntity create(@RequestBody Account resource) {
        if (Objects.isNull(resource))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (Objects.nonNull(service.retrieve(resource.getUserName()))) {
            System.out.println("User already exists, cannot create.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (service.create(resource)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
