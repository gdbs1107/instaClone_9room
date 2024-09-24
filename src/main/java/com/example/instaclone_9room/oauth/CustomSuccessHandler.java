package com.example.instaclone_9room.oauth;

import com.example.instaclone_9room.controller.dto.CustomOAuth2User;
import com.example.instaclone_9room.jwt.JwtUtil;
import com.example.instaclone_9room.service.RefreshTokenService;
import com.example.instaclone_9room.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("onAuthenticationSuccess가 작동하여 쿠키가 생성됩니다");

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String name=customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        Integer expireS = 24 * 60 * 60;

        String access = jwtUtil.createJwt("access", username, role, 60 * 10 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, expireS * 1000L);

        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        refreshTokenService.saveRefresh(username,86400000,refresh);


        // redirect query param 인코딩 후 전달
        // 이후에 JWT 를 읽어서 데이터를 가져올 수도 있지만, JWT 파싱 비용이 많이 들기 때문에
        // 처음 JWT 발급할 때 이름을 함께 넘긴 후, 로컬 스토리지에 저장한다.
        String encodedName = URLEncoder.encode(name, "UTF-8");
        response.sendRedirect("http://localhost:3000/oauth2-jwt-header?name=" + encodedName);


    }
}