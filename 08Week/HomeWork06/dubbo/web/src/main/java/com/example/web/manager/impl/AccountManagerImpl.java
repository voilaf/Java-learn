package com.example.web.manager.impl;

import com.example.user.model.AccountPO;
import com.example.user.service.AccountService;
import com.example.web.manager.AccountManager;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountManagerImpl implements AccountManager {

    @DubboReference
    private AccountService accountService;

    @Override
    public List<AccountPO> list() {
        return accountService.list();
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmCreate", cancelMethod = "cancelCreate")
    public int create(String phone) {
        // 创建账号
        accountService.create(phone);

        // 添加操作日志(50%概率添加失败，触发cancel删除账号)，模拟分布式操作
        accountService.addOperateLog(phone, "注册");

        return 1;
    }

    public void confirmCreate(String phone) {
        // 未设计中间表，空操作
        System.out.println("create success");
    }

    public void cancelCreate(String phone) {
        // 回退create
        int n = 3;
        // 若删除失败，重试3次
        while ((n--) > 0) {
            if (accountService.delete(phone)) {
                break;
            }
        }
        System.out.println("cancel done");
    }
}