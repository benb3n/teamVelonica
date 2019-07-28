package com.example.service;

import com.example.dao.AccessDao;
import com.example.dao.AccountDao;
import com.example.pojo.Access;
import com.example.pojo.Account;
import com.example.security.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by luqman on 27/7/2019.
 */
@Service
public class LoginService implements ILoginService {

    @Autowired
    private AccessDao accessDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public HashMap<String, Object> login(String email, String password) {
        HashMap<String,Object> toReturn = new HashMap<>();
        // Get account details to add into map
        // AccountDao ad = new AccountDao();
        //accountDao = new AccountDao();
        try {
            Account account = accountDao.getAccountByEmail(email);
            SecurityHelper sh = new SecurityHelper();
            String hashedPassword = sh.encrypt(password).toString();
            if (account == null) {
                return null;
            }
            if (account.getEmail().equals(email) && account.getPassword().equals(hashedPassword)) {
                toReturn.put("UserId",account.getUserId());
                toReturn.put("Email", account.getEmail());
                toReturn.put("FirstName", account.getFirstName());
                toReturn.put("LastName", account.getLastName());
                toReturn.put("Gender", account.getGender());
                toReturn.put("BirthDate", account.getBirthDate());
                toReturn.put("Interests", account.getInterest());
                toReturn.put("Nationality", account.getNationality());
                // Get access details to add into map
                List<Access> access=accessDao.retrieveAccessByUserID(account.getUserId());
                toReturn.put("Access", access);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
