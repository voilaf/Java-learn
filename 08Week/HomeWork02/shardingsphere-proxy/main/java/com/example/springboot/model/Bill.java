package com.example.springboot.model;

import lombok.Data;

import java.util.Date;

@Data
public class Bill {

    private Long id;

    private Long userId;

    private Integer billPrice;

    private Integer actualPrice;

    private Integer refundPrice;

    private String contact;

    private String address;

    private String refundReason;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
