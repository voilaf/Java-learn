package com.example.web.manager;

import com.example.user.model.AccountPO;
import org.dromara.hmily.annotation.Hmily;

import java.util.List;

public interface AccountManager {

    List<AccountPO> list();

    @Hmily
    int create(String phone);
}
