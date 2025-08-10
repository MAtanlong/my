package com.permission.common.config;

/*
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
*/

/**
 * Swagger配置 - 暂时禁用
 *
 * @author system
 * @since 2024-01-01
 */
// @Configuration
public class SwaggerConfig {
    
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";
    
    /*
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("请输入JWT令牌，格式：Bearer {token}")
                        )
                );
    }
    */
    
    /*
    /**
     * API信息
     */
    /*
    private Info apiInfo() {
        return new Info()
                .title("权限管理系统API文档")
                .description("基于Spring Cloud微服务架构的权限管理系统，提供用户管理、角色管理、权限管理、菜单管理等功能")
                .version("1.0.0")
                .contact(new Contact()
                        .name("系统管理员")
                        .email("admin@permission.com")
                        .url("https://github.com/permission-system")
                )
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")
                );
    }
    */
    
}