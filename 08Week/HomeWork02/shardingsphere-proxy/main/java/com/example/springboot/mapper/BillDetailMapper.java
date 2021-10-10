package com.example.springboot.mapper;

import com.example.springboot.model.BillDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BillDetailMapper {

    @Select("select * from t_bill_details where bill_id = #{billId}")
    @Results({
            @Result(column = "bill_id", property = "billId"),
            @Result(column = "commodity_id", property = "commodityId"),
            @Result(column = "commodity_sell_id", property = "commoditySellId"),
            @Result(column = "sell_nums", property = "sellNums"),
            @Result(column = "total_price", property = "totalPrice"),
            @Result(column = "create_time", property = "createTime")
    })
    List<BillDetail> listByBillId(Long billId);

    @Insert("insert into t_bill_details " +
            " (bill_id, commodity_id, commodity_sell_id, sell_nums, price, total_price, create_time)" +
            " values (#{billId}, #{commodityId}, #{commoditySellId}, #{sellNums}, #{price}, #{totalPrice}, now())")
    int create(BillDetail billDetail);

    @Delete("delete from t_bill_details where bill_id = #{billId}")
    int delete(Long billId);
}
