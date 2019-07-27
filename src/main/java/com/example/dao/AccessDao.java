package com.example.dao;

import com.example.pojo.Access;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by michelle on 27/7/2019.
 */
@Repository
public class AccessDao implements IAccessDao {
    private final DataRetriever retriever = new DataRetriever();

    public AccessDao() { }

    // Return the list of accounts
    @Override
    public List<Access> getAllAccesses() {
        String query = "SELECT * FROM Accesses";
        return retriever.retrieveStatement(query);
    }

    @Override
    public List<Access> retrieveAccessByUserID(String userID) {
        List<Access> accesses = new ArrayList<>();
        try {
            String query = "SELECT * FROM Accesses WHERE  UserID = ?";
            accesses = retriever.retrievePreparedStatement(query, Collections.singletonList(userID));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Access objects retrieved!");
        }
        return accesses;
    }


    public String closeConnection() {
        return "Close Connection - Success";
    }
}
