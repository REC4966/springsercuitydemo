package com.example.springsercuitydemo.Exceptionhandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.example.springsercuitydemo.entity.ResponseResult;
import com.example.springsercuitydemo.utils.WebUtils;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest arg0, HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "权限不足，请联系管理员");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }

}
