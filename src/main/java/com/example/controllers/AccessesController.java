package com.example.controllers;

import com.example.pojo.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/organisations")
public class AccessesController {
    @Autowired
    private IRegistrationService service;

    @PostMapping(value = "/accesses")
    public ResponseEntity createAdminAccess(@RequestBody Access access) {
        if (Objects.isNull(access))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (Objects.isNull(service.retrieve(access.getUserID()))) {
            System.out.println("User does not exist, cannot add access.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (service.createAccess(access))
            return new ResponseEntity(HttpStatus.CREATED);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/accesses/{id}")
    public List<Access> retrieveAccessesByID(@PathVariable("id") int id) {
        return service.retrieveAllAccesses(id);
    }
}
