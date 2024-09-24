package com.example.instaclone_9room.controller.feedController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.domain.userEntity.UserEntity;

import com.example.instaclone_9room.service.feedService.FeedLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedLikes")
public class FeedLikesController {
    
    private final FeedLikesService feedLikesService;
    
    @PostMapping("/{feedId}")
    public ApiResponse<String> toggleLike(@PathVariable Long feedId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        feedLikesService.toggleFeedLike(feedId, userDetails.getUsername());
        return ApiResponse.onSuccess("성공");
    }
}
