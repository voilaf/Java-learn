package com.example.springboot.service.impl;

import com.example.springboot.mapper.BillMapper;
import com.example.springboot.model.Bill;
import com.example.springboot.model.BillDetail;
import com.example.springboot.service.BillDetailService;
import com.example.springboot.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillDetailService billDetailService;

    @Override
    public List<Bill> list() {
        return billMapper.list();
    }

    @Override
    public long create() {
        Long userId = Long.valueOf(new Random().nextInt(2000));
        Integer price = new Random().nextInt(9999);
        Bill bill = new Bill();
        bill.setUserId(userId);
        bill.setBillPrice(price);
        bill.setActualPrice(price);
        bill.setRefundPrice(0);
        bill.setContact("13700000" + new Random().nextInt(10));
        bill.setAddress("xxx");
        bill.setRefundReason("");
        if (billMapper.create(bill) <= 0) {
            return 0;
        }
        BillDetail billDetail = BillDetail.builder()
                .billId(bill.getId())
                .commodityId(1l)
                .commoditySellId(1l)
                .sellNums(1)
                .price(price)
                .totalPrice(price)
                .build();
        billDetailService.create(billDetail);
        return bill.getId();
    }

    @Override
    public int update(Long id) {
        return billMapper.update(id, "13700000" + new Random().nextInt(10));
    }

    @Override
    @Transactional
    public int delete(Long id) {
        billDetailService.delete(id);
        return billMapper.delete(id);
    }
}
