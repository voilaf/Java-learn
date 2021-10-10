package com.example.web.controller;

import com.example.user.model.AccountPO;
import com.example.web.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountManager accountManager;

    @GetMapping("/list")
    public List<AccountPO> list() {
        return accountManager.list();
    }

    @GetMapping("/create")
    public String create() {
        String phone = "137000000" + (new Random().nextInt(100));
        try {
            accountManager.create(phone);
        } catch (RuntimeException e) {
            return "create fail";
        }
        return "create success";
    }
}
