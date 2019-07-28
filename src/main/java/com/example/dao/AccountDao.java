package com.example.dao;

import com.example.helpers.Field;
import com.example.pojo.Account;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                String userName = rs.getString(Field.USERNAME);
                String password = rs.getString(Field.PASSWORD);
                String firstName = rs.getString(Field.FIRST_NAME);
                String lastName = rs.getString(Field.LAST_NAME);
                String email = rs.getString(Field.EMAIL);
                String gender = rs.getString(Field.GENDER);
                String birthDate = rs.getString(Field.BIRTH_DATE);
                String nationality = rs.getString(Field.NATIONALITY);
                String interest = rs.getString(Field.INTEREST);
                String region = rs.getString(Field.REGION);

                Account account = new Account(id, userName, password, firstName, lastName, email, gender, birthDate, nationality, interest, region);

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
                        "Username,\n" +
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
                appendStringParameterNullable(account.getUserName(), query);
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
                query.append(account.getRegion());
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
                        "UserID = \'" + account.getUserId() + "\'," +
                        "Username = \'" + account.getUserName() + "\', " +
                        "Password = \'" + account.getPassword() + "\'," +
                        "FirstName = \'" + account.getFirstName() + "\'," +
                        "LastName = \'" + account.getLastName() + "\'," +
                        "Email = \'" + account.getEmail() + "\'," +
                        "Gender = \'" + account.getGender() + "\'," +
                        "BirthDate = \'" + account.getBirthDate() + "\'," +
                        "Nationality = \'" + account.getNationality() + "\'," +
                        "Interest = \'" + account.getInterest() + "\'," +
                        "Region = \'" + account.getRegion() + "\'," +
                        "WHERE UserID = \'" + account.getUserId() + "\',";

                return this.executeStatement(query);
            }
            return false;
        }

        @Override
        boolean deleteStatement(Object object) {
            if (object instanceof Account) {
                return false; // TODO
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
        return retriever.retrieveStatement(query).stream().map(o -> (Account) o).collect(Collectors.toList());
    }

    @Override
    public Account getAccountByUsername(String username) {
        String query = "SELECT * FROM Accounts WHERE Username = \'" + username + "\'";
        return retrieveAccount(query);
    }

    // Return the number of rows
    @Override
    public int getCountRows() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(UserID) AS MaxID FROM Accounts";

        return retriever.retrieveMaxID(query);
    }

    @Override
    public boolean loginInfo(String username, String password) {
        String query = "SELECT * FROM Accounts WHERE username=\'"+username+"\' AND password=\'"+password+"\'";
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
    public Account getAccountByID(int userID) {
        String query = "SELECT * FROM Accounts WHERE UserID = " + userID;

        return retrieveAccount(query);
    }

    private Account retrieveAccount(String query) {
        List<Account> results = retriever.retrieveStatement(query).stream().map(o -> (Account) o).collect(Collectors.toList());

        if (results.size() == 1)
            return results.iterator().next();

        System.out.println("Unexpected number of accounts returned: " + results.size());
        return null;
    }
}
