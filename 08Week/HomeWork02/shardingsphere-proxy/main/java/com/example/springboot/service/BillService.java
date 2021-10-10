package com.example.springboot.service;

import com.example.springboot.model.Bill;

import java.util.List;

public interface BillService {

    List<Bill> list();

    long create();

    int update(Long id);

    int delete(Long id);
}
