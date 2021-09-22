package com.example.springboot.controller;

import com.example.springboot.model.UserPO;
import com.example.springboot.service.UserService;
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
        String name = "user-name:" + (new Random().nextInt(999999));
        System.out.println(userService.create(name));
        return "create";
    }

    @GetMapping("/mixed")
    public String mixed() {
        String name = "user-name:" + (new Random().nextInt(999999));
        UserPO userPO = userService.create(name);
        System.out.println(userPO);
        System.out.println(userService.findById(userPO.getId()));
        return "mixed";
    }
}
