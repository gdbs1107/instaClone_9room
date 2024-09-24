package com.example.instaclone_9room.controller.userController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.JoinDto;
import com.example.instaclone_9room.controller.dto.UserDTO;
import com.example.instaclone_9room.service.userService.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Tag(name = "회원관련 API", description = "로그인을 제외한 회원관련 API 입니다")
public class UserController {


    private final UserCommandService userCommandService;


    @Operation(
            summary = "회원정보 업데이트",
            description = "회원정보 업데이트 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3002", description = "닉네임은 필수입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3005", description = "성별을 제대로 입력해주세요"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3006", description = "본인의 계정이 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3004", description = "최대 20자입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3006", description = "본인의 계정이 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @DeleteMapping("/")
    public ApiResponse<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails){

        userCommandService.deleteUser(userDetails.getUsername());
        return ApiResponse.onSuccess("deleted user");
    }




    @Operation(
            summary = "회원가입",
            description = "회원가입 API입니다. 헤더에 accessToken 없이 작동합니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3002", description = "닉네임은 필수입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3005", description = "성별을 제대로 입력해주세요"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3006", description = "본인의 계정이 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3004", description = "최대 20자입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3006", description = "본인의 계정이 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3006", description = "본인의 계정이 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/home")
    public ApiResponse<UserDTO.UserGetHomeResponseDTO> getHomeUser(@AuthenticationPrincipal UserDetails userDetails){

        UserDTO.UserGetHomeResponseDTO userGetHomeResponseDTO = userCommandService.userGetHomeProfile(userDetails.getUsername());
        return ApiResponse.onSuccess(userGetHomeResponseDTO);
    }




    @Operation(
            summary = "다른 사람의 회원정보 조회",
            description = "다른 사람의 회원정보를 조회할 수 있는 API입니다. 공개 계정은 인증 없이 조회 가능하며, 비공개 계정은 팔로워만 조회할 수 있습니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/profile/{targetUsername}")
    public ApiResponse<UserDTO.UserGetResponseDTO> getUserProfileByUsername(
            @PathVariable String targetUsername,
            @AuthenticationPrincipal UserDetails userDetails) {

        String requestingUsername = userDetails != null ? userDetails.getUsername() : null;
        UserDTO.UserGetResponseDTO userProfile = userCommandService.getUserProfileByUsername(targetUsername, requestingUsername);
        return ApiResponse.onSuccess(userProfile);
    }


    @Operation(
            summary = "소셜로그인 후 회원상세정보 입력 api",
            description = "소셜로그인 후 회원상세정보 입력 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/setUserInfo")
    public ApiResponse<String> setUserInfo(
            @RequestBody UserDTO.UserSetInfo request,
            @AuthenticationPrincipal UserDetails userDetails) {

        userCommandService.setUserInfo(request, userDetails.getUsername());
        return ApiResponse.onSuccess("success");
    }



}
