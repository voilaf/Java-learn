package com.example.springboot.service;

import com.example.springboot.model.UserPO;

public interface UserService {

    UserPO findById(Integer id);

    UserPO create(String name);
}
