package com.example.instaclone_9room.controller.followController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follows")
@Tag(name = "팔로우 API", description = "팔로우 API입니다.")
public class FollowController {

    private final FollowService followService;



    @Operation(
            summary = "팔로우 토글 API",
            description = "팔로우 등록 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다.<br>" +
                    "팔로우 할 대상을 PathVariable에 담아서 요청하시면 됩니다.<br>" +
                    "한 번 클릭하면 팔로우 등록, 두 번 클릭하면 팔로우에서 제외됩니다.<br>" +
                    "팔로우 할 대상의 팔로워가 추가됩니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FOLLOW5002", description = "대상을 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/follow/{targetUserId}")
    public ApiResponse<String> follow(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable Long targetUserId) {

        followService.toggleFollowUser(userDetails.getUsername(), targetUserId);
        return ApiResponse.onSuccess("follow successfully");

    }


    @Operation(
            summary = "팔로우 조회 API",
            description = "팔로우 조회 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/follows")
    public ApiResponse<List<FollowDTO.FollowResponseDTO>> getAllFollows(@AuthenticationPrincipal UserDetails userDetails) {

        List<FollowDTO.FollowResponseDTO> followedUsers = followService.getFollowedUsers(userDetails.getUsername());

        return ApiResponse.onSuccess(followedUsers);
    }



    @Operation(
            summary = "팔로워 조회 API",
            description = "팔로워 조회 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @GetMapping("/followers")
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getAllFollowers(@AuthenticationPrincipal UserDetails userDetails) {

        List<FollowDTO.FollowerResponseDTO> followerUsers = followService.getFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(followerUsers);
    }



}
