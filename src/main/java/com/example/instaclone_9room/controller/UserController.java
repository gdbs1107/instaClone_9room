package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.service.userService.UserCommandService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;



    @PutMapping("/")
    public ApiResponse<String> updateUser(@RequestBody UserDTO.UserUpdateRequestDTO request,
                                          @AuthenticationPrincipal UserDetails userDetails){

        userCommandService.updateUser(request,userDetails.getUsername());
        return ApiResponse.onSuccess("updated user");
    }

    @DeleteMapping("/")
    public ApiResponse<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails){

        userCommandService.deleteUser(userDetails.getUsername());
        return ApiResponse.onSuccess("deleted user");
    }

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDto.JoinRequestDTO joinDto){

        userCommandService.joinProcess(joinDto);
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


    @GetMapping("/")
    public ApiResponse<UserDTO.UserGetResponseDTO> getUser(@AuthenticationPrincipal UserDetails userDetails){

        UserDTO.UserGetResponseDTO userProfile = userCommandService.getUserProfile(userDetails.getUsername());
        return ApiResponse.onSuccess(userProfile);

    }

    @GetMapping("/home")
    public ApiResponse<UserDTO.UserGetHomeResponseDTO> getHomeUser(@AuthenticationPrincipal UserDetails userDetails){

        UserDTO.UserGetHomeResponseDTO userGetHomeResponseDTO = userCommandService.userGetHomeProfile(userDetails.getUsername());
        return ApiResponse.onSuccess(userGetHomeResponseDTO);
    }
}
