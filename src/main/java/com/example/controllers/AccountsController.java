package com.example.controllers;

import com.example.pojo.Account;
import com.example.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private IRegistrationService service;

    @Autowired
    private ILoginService loginService;

    @PostMapping(value = "/registration")
    public ResponseEntity create(@RequestBody Account resource) {
        if (Objects.isNull(resource))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (Objects.nonNull(service.retrieveAccount(resource.getEmail()))) {
            System.out.println("User already exists, cannot create.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (service.create(resource)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @RequestMapping(value="/login")
    public HashMap<String, Object> login(String email, String password) {
        if (email!=null && !email.isEmpty() && password!=null && !password.isEmpty()) {
            return loginService.login(email,password);
        }
        return null;
    }

    @DeleteMapping(value="/delete")
    public ResponseEntity deleteAccount(String email) {
        if (StringUtils.isEmpty(email))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (service.deleteAccount(email))
            return new ResponseEntity(HttpStatus.OK);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
