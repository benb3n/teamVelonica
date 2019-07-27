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
        List<Object> retrieveStatement(String query) {
            List<Object> accesses = new ArrayList<>();

            try {
                Connection con = retriever.openConnection();

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                accesses = this.parseResultSet(rs);

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

        @Override
        List<Object> retrievePreparedStatement(String query, List<Object> parameters) {
            int countInQuery = StringUtils.countOccurrencesOf(query, ConstantHelper.QUESTION_MARK);

            if (countInQuery != parameters.size()) {
                System.out.println("Parameter lengths do not match, cannot proceed to query.");
                return null;
            }

            List<Object> accesses = new ArrayList<>();
            try {
                Connection con = retriever.openConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                buildParameterisedQuery(parameters, countInQuery, pstmt);

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

        @Override
        boolean executeStatement(String query) {
            Connection con = null;
            try {
                con = retriever.openConnection();
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
            return false;
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
        List<Access> accesses = new ArrayList<>();
        try {
            String query = "SELECT * FROM Accesses WHERE  UserID = ?";
            accesses =
                    retriever.retrievePreparedStatement(query, Collections.singletonList(userID))
                            .stream()
                            .map(o -> (Access) o)
                            .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accesses;
    }

    private void buildParameterisedQuery(List<Object> parameters, int countInQuery, PreparedStatement pstmt) throws SQLException {
        for (int i = 0; i < countInQuery; i++) {
            if (parameters.get(i) instanceof String)
                pstmt.setString(i+1, (String) parameters.get(i));
            else if (parameters.get(i) instanceof Integer)
                pstmt.setInt(i+1, (Integer) parameters.get(i));
        }
    }

}
