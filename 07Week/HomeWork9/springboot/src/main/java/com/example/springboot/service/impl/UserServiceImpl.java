package com.example.springboot.service.impl;

import com.example.springboot.dao.UserMapper;
import com.example.springboot.model.UserPO;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserPO findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    public UserPO create(String name) {
        UserPO userPO = new UserPO();
        userPO.setName(name);
        userMapper.create(userPO);
        return userPO;
    }
}
