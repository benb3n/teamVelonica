package com.example.dao;

import com.example.pojo.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountDao {
    // Return the list of accounts
    List<Account> getAllAccounts();
    Account getAccountByEmail(String email) throws SQLException;

    // Return the number of rows
    int getCountRows() throws SQLException, ClassNotFoundException;

    boolean loginInfo(String email, String password);

    boolean createAccount(Account account);

    boolean updateAccountParticulars(Account account);

    Account getAccountByID(int userID) throws SQLException;
}
