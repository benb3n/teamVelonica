package com.example.demo;

import lombok.Data;

@Data
public class Access {
    private final String userID;
    private final String access;
    private final String organisationID;

    public Access(String userID, String access, String organisationID) {
        this.userID = userID;
        this.access = access;
        this.organisationID = organisationID;
    }
}
