package com.example.springboot.mapper;

import com.example.springboot.model.Bill;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BillMapper {

    @Select("select * from t_bill order by id desc")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "bill_price", property = "billPrice"),
            @Result(column = "actual_price", property = "actualPrice"),
            @Result(column = "refund_price", property = "refundPrice"),
            @Result(column = "refund_reason", property = "refundReason"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
    })
    List<Bill> list();

    @Insert("insert into t_bill " +
            " (user_id, bill_price, actual_price, refund_price, contact, address, refund_reason, create_time, update_time)" +
            " values (#{userId}, #{billPrice}, #{actualPrice}, #{refundPrice}, #{contact}, #{address}, #{refundReason}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(Bill bill);

    @Update("update t_bill set contact = #{contact} where id = #{id}")
    int update(@Param("id") Long id, @Param("contact") String contact);

    @Delete("delete from t_bill where id = #{id}")
    int delete(@Param("id") Long id);
}
