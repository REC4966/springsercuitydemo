package com.example.springsercuitydemo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsercuitydemo.filter.JwtAuthenticationTokenFilter;
import com.example.springsercuitydemo.Exceptionhandler.AccessDeniedHandlerImpl;
import com.example.springsercuitydemo.Exceptionhandler.AuthenticationEntryPointImpl;

@Configuration
// 开启权限注解 EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandlerImpl AccessDeniedHandlerImpl;
    @Autowired
    private AuthenticationEntryPointImpl AuthenticationEntryPointImpl;

    // 更改加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 因为我们都是携带token所以我可以关掉这个防护
                .csrf().disable()
                // 不允许通过session获取 SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 登录接口允许匿名访问
                .antMatchers("/user/login", "/user/register").anonymous()
                // 其他接口都要认证后才能访问
                .anyRequest().authenticated();
        // 添加自定义的filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 异常处理
        http.exceptionHandling().accessDeniedHandler(AccessDeniedHandlerImpl)
                .authenticationEntryPoint(AuthenticationEntryPointImpl);
    }
}
