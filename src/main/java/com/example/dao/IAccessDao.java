package com.example.dao;

import com.example.pojo.Access;

import java.util.List;

public interface IAccessDao {
    // Return the list of accesses
    List<Access> getAllAccesses();

    List<Access> retrieveAccessByUserID(int userID);

    boolean createAccess(int userID);

    boolean createAccess(Access access);

    boolean deleteAccess(Access access);
}
