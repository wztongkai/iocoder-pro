package com.iocoder.yudao.module.framework.config.swagger;

import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2的接口配置
 * @author kai wu
 */
@Configuration
public class Knife4jConfig {
    /**
     * 是否开启swagger
     */
    @Value("${swagger.enabled}")
    private boolean enabled;

    @Value("${server.port}")
    private String port;

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(this.enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(this.createApiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 只扫描带有ApiOperation注解的方法
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 对指定路径监控
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo createApiInfo() {
        return new ApiInfoBuilder()
                // api文档的标题属性:会在api文档中相应显示
                .title("iocoder系统在线接口文档")
                // api文档描述属性:会在api文档中相应显示
                .description("描述:展示系统中的接口信息")
                // 作者信息
                .contact(new Contact(IocoderConfig.getName(), null, null))
                // 服务url属性:会在api文档中相应显示
                .termsOfServiceUrl("http://localhost:" + this.port + "/")
                // 版本
                .version("版本号:" + IocoderConfig.getVersion())
                .build();
    }
}
