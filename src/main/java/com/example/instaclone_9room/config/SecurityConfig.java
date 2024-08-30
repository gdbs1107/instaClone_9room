package com.example.instaclone_9room.config;


import com.example.instaclone_9room.jwt.CustomLogoutFilter;
import com.example.instaclone_9room.jwt.JWTFilter;
import com.example.instaclone_9room.jwt.JwtUtil;
import com.example.instaclone_9room.jwt.LoginFilter;
import com.example.instaclone_9room.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록, UsernamePasswordAuthenticationFilter에서 필요하기 때문에 생성자로 주입해
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 클라이언트 URL
        configuration.setAllowedMethods(List.of("*")); // 모든 HTTP 메소드 허용
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키, 인증 정보 허용
        configuration.setMaxAge(3600L); // CORS 사전 요청 캐시 시간 설정
        configuration.setExposedHeaders(List.of("Authorization")); // 클라이언트가 접근할 수 있는 응답 헤더

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 클라이언트 URL
                            configuration.setAllowedMethods(List.of("*")); // 모든 HTTP 메소드 허용
                            configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
                            configuration.setAllowCredentials(true); // 쿠키, 인증 정보 허용
                            configuration.setMaxAge(3600L); // CORS 사전 요청 캐시 시간 설정
                            configuration.setExposedHeaders(List.of("Authorization")); // 클라이언트가 접근할 수 있는 응답 헤더
                            return configuration;
                        })
                );



        //csrf는 rest구조에서 필요없음(STATELESS상태이기 때문)
        http.csrf((auth)->auth.disable());

        //위와 동
        http.formLogin((auth)->auth.disable());
        http.httpBasic((auth)->auth.disable());


        //소셜로그인
        http.oauth2Login(Customizer.withDefaults());


        //인가 구현
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/login","/","/join").permitAll()
                .requestMatchers("/reissue").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Swagger 관련 경로를 허용
                .anyRequest().authenticated());

        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,refreshRepository), UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}