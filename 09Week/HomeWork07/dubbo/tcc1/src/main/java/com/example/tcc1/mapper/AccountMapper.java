package com.example.tcc1.mapper;

import com.example.tcc1.model.AssetsAccountPO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {


    @Select("select * from t_assets_account where user_id = #{userId}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "dollar_balance", property = "dollarBalance"),
            @Result(column = "rmb_balance", property = "rmbBalance"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    AssetsAccountPO findByUserId(int userId);

    @Insert("insert into t_assets_account (user_id) values (#{userId})")
    int create(int userId);

    @Update("update t_assets_account set version = version + 1, " +
            " dollar_balance = #{dollarBalance}, rmb_balance = #{rmbBalance}, update_time = now() " +
            " where user_id = #{userId} and version = #{version}")
    int updateAssets(AssetsAccountPO accountPO);
}
