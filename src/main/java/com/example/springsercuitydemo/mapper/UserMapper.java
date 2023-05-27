package com.example.springsercuitydemo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springsercuitydemo.entity.User;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from sys_user where user_name =#{name}")
    User selecUserByusername(String name);

    @Insert("insert into user (user_name,nick_name,password,user_type) values (#{userName},#{nickName},#{password},#{userType})")
    int insertUser(User user);
}
