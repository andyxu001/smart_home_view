package com.andy.home.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment){
        Profiles profiles = Profiles.of("dev","qa");
        boolean acceptsProfiles = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                //选择环境
                .enable(acceptsProfiles)
                .apiInfo(apiInfo())
                //过滤掉basic-error-controller，只显示apiOperation
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("swagger title for andy demo")
                .description("andy demo 的描述文档")
                .version("3.0.0")
                .termsOfServiceUrl("https://baidu.com")
                .contact(new Contact("andy","https//qq.com","597248181@qq.com"))
                .build();
    }
}
