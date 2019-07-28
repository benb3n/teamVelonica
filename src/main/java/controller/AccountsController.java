/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.example.dao.AccessDao;
import com.example.pojo.Access;
import com.example.pojo.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cathylee
 */
@RestController
public class AccountsController {
    @Autowired
    AccessDao accessDAO;
    
    //@RequestMapping("/accounts")
    @RequestMapping(method = RequestMethod.GET, value="id")
    public Account account(@RequestParam(value="id", defaultValue="World") String userId){
        List<Access> accessList = accessDAO.retrieveAccessByUserID(Integer.parseInt(userId));
        
        
        
            
        return null;
    }

}


