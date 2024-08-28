package com.example.instaclone_9room.controller;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.FollowDTO;
import com.example.instaclone_9room.service.followService.FollowService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{targetUserId}")
    public ApiResponse<String> follow(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable Long targetUserId) {

        followService.followUser(userDetails.getUsername(), targetUserId);
        return ApiResponse.onSuccess("follow successfully");

    }


    @PostMapping("/unfollow/{targetUserId}")
    private ApiResponse<String> unfollow(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long targetUserId) {

        followService.unfollowUser(userDetails.getUsername(), targetUserId);
        return ApiResponse.onSuccess("unfollow successfully");
    }

    @GetMapping("/follows")
    public ApiResponse<List<FollowDTO.FollowResponseDTO>> getAllFollows(@AuthenticationPrincipal UserDetails userDetails) {

        List<FollowDTO.FollowResponseDTO> followedUsers = followService.getFollowedUsers(userDetails.getUsername());

        return ApiResponse.onSuccess(followedUsers);
    }

    @GetMapping("/followers")
    public ApiResponse<List<FollowDTO.FollowerResponseDTO>> getAllFollowers(@AuthenticationPrincipal UserDetails userDetails) {

        List<FollowDTO.FollowerResponseDTO> followerUsers = followService.getFollowers(userDetails.getUsername());

        return ApiResponse.onSuccess(followerUsers);
    }



    @PostMapping("/closeFollower/{closeFollowerId}")
    public ApiResponse<String> closeFollowers(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long closeFollowerId) {
        followService.toggleCloseFollower(userDetails.getUsername(), closeFollowerId);
        return ApiResponse.onSuccess("closeFollow successfully");
    }

    @PostMapping("/blockFollower/{blockedFollowerId}")
    public ApiResponse<String> blockedFollowers(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long blockedFollowerId) {
        followService.toggleBlockedFollower(userDetails.getUsername(), blockedFollowerId);
        return ApiResponse.onSuccess("closeFollow successfully");
    }
}
