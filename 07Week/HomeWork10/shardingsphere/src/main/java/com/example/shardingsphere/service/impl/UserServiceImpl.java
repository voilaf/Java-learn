package com.example.shardingsphere.service.impl;

import com.example.shardingsphere.dao.UserMapper;
import com.example.shardingsphere.model.UserPO;
import com.example.shardingsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserPO findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public UserPO create(String name) {
        UserPO userPO = new UserPO();
        userPO.setName(name);
        userMapper.create(userPO);
        return userPO;
    }
}
