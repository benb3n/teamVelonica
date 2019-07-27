package com.example.dao;

import com.example.helpers.AccessType;
import com.example.helpers.ConstantHelper;
import com.example.helpers.Field;
import com.example.pojo.Access;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michelle on 27/7/2019.
 */

@Data
class DataRetriever {
    private static final String DB_USERNAME ="CitiAdmin";
    private static final String DB_PASSWORD ="citihack2019";
    private static final String DB_URL = "jdbc:mysql://citihack2019.cwop36kfff9j.ap-southeast-1.rds.amazonaws.com:3306/innodb";

    List<Access> retrieveStatement(String query) {
        List<Access> accesses = new ArrayList<>();
        System.out.println("query is " + query);
        System.out.println("url is " + DB_URL);
        try {
            Connection con = openConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            accesses = parseResultSet(rs);

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Access objects retrieved!");
        }

        return accesses;
    }

    List<Access> retrievePreparedStatement(String query, List<String> parameters) {
        int countInQuery = StringUtils.countOccurrencesOf(query, ConstantHelper.QUESTION_MARK);

        if (countInQuery != parameters.size()) {
            System.out.println("Parameter lengths do not match, cannot proceed to query.");
            return Collections.emptyList();
        }

        List<Access> accesses = new ArrayList<>();
        try {
            Connection con = openConnection();
            PreparedStatement pstmt = con.prepareStatement(query);

            for (int i = 0; i < countInQuery; i++) {
                pstmt.setString(i+1, parameters.get(i));
            }

            ResultSet rs = pstmt.executeQuery();

            accesses = parseResultSet(rs);

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Access objects retrieved!");
        }

        return accesses;
    }

    private List<Access> parseResultSet(ResultSet rs) throws SQLException {
        List<Access> accesses = new ArrayList<>();
        while (rs.next()) {
            int userID = rs.getInt(Field.USER_ID);
            String access = rs.getString(Field.ACCESS);
            String organisationID = rs.getString(Field.ORGANISATION_ID);

            Access accessObj = new Access(userID, AccessType.valueOf(access), organisationID);
            System.out.println("Retrieved access object: " + accessObj.toString());
            accesses.add(accessObj);
        }

        return accesses;
    }


    private Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
