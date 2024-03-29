package com.example.dao;

import com.example.helpers.ConstantHelper;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;

/**
 * Created by michelle on 27/7/2019.
 */

@Data
abstract class DataRetriever {
    private static final String DB_USERNAME ="CitiAdmin";
    private static final String DB_PASSWORD ="citihack2019";
    private static final String DB_URL = "jdbc:mysql://citihack2019.cwop36kfff9j.ap-southeast-1.rds.amazonaws.com:3306/innodb";

    private HashMap<String, String> columnNameTypeMap = new HashMap<>();

    Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    List<Object> retrieveStatement(String query) {
        List<Object> objects = new ArrayList<>();

        try {
            Connection con = openConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           

            objects = this.parseResultSet(rs);
            System.out.println(objects);

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Objects retrieved!");
        }
        return objects;
    }

    List<Object> retrievePreparedStatement(String query, List<Object> parameters) {
        List<Object> objects = new ArrayList<>();

        if (StringUtils.isEmpty(query))
            return objects;

        int countInQuery = StringUtils.countOccurrencesOf(query, ConstantHelper.QUESTION_MARK);

        if (countInQuery != parameters.size()) {
            System.out.println("Parameter lengths do not match, cannot proceed to query.");
            return null;
        }

        try {
            Connection con = openConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            buildParameterisedQuery(parameters, countInQuery, pstmt);

            ResultSet rs = pstmt.executeQuery();
            objects = parseResultSet(rs);

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Access objects retrieved!");
        }

        return objects;
    }

    boolean executeStatement(String query) {
        if (StringUtils.isEmpty(query))
            return false;

        Connection con;
        try {
            con = openConnection();
            Statement stmt = con.createStatement();
            int count = stmt.executeUpdate(query);

            if (count != 0)
                return true;

            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void buildParameterisedQuery(List<Object> parameters, int countInQuery, PreparedStatement pstmt) throws SQLException {
        for (int i = 0; i < countInQuery; i++) {
            if (parameters.get(i) instanceof String)
                pstmt.setString(i+1, (String) parameters.get(i));
            else if (parameters.get(i) instanceof Integer)
                pstmt.setInt(i+1, (Integer) parameters.get(i));
        }
    }

    void appendStringParameterNullable(String value, StringBuilder query) {
        if (Objects.nonNull(value)) {
            query.append("\'");
            query.append(value);
            query.append("\'");
        } else
            query.append("null");
    }

    int retrieveMaxID(String query) throws SQLException, ClassNotFoundException {
        return 0;
    }
    boolean deleteAllStatement(int userID) { return false; }

    abstract List<Object> parseResultSet(ResultSet rs) throws SQLException;
    abstract boolean insertStatement(Object object);
    abstract boolean updateStatement(Object object);
    abstract boolean deleteStatement(Object object);

}
