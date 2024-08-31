package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.apiPayload.exception.handler.MemberCategoryHandler;
import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.service.S3TestService;
import com.example.instaclone_9room.service.userService.UserCommandService;
import com.example.instaclone_9room.service.userService.UserProfileImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Tag(name = "회원관련 API", description = "로그인을 제외한 회원관련 API 입니다")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserProfileImageService s3service;


    @Operation(
            summary = "회원정보 업데이트",
            description = "회원정보 업데이트 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @PutMapping("/")
    public ApiResponse<String> updateUser(@RequestBody @Valid UserDTO.UserUpdateRequestDTO request,
                                          @AuthenticationPrincipal UserDetails userDetails){

        userCommandService.updateUser(request, userDetails.getUsername());
        return ApiResponse.onSuccess("User updated successfully");
    }



    @Operation(
            summary = "회원정보 삭제",
            description = "회원정보 업데이트 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @DeleteMapping("/")
    public ApiResponse<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails){

        userCommandService.deleteUser(userDetails.getUsername());
        return ApiResponse.onSuccess("deleted user");
    }




    @Operation(
            summary = "회원가입",
            description = "회원가입 API입니다. 헤더에 accessToken 없이 작동합니다"
    )
    @PostMapping("/join")
    public String joinProcess(@RequestBody @Valid JoinDto.JoinRequestDTO joinDto){

        userCommandService.joinProcess(joinDto);
        return "ok";
    }




    @Operation(
            summary = "로그아웃",
            description = "로그아웃 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
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





    @Operation(
            summary = "본인 회원정보 상세조회",
            description = "회원정보 조회 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @GetMapping("/")
    public ApiResponse<UserDTO.UserGetResponseDTO> getUser(@AuthenticationPrincipal UserDetails userDetails){

        UserDTO.UserGetResponseDTO userProfile = userCommandService.getUserProfile(userDetails.getUsername());
        return ApiResponse.onSuccess(userProfile);

    }




    @Operation(
            summary = "프로필 홈페이지 조회",
            description = "프로필 홈페이지 조회 API입니다. 인스타그램 웹에서 프로필 버튼 누르면 바로 보이는 정보가 담겨있습니다." +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다" +
                    "게시물 API가 완성되면 전체 게시물 조회 API가 담길 예정입니다"
    )
    @GetMapping("/home")
    public ApiResponse<UserDTO.UserGetHomeResponseDTO> getHomeUser(@AuthenticationPrincipal UserDetails userDetails){

        UserDTO.UserGetHomeResponseDTO userGetHomeResponseDTO = userCommandService.userGetHomeProfile(userDetails.getUsername());
        return ApiResponse.onSuccess(userGetHomeResponseDTO);
    }




    @Operation(
            summary = "다른 사람의 회원정보 조회",
            description = "다른 사람의 회원정보를 조회할 수 있는 API입니다. 공개 계정은 인증 없이 조회 가능하며, 비공개 계정은 팔로워만 조회할 수 있습니다."
    )
    @GetMapping("/profile/{targetUsername}")
    public ApiResponse<UserDTO.UserGetResponseDTO> getUserProfileByUsername(
            @PathVariable String targetUsername,
            @AuthenticationPrincipal UserDetails userDetails) {

        String requestingUsername = userDetails != null ? userDetails.getUsername() : null;
        UserDTO.UserGetResponseDTO userProfile = userCommandService.getUserProfileByUsername(targetUsername, requestingUsername);
        return ApiResponse.onSuccess(userProfile);
    }



    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfileImage(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {

        String username = userDetails.getUsername();

        String dirName="User Profile Image";
        String url = s3service.upload(multipartFile, dirName, username);
        log.info("파일 업로드 완료: {}", url);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }




    @GetMapping(path = "/{imageId}")
    public ResponseEntity<byte[]> getPetImage(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();
        try {
            byte[] fileData = s3service.download(username);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename");
            log.info("파일 다운로드 완료: ID {}", username);
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("파일 다운로드 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping(path = "/{imageId}")
    public ResponseEntity<Void> deletePetImage(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        try {
            s3service.deleteFile(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("파일 삭제 오류: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
