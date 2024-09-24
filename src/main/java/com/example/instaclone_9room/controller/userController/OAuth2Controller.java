package com.example.instaclone_9room.controller.userController;

import com.example.instaclone_9room.service.oauth2Service.OAuth2JWTHeaderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class OAuth2Controller {

    private final OAuth2JWTHeaderService oAuth2JwtHeaderService;


    @PostMapping("/oauth2-jwt-header")
    public String oauth2JwtHeader(HttpServletRequest request, HttpServletResponse response) {
        log.info("oauth2-jwt-header 컨트롤러가 실행됩니다");
        return oAuth2JwtHeaderService.oauth2JwtHeaderSet(request, response);
    }

}