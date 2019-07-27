/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

import com.example.pojo.Organisation;
import java.util.List;

/**
 *
 * @author cathylee
 */
public interface IOrganisationDAO {
    Organisation getOrganiserByName(String orgName);
    Organisation getOrganiserById(String orgId);
    List<Organisation> getAllAccounts();
    
    
    
}
