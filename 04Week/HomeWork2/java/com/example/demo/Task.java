package com.example.demo;

import java.util.concurrent.Callable;

public class Task implements Callable<String> {


    @Override
    public String call() throws Exception {
        return "yes";
    }
}
