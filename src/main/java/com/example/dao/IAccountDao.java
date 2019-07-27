package com.example.dao;

import com.example.pojo.Account;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by luqman on 27/7/2019.
 */
public interface IAccountDao {
    List<Account> getAllAccounts();
    int getCountRows();
    Account getAccount(String uname) throws SQLException;
    boolean updateStatement(Object object) throws SQLException;
    boolean deleteStatement(String email) throws SQLException;
}
