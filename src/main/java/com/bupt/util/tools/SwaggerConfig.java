package com.bupt.util.tools;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * swagger2配置文件
 */

@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()       .apis(RequestHandlerSelectors.basePackage("com.bupt.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                //页面标题
                .title("贵州OTN项目后台")
                //创建人
                .contact(new Contact("北邮网管实验室研发组","https://github.com/buptnml/GuiZhou_OTN_Backend"," " +
                        "buptnml@163.com"))
                //版本号
                .version("0.0.8")
                //描述
                .build();

    }
}