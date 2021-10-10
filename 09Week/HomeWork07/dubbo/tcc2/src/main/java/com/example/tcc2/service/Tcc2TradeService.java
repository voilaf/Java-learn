package com.example.tcc2.service;


import java.math.BigDecimal;

public interface Tcc2TradeService {

    boolean transfer(int userId, BigDecimal transferAmount, int type, String distributedUniqueNo);

    boolean transferConfirm(Integer userId, String distributedUniqueNo);

    boolean transferCancel(Integer userId, String distributedUniqueNo);
}
