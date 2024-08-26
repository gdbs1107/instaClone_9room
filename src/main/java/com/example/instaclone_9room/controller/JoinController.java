package com.example.instaclone_9room.controller;


import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.service.JoinService;
import com.example.instaclone_9room.service.userService.UserCommandService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    private final UserCommandService userCommandService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDto.JoinRequestDTO joinDto){

        joinService.joinProcess(joinDto);
        return "ok";
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        userCommandService.logout(refreshToken);

        // 클라이언트 쪽 쿠키 삭제
        Cookie refreshCookie = new Cookie("refresh", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃 성공"));
    }
}
