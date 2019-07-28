package com.example.dao;

import com.example.pojo.Organisation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
public class OrganisationDaoTest {
    private IOrganisationDAO organisationDAO = new OrganisationDao();

    @Test
    public void getAllOrganisations_Test() {
        List<Organisation> organisations = organisationDAO.getAllOrganisations();
        assertFalse(organisations.isEmpty());
    }

    @Test
    public void getOrganiserByName() {
        String orgName = "Child Cancer Foundation";
        Organisation organisation = organisationDAO.getOrganiserByName(orgName);
        assertNotNull(organisation);
    }

    @Test
    public void getOrganiserById() {
        String orgId = "CCF12345678912345678";
        Organisation organisation = organisationDAO.getOrganiserById(orgId);
        assertNotNull(organisation);
    }
}