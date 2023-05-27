package com.example.springsercuitydemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springsercuitydemo.entity.ResponseResult;
import com.example.springsercuitydemo.entity.User;
import com.example.springsercuitydemo.Service.Userservice;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Userservice UserService;

    @RequestMapping("/hello")
    // @PreAuthorize("hasAuthority('system:dept:list')")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping("/hello1")
    public String hello1() {
        return "Hello World!";
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return UserService.login(user);
    }

    @RequestMapping("/logout")
    public ResponseResult logout() {
        return UserService.logout();
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        return UserService.register(user);
    }

}
