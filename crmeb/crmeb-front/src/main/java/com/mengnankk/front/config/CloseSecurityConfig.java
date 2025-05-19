package com.mengnankk.front.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 访问接口不在调用security
 */
@Configuration
@EnableWebSecurity
public class CloseSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();//取消 CSRF 防护
        http.authorizeRequests().anyRequest().permitAll().//放开所有请求（不鉴权）
                and().logout().permitAll();//放开注销请求

    }
}
