package com.example.instaclone_9room.controller.feedController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.feedDTO.FeedDTO;
import com.example.instaclone_9room.service.feedService.FeedPinnedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedPinned")
public class FeedPinnedController {
    
    private final FeedPinnedService feedPinnedService;
    
    @PostMapping("/{feedId}")
    public ApiResponse<String> pinFeed(@PathVariable Long feedId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        
        feedPinnedService.savePinnedFeed(userDetails.getUsername(), feedId);
        
        return ApiResponse.onSuccess("pinned feed");
    }
    
    @GetMapping("/")
    public ApiResponse<List<FeedDTO.FeedResponseDTO>> getPinnedFeeds(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        List<FeedDTO.FeedResponseDTO> feedResponseDTOList = feedPinnedService.getPinnedFeed(userDetails.getUsername());
        
        return ApiResponse.onSuccess(feedResponseDTOList);
    }
}
