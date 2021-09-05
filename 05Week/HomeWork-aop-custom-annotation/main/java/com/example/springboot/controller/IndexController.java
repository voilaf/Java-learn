package com.example.springboot.controller;

import com.example.springboot.annotation.MyCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class IndexController {

    @GetMapping("/index")
    @MyCache(60)
    public String index() {
        return "index:" + (new Random().nextInt(9999));
    }

    @GetMapping("/hello")
    @MyCache(60)
    public String hello() {
        return "hello:" + (new Random().nextInt(9999));
    }
}
