package com.example.springboot.dao;

import com.example.springboot.model.UserPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    UserPO findById(Integer id);

    @Insert("insert into user (name) values (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(UserPO userPO);
}
