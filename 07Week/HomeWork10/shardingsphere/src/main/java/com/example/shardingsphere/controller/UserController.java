package com.example.shardingsphere.controller;

import com.example.shardingsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id) {
        System.out.println(userService.findById(id));
        return "show";
    }

    @PostMapping
    public String create() {
        String name = "shardingsphere-" + (new Random().nextInt(999999999));
        System.out.println(userService.create(name));
        return "create";
    }
}
