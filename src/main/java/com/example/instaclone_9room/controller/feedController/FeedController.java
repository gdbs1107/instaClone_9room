package com.example.instaclone_9room.controller.feedController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.feedDTO.FeedDTO;
import com.example.instaclone_9room.service.feedService.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    
    private final FeedService feedService;
    
    @PostMapping("/")
    public ApiResponse<String> postFeed(@RequestBody FeedDTO.FeedPostRequestDTO req,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        feedService.postFeed(req, userDetails.getUsername());
        return ApiResponse.onSuccess("saved feed");
    }
    
    @PutMapping("/{feedId}")
    public ApiResponse<String> updateFeed(@PathVariable Long feedId,
                                          @RequestBody FeedDTO.FeedUpdateRequestDTO req,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        feedService.updateFeed(feedId, req, userDetails.getUsername());
        
        return ApiResponse.onSuccess("updated feed");
    }
    
    @DeleteMapping("/{feedId}")
    public ApiResponse<String> deleteFeed(@PathVariable Long feedId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        
        feedService.deleteFeed(feedId, userDetails.getUsername());
        
        return ApiResponse.onSuccess("deleted feed");
    }
}
