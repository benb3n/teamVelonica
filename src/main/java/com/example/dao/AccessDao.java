package com.example.dao;

import com.example.helpers.AccessType;
import com.example.helpers.ConstantHelper;
import com.example.helpers.Field;
import com.example.pojo.Access;
import com.example.pojo.Account;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
                query.append("INSERT INTO Access (UserID, Access, OrganisationID) VALUES (");
                query.append(access.getUserID());
                query.append(COMMA_SPACE);
                query.append(access.getAccess());
                query.append(COMMA_SPACE);
                query.append(access.getOrganisationID());
                query.append(")");

                System.out.println("Constructed: " + query);
                return this.executeStatement(query.toString());
            }
            return false;
        }

        @Override
        boolean updateStatement(String query) {
            return false;
        }

        @Override
        boolean deleteStatement(String query) {
            return this.executeStatement(query);
        }
    };

    // Return the full list of accesses
    @Override
    public List<Access> getAllAccesses() {
        String query = "SELECT * FROM Accesses";

        return retriever.retrieveStatement(query).stream().map(o -> (Access) o).collect(Collectors.toList());
    }

    // Return the list of accesses for a given user ID
    @Override
    public List<Access> retrieveAccessByUserID(int userID) {

        String query = "SELECT * FROM Accesses WHERE  UserID = ?";

        return
            retriever.retrievePreparedStatement(query, Collections.singletonList(userID))
                .stream()
                .map(o -> (Access) o)
                .collect(Collectors.toList());
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

        String query =
                "DELETE FROM Accesses WHERE UserID = "
                        + access.getUserID()
                        + " AND AccessType = \'"
                        + access.getAccess().name()
                        + "\'";

        return retriever.deleteStatement(query);
    }
}
