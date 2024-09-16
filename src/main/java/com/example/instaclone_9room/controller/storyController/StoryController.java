package com.example.instaclone_9room.controller.storyController;

import com.example.instaclone_9room.apiPayload.ApiResponse;
import com.example.instaclone_9room.controller.dto.storyDTO.StoryDTO;
import com.example.instaclone_9room.service.storyService.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryController {
    
    private final StoryService storyService;
    
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> postFeed(@RequestPart MultipartFile file,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        String dirName = "Story_Image";
        storyService.postStory(file, userDetails.getUsername(), dirName);
        return ApiResponse.onSuccess("saved story");
    }
    
    @DeleteMapping("/{storyId}")
    public ApiResponse<String> deleteFeed(@PathVariable Long storyId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        
        storyService.deleteStory(storyId, userDetails.getUsername());
        
        return ApiResponse.onSuccess("deleted story");
    }
    
    @GetMapping("/{storyId}")
    public ApiResponse<StoryDTO.StoryResponseDTO> getStory(@PathVariable Long storyId) {
        StoryDTO.StoryResponseDTO responseDTO = storyService.searchStory(storyId);
        
        return ApiResponse.onSuccess(responseDTO);
    }
    
    
    @GetMapping("/{id}/stories")
    public ApiResponse<List<StoryDTO.StoryResponseDTO>> getStoryById(@PathVariable Long id) {
        List<StoryDTO.StoryResponseDTO> responseDTOS = storyService.searchStoryById(id);
        
        return ApiResponse.onSuccess(responseDTOS);
    }
}
