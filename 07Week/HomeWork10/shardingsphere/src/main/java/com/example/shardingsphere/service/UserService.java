package com.example.shardingsphere.service;

import com.example.shardingsphere.model.UserPO;

public interface UserService {

    UserPO findById(Integer id);

    UserPO create(String name);
}
