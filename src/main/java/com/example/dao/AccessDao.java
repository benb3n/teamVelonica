package com.example.dao;

import com.example.helpers.AccessType;
import com.example.helpers.Field;
import com.example.pojo.Access;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.helpers.ConstantHelper.COMMA_SPACE;


/**
 * Created by michelle on 27/7/2019.
 */
@Repository
public class AccessDao implements IAccessDao {
    AccessDao() { }

    private final DataRetriever retriever = new DataRetriever() {
        @Override
        List<Object> parseResultSet(ResultSet rs) throws SQLException {
            List<Object> accesses = new ArrayList<>();
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

        @Override
        boolean deleteAllStatement(int userID) {
            String deleteAllQuery = "DELETE FROM Accesses WHERE UserID = " + userID;
            return this.executeStatement(deleteAllQuery);
        }
    };

    // Return the full list of accesses
    @Override
    public List<Access> getAllAccesses() {
        String query = "SELECT * FROM Accesses";
        return mapToAccess(retriever.retrieveStatement(query));
    }

    // Return the list of accesses for a given user ID
    @Override
    public List<Access> retrieveAccessByUserID(int userID) {
        String query = "SELECT * FROM Accesses WHERE  UserID = " + userID;

        return mapToAccess(retriever.retrieveStatement(query));
    }

    private List<Access> mapToAccess(List<Object> results) {
        return results.stream().map(result -> (Access) result).collect(Collectors.toList());
    }

    @Override
    public boolean createAccess(int userID) {
        return createAccess(new Access(userID, AccessType.VOLUNTEER, null));
    }

    @Override
    public boolean createAccess(Access access) {
        return retriever.insertStatement(access);
    }

    @Override
    public boolean deleteAccess(Access access) {
        return retriever.deleteStatement(access);
    }

    @Override
    public boolean deleteAllAccesses(int userID) {
        return retriever.deleteAllStatement(userID);
    }
}

