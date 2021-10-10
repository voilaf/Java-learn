package com.example.tcc1.service;


import java.math.BigDecimal;

public interface Tcc1TradeService {

    boolean transfer(int userId, BigDecimal transferAmount, int type, String distributedUniqueNo);

    boolean transferConfirm(Integer userId, String distributedUniqueNo);

    boolean transferCancel(Integer userId, String distributedUniqueNo);
}
