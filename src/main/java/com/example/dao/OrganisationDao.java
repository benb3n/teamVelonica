package com.example.dao;

import com.example.pojo.Organisation;
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
public class OrganisationDao implements IOrganisationDAO {
    String username;
    String password;
    String url;
    ResultSet rs;
    Connection con;
    Statement stmt;

    public OrganisationDao() {
        username ="CitiAdmin";
        password = "citihack2019";
        url = "jdbc:mysql://citihack2019.cwop36kfff9j.ap-southeast-1.rds.amazonaws.com:3306/innodb";
    }

    public OrganisationDao(String username,String password,String url) {
        this.username=username;
        this.password=password;
        this.url=url;
    }

    // Return the list of organisers
    public List<Organisation> getAllAccounts() {
        List<Organisation> organisations = new ArrayList<>();
        String query = "SELECT * FROM Organisers";
        System.out.println("query is "+query);
        System.out.println("url is "+url);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            System.out.println("Running getAllAccounts");

            while(rs.next()) {
                Organisation org = new Organisation();
                org.setOrganisationID(rs.getString("OrganiserId"));
                org.setOrganisationName(rs.getString("OrganiserName"));

                organisations.add(org);
            }

            rs.close();
            stmt.close();
            con.close();

            return organisations;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("DONE!");


        }
        return organisations;
    }

    // return specific organizers
    @Override
    public Organisation getOrganiserByName(String orgName) {
        String query = "Select * from Organisers where OrganiserName=\'"+orgName+"\'";
        Organisation org = new Organisation();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()) {
                org.setOrganisationID(rs.getString("OrganiserID"));
                org.setOrganisationName(rs.getString("OrganiserName"));
            }

            rs.close();
            stmt.close();
            con.close();

            return org;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org;
    }

    public Organisation getOrganiserById(String orgId) {
        String query = "Select * from Organisers where OrganiserID=\'"+orgId+"\'";
        Organisation org = new Organisation();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()) {
                org.setOrganisationID(rs.getString("OrganiserID"));
                org.setOrganisationName(rs.getString("OrganiserName"));
            }

            rs.close();
            stmt.close();
            con.close();

            return org;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return org;
    }

}
