package com.example.web.controller;

import com.example.web.manager.TradeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeManager tradeManager;
    
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping("/transfer")
    public String transfer(@RequestParam("userId1") Integer userId1,
                           @RequestParam("userId2") Integer userId2) {
        // 模拟生成单次分布式事务对应的唯一序列号
        int sequence = atomicInteger.incrementAndGet();
        String uniqueNo = String.valueOf(System.currentTimeMillis()) + sequence;

        try {
            tradeManager.transfer(userId1, userId2, uniqueNo);
        } catch (RuntimeException e) {
            return "transfer error";
        }
        return "transfer done";
    }
}
