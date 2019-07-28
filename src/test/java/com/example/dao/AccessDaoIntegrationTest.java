package com.example.dao;

import com.example.pojo.Access;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
public class AccessDaoIntegrationTest { // this test accesses the database
    IAccessDao accessDao = new AccessDao();

    @Test
    public void getAllAccesses_Test() {
        List<Access> accesses = accessDao.getAllAccesses();
        assertFalse(accesses.isEmpty());
    }

    @Test
    public void retrieveAccessByUserID() {
        int userID = 1;
        List<Access> accesses = accessDao.retrieveAccessByUserID(userID);
        assertFalse(accesses.isEmpty());
    }

}