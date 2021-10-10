package com.example.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountPO implements Serializable {

    private Integer id;

    private String name;

    private String phone;
}
