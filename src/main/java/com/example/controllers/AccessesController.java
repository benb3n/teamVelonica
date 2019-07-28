package com.example.controllers;

import com.example.pojo.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/organizations")
public class AccessesController {
    @Autowired
    private IRegistrationService service;

    @PostMapping(value = "/accesses")
    public ResponseEntity createAdminAccess(@RequestBody Access access) {
        if (Objects.isNull(access))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (Objects.isNull(service.retrieveAccount(access.getUserID()))) {
            System.out.println("User does not exist, cannot add access.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(service.retrieveOrganisation(access.getOrganisationID()))) {
            System.out.println("Organisation does not exist, cannot add access to invalid organisation.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (service.createAccess(access))
            return new ResponseEntity(HttpStatus.CREATED);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/accesses/{id}")
    public List<Access> retrieveAccessesByID(@PathVariable("id") int currentUserID, int idToRetrieve) {
        return service.retrieveAllAccesses(currentUserID);
    }

    @DeleteMapping(value = "/accesses/{id}")
    public ResponseEntity deleteAdminAccess(@PathVariable("id") int id, @RequestBody Access accessToDelete) {
        if (Objects.isNull(accessToDelete))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (Objects.isNull(service.retrieveAccount(id)) || Objects.isNull(service.retrieveAccount(accessToDelete.getUserID()))) {
            System.out.println("User does not exist, cannot delete access.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (service.deleteAccess(id, accessToDelete))
             return new ResponseEntity(HttpStatus.OK);

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
