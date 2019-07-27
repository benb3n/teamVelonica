package com.example.dao;

import com.example.helpers.AccessType;
import com.example.helpers.ConstantHelper;
import com.example.helpers.Field;
import com.example.pojo.Access;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    abstract List<Object> retrieveStatement(String query);
    abstract List<Object> retrievePreparedStatement(String query, List<Object> parameters);
    abstract List<Object> parseResultSet(ResultSet rs) throws SQLException;
}
