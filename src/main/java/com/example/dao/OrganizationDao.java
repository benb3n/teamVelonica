package com.example.dao;

import com.example.pojo.Organization;
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
public class OrganizationDao {
    String username;
    String password;
    String url;
    ResultSet rs;
    Connection con;
    Statement stmt;

    public OrganizationDao() {
        username ="CitiAdmin";
        password = "citihack2019";
        url = "jdbc:mysql://citihack2019.cwop36kfff9j.ap-southeast-1.rds.amazonaws.com:3306/innodb";
    }

    public OrganizationDao(String username,String password,String url) {
        this.username=username;
        this.password=password;
        this.url=url;
    }

    // Return the list of organisers
    public List<Organization> getAllAccounts() {
        List<Organization> organizations = new ArrayList<>();
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
                Organization org = new Organization();
                org.setOrganisationID(rs.getString("OrganiserId"));
                org.setOrganisationName(rs.getString("OrganiserName"));

                organizations.add(org);
            }

            rs.close();
            stmt.close();
            con.close();

            return organizations;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("DONE!");


        }
        return organizations;
    }

    // return specific organizers
    public Organization getOrganizerByName(String orgName) {
        String query = "Select * from Organisers where OrganiserName=\'"+orgName+"\'";
        Organization org = new Organization();
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

    public Organization getOrganizerById(String orgId) {
        String query = "Select * from Organisers where OrganiserID=\'"+orgId+"\'";
        Organization org = new Organization();
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
