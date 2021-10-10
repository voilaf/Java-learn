package com.example.springboot.service;

import com.example.springboot.model.BillDetail;

import java.util.List;

public interface BillDetailService {

    List<BillDetail> detail(Long billId);

    int create(BillDetail billDetail);

    int delete(Long billId);
}
