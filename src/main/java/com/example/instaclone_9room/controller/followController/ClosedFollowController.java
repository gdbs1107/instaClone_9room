package com.example.instaclone_9room.controller.followController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.ClosedFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/closedFollows")
public class ClosedFollowController {

    private final ClosedFollowService closedFollowService;

    @PostMapping("/{closeFollowerId}")
    public ApiResponse<String> closeFollowers(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long closeFollowerId) {
        closedFollowService.toggleCloseFollower(userDetails.getUsername(), closeFollowerId);
        return ApiResponse.onSuccess("closeFollow successfully");
    }

    @GetMapping("/")
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getCloseFollowers(@AuthenticationPrincipal UserDetails userDetails) {
        List<FollowDTO.FollowerResponseDTO> closeFollowers = closedFollowService.getCloseFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(closeFollowers);
    }
}
