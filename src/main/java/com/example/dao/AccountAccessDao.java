/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

import com.example.helpers.AccessType;
import static com.example.helpers.ConstantHelper.COMMA_SPACE;
import com.example.helpers.Field;
import com.example.pojo.Access;
import com.example.pojo.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author cathylee
 */
public class AccountAccessDao implements IAccountAccessDao {

    private final DataRetriever retriever = new DataRetriever() {
        @Override
        List<Object> parseResultSet(ResultSet rs) throws SQLException {
            List<Object> accessMap = new ArrayList<>();

            while (rs.next()) {

                int userID = rs.getInt(Field.USER_ID);
                String access = rs.getString(Field.ACCESS);
                String email = rs.getString(Field.EMAIL);

                HashMap<String, Object> accessObj = new HashMap<>();
                accessObj.put("userID", userID);
                accessObj.put("access", access);
                accessObj.put("email", email);
                accessMap.add(accessObj);

                System.out.println("Retrieved access object: " + accessMap.toString());

            }

            return accessMap;
        }

        @Override
        boolean insertStatement(Object object) {
            if (object instanceof Access) {
                Access access = (Access) object;

                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO Accesses (UserID, Access, OrganisationID) VALUES (");
                query.append(access.getUserID());
                query.append(COMMA_SPACE);
                appendStringParameterNullable(access.getAccess().name(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(access.getOrganisationID(), query);
                query.append(")");

                System.out.println("Constructed: " + query);
                return this.executeStatement(query.toString());
            }
            return false;
        }

        @Override
        boolean updateStatement(Object object) {
            return false;
        }

        @Override
        boolean deleteStatement(Object object) {
            if (object instanceof Access) {
                Access access = (Access) object;
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM Accesses WHERE UserID = ");
                query.append(access.getUserID());

                query.append(" AND Access = ");
                retriever.appendStringParameterNullable(access.getAccess().name(), query);

                query.append(" AND OrganisationID ");

                String organisationID = access.getOrganisationID();
                if (Objects.isNull(organisationID)) {
                    query.append("IS ");
                } else {
                    query.append("= ");
                }

                retriever.appendStringParameterNullable(organisationID, query);

                return this.executeStatement(query.toString());
            }

            System.out.println("ERROR: Bad Object.");
            return false;
        }

    };

    // Return the list of accesses for a given user ID
    @Override
    public List<Object> retrieveAccessByEmail(String email) {
        String query = "SELECT * FROM accountAccess WHERE  email = " + email;

        return mapToAccess(retriever.retrieveStatement(query));
    }

    private List<Object> mapToAccess(List<Object> results) {
        return results.stream().map(result -> (Object) result).collect(Collectors.toList());
    }
    
   

}
