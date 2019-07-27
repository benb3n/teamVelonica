/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pojo;

import lombok.Data;

/**
 *
 * @author cathylee
 */

@Data
public class Organisation {
    private String id;
    private String name;

    public Organisation(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
