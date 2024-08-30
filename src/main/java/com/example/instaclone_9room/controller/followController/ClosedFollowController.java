package com.example.instaclone_9room.controller.followController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.ClosedFollowService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/")
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getCloseFollowers(@AuthenticationPrincipal UserDetails userDetails) {
        List<FollowDTO.FollowerResponseDTO> closeFollowers = closedFollowService.getCloseFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(closeFollowers);
    }
}
