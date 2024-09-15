package com.example.instaclone_9room.controller.feedController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.service.feedService.FeedCommentLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedCommentLikes")
public class FeedCommentLikesController {
    
    private final FeedCommentLikesService feedCommentLikesService;
    
    @PostMapping("/{feedCommentId}")
    public ApiResponse<String> toggleLike(@PathVariable Long feedCommentId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        feedCommentLikesService.toggleFeedCommentLike(feedCommentId, userDetails.getUsername());
        return ApiResponse.onSuccess("성공");
    }
}
