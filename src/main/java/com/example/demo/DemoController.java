package com.example.demo;

import com.example.dao.AccountAccessDao;
import com.example.pojo.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    AccountAccessDao accountAccessDao;

    @GetMapping(value = "/accounts/{email}")
    public List<Object> accountByEmail(@PathVariable("email") String email) {
        System.out.println("starting");
        try {
            System.out.println(email);
            List<Object> account = accountAccessDao.retrieveAccessByEmail(email);
            
            
            System.out.println(account);
            return account;
            
        } catch (Exception e) {

        }
        return null;

    }

    @RequestMapping("/index")
    String index() {

        System.out.println("starting");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String username = "CitiAdmin";
            String password = "citihack2019";
            Connection con = DriverManager.getConnection("jdbc:mysql://citihack2019.cwop36kfff9j.ap-southeast-1.rds.amazonaws.com:3306/innodb", username, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from test");
            System.out.println("Pulled data");

            while (rs.next()) {
                return "Read from database : " + rs.getString(1) + "  " + rs.getString(2);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Well Done!! Nice Try!! Good bye!";
    }

}
