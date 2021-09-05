package com.voilaf.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogAspect implements InvocationHandler {

    private Object target;

    public LogAspect(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke method, aop for logging...");
        method.invoke(target);
        System.out.println("after invoke method, aop for logging...");
        return null;
    }
}
