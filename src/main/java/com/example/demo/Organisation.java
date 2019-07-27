/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

/**
 *
 * @author cathylee
 */
public class Organisation {
    
    private String OrganisationName;
    private String OrganisationID;
    
    
    public Organisation(String OrganisationName, String OrganisationID){
        this.OrganisationName = OrganisationName;
        this.OrganisationID = OrganisationID;
    
    
    }
    public String getOrganisationID(){
        return this.OrganisationID;
    }
    public String getOrganisationName(){
        return this.OrganisationName;
    }
    public void setOrganisationID(String OrganisationID){
        this.OrganisationID = OrganisationID;
    }
    public void setOrganisationName(String OrganisationName){
        this.OrganisationName = OrganisationName;
    }
    
}
