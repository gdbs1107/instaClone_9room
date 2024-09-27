package com.example.instaclone_9room.controller.followController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.BlockedFollowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blockedFollows")
@Tag(name = "차단 계정 API", description = "차단계정 관리 API입니다. 팔로워를 기준으로 작동합니다")
public class BlockedFollowsController {

    private final BlockedFollowerService blockedFollowerService;



    @Operation(
            summary = "차단계정 토글",
            description = "차단계정 토글 API입니다. 한 번 호출하면 차단 -> 두 번 호출시 차단이 풀립니다. " +
                    "헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "FOLLOW5001", description = "팔로우가 아닌 상대를 차단할 경우 응답됩니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "FOLLOW5002", description = "대상을 찾을 수 없습니다"
            )
    })
    @PostMapping("/{blockedFollowerId}")
    public ApiResponse<String> blockedFollowers(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Long blockedFollowerId) {
        blockedFollowerService.toggleBlockedFollower(userDetails.getUsername(), blockedFollowerId);
        return ApiResponse.onSuccess("closeFollow successfully");
    }




    @Operation(
            summary = "차단계정 조회 API",
            description = "차단계정 조회 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/")
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getBlockedFollowers(@AuthenticationPrincipal UserDetails userDetails) {
        List<FollowDTO.FollowerResponseDTO> blockedFollowers = blockedFollowerService.getBlockedFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(blockedFollowers);
    }
}
