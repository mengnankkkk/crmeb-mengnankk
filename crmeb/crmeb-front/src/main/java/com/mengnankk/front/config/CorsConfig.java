package com.mengnankk.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS（跨源资源共享）是一种浏览器安全机制，默认禁止浏览器从一个域向另一个域发起请求。Spring Boot 默认开启了安全限制，跨域请求会被拦截。
 * 开启全局放行
 */
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");  // 允许所有来源（也可以指定域名）
        corsConfiguration.addAllowedHeader("*");  // 允许所有请求头
        corsConfiguration.addAllowedMethod("*");  // 允许所有请求方式（GET、POST、PUT、DELETE等）
        corsConfiguration.setAllowCredentials(true); // 允许携带 cookie
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对所有路径生效
        return new CorsFilter(source);
    }
}
