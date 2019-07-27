package com.example.pojo;

import com.example.helpers.AccessType;
import lombok.Data;

@Data
public class Access {
    private final int userID;
    private final AccessType access;
    private final String organisationID;

    public Access(int userID, AccessType access, String organisationID) {
        this.userID = userID;
        this.access = access;
        this.organisationID = organisationID;
    }
}
