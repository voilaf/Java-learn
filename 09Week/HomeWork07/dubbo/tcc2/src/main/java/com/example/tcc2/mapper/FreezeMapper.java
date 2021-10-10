package com.example.tcc2.mapper;

import com.example.tcc2.model.AssetsFreezePO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

@Mapper
public interface FreezeMapper {

    @Insert("insert into t_assets_freeze (user_id, transfer_amount, type, distributed_unique_no, create_time, update_time) " +
            " values(#{userId}, #{transferAmount}, #{type}, #{distributedUniqueNo}, now(), now())")
    int create(@Param("userId") Integer userId, @Param("transferAmount")BigDecimal transferAmount, @Param("type") int type, @Param("distributedUniqueNo") String distributedUniqueNo);

    @Select("select * from t_assets_freeze where user_id = #{userId} and distributed_unique_no = #{distributedUniqueNo}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "transfer_amount", property = "transferAmount"),
            @Result(column = "distributed_unique_no", property = "distributedUniqueNo"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    AssetsFreezePO findByUserIdAndUniqueNo(Integer userId, String distributedUniqueNo);

    @Update("update t_assets_freeze set status = #{status}, update_time = now() where id = #{id} and status = 0")
    int updateStatus(Integer id, Integer status);
}
