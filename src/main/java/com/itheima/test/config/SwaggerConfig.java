//package com.itheima.test.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.RequestHandler;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2//开启swagger2功能
//public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("default") // 确保组名是唯一的
//                //指定构建文档api的详细信息
//                .apiInfo(apiInfo())
//                //此时返回的是ApiSelectorBuilder对象
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.itheima.test.controller"))//指定包路径
//                .paths(PathSelectors.any())
//                //此时返回的是Docket对象
//                .build();
//
//    }
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("接口测试")
//                .description("swagger接口测试")
//                .build();
//    }
//}
