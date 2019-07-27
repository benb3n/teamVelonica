package com.example.dao;

import com.example.pojo.Access;

import java.util.List;

public interface IAccessDao {
    // Return the list of accounts
    List<Access> getAllAccesses();

    List<Access> retrieveAccessByUserID(String userID);
}
