package com.andy.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*配置默认访问页:需要在浏览器中输入ip+端口号*/
        registry.addViewController("/").setViewName("forward:/login.do");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
