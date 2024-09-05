package com.example.instaclone_9room.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI UMCstudyAPI(){
        Info info=new Info()
                .title("인스타 클론 코딩")
                .description("구름톤에서 스터디로 진행한 인스타그램 클론코딩 프로젝트 API 명세서 입니다 <br>" +
                        "각 API의 회원정보에 대해서는 세션에서 가져오기 때문에 accessToken만 헤더에 담아서 요청 넘겨주시면 됩니다")
                .version("1.0");

        String jwtSchemeName="JWTToken";

        SecurityRequirement securityRequirement=new SecurityRequirement().addList(jwtSchemeName);

        Components components=new Components()
                .addSecuritySchemes(jwtSchemeName,new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);

    }

}
