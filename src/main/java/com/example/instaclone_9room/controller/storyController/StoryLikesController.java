package com.example.instaclone_9room.controller.storyController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.service.storyService.StoryLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storyLikes")
public class StoryLikesController {
    
    private final StoryLikesService storyLikesService;
    
    @PostMapping("/{storyId}")
    public ApiResponse<String> toggleLike(@PathVariable Long storyId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        
        storyLikesService.toggleStoryLike(storyId, userDetails.getUsername());
        return ApiResponse.onSuccess("성공");
    }
}
