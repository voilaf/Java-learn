package com.example.user.mapper;

import com.example.user.model.AccountPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {


    @Select("select * from t_account")
    List<AccountPO> list();

    @Insert("insert into t_account values (null, #{name}, #{phone})")
    int create(AccountPO accountPO);

    @Insert("insert into t_operate_log values (null, #{content})")
    int addOperateLog(String content);

    @Select("select * from t_account where phone = #{phone}")
    AccountPO findByPhone(String phone);

    @Delete("delete from t_account where phone = #{phone}")
    int deleteByPhone(String phone);
}
