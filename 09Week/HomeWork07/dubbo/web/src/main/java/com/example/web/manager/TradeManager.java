package com.example.web.manager;

import org.dromara.hmily.annotation.Hmily;

public interface TradeManager {

    @Hmily
    boolean transfer(Integer userId1, Integer userId2, String uniqueNo);
}
