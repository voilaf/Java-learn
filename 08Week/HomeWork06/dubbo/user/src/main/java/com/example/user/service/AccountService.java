package com.example.user.service;


import com.example.user.model.AccountPO;

import java.util.List;

public interface AccountService {

    List<AccountPO> list();

    int create(String phone);

    void addOperateLog(String phone, String msg);

    boolean delete(String phone);
}
