package com.example.springsercuitydemo.Service;

import com.example.springsercuitydemo.entity.ResponseResult;
import com.example.springsercuitydemo.entity.User;

public interface Userservice {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult register(User user);
}
