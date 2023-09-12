package com.andy.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域无法访问的问题
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   //所有接口
        .allowCredentials(true)    //是否发送cookie
        .allowedOrigins("*")  //支持所有域
        .allowedMethods(new String[]{"POST","PUT","GET","DELETE","PATCH"})
        .allowedHeaders("*")
        .exposedHeaders("*");
    }
}
