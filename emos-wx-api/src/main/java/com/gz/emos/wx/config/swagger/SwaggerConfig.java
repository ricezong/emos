package com.gz.emos.wx.config.swagger;

import io.swagger.v3.oas.annotations.Operation;
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
     * http:IP:端口号/doc.html Knife4j地址
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //指定api类型
        Docket docket = new Docket(DocumentationType.OAS_30);
        // ApiInfoBuilder 用于在Swagger界面上添加各种信息
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("EMOS在线办公系统");
        ApiInfo info = builder.build();
        docket.apiInfo(info);
        // ApiSelectorBuilder 用来设置哪些类中的方法会生成到REST API中
        ApiSelectorBuilder selectorBuilder = docket.select();
        //所有包下的所有类
        selectorBuilder.paths(PathSelectors.any());
        //可访问方法，使用@Operation的方法会被提取到REST API中
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class));
        docket = selectorBuilder.build();
        /**
         * 下面的语句是开启对JWT的支持，当用户用Swagger调用受JWT认证保护的方法，
         * 必须要先提交参数（例如令牌）
         */
        //规定用户需要输入什么参数
        ApiKey apiKey = new ApiKey("token", "token", "header");
        //存储用户必须提交的参数
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(apiKey);
        docket.securitySchemes(securitySchemes);
        //如果用户JWT认证通过，则在Swagger中全局有效
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = {scope};
        //存储令牌和作用域
        SecurityReference reference = new SecurityReference("token", scopes);
        List<SecurityReference> refList = new ArrayList();
        refList.add(reference);
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
        List<SecurityContext> ctxList = new ArrayList();
        ctxList.add(context);
        docket.securityContexts(ctxList);
        return docket;
    }
}
