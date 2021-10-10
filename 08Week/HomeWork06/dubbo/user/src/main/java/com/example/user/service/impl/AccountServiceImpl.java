package com.example.user.service.impl;

import com.example.user.mapper.AccountMapper;
import com.example.user.model.AccountPO;
import com.example.user.service.AccountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@DubboService
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<AccountPO> list() {
        return accountMapper.list();
    }

    @Override
    public int create(String phone) {
        AccountPO accountPO = new AccountPO();
        accountPO.setPhone(phone);
        accountPO.setName("name--" + (new Random().nextInt(9999)));
        return accountMapper.create(accountPO);
    }

    @Override
    public void addOperateLog(String phone, String msg) {
        if ((new Random().nextInt(10)) < 5) {
            throw new RuntimeException();
        }
        accountMapper.addOperateLog(phone + ":" + msg);
    }

    @Override
    public boolean delete(String phone) {
        AccountPO accountPO = accountMapper.findByPhone(phone);
        if (accountPO == null) {
            return true;
        }
        int effectNum = accountMapper.deleteByPhone(phone);
        return effectNum > 0 ? true : false;
    }

}
