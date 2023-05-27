package com.example.springsercuitydemo.Service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springsercuitydemo.entity.LoginUser;
import com.example.springsercuitydemo.entity.User;
import com.example.springsercuitydemo.mapper.UserMapper;
import com.example.springsercuitydemo.mapper.menuMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private menuMapper menumapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userMapper.selecUserByusername(name);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 根据用户查询权限信息 添加到LoginUser中
        List<String> list = menumapper.selectPermsByUserid(user.getId());
        // 如果用户存在，就返回一个UserDetails的实现类
        return new LoginUser(user, list);
    }

}
