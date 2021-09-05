package com.voilaf;

import com.voilaf.aspect.LogAspect;
import com.voilaf.service.PersonService;
import com.voilaf.service.impl.PersonServiceImpl;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        PersonService initService = new PersonServiceImpl();
        LogAspect logAspect = new LogAspect(initService);
        // 生成代理类
        PersonService personService = (PersonService) Proxy. newProxyInstance(logAspect.getClass().getClassLoader(), initService.getClass().getInterfaces(), logAspect);
        personService.say();
    }
}
