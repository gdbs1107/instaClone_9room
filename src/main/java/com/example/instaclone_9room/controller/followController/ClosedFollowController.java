package com.example.instaclone_9room.controller.followController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.ClosedFollowService;
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
@RequestMapping("/closedFollows")
@Tag(name = "친한계정 API", description = "친한계정 API입니다. 팔로워를 기준으로 작동합니다")
public class ClosedFollowController {

    private final ClosedFollowService closedFollowService;


    @Operation(
            summary = "친한계정 토글 API",
            description = "친한계정 토글 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다" +
                    "한 번 클릭하면 친한친구 등록, 두 번 클릭하면 친한친구에서 제외됩니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER3001", description = "사용자를 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FOLLOW5001", description = "팔로우가 되어있지 않아 요청을 처리 할 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FOLLOW5002", description = "대상을 찾을 수 없습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2001", description = "유효하지 않은 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2002", description = "만료된 토큰입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN2003", description = "토큰이 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON1001", description = "서버에러, 관리자에게 문의 바랍니다",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{closeFollowerId}")
    public ApiResponse<String> closeFollowers(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long closeFollowerId) {
        closedFollowService.toggleCloseFollower(userDetails.getUsername(), closeFollowerId);
        return ApiResponse.onSuccess("closeFollow successfully");
    }


    @Operation(
            summary = "친한계정 조회 API",
            description = "친한계정 조회 API입니다. 헤더에 accessToken을 담아서 요청하시면 됩니다"
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
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getCloseFollowers(@AuthenticationPrincipal UserDetails userDetails) {
        List<FollowDTO.FollowerResponseDTO> closeFollowers = closedFollowService.getCloseFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(closeFollowers);
    }
}
