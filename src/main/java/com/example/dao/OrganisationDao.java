package com.example.dao;

import com.example.helpers.Field;
import com.example.pojo.Access;
import com.example.pojo.Organisation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.helpers.ConstantHelper.COMMA_SPACE;

/**
 * Created by luqman on 27/7/2019.
 */
@Repository
public class OrganisationDao implements IOrganisationDAO {
    OrganisationDao() {}
    private final DataRetriever retriever = new DataRetriever() {
        @Override
        List<Object> parseResultSet(ResultSet rs) throws SQLException {
            List<Object> organisations = new ArrayList<>();
            while (rs.next()) {
                String organisationID = rs.getString(Field.ORGANISATION_ID);
                String organisationName = rs.getString(Field.ORGANISATION_NAME);

                Organisation organisationObj = new Organisation(organisationID, organisationName);
                System.out.println("Retrieved access object: " + organisationObj.toString());
                organisations.add(organisationObj);
            }

            return organisations;
        }

        @Override
        boolean insertStatement(Object object) {
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

    // Return the list of organisers
    @Override
    public List<Organisation> getAllOrganisations() {
        String query = "SELECT * FROM Organisations";
        return retriever.retrieveStatement(query).stream().map(o -> (Organisation) o).collect(Collectors.toList());
    }

    // return specific organizers
    @Override
    public Organisation getOrganiserByName(String orgName) {
        String query = "SELECT * FROM Organisations where OrganisationName = ?";

        return retrieveSingleOrganisation(orgName, query);
    }

    private Organisation retrieveSingleOrganisation(String orgName, String query) {
        List<Organisation> result =
                retriever.retrievePreparedStatement(query, Collections.singletonList(orgName))
                        .stream()
                        .map(o -> (Organisation) o)
                        .collect(Collectors.toList());

        if (result.size() == 1)
            return result.iterator().next();

        System.out.println("Received unexpected number of organisations: " + result.size());
        return null;
    }

    @Override
    public Organisation getOrganiserById(String orgId) {
        String query = "SELECT * FROM Organisation where OrganiserID = " + orgId;
        return retrieveSingleOrganisation(orgId, query);
    }

}
