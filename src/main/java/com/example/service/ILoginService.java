package com.example.service;

import java.util.HashMap;

/**
 * Created by luqman on 27/7/2019.
 */
public interface ILoginService {
    HashMap<String,Object> login(String email,String password);
}
