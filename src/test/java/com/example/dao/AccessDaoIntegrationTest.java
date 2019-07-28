package com.example.dao;

import com.example.helpers.AccessType;
import com.example.pojo.Access;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class AccessDaoIntegrationTest { // this test accesses the database
    private IAccessDao accessDao = new AccessDao();

    @Test
    public void getAllAccesses_Test() {
        List<Access> accesses = accessDao.getAllAccesses();
        assertFalse(accesses.isEmpty());
    }

    @Test
    public void retrieveAccessByUserID_Test() {
        int userID = 1;
        List<Access> accesses = accessDao.retrieveAccessByUserID(userID);
        assertFalse(accesses.isEmpty());
    }

    @Test
    public void createAndDeleteAccessByUserID_Test() {
        int userID = -1;
        Access access = new Access(userID, AccessType.VOLUNTEER, null);
        boolean success = accessDao.createAccess(access);

        if (success) {
            List<Access> retrieveAccess = accessDao.retrieveAccessByUserID(userID);
            boolean matches = retrieveAccess.stream().anyMatch(dbAccess -> dbAccess.equals(access));
            assertTrue(matches);
        }

        success = accessDao.deleteAccess(access);

        if (success) {
            List<Access> retrieveAccess = accessDao.retrieveAccessByUserID(userID);
            boolean noneMatch = retrieveAccess.stream().noneMatch(dbAccess -> dbAccess.equals(access));
            assertTrue(noneMatch);
        }
    }
}