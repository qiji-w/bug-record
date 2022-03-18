package com.bug.bugrecordbackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Value("${springdoc.server.host}")
    String springdocServerHost;

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("BugRecord")
                .packagesToScan("com.bug.bugrecordbackend.controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();
        //添加服务地址
        Server server = new Server();
        server.setUrl(springdocServerHost);

        return new OpenAPI()
                .components(components)
                .info(apiInfo())
                .addServersItem(server);
    }

    private Info apiInfo() {
        Contact contact = new Contact();
        return new Info()
                .title("《测试BUG排行》接口文档")
                .version("1.0")
                .contact(contact)
                .description("描述《测试BUG排行》后端所有REST接口信息。");
    }
}