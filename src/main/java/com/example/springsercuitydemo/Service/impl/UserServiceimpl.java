package com.example.springsercuitydemo.Service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.example.springsercuitydemo.Service.Userservice;
import com.example.springsercuitydemo.entity.LoginUser;
import com.example.springsercuitydemo.entity.ResponseResult;
import com.example.springsercuitydemo.entity.User;
import com.example.springsercuitydemo.mapper.UserMapper;
import com.example.springsercuitydemo.utils.JwtUtil;
import com.example.springsercuitydemo.utils.RedisCache;

@Service
public class UserServiceimpl implements Userservice {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 通过Userid去生成token
        String Userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(Userid);
        // 将 用户信息存到redis中
        redisCache.setCacheObject("login:" + Userid, loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        // 先从SecurityContext中获取用户信息
        UsernamePasswordAuthenticationToken authentication;
        LoginUser loginUser;
        try {
            authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                    .getContext().getAuthentication();
            loginUser = (LoginUser) authentication.getPrincipal();
        } catch (Exception e) {
            return new ResponseResult(201, "用户未登录");
        }
        Long id = loginUser.getUser().getId();
        // 删除redis中的登录信息
        redisCache.deleteObject("login:" + id);
        return new ResponseResult(200, "退出成功");
    }

    @Override
    public ResponseResult register(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return new ResponseResult(200, "注册成功");
        }

        return new ResponseResult(201, "注册失败");
    }

}
