package com.example.springboot.service.impl;

import com.example.springboot.mapper.BillDetailMapper;
import com.example.springboot.model.BillDetail;
import com.example.springboot.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailServiceImpl implements BillDetailService {

    @Autowired
    private BillDetailMapper billDetailMapper;

    @Override
    public List<BillDetail> detail(Long billId) {
        return billDetailMapper.listByBillId(billId);
    }

    @Override
    public int create(BillDetail billDetail) {
        return billDetailMapper.create(billDetail);
    }

    @Override
    public int delete(Long billId) {
        return billDetailMapper.delete(billId);
    }
}
