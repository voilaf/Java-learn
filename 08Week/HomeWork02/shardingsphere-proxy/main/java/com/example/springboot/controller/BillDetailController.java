package com.example.springboot.controller;

import com.example.springboot.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billDetail")
public class BillDetailController {

    @Autowired
    private BillDetailService billDetailService;

    @GetMapping("/{billId}")
    public Object detail(@PathVariable("billId") Long billId) {
        return billDetailService.detail(billId);
    }
}
