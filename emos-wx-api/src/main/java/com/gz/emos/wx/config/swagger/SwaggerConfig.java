package com.gz.emos.wx.config.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
     * swagger各种配置信息存到docket对象中
     * 创建API
     * http:IP:端口号/swagger-ui/index.html 原生地址
     * http:IP:端口号/doc.html bootStrap-UI地址
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //指定api类型
        Docket docket = new Docket(DocumentationType.OAS_30);
        //配置基本信息
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("EMOS在线办公系统");
        ApiInfo info = builder.build();
        docket.apiInfo(info);
        //配置swagger可访问范围
        ApiSelectorBuilder selectorBuilder = docket.select();
        //所有包下的所有类
        selectorBuilder.paths(PathSelectors.any());
        //可访问方法
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        docket = selectorBuilder.build();

        //配置jwt信息
        ApiKey apiKey = new ApiKey("token", "token", "header");
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(apiKey);
        docket.securitySchemes(securitySchemes);
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = {scope};
        SecurityReference reference=new SecurityReference("token",scopes);
        List<SecurityReference> refList=new ArrayList();
        refList.add(reference);
        SecurityContext context=SecurityContext.builder().securityReferences(refList).build();
        List<SecurityContext> ctxList=new ArrayList();
        ctxList.add(context);
        docket.securityContexts(ctxList);
        return docket;
    }
}
