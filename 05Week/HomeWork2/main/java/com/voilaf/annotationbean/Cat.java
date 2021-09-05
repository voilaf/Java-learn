package com.voilaf.annotationbean;

import org.springframework.stereotype.Service;

@Service
public class Cat {

    public Cat() {
        System.out.println("cat is initializing by annotation..");
    }

    public void run() {
        System.out.println("cat is running...");
    }
}
