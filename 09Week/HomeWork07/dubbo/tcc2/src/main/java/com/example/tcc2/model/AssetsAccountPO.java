package com.example.tcc2.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AssetsAccountPO implements Serializable {

    private Integer id;

    private Integer userId;

    private BigDecimal dollarBalance;

    private BigDecimal rmbBalance;

    private Integer version;

    private Date createTime;

    private Date updateTime;
}
