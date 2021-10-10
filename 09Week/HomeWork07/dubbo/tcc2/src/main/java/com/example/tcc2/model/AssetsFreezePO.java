package com.example.tcc2.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AssetsFreezePO {

    private Integer id;

    private Integer userId;

    private BigDecimal transferAmount;

    private Integer type;

    private Integer status;

    private String distributedUniqueNo;

    private Date createTime;

    private Date updateTime;
}
