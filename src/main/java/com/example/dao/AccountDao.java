package com.example.dao;

import com.example.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by luqman on 27/7/2019.
 */
@Repository
public class AccountDao {

    String username;
    String password;
    String url;
    ResultSet rs;
    Connection con;
    Statement stmt;

    public AccountDao() {
        username ="CitiAdmin";
        password = "citihack2019";
        url = "jdbc:mysql://citihack2019.cwop36kfff9j.ap-southeast-1.rds.amazonaws.com:3306/innodb";
    }

    public AccountDao(String username,String password,String url) {
        this.username=username;
        this.password=password;
        this.url=url;
    }

    // Return the list of accounts
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Accounts";
        System.out.println("query is "+query);
        System.out.println("url is "+url);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            System.out.println("Running getAllAccounts");

            while(rs.next()) {
                int id = rs.getInt("UserID");
                String userName = rs.getString("Username");
                String password = rs.getString("Password");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String gender = rs.getString("Gender");
                String birthDate = rs.getString("BirthDate");
                String nationality = rs.getString("Nationality");
                String interest = rs.getString("Interest");
                String region = rs.getString("Region");

                Account account = new Account(id,userName,password,firstName,lastName,email,gender,birthDate,nationality,interest,region);
                System.out.println("username is "+ userName);
                System.out.println("username from accounts is "+account.getUserName());

                System.out.println("gender is "+gender);
                System.out.println("gender from accounts is "+account.getGender());
                accounts.add(account);
            }

            rs.close();
            stmt.close();
            con.close();

            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("DONE!");


        }
        return accounts;
    }

    // Return the number of rows
    public int getCountRows() {
        String query = "SELECT COUNT(*) FROM Accounts";
        int toReturn=0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if (rs != null)
            {
                rs.last();    // moves cursor to the last row
                toReturn = rs.getRow(); // get row id
            }

            rs.close();
            stmt.close();
            con.close();

            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("DONE!");
        }
        return toReturn;
    }

    public boolean loginInfo(String username, String password) {
        String query = "SELECT * FROM Accounts WHERE username=\'"+username+"\' AND password=\'"+password+"\'";
        int size=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if (rs != null)
            {
                rs.last();    // moves cursor to the last row
                size = rs.getRow(); // get row id
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (size == 0) {
            System.out.println("Size is 0");
            return false;
        } else {
            System.out.println("Size is > 0");
            return true;
        }
    }

    public String openConnection(){
        return "Open Connection - Success";
    }

    public String closeConnection() {
        return "Close Connection - Success";
    }
}
