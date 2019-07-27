package com.example.dao;

import com.example.pojo.Access;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
public class AccessDaoTest {
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