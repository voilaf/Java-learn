package com.example.tcc1.service.impl;

import com.example.tcc1.enums.FreezeStatusEnum;
import com.example.tcc1.enums.TransferTypeEnum;
import com.example.tcc1.mapper.AccountMapper;
import com.example.tcc1.mapper.FreezeMapper;
import com.example.tcc1.model.AssetsAccountPO;
import com.example.tcc1.model.AssetsFreezePO;
import com.example.tcc1.service.Tcc1TradeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@DubboService
public class Tcc1TradeServiceImpl implements Tcc1TradeService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private FreezeMapper freezeMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean transfer(int userId, BigDecimal transferAmount, int type, String distributedUniqueNo) {
        if (transferAmount == null || transferAmount.doubleValue() < 0) {
            return false;
        }
        AssetsAccountPO accountPO = accountMapper.findByUserId(userId);
        if (accountPO == null) {
            if (accountMapper.create(userId) <= 0) {
                return false;
            }
            accountPO = accountMapper.findByUserId(userId);
        }

        TransferTypeEnum transferTypeEnum = TransferTypeEnum.findEnumByType(type);
        if (transferTypeEnum == null) {
            return false;
        }
        int n = 3;
        while ((n--) > 0) {
            switch (transferTypeEnum) {
                case DOLLAR_TO_RMB:     // 美元转人民币
                    // 判断美元余额是否足够
                    BigDecimal extraDollar = accountPO.getDollarBalance().subtract(transferAmount);
                    if (extraDollar.doubleValue() < 0) {
                        System.out.println("美元余额不足");
                        return false;
                    }
                    // 更新新的美元余额
                    accountPO.setDollarBalance(extraDollar);
                    break;
                case RMB_TO_DOLLAR:     // 人民币转美元
                    // 判断人民币余额是否足够
                    BigDecimal extraRmb = accountPO.getRmbBalance().subtract(transferAmount);
                    if (extraRmb.doubleValue() < 0) {
                        System.out.println("人民币余额不足");
                        return false;
                    }
                    // 更新新的人民币余额
                    accountPO.setRmbBalance(extraRmb);
                    break;
                default:
                    return false;
            }
            // 未更新成功，重试
            if (accountMapper.updateAssets(accountPO) <= 0) {
                accountPO = accountMapper.findByUserId(userId);
                continue;
            }

            // 新余额更新成功，添加资产冻结表
            if (freezeMapper.create(userId, transferAmount, type, distributedUniqueNo) <= 0) {
                throw new RuntimeException();
            }

            // 处理完成
            break;
        }
        // 随机模拟失败情况
        if ((new Random().nextInt(10)) < 3) {
            throw new RuntimeException("处理失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean transferConfirm(Integer userId, String distributedUniqueNo) {
        // 完成最终转账

        AssetsFreezePO freezePO = freezeMapper.findByUserIdAndUniqueNo(userId, distributedUniqueNo);
        if (freezePO == null || !freezePO.getStatus().equals(FreezeStatusEnum.UNDO.getStatus())) {
            return true;
        }

        TransferTypeEnum transferTypeEnum = TransferTypeEnum.findEnumByType(freezePO.getType());
        int n = 3;
        while ((n--) > 0) {
            // 1. 增加用户的美元或人民币账户余额
            AssetsAccountPO accountPO = accountMapper.findByUserId(userId);
            switch (transferTypeEnum) {
                case DOLLAR_TO_RMB: // 美元转人民币
                    // 增加人民币账户余额
                    accountPO.setRmbBalance(accountPO.getRmbBalance().add(dollarToRmb(freezePO.getTransferAmount())));
                    break;
                case RMB_TO_DOLLAR: // 人民币转美元
                    // 增加美元账户余额
                    accountPO.setDollarBalance(accountPO.getDollarBalance().add(rmbToDollar(freezePO.getTransferAmount())));
                    break;
            }
            // 未更新成功，重试
            if (accountMapper.updateAssets(accountPO) <= 0) {
                continue;
            }

            // 2. 修改处理状态
            if (freezeMapper.updateStatus(freezePO.getId(), FreezeStatusEnum.COMMIT_STATUS.getStatus()) <= 0) {
                throw new RuntimeException();
            }

            // 处理完成
            break;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean transferCancel(Integer userId, String distributedUniqueNo) {
        // 转账取消，撤回初始操作
        // 完成最终转账

        AssetsFreezePO freezePO = freezeMapper.findByUserIdAndUniqueNo(userId, distributedUniqueNo);
        if (freezePO == null || !freezePO.getStatus().equals(FreezeStatusEnum.UNDO.getStatus())) {
            return true;
        }

        TransferTypeEnum transferTypeEnum = TransferTypeEnum.findEnumByType(freezePO.getType());
        int n = 3;
        while ((n--) > 0) {
            // 1. 增加用户的美元或人民币账户余额
            AssetsAccountPO accountPO = accountMapper.findByUserId(userId);
            switch (transferTypeEnum) {
                case DOLLAR_TO_RMB: // 美元转人民币
                    // 恢复美元账户余额
                    accountPO.setDollarBalance(accountPO.getDollarBalance().add(freezePO.getTransferAmount()));
                    break;
                case RMB_TO_DOLLAR: // 人民币转美元
                    // 恢复人民币账户余额
                    accountPO.setRmbBalance(accountPO.getRmbBalance().add(freezePO.getTransferAmount()));
                    break;
            }
            // 未更新成功，重试
            if (accountMapper.updateAssets(accountPO) <= 0) {
                continue;
            }

            // 2. 修改处理状态
            if (freezeMapper.updateStatus(freezePO.getId(), FreezeStatusEnum.CANCEL_STATUS.getStatus()) <= 0) {
                throw new RuntimeException();
            }

            // 处理完成
            break;
        }
        return true;
    }

    /**
     * 人民币转美元汇率计算
     * @return
     */
    private BigDecimal rmbToDollar(BigDecimal amount) {
        return amount.divide(new BigDecimal(7));
    }

    /**
     * 美元转人民币汇率计算
     * @return
     */
    private BigDecimal dollarToRmb(BigDecimal amount) {
        return amount.multiply(new BigDecimal(7));
    }
}
