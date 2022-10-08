package com.xu.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

//@Configuration
////@EnableSwagger2
//@EnableSwagger2WebMvc
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//    /** 是否开启swagger */
//    @Value("${swagger.enabled}")
//    private boolean enabled;
//
//    //配置swagger的docket的bean实例
//    @Bean
//    public Docket permissionAdmin(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                //分组
//                .groupName("后台系统功能接口")
//                .apiInfo(apiInfo())
////                enable是否启动swagger，false则不能在浏览器访问
//                .enable(enabled)
////                enable是否启动swagger，false则不能在浏览器访问
//                .select()
//                //配置要扫描接口的包
//                //basePackage: 指定扫描包
//                //any：扫描全部
//                //none():不扫描
//                //withClassAnnotation():扫描类上的注解，参数是一个注解的反射对象
//                //withMethodAnnotation():扫描方法上的注解
//                .apis(RequestHandlerSelectors.basePackage("com.xu"))
//                // path().过滤扫描路径
////                .paths(PathSelectors.ant("/xu/**"))
//                .paths(PathSelectors.any())
//                .build();   //build
//    }
//
//    //配置文档信息
//    private ApiInfo apiInfo() {
//        //作者信息
//        Contact contact = new Contact("YY", "localhost:83", "12adf@adf.com");
//        return new ApiInfo(
//                "各个功能模块的接口", // 标题
//                "可以测试各个接口", // 描述
//                "v1.0", // 版本
//                "localhost:83", // 组织链接
//                contact, // 联系人信息
//                "Apach 2.0 许可", // 许可
//                "localhost:83", // 许可连接
//                new ArrayList<>()// 扩展
//        );
//    }
//    // 添加资源
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//
////    @Override
////    protected void configureViewResolvers(ViewResolverRegistry registry) {
////        registry.viewResolver(new InternalResourceViewResolver());
////    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 设置允许跨域的路径
//        registry.addMapping("/**")
//                // 设置允许跨域请求的域名
//                .allowedOrigins("*")
//                // 是否允许证书 不再默认开启
//                .allowCredentials(true)
//                // 设置允许的方法
//                .allowedMethods("*")
//                // 跨域允许时间
//                .maxAge(3600);
//    }
//}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig implements WebMvcConfigurer {
    //配置swagger的docket的bean实例
    @Bean
    public Docket permissionAdmin(){
        return new Docket(DocumentationType.SWAGGER_2)
                //分组
                .groupName("后台系统功能接口")
                .apiInfo(apiInfo())
//                .enable(false) enable是否启动swagger，false则不能在浏览器访问
                .select()
                //配置要扫描接口的包
                //basePackage: 指定扫描包
                //any：扫描全部
                //none():不扫描
                //withClassAnnotation():扫描类上的注解，参数是一个注解的反射对象
                //withMethodAnnotation():扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.xu"))
                // path().过滤扫描路径
//                .paths(PathSelectors.ant("/xu/**"))
                .build();   //build
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("YY", "localhost:8080", "12adf@adf.com");
        return new ApiInfo(
                "各个功能模块的接口", // 标题
                "可以测试各个接口", // 描述
                "v1.0", // 版本
                "localhost:8080", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "localhost:8080", // 许可连接
                new ArrayList<>()// 扩展
        );
    }






    // 添加资源
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/v2/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
