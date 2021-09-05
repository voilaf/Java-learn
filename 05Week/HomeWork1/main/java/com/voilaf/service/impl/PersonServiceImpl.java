package com.voilaf.service.impl;

import com.voilaf.service.PersonService;

public class PersonServiceImpl implements PersonService {
    @Override
    public void say() {
        System.out.println("person is saying...");
    }
}
