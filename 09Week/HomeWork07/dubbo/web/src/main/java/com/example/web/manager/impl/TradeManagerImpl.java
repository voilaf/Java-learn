package com.example.web.manager.impl;

import com.example.tcc1.enums.TransferTypeEnum;
import com.example.tcc1.service.Tcc1TradeService;
import com.example.tcc2.service.Tcc2TradeService;
import com.example.web.manager.TradeManager;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TradeManagerImpl implements TradeManager {

    @DubboReference
    private Tcc1TradeService tcc1TradeService;

    @DubboReference
    private Tcc2TradeService tcc2TradeService;


    @Override
    @HmilyTCC(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    public boolean transfer(Integer userId1, Integer userId2, String uniqueNo) {
        // userId1 美元转人民币
        BigDecimal dollarTransferAmount = new BigDecimal(1);
        if (!tcc1TradeService.transfer(userId1, dollarTransferAmount,
                TransferTypeEnum.DOLLAR_TO_RMB.getType(), uniqueNo)) {
            return false;
        }
        // userId2 人民币转美元
        BigDecimal rmbTransferAmount = new BigDecimal(7);
        if (!tcc2TradeService.transfer(userId2, rmbTransferAmount,
                TransferTypeEnum.RMB_TO_DOLLAR.getType(), uniqueNo)) {
            throw new RuntimeException();
        }
        return true;
    }

    public void transferConfirm(Integer userId1, Integer userId2, String uniqueNo) {
        tcc1TradeService.transferConfirm(userId1, uniqueNo);
        tcc2TradeService.transferConfirm(userId2, uniqueNo);
        System.out.println("transferConfirm done");
    }

    public void transferCancel(Integer userId1, Integer userId2, String uniqueNo) {
        tcc1TradeService.transferCancel(userId1, uniqueNo);
        tcc2TradeService.transferCancel(userId2, uniqueNo);
        System.out.println("transferCancel done");
    }
}
