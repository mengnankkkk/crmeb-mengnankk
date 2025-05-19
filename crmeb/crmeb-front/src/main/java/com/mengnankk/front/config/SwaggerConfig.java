package com.mengnankk.front.config;


import com.google.common.base.Predicate;
import com.mengnankk.common.config.CrmebConfig;
import com.mengnankk.common.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 用于在 Spring Boot 项目中生成 REST API 文档。它根据接口路径将接口文档分为两个分组（public 和 front），并添加了认证信息。
 */

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "api.doc")
public class SwaggerConfig {
    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    Boolean swaggerEnable = true;
    @Autowired
    CrmebConfig crmebConfig;

    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> res = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", Constants.HEADER_AUTHORIZATION_KEY);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] =authorizationScope;
        res.add(new SecurityReference(Constants.HEADER_AUTHORIZATION_KEY,authorizationScopes));
        return res;
    }
    private List<SecurityContext> securityReferences(){
        List<SecurityContext> res = new ArrayList<>();
        res.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build());
        return res;
    }
    private List<ApiKey> security(){
        return newArrayList(
                new ApiKey(Constants.HEADER_AUTHORIZATION_KEY,Constants.HEADER_AUTHORIZATION_KEY,"header")
        );
    }
    private Predicate<String> frontPathsAnt(){
        return PathSelectors.ant("/api/front/**");
    }
    private Predicate<String> publicPathsAnt() {
        return PathSelectors.ant("/api/public/**");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Crmeb Java")
                .description("Crmeb")
                .termsOfServiceUrl("http://host:port")
                .version("1.0.0").build();
    }
    private List<SecurityContext> securityContexts() {
        return securityReferences(); // 实际是调用 securityReferences() 方法
    }

    @Bean("public")
    public Docket create2RestApis(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("public")
                .host(crmebConfig.getDomain())
                .apiInfo(apiInfo())
                .enable(swaggerEnable)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.mengnankk.front"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(publicPathsAnt()) //只监听
                .build()
                .securitySchemes(security())
                .securityContexts(securityContexts())
//                .globalOperationParameters(pars) // 针对单个url的验证 如果需要的话
                .pathMapping("/");
    }
    @Bean("front")
    public Docket create1RestApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("front")
                .host(crmebConfig.getDomain())
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnable)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.mengnankk.front"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(frontPathsAnt()) //只监听
                .build()
                .securitySchemes(security())
                .securityContexts(securityContexts())
//                .globalOperationParameters(pars) // 针对单个url的验证 如果需要的话
                .pathMapping("/");
    }

}
