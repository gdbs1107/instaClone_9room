package com.example.instaclone_9room.controller.followController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.BlockedFollowerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blockedFollows")
public class BlockedFollowsController {

    private final BlockedFollowerService blockedFollowerService;

    @PostMapping("/{blockedFollowerId}")
    public ApiResponse<String> blockedFollowers(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Long blockedFollowerId) {
        blockedFollowerService.toggleBlockedFollower(userDetails.getUsername(), blockedFollowerId);
        return ApiResponse.onSuccess("closeFollow successfully");
    }

    @GetMapping("/")
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getBlockedFollowers(@AuthenticationPrincipal UserDetails userDetails) {
        List<FollowDTO.FollowerResponseDTO> blockedFollowers = blockedFollowerService.getBlockedFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(blockedFollowers);
    }
}
