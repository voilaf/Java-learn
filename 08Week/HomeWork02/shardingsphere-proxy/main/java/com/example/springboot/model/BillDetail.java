package com.example.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {

    private Long id;

    private Long billId;

    private Long commodityId;

    private Long commoditySellId;

    private Integer sellNums;

    private Integer price;

    private Integer totalPrice;

    private Date createTime;
}
