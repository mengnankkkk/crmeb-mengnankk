package com.mengnankk.front.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置 Druid 数据库连接池和监控面板
 */
@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean druidServlet(){//// 主要实现WEB监控的配置处理
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");//// 进行druid监控的配置处理操作
        servletRegistrationBean.addInitParameter("loginUsername", "kf");
        servletRegistrationBean.addInitParameter("loginPassword", "654321");
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        servletRegistrationBean.addInitParameter("deny", "192.168.1.200");
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,192.168.1.159");
        return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        // 所有请求进行监控处理
        filterRegistrationBean.addUrlPatterns("/*");
        //不必监控的请求
        filterRegistrationBean.addInitParameter("exclusions", "*.html,*.png,*.ico,*.js,*.gif,*.jpg,*.css,/druid/*");
        return filterRegistrationBean;
    }
    //配置 Druid 数据源
    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return  new DruidDataSource();
    }
}
