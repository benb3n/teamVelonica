package com.example.dao;

import com.example.pojo.Access;

import java.util.HashMap;
import java.util.List;

public interface IAccessDao {
    // Return the list of accesses
    List<Access> getAllAccesses();

    List<Access> retrieveAccessByUserID(int userID);
}
