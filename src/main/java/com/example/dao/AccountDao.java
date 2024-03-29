package com.example.dao;

import com.example.helpers.Field;
import com.example.pojo.Account;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.helpers.ConstantHelper.COMMA_SPACE;


/**
 * Created by luqman on 27/7/2019.
 */

@Repository
public class AccountDao implements IAccountDao {
    AccountDao() { }

    private final DataRetriever retriever = new DataRetriever() {
        @Override
        List<Object> parseResultSet(ResultSet rs) throws SQLException {
            List<Object> accounts = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(Field.USER_ID);
                String password = rs.getString(Field.PASSWORD);
                String firstName = rs.getString(Field.FIRST_NAME);
                String lastName = rs.getString(Field.LAST_NAME);
                String email = rs.getString(Field.EMAIL);
                String gender = rs.getString(Field.GENDER);
                String birthDate = rs.getString(Field.BIRTH_DATE);
                String nationality = rs.getString(Field.NATIONALITY);
                String interest = rs.getString(Field.INTEREST);
                String region = rs.getString(Field.REGION);

                Account account = new Account(id, password, firstName, lastName, email, gender, birthDate, nationality, interest, region);

                System.out.println("Retrieved access object: " + account.toString());
                accounts.add(account);
            }

            return accounts;
        }

        @Override
        boolean insertStatement(Object object) {
            if (object instanceof Account) {
                Account account = (Account) object;

                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO Accounts\n" +
                        "(UserID,\n" +
                        "Password,\n" +
                        "FirstName,\n" +
                        "LastName,\n" +
                        "Email,\n" +
                        "Gender,\n" +
                        "BirthDate,\n" +
                        "Nationality,\n" +
                        "Interest,\n" +
                        "Region)\n" +
                        "VALUES\n" +
                        "(");

                query.append(account.getUserId());
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getPassword(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getFirstName(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getLastName(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getEmail(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getGender(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getBirthDate(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getNationality(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getInterest(), query);
                query.append(COMMA_SPACE);
                appendStringParameterNullable(account.getRegion(), query);
                query.append(")");

                System.out.println("Constructed: " + query);
                return this.executeStatement(query.toString());
            }
            return false;
        }

        @Override
        boolean updateStatement(Object object) {
            if (object instanceof Account) {
                Account account = (Account) object;
                String query = "UPDATE Accounts " +
                        "SET " +
                        "UserID = " + account.getUserId() + "," +
                        "Password = \'" + account.getPassword() + "\'," +
                        "FirstName = \'" + account.getFirstName() + "\'," +
                        "LastName = \'" + account.getLastName() + "\'," +
                        "Email = \'" + account.getEmail() + "\'," +
                        "Gender = \'" + account.getGender() + "\'," +
                        "BirthDate = \'" + account.getBirthDate() + "\'," +
                        "Nationality = \'" + account.getNationality() + "\'," +
                        "Interest = \'" + account.getInterest() + "\'," +
                        "Region = \'" + account.getRegion() + "\' " +
                        "WHERE UserID = " + account.getUserId();

                System.out.println("Constructed update statement: " + query);
                return this.executeStatement(query);
            }
            return false;
        }

        @Override
        boolean deleteStatement(Object object) {
            if (object instanceof Account) {
                Account account = (Account) object;
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM Accounts WHERE ");
                query.append("Email = ");
                appendStringParameterNullable(account.getEmail(), query);
                query.append(" AND UserID = ");
                query.append(account.getUserId());

                return this.executeStatement(query.toString());
            }
            return false;
        }

        @Override
        int retrieveMaxID(String query) throws ClassNotFoundException, SQLException {
            Connection con = retriever.openConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt("MaxID");
            }

            rs.close();
            stmt.close();
            con.close();
            return 0;
        }
    };



    // Return the list of accounts
    @Override
    public List<Account> getAllAccounts() {
        String query = "SELECT * FROM Accounts";
        return mapToAccount(retriever.retrieveStatement(query));
    }

    @Override
    public Account getAccountByEmail(String email) {
        String query = "SELECT * FROM Accounts WHERE Email = \'" + email + "\'";
        return retrieveAccount(query);
    }

    // Return the number of rows
    @Override
    public int getMaxUserID() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(UserID) AS MaxID FROM Accounts";

        return retriever.retrieveMaxID(query);
    }

    @Override
    public boolean loginInfo(String email, String password) {
        String query = "SELECT * FROM Accounts WHERE Email = \'" + email + "\' AND Password=\'" + password + "\'";
        return Objects.nonNull(retrieveAccount(query));
    }

    @Override
    public boolean createAccount(Account account) {
        return retriever.insertStatement(account);
    }

    @Override
    public boolean updateAccountParticulars(Account account) {
        return retriever.updateStatement(account);
    }

    @Override
    public boolean deleteAccount(Account account) {
        if (Objects.nonNull(account)) {
            return retriever.deleteStatement(account);
        }
        return false;
    }

    @Override
    public boolean deleteByQuery(String query) {
        if (query!=null && !query.isEmpty()) {
            return retriever.executeStatement(query);
        }
        return false;
    }

    @Override
    public Account getAccountByID(int userID) {
        String query = "SELECT * FROM Accounts WHERE UserID = " + userID;

        return retrieveAccount(query);
    }

    private Account retrieveAccount(String query) {
        List<Account> results = mapToAccount(retriever.retrieveStatement(query));

        if (results.size() == 1)
            return results.iterator().next();

        System.out.println("Unexpected number of accounts returned: " + results.size());
        return null;
    }

    private List<Account> mapToAccount(List<Object> objects) {
        return objects.stream().map(o -> (Account) o).collect(Collectors.toList());
    }
}
