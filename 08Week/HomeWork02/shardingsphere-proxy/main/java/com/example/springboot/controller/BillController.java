package com.example.springboot.controller;

import com.example.springboot.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/list")
    public Object list() {
        return billService.list();
    }

    @GetMapping("/create")
    public long create() {
        return billService.create();
    }

    @GetMapping("/update/{id}")
    public int update(@PathVariable("id") Long id) {
        return billService.update(id);
    }

    @GetMapping("/delete/{id}")
    public int delete(@PathVariable("id") Long id) {
        return billService.delete(id);
    }
}
