package com.andy.home.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class JspConfig {

    @Bean//注册视图解析器
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");//自动添加前缀
        resolver.setSuffix(".jsp");//自动添加后缀
        return resolver;
    }


}
